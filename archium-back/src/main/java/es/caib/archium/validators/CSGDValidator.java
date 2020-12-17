package es.caib.archium.validators;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.csgd.apirest.constantes.Estado;
import es.caib.archium.csgd.apirest.constantes.TipoAcceso;
import es.caib.archium.csgd.apirest.constantes.TipoDictamen;
import es.caib.archium.csgd.apirest.constantes.ValorSecundario;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.persistence.model.*;
import es.caib.archium.utils.CalculoUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

@ApplicationScoped
public class CSGDValidator {

    Logger log = LoggerFactory.getLogger(CSGDValidator.class);

    @Inject
    private CalculoUtils calculoUtils;

    /**
     * Valida que toda la informacion requerida por GDIB este informada
     *
     * @param cuadrodb
     */
    public void validarCuadro(Quadreclassificacio cuadrodb) throws I18NException {
        log.debug("Validamos los datos del cuadro");

        if(cuadrodb.isObsolete()){
            log.error("Error de validacion del cuadro. El cuadro esta obsoleto y no se puede sincronizar");
            throw new I18NException("validaciones.cuadro.obsoleto", this.getClass().getSimpleName());
        }

        if(StringUtils.trimToNull(cuadrodb.getEstat()) == null
                || Estado.getEstado(cuadrodb.getEstat())==null){
            log.error("Error de validacion del cuadro de clasificacion. Alguno de los campos obligatorios no esta informado");
            throw new I18NException("validaciones.cuadro", this.getClass().getSimpleName());
        }else{
            log.info("Cuadro de clasificacion validado");
        }
    }

    /**
     * Valida que toda la informacion requerida por GDIB este informada
     *
     * @param funciondb
     */
    public void validarFuncion(Funcio funciondb) throws I18NException {
        log.debug("Validamos los datos de la funcion");

        if(funciondb.isObsolete()){
            log.error("Error de validacion de la funcion ["+funciondb.getCodi()+"]. La funcion esta obsoleta y no se puede sincronizar");
            throw new I18NException("validaciones.funcion.obsoleta", this.getClass().getSimpleName());
        }

        if(funciondb.getAchQuadreclassificacio().isObsolete()){
            log.error("La funcion ["+funciondb.getCodi()+"] pertenece a un cuadro obsoleto y no se puede sincronizar");
            throw new I18NException("validaciones.funcion.padre.obsoleto", this.getClass().getSimpleName());
        }

        if(StringUtils.trimToNull(funciondb.getCodi()) == null
                || StringUtils.trimToNull(funciondb.getEstat()) == null
                || Estado.getEstado(funciondb.getEstat())==null){
            log.error("Error de validacion de la funcion ["+funciondb.getCodi()+"]. Alguno de los campos obligatorios no esta informado");
            throw new I18NException("validaciones.funcion", this.getClass().getSimpleName());
        }else{
            log.info("Funcion validada");
        }
    }

    /**
     * Comprueba que todos los padres de la serie esten sincronizados, si no lo estan, devuelve una excepcion
     *
     * @param serie
     * @throws I18NException
     */
    public void checkSynchronizedParents(Seriedocumental serie) throws I18NException {
        if (!this.isParentsSynchronized(serie.getAchFuncio())) {
            log.error("La serie [Id = " + serie.getId() + ", Cod = " + serie.getCodi() + "] no puede sincronizarse hasta que " +
                    "todos sus padres esten sincronizados");
            throw new I18NException("validaciones.serie.padre.no.sincronizado", this.getClass().getSimpleName());
        }
        log.info("Todos los padres de la serie se encuentran sincronizados, se procede con la sincronizacion");
    }

    /**
     * Comprueba que los padres de la funcion esten sincronizados, en caso afirmativo retorna su padre mas cercano, si no,
     * devuelve una excepción
     *
     * @param funciondb
     * @return
     * @throws I18NException
     */
    public String checkSynchronizedParents(Funcio funciondb) throws I18NException {
        String parent;
        if(funciondb.getAchFuncio()!=null) {
            parent = funciondb.getAchFuncio().getNodeId();
            if (!this.isParentsSynchronized(funciondb.getAchFuncio())) {
                log.error("La funcion [Id = "+ funciondb.getId()+", Cod = "+ funciondb.getCodi()+"] no puede sincronizarse" +
                        " hasta que todos sus padres esten sincronizados");
                throw new I18NException("validaciones.funcio.padre.no.sincronizado", this.getClass().getSimpleName());
            }
        }else{
            parent = funciondb.getAchQuadreclassificacio().getNodeId();
            if(!funciondb.getAchQuadreclassificacio().isSynchronized()){
                log.error("El cuadro de clasificacion [Id = " + funciondb.getAchQuadreclassificacio().getId() + ", " +
                        "Cod = " + funciondb.getAchQuadreclassificacio().getNomcas() +"] debe sincronizarse para poder" +
                        "sincronizar la funcion [Id = "+ funciondb.getId()+", Cod = "+ funciondb.getCodi()+"]");
                throw new I18NException("validaciones.funcio.padre.no.sincronizado", this.getClass().getSimpleName());
            }
        }
        return parent;
    }

    /**
     * Comprueba que todos los padres hasta el cuadro de clasificacion esten sincronizados
     *
     * @param function
     * @return
     */
    private boolean isParentsSynchronized(Funcio function) {
        if(!isSynchronized(function)){
            log.error("La funcion ["+function.getId()+"] no esta sincronizada");
            return false;
        }else{
            log.debug("La funcion ["+function.getId()+"] esta sincronizada");
        }
        //Comprobamos si tiene funcion padre
        if(function.getAchFuncio()!=null){
            log.debug("Comprobamos si la funcion padre esta sincronizada...");
            return isParentsSynchronized(function.getAchFuncio());
        }else{
            if(isSynchronized(function.getAchQuadreclassificacio())){
                log.debug("El cuadro ["+function.getAchQuadreclassificacio().getNomcas()+"] esta sincronizado");
                return true;
            }else{
                log.error("El cuadro ["+function.getAchQuadreclassificacio().getNomcas()+"] no esta sincronizado");
                return false;
            }
        }
    }

    /**
     * True si el elemento esta sincronizado y tiene el nodeId informado, falase en caso contrario
     *
     * @param function
     * @return
     */
    private boolean isSynchronized(Funcio function){
        return  function.isSynchronized() && StringUtils.trimToNull(function.getNodeId()) != null;
    }
    /**
     * True si el elemento esta sincronizado y tiene el nodeId informado, falase en caso contrario
     *
     * @param cuadro
     * @return
     */
    private boolean isSynchronized(Quadreclassificacio cuadro){
        return  cuadro.isSynchronized() && StringUtils.trimToNull(cuadro.getNodeId()) != null;
    }

    /**
     * Valida que toda la informacion requerida por GDIB este informada (marcada como mandatory = true en el
     * modelo de Alfresco)
     *
     * @param serie
     */
    public void validarSincronizarSerieDatosMinimos(Seriedocumental serie) throws I18NException {
        log.info("Se valida los datos minimos de la serie");
        if (serie.getAchDictamens() == null || serie.getAchDictamens().isEmpty()) {
            log.error("Error de validación de la serie [" + serie.getId() + "]. " +
                    "La serie ha de tener un dictamen en estado 'Vigent' o 'Esborrany'");
            throw new I18NException("validaciones.serie.dictamen", this.getClass().getSimpleName());
        }
        if (StringUtils.trimToNull(serie.getCodi()) == null) {
            log.error("Error de validación de la serie [" + serie.getId() + "]. " +
                    "El código de la serie no puede ser nulo");
            throw new I18NException("validaciones.serie.codi", this.getClass().getSimpleName());
        }
        if (StringUtils.trimToNull(serie.getNomcas()) == null) {
            log.error("Error de validación de la serie [" + serie.getId() + "]. " +
                    "El nombre en castellano de la serie no puede ser nulo");
            throw new I18NException("validaciones.serie.nomcas", this.getClass().getSimpleName());
        }
        if (serie.getAchValoracios() == null || serie.getAchValoracios().isEmpty()) {
            log.error("Error de validación de la serie [" + serie.getId() + "]. " +
                    "Las valoraciones de la serie no pueden estar vacías");
            throw new I18NException("validaciones.serie.valoracio", this.getClass().getSimpleName());
        }
        if (serie.getAchFuncio() == null) {
            log.error("Error de validación de la serie [" + serie.getId() + "]. " +
                    "La serie ha de tener informada su funcion padre");
            throw new I18NException("validaciones.serie.funcion", this.getClass().getSimpleName());
        }
        log.info("Serie validada");

    }

    /**
     * Valida que toda la informacion requerida por GDIB este informada
     *
     * 1.	En el dictamen debe estar especificado el nivel de sensibilidad de datos de carácter personal (LOPD).
     * 2.	En el dictamen debe estar especificado el tipo de acceso a la serie.
     * 3.	En el dictamen debe estar especificado el nivel de confidencialidad ENS.
     * 4.	En el dictamen debe estar especificado el tipo de dictamen.
     * 5.	En el dictamen debe estar especificado si la serie es esencial o no.
     *
     * @param dictamen
     */
    public void validarDictamenSicronizarSerieDatosMinimos(Dictamen dictamen) throws I18NException {
        log.info("Se valida los datos minimos del dictamen activo de la serie");
        // 1.
        if (dictamen.getAchLopd() == null || StringUtils.trimToNull(dictamen.getAchLopd().getNomcas()) == null) {
            log.error("Error de validación del dictamen [" + dictamen.getId() + "]. " +
                    "El dictamen ha de tener informado un nivel de lopd");
            throw new I18NException("validaciones.dictamen.lopd", this.getClass().getSimpleName());
        }
        // 2.
        if (dictamen.getAchTipusacce() == null || StringUtils.trimToNull(dictamen.getAchTipusacce().getNomcas()) == null) {
            log.error("Error de validación del dictamen [" + dictamen.getId() + "]. " +
                    "El dictamen ha de tener informado un tipo de acceso");
            throw new I18NException("validaciones.dictamen.tipusacce", this.getClass().getSimpleName());
        }
        // 3.
        if (dictamen.getAchEn() == null || StringUtils.trimToNull(dictamen.getAchEn().getNomcas()) == null) {
            log.error("Error de validación del dictamen [" + dictamen.getId() + "]. " +
                    "El dictamen ha de tener informado la confidencialidad (ens)");
            throw new I18NException("validaciones.dictamen.ens", this.getClass().getSimpleName());
        }
        // 4.
        if (dictamen.getAchTipusdictamen() == null || StringUtils.trimToNull(dictamen.getAchTipusdictamen().getCodi()) == null) {
            log.error("Error de validación del dictamen [" + dictamen.getId() + "]. " +
                    "El dictamen ha de tener informado un tipo");
            throw new I18NException("validaciones.dictamen.tipo", this.getClass().getSimpleName());
        }
        // 5.
        if (dictamen.getSerieessencial() == null) {
            log.error("Error de validación del dictamen [" + dictamen.getId() + "]. " +
                    "El dictamen ha de tener informado el campo serie esencial");
            throw new I18NException("validaciones.dictamen.seriesencial", this.getClass().getSimpleName());
        }
        log.info("Dictamen validado");

    }


    /**
     * Validaciones para un dictamen en estado vigente:
     * <p>
     * 1.	El dictamen debe tener código asignado.
     * 2.	El tipo de la serie es obligatorio (serie.tipusserie_id obligatorio).
     * 3.	La serie debe tener una descripción en catalán (serie.descripcio).
     * 4.	La serie debe tener al menos una normativa de marco legal asignada, por tanto, al menos debe existir un
     *      registro en la tabla ACH_normativa_seriedocumental para la serie en cuestión.
     * 5.	El dictamen ha de tener un tipo de dictamen diferente a «PD» (no puede ser pendiente de dictamen).
     * 6.	De cada procedimiento para los que la serie guarde sus expedientes (relación entre ACH_procediment i
     *      ACH_seriedocumental), asegurar que al menos se ha descrito un tipo documental con recapitulativo a «No».
     *      Es decir, si la serie se vincula con 2 procedimientos, cada uno de estos procedimientos debe tener al menos
     *      1 registro en la tabla ACH_tipusdocument_procediment con recapitulatiu a «No».
     *
     * @param dictamen
     */
    public void validarDictamenVigente(Dictamen dictamen, Seriedocumental serie) throws I18NException {
        log.info("Se valida los requisitos para que un dictamen este en estado activo");
        // 1.
        if (StringUtils.trimToNull(dictamen.getCodi()) == null) {
            log.error("Error de validación de la [serie,dictamen] = [" + serie.getId() + "," + dictamen.getId() + "]. " +
                    "El dictamen en estado activo no puede tener un código nulo");
            throw new I18NException("validaciones.serie.dictamen.vigente.codi", this.getClass().getSimpleName());
        }

        // 2.
        if (serie.getAchTipusserie() == null || serie.getAchTipusserie().getId() == null) {
            log.error("Error de validación de la [serie,dictamen] = [" + serie.getId() + "," + dictamen.getId() + "]. " +
                    "El tipo de la serie no puede ser nulo");
            throw new I18NException("validaciones.serie.tipo", this.getClass().getSimpleName());
        }

        // 3.
        if (StringUtils.trimToNull(serie.getDescripcio()) == null) {
            log.error("Error de validación de la [serie,dictamen] = [" + serie.getId() + "," + dictamen.getId() + "]. " +
                    "La descripción en catalán de la serie no puede ser nula");
            throw new I18NException("validaciones.serie.descripcio", this.getClass().getSimpleName());
        }

        // 4.
        if (serie.getAchNormativaSeriedocumentals() == null || serie.getAchNormativaSeriedocumentals().isEmpty()) {
            log.error("Error de validación de la [serie,dictamen] = [" + serie.getId() + "," + dictamen.getId() + "]. " +
                    "La serie debe tener al menos una normativa de marco legal asignada");
            throw new I18NException("validaciones.serie.normativa", this.getClass().getSimpleName());
        }

        // 5.
        if (TipoDictamen.PD.equals(TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi()))) {
            log.error("Error de validación de la [serie,dictamen] = [" + serie.getId() + "," + dictamen.getId() + "]. " +
                    "El dictamen en estado vigente no puede ser del tipo PD (Pendiente de Dictamen)");
            throw new I18NException("validaciones.serie.dictamen.tipo", this.getClass().getSimpleName());
        }

        // 6.
        if (!checkProcedimientosSerieDictamenVigente(serie.getAchProcediments())) {
            log.error("Error de validación de la [serie,dictamen] = [" + serie.getId() + "," + dictamen.getId() + "]. " +
                    "Para cada procedimiento de la serie ha de contar al menos un tipo documental con recapitulativo a 'no'");
            throw new I18NException("validaciones.serie.tipoDocumental", this.getClass().getSimpleName());
        }

        log.info("Dictamen validado");
    }

    /**
     * Cuando el dictamen esta vigente, ha de cumplir lo siguiente:
     * <p>
     * De cada procedimiento para los que la serie guarde sus expedientes (relación entre ACH_procediment i
     * ACH_seriedocumental), asegurar que al menos se ha descrito un tipo documental con recapitulativo a «No».
     * Es decir, si la serie se vincula con 2 procedimientos, cada uno de estos procedimientos debe tener al menos
     * 1 registro en la tabla ACH_tipusdocument_procediment con recapitulatiu a «No».
     *
     * @param achProcediments
     * @return
     */
    private boolean checkProcedimientosSerieDictamenVigente(List<Procediment> achProcediments) {
        if(achProcediments!=null && !achProcediments.isEmpty()) {
            for (Procediment p : achProcediments) {
                boolean recapitulativoNo = false;
                // Recorremos los tipos documentales en busca de uno con recapitulativo a no
                for (TipusdocumentProcediment td : p.getAchTipusdocumentProcediments()) {
                    if (BigDecimal.ZERO.equals(td.getRecapitulatiu())) {
                        // Se encuentra un recapitulativo a no, se continua con el siguiente procedimiento
                        recapitulativoNo = true;
                        break;
                    }
                }
                if (!recapitulativoNo) {
                    // Si acaba de revisar todos los tipos documentales del procedimiento, y el booleano sigue a falso,
                    // es que no tiene ningun recapitulativo a "NO" (False), por lo tanto no cumple la validacion
                    log.error("No se ha encontrado un tipo documental con recapitulativo a no para el procedimiento [" + p.getNom() + "] ");
                    return false;
                }
            }
        }
        return true;
    }

    /**
     * Se realizan una serie de validaciones de negocio para asegurar que la serie se puede sincronizar. Esto es:
     * 1.   Que cumplan los datos mínimos marcados como mandatory en el modelo de Alfresco
     * 2.   La funcion a la que pertenezca no puede estar obsoleta (con fi anterior a la actual)
     * 3.   Que tenga asociado un dictamen activo (estado vigente o esborrany)
     * 4.   Se validan datos minimos del dictamen y que si esta en estado vigent, debe cumplir una serie de validaciones
     *      extra (se informan en el propio metodo)
     * 5.	Si para una serie documental existen registros en la tabla ACH_limitacio_normativa_serie, el dictamen no debe
     *      tener tipo de acceso “Libre”.
     * 5.1. Por el contrario, si para una serie documental no existe registro alguno en la tabla
     *      ACH_limitacio_normativa_serie, el dictamen debe tener tipo de acceso “Libre”.
     * 6.	La serie debe tener, en la valoración más reciente y sin fecha de fin, alguna valoración primaria (algún tipo
     *      de valor con su plazo obligatorio). Es decir, la serie a sincronizar debe tener valores primarios (tipo valor)
     * 7.	La serie debe tener, en la valoración más reciente y sin fecha de fin, especificado el valor secundario de
     *      la serie.
     * 8.	Si valor_secundario de esta valoración = 'Sí' y tipo_dictamen no es 'CP' (Conservación permanente), error.
     *      Siempre que haya valor secundario, el tipo de dictamen ha de ser CP.
     * 9.	Sobre plazo de la acción dictaminada,
     * a)	Si tipo de dictamen = 'CP', no habría que indicar plazo ni accion dictaminada.
     * b)	Si tipo de dictamen = 'ET', entonces plazo accion dictaminada es obligatorio, pero la accion dic no.
     * c)	Si tipo de dictamen = 'PD', entonces plazo dictamen y accion dictaminada son opcionales.
     * d)	Si tipo de dictamen = 'EP':
     *      •	Han de existir al menos 2 registros en la tabla ACH_DICTAMEN_TIPUSDOCUMENTAL:
     *          Al menos 1 registro con conservable = 'Sí' y al menos 1 registro con conservable = 'No'.
     *      •	Los plazos de los tipos documentales (plazo concreto, ACH_DICTAMEN_TIPUSDOCUMENTAL.termini) pueden ser
     *          nulos si existe plazo en el dictamen (plazo general, ACH_DICTAMEN.termini). En ausencia de plazo concreto,
     *          a la hora de construir/calcular la acción dictaminada, se coge el plazo general. NVL(plazo concreto,
     *          plazo general). Se debe validar que o bien el plazo concreto o bien el plazo general está rellenado.
     *          Por tanto, podría darse el caso de un dictamen con plazo general y sin plazo concreto en los tipos
     *          documentales. Y podría darse el caso de un dictamen sin plazo general y entonces sí que todas las
     *          entradas en ACH_dictamen_tipusdocumental han de tener la columna termini rellenada.
     * 10.	Si el tipo de acceso es “Libre”, la condición de reutilización es obligatoria.
     *
     * @param serie
     */
    public void validarSincronizarSerie(Seriedocumental serie) throws I18NException {
        log.info("Se procede a realizar las validaciones de sincronizacion de la serie");

        // 1.
        validarSincronizarSerieDatosMinimos(serie);

        // 2.
        if(serie.getAchFuncio().isObsolete()){
            log.error("La serie [" + serie.getCodi() + "] pertenece a una funcion obsoleta y no se puede sincronizar");
            throw new I18NException("validaciones.serie.padre.obsoleta", this.getClass().getSimpleName());
        }

        // 3.
        Dictamen dictamen = this.calculoUtils.getDictamenActivo(serie);
        if(dictamen==null){
            log.error("La serie [" + serie.getCodi() + "] no cuenta con un dictamen en estado activo ('vigent'), por lo que no es posible sincronizarse");
            throw new I18NException("validaciones.serie.dictamen.novigente", this.getClass().getSimpleName());
        }else{
            // 4.

            //Validamos que los datos obligatorios del dictamen estén informados
            this.validarDictamenSicronizarSerieDatosMinimos(dictamen);

            // Si el dictamen esta en estado activo lleva unas validaciones extra
            if (Estado.VIGENT.getValue().equalsIgnoreCase(dictamen.getEstat())) {
                this.validarDictamenVigente(dictamen, serie);
            }
        }

        // 5. y 5.1
        List<String> codigoLimitacion = this.calculoUtils.extraerCodigoLimitacion(serie);
        if (codigoLimitacion != null && !codigoLimitacion.isEmpty() && TipoAcceso.LIBRE ==
                TipoAcceso.getTipoAcceso(dictamen.getAchTipusacce().getNomcas())) {
            log.error("La serie a sincronizar no cumple con las validaciones necesarias: La serie tiene registros de " +
                    "causa limitacion, por lo que no puede tener tipo acceso 'Libre'");
            throw new I18NException("validaciones.serie.codigoLimitacion", this.getClass().getSimpleName());
        }else if((codigoLimitacion == null || codigoLimitacion.isEmpty()) && TipoAcceso.LIBRE !=
                TipoAcceso.getTipoAcceso(dictamen.getAchTipusacce().getNomcas())){
            log.error("La serie a sincronizar no cumple con las validaciones necesarias: La serie NO tiene registros de " +
                    "causa limitacion, por lo que ha de tener tipo acceso 'Libre'");
            throw new I18NException("validaciones.serie.codigoLimitacion.sinCodigoLimitacion", this.getClass().getSimpleName());
        }
        // 6.
        Serie serieAux = new Serie();
        this.calculoUtils.extraerValoresYPlazo(serie,serieAux);
        if (serieAux.getTipoValor() == null || serieAux.getTipoValor().isEmpty()) {
            log.error("La serie a sincronizar no cumple con las validaciones necesarias: La serie debe tener en su " +
                    "valoracion mas reciente, y sin fecha fin, alguna valoracion primaria");
            throw new I18NException("validaciones.serie.tipoValor", this.getClass().getSimpleName());
        }
        // 7.
        if (serieAux.getValorSecundario() == null) {
            log.error("La serie a sincronizar no cumple con las validaciones necesarias: La serie debe tener en su " +
                    "valoracion mas reciente, y sin fecha fin, especificado el valor secundario");
            throw new I18NException("validaciones.serie.valorSecundario", this.getClass().getSimpleName());
        }
        // 8.
        if (ValorSecundario.SI == serieAux.getValorSecundario() && TipoDictamen.CP !=
                TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            log.error("La serie a sincronizar no cumple con las validaciones necesarias: Si el valor secundario de la " +
                    "serie es 'Sí', el tipo del dictamen ha de ser 'CP' (Conservación permanente)");
            throw new I18NException("validaciones.serie.valorSecundario.tipoDictamen", this.getClass().getSimpleName());
        }
        // 9.
        switch (TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            case CP:
                // No hace falta comprobar nada, para este valor es opcional
                break;
            case ET:
                if (StringUtils.trimToNull(dictamen.getTermini()) == null) {
                    log.error("La serie a sincronizar no cumple con las validaciones necesarias: Si el tipo dictamen es " +
                            "'ET', es obligatorio informar un plazo de accion dictaminada");
                    throw new I18NException("validaciones.serie.tipoDictamen.ET.plazoAccionDictaminada", this.getClass().getSimpleName());
                }
                break;
            case PD:
                // No hace falta comprobar nada, para este valor es opcional
                break;
            case EP:
                if (dictamen.getAchDictamenTipusdocumentals() == null || dictamen.getAchDictamenTipusdocumentals().isEmpty()) {
                    log.error("La serie a sincronizar no cumple con las validaciones necesarias: Si el tipo dictamen es " +
                            "'EP', es obligatorio que cuente con registros de tipos documentales");
                    throw new I18NException("validaciones.serie.tipoDictamen.EP.tipoDocumental", this.getClass().getSimpleName());
                }

                boolean conservableSi = false;
                boolean conservableNo = false;
                boolean isDictamenTerminoNull = StringUtils.trimToNull(dictamen.getTermini()) == null;
                for (var t : dictamen.getAchDictamenTipusdocumentals()) {
                    // Descartamos los tipos documentales con fecha fin
                    if(t.isObsolete()) {
                        // Si el dictamen no tiene informado el termino, es obligatorio que lo tengan los tipos documentales
                        if (isDictamenTerminoNull && StringUtils.isEmpty(t.getTermini())) {
                            log.error("La serie a sincronizar no cumple con las validaciones necesarias: Si el tipo dictamen es " +
                                    "'EP', es obligatorio informar un plazo para cada tipo documental del dictamen");
                            throw new I18NException("validaciones.serie.tipoDictamen.EP.tipoDocumental.termini", this.getClass().getSimpleName());
                        }
                        // Ha de existir al menos un tipo documental con conservable a si y otro a no
                        if (BigDecimal.ZERO.equals(t.getConservable())) {
                            conservableNo = true;
                        } else if (BigDecimal.ONE.equals(t.getConservable())) {
                            conservableSi = true;
                        }
                        if (conservableNo && conservableSi && !isDictamenTerminoNull) {
                            // Si ya encontramos uno de cada tipo, y el dictamen tiene informado plazo, no hace falta seguir
                            // en el bucle
                            break;
                        }
                    }
                }
                // Si no se encontro alguno de los dos tipos, error de validacion
                if(!conservableNo || !conservableSi){
                    log.error("La serie a sincronizar no cumple con las validaciones necesarias: Si el tipo dictamen es " +
                            "'EP', han de existir al menos dos registros en la tabla ACH_DICTAMEN_TIPUSDOCUMENTAL" +
                            " uno con conservable a si y otro a no");
                    throw new I18NException("validaciones.serie.tipoDictamen.EP.tipoDocumental.conservable", this.getClass().getSimpleName());
                }

                break;
        }
        // 10.
        if (TipoAcceso.LIBRE == TipoAcceso.getTipoAcceso(dictamen.getAchTipusacce().getNomcas())
                && StringUtils.trimToNull(dictamen.getCondicioreutilitzacio()) == null) {
            log.error("La serie a sincronizar no cumple con las validaciones necesarias: Si el tipo de acceso es 'Libre'," +
                    " la condicion de reutilizacion es obligatoria");
            throw new I18NException("validaciones.serie.tipoAcceso.libre.condicionReutilizacion", this.getClass().getSimpleName());
        }


        log.info("La serie cumple todas las validaciones para poder ser sincronizada en Alfresco");
    }
}

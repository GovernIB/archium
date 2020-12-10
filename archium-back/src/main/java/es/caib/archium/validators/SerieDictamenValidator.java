package es.caib.archium.validators;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.csgd.apirest.constantes.TipoAcceso;
import es.caib.archium.csgd.apirest.constantes.TipoDictamen;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Procediment;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.TipusdocumentProcediment;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.List;

@ApplicationScoped
public class SerieDictamenValidator {

    Logger log = LoggerFactory.getLogger(SerieDictamenValidator.class);

    /**
     * Valida que toda la informacion requerida por GDIB este informada
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
        if (StringUtils.trimToNull(serie.getAchFuncio().getNodeId()) == null) {
            log.error("Error de validación de la serie [" + serie.getId() + "]. " +
                    "La funcion padare de la serie ha de estar sincronizada");
            throw new I18NException("validaciones.serie.funcion.sincro", this.getClass().getSimpleName());
        }
        log.info("Serie validada");

    }

    /**
     * Valida que toda la informacion requerida por GDIB este informada
     *
     * @param dictamen
     */
    public void validarDictamenSicronizarSerieDatosMinimos(Dictamen dictamen) throws I18NException {
        log.info("Se valida los datos minimos del dictamen activo de la serie");
        if (StringUtils.trimToNull(dictamen.getAchLopd().getNomcas()) == null) {
            log.error("Error de validación del dictamen [" + dictamen.getId() + "]. " +
                    "El dictamen ha de tener informado un nivel de lopd");
            throw new I18NException("validaciones.dictamen.lopd", this.getClass().getSimpleName());
        }
        if (StringUtils.trimToNull(dictamen.getAchTipusacce().getNomcas()) == null) {
            log.error("Error de validación del dictamen [" + dictamen.getId() + "]. " +
                    "El dictamen ha de tener informado un tipo de acceso");
            throw new I18NException("validaciones.dictamen.tipusacce", this.getClass().getSimpleName());
        }
        if (StringUtils.trimToNull(dictamen.getAchEn().getNomcas()) == null) {
            log.error("Error de validación del dictamen [" + dictamen.getId() + "]. " +
                    "El dictamen ha de tener informado la confidencialidad (ens)");
            throw new I18NException("validaciones.dictamen.ens", this.getClass().getSimpleName());
        }
        if (StringUtils.trimToNull(dictamen.getAchTipusdictamen().getCodi()) == null) {
            log.error("Error de validación del dictamen [" + dictamen.getId() + "]. " +
                    "El dictamen ha de tener informado un tipo");
            throw new I18NException("validaciones.dictamen.tipo", this.getClass().getSimpleName());
        }
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
     * registro en la tabla ACH_normativa_seriedocumental para la serie en cuestión.
     * 5.	El dictamen ha de tener un tipo de dictamen diferente a «PD» (no puede ser pendiente de dictamen).
     * 6.	De cada procedimiento para los que la serie guarde sus expedientes (relación entre ACH_procediment i
     * ACH_seriedocumental), asegurar que al menos se ha descrito un tipo documental con recapitulativo a «No».
     * Es decir, si la serie se vincula con 2 procedimientos, cada uno de estos procedimientos debe tener al menos
     * 1 registro en la tabla ACH_tipusdocument_procediment con recapitulatiu a «No».
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
        return true;
    }

    /**
     * Se realizan una serie de validaciones de negocio para asegurar que la serie se puede sincronizar. Esto es:
     * <p>
     * 1.	Si para una serie documental existen registros en la tabla ACH_limitacio_normativa_serie, el dictamen no debe
     * tener tipo de acceso “Libre”.
     * 2.	La serie debe tener, en la valoración más reciente y sin fecha de fin, alguna valoración primaria (algún tipo
     * de valor con su plazo obligatorio). Es decir, la serie a sincronizar debe tener valores primarios (tipo valor)
     * 3.	La serie debe tener, en la valoración más reciente y sin fecha de fin, especificado el valor secundario de
     * la serie.
     * 4.	Si valor_secundario de esta valoración = 'Sí' y tipo_dictamen no es 'CP' (Conservación permanente), error.
     * Siempre que haya valor secundario, el tipo de dictamen ha de ser CP.
     * 5.	Sobre plazo de la acción dictaminada,
     * a)	Si tipo de dictamen = 'CP', no habría que indicar plazo.
     * b)	Si tipo de dictamen = 'ET', entonces plazo accion dictaminada es obligatorio.
     * c)	Si tipo de dictamen = 'PD', entonces plazo dictamen es opcional.
     * d)	Si tipo de dictamen = 'EP', para cada tipo documental de la tabla ACH_tipusdocumentaldictamen,
     * debe disponer de un plazo (o específico del tipo o general del dictamen). puede ser opcional siempre que se
     * haya especificado un plazo concreto por tipo documental
     * 6.	Si el tipo de acceso es “Libre”, la condición de reutilización es obligatoria.
     * 7.	Sobre la acción dictaminada,
     * a)	Si tipo de dictamen = 'CP', entonces accion dictaminada puede quedar en blanco.
     * b)	Si tipo de dictamen = 'ET', entonces accion dictaminada puede quedar en blanco
     * pero plazo accion dictaminada es obligatorio (para indicar cuándo se eliminará el expediente)
     * c)	Si tipo de dictamen = 'PD', entonces accion dictaminada puede quedar en blanco.
     * d)	Si tipo de dictamen = 'EP', entonces accion dictaminada puede quedar en blanco,
     * pero deben existir registros en la tabla que relaciona tipos documentales con dictamen
     * (ACH_dictamen_tipusdocumental) para distinguir los documentos que se conservan de los que no. En este caso,
     * el campo plazo de la accion dictaminada (del dictamen) puede estar vacío ya que, para cada tipo documental
     * se puede especificar un plazo concreto.
     *
     * @param serie
     * @param dictamen se utiliza este parametro únicamente para las validaciones 5 y 7
     */
    public void validarSincronizarSerie(Serie serie, Dictamen dictamen) throws I18NException {
        log.info("Se procede a realizar las validaciones de sincronizacion de la serie");

        // 1.
        if (serie.getCodigoLimitacion() != null && serie.getCodigoLimitacion().isEmpty() && TipoAcceso.LIBRE == serie.getTipoAcceso()) {
            log.error("La serie a sincronizar no cumple con las validaciones necesarias: La serie tiene registros de " +
                    "causa limitacion, por lo que no puede tener tipo acceso 'Libre'");
            throw new I18NException("validaciones.serie.codigoLimitacion", this.getClass().getSimpleName());
        }
        // 2.
        if (serie.getTipoValor() == null || serie.getTipoValor().isEmpty()) {
            log.error("La serie a sincronizar no cumple con las validaciones necesarias: La serie debe tener en su " +
                    "valoracion mas reciente, y sin fecha fin, alguna valoracion primaria");
            throw new I18NException("validaciones.serie.tipoValor", this.getClass().getSimpleName());
        }
        // 3.
        if (StringUtils.trimToNull(serie.getValorSecundario()) == null) {
            log.error("La serie a sincronizar no cumple con las validaciones necesarias: La serie debe tener en su " +
                    "valoracion mas reciente, y sin fecha fin, especificado el valor secundario");
            throw new I18NException("validaciones.serie.valorSecundario", this.getClass().getSimpleName());
        }
        // 4.
        if (Constants.ArchiumConstants.VALOR_SECUNDARIO_SI.equals(serie.getValorSecundario()) && TipoDictamen.CP != serie.getTipoDictamen()) {
            log.error("La serie a sincronizar no cumple con las validaciones necesarias: Si el valor secundario de la " +
                    "serie es 'Sí', el tipo del dictamen ha de ser 'CP' (Conservación permanente)");
            throw new I18NException("validaciones.serie.valorSecundario.tipoDictamen", this.getClass().getSimpleName());
        }
        // 5. y 7
        switch (serie.getTipoDictamen()) {
            case CP:
                // No hace falta comprobar nada, para este valor es opcional
                break;
            case ET:
                if (serie.getPlazoAccionDictaminada() == null) {
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
                for (var t : dictamen.getAchDictamenTipusdocumentals()) {
                    if (StringUtils.isEmpty(t.getTermini())) {
                        log.error("La serie a sincronizar no cumple con las validaciones necesarias: Si el tipo dictamen es " +
                                "'EP', es obligatorio informar un plazo para cada tipo documental del dictamen");
                        throw new I18NException("validaciones.serie.tipoDictamen.EP.tipoDocumental.termini", this.getClass().getSimpleName());
                    }
                }
                break;
        }
        // 6.
        if (TipoAcceso.LIBRE == serie.getTipoAcceso() && StringUtils.trimToNull(serie.getCondicionReutilizacion()) == null) {
            log.error("La serie a sincronizar no cumple con las validaciones necesarias: Si el tipo de acceso es 'Libre'," +
                    " la condicion de reutilizacion es obligatoria");
            throw new I18NException("validaciones.serie.tipoAcceso.libre.condicionReutilizacion", this.getClass().getSimpleName());
        }
        // 7. junto al 5


        log.info("La serie cumple todas las validaciones para poder ser sincronizada en Alfresco");
    }
}

package es.caib.archium.utils;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.csgd.apirest.constantes.TipoDictamen;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.DictamenTipusdocumental;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * Clase que calcula los valores de algunos campos de la sincronización de la serie/dictamen cuando no estan informados
 *
 */
@ApplicationScoped
public class CalculoUtils {

    Logger log = LoggerFactory.getLogger(CalculoUtils.class);

    private static String PLANTILLA_TIPO_ET = "TIPO_NOM al/los TERMINI de la finalización del expediente";
    private static String PLANTILLA_TIPO_EP = "TIPO_NOM \nConservacion: \nCONS_LISTEliminacion:\nELIM_LIST";
    private static String PLANTILLA_TIPO_EP_TIPO_DOCUMENTAL = "- TIPO_NOM (TERMINI) \n";
    private static final String SUSTITUIR_TIPO_NOM = "TIPO_NOM";
    private static final String SUSTITUIR_TERMINI = "TERMINI";
    private static final String SUSTITUIR_CONSERVACION_LIST = "CONS_LIST";
    private static final String SUSTITUIR_ELIMINACION_LIST = "ELIM_LIST";

    private static final String YEAR = "A";
    private static final String MES = "M";
    private static final String DIA = "D";
    private static final String HORA = "H";

    /**
     * a)	Si tipus dictamen = 'CP', la acción dictaminada "calculada" (metadato) sería "Conservación permanente"
     * (tipusdictamen.nomcas)
     * b)	Si tipus dictamen = 'ET', la acción dictaminada estará vacía en Archium, pero es obligatorio disponer de
     * termini en el dictamen. La acción dictaminada "calculada" (metadato) sería: <tipusdictamen.nomcas> «a los »
     * <dictamen.termini>  « de la finalización del expediente». Ejemplo: «Eliminación total a los 4 años de la
     * finalización del expediente».
     * <p>
     * c)	Si tipus dictamen = 'EP', la acción dictaminada puede estar vacía en Archium, pero han de existir datos en
     * la tabla que relaciona un dictamen con los tipos documentales para discriminar cuáles se conservan y cuáles
     * no. La acción dictaminada "calculada" (metadato) sería:
     * <tipusdictamen.nomcas>
     * «Conservación:»
     * - <tipo documental> (<plazo tipodocumental o, en su ausencia, plazo dictamen...pero uno de ellos debe estar especificado>)
     * - <tipo documental> (<plazo tipodocumental o, en su ausencia, plazo dictamen...pero uno de ellos debe estar especificado>)
     * ...
     * «Eliminación:»
     * - <tipo documental> (<plazo tipodocumental o, en su ausencia, plazo dictamen...pero uno de ellos debe estar especificado>)
     * ...
     * d)	Si tipus dictamen = 'PD', la acción dictaminada puede estar vacía (metadato sin especificar), o bien, poner
     * el nombre del tipo de dictamen “Pendiente de dictamen”
     *
     * @param dictamen
     * @return
     */
    public String calcularAccionDictaminada(Dictamen dictamen) {
        log.info("Se calcula el valor del campo accionDictaminada");
        if (TipoDictamen.CP == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            return dictamen.getAchTipusdictamen().getNomcas();
        } else if (TipoDictamen.ET == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            String plazo = getPlazoComoTexto(dictamen.getTermini());
            return PLANTILLA_TIPO_ET.replace(SUSTITUIR_TIPO_NOM, dictamen.getAchTipusdictamen().getNomcas()).replace(SUSTITUIR_TERMINI, plazo);
        } else if (TipoDictamen.EP == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            if (dictamen.getAchDictamenTipusdocumentals() != null && !dictamen.getAchDictamenTipusdocumentals().isEmpty()) {
                List<String> conservacion = new ArrayList<>();
                List<String> eliminacion = new ArrayList<>();
                for (DictamenTipusdocumental d : dictamen.getAchDictamenTipusdocumentals()) {
                    // El plazo se saca del tipo documental, si no esta informado, lo estara el termino del dictamen
                    // Ya que es obligatorio que uno de los dos este informado para pasar las validaciones
                    String plazo = null;
                    if(StringUtils.trimToNull(d.getTermini())!=null){
                        plazo = getPlazoComoTexto(d.getTermini());
                    }else{
                        plazo = getPlazoComoTexto(dictamen.getTermini());
                    }
                    // Si conservable = false, es eliminacion, si = true es conservacion
                    if(BigDecimal.ZERO.equals(d.getConservable())){
                        eliminacion.add(PLANTILLA_TIPO_EP_TIPO_DOCUMENTAL.replace(SUSTITUIR_TIPO_NOM,d.getAchTipusdocumental().getNom()).replace(SUSTITUIR_TERMINI,plazo));
                    }else{
                        conservacion.add(PLANTILLA_TIPO_EP_TIPO_DOCUMENTAL.replace(SUSTITUIR_TIPO_NOM,d.getAchTipusdocumental().getNom()).replace(SUSTITUIR_TERMINI,plazo));
                    }
                }
                return PLANTILLA_TIPO_EP.replace(SUSTITUIR_TIPO_NOM,dictamen.getAchTipusdictamen().getNomcas())
                        .replace(SUSTITUIR_CONSERVACION_LIST,listToString(conservacion))
                        .replace(SUSTITUIR_ELIMINACION_LIST,listToString(eliminacion));
            }


        } else if (TipoDictamen.PD == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            return dictamen.getAchTipusdictamen().getNomcas();
        }

        return null;
    }

    /**
     * Para que se devuelva la lista creada tal cual en vez de tener el formato toString (separando elementos por ',' y
     * la lista completa dentro de unos '[]'
     *
     * @param lista
     * @return
     */
    private String listToString(List<String> lista) {
        if(lista==null || lista.isEmpty()){
            return "";
        }else{
            String resultado = "";
            for(String entry : lista){
                resultado+=entry;
            }
            return resultado;
        }
    }

    /**
     * El termino viene de la manera 4H, 2A, 6M... etc, por lo que sustituimos la ultima letra por la cadena
     * adecuada en funcion el valor (4 hora(s), 2 año(s), 6 mes(es)... etc
     *
     * @param termino
     * @return
     */
    private String getPlazoComoTexto(String termino) {
        if(StringUtils.trimToNull(termino)==null){
            return null;
        }
        //
        String substring = termino.substring(termino.length() - 1, termino.length());
        if (YEAR.equalsIgnoreCase(substring)) {
           return termino.replace(YEAR, " año(s)");
        } else if (MES.equalsIgnoreCase(substring)) {
            return termino.replace(MES, " mes(es)");
        } else if (DIA.equalsIgnoreCase(substring)) {
            return termino.replace(DIA, " día(s)");
        } else if (HORA.equalsIgnoreCase(substring)) {
            return termino.replace(HORA, " hora(s)");
        }
        return null;
    }

    /**
     * • 	Si es un dictamen de tipo «pendent de dictamen», puede o no haber plazo.
     * •	Si es un dictamen de tipo «conservación permanente», no debería haber plazo.
     * •	Si es un dictamen de eliminación parcial, podemos tener diferentes plazos en juego.
     *      Cuál de todos ellos poner? La propuesta sería coger de Archium el mayor de todos los plazos entre el plazo
     *      del dictamen y los plazos específicos de los tipos documentales.
     *
     * @param dictamen
     * @return
     */
    public String calcularPlazoAccionDictaminada(Dictamen dictamen) throws I18NException {
        if (TipoDictamen.PD == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            return null;
        } else if (TipoDictamen.CP == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            return null;
        } else if (TipoDictamen.ET == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            log.error("Error de validacion del dictamen de la serie. Es obligatorio informar la accion dictaminada para " +
                    "el tipo ET. Id dictamen, codigo = ["+dictamen.getId()+","+dictamen.getCodi()+"]");
            throw new I18NException("nuevodictamen.validate.tipo.et.accionDictaminada", this.getClass().getSimpleName());
        } else if (TipoDictamen.EP == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            return getPlazoMayor(dictamen.getAchDictamenTipusdocumentals());
        }

        return null;
    }

    /**
     * El String de plazo lleva junto la unidad, hace falta descomponerlo para saber cuál es el mayor
     * Obtiene el plazo mayor de todos los tipos documentales
     *
     * @param tiposDoc
     * @return
     */
    private String getPlazoMayor(List<DictamenTipusdocumental> tiposDoc) {
        int valorMax = -1;
        UnidadPlazo unidadMax = null;
        for(DictamenTipusdocumental t : tiposDoc) {
                int valor = Integer.valueOf(t.getTermini().substring(0, t.getTermini().length() - 1));
                UnidadPlazo unidad = UnidadPlazo.getUnidad(t.getTermini().substring(t.getTermini().length() - 1, t.getTermini().length()));
                if (valorMax == -1) {
                    unidadMax = unidad;
                    valorMax = valor;
                } else if (valorMax < valor && !unidadMax.isGreaterThan(unidad)) {
                    unidadMax = unidad;
                    valorMax = valor;
                }
        }
        return valorMax + unidadMax.toString();
    }


}

package es.caib.archium.utils;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.csgd.apirest.constantes.TipoDictamen;
import es.caib.archium.csgd.apirest.constantes.TipoValor;
import es.caib.archium.csgd.apirest.constantes.UnidadPlazo;
import es.caib.archium.csgd.apirest.constantes.ValorSecundario;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.persistence.model.*;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import java.math.BigDecimal;
import java.util.*;

/**
 * Clase que calcula los valores de algunos campos de la sincronización de la serie/dictamen cuando no estan informados
 */
@ApplicationScoped
public class CalculoUtils {

    Logger log = LoggerFactory.getLogger(CalculoUtils.class);

    private static String PLANTILLA_TIPO_ET = "TIPO_NOM al/los TERMINI de la finalización del expediente";
    private static String PLANTILLA_TIPO_EP = "TIPO_NOM \nConservacion: \nCONS_LISTEliminacion:\nELIM_LIST\n";
    private static String PLANTILLA_TIPO_EP_TIPO_DOCUMENTAL = "- TIPO_NOM (TERMINI) \n";
    private static String PLANTILLA_TIPO_EP_TIPO_DOCUMENTAL_SIN_TERMINI = "- TIPO_NOM \n";
    private static final String SUSTITUIR_TIPO_NOM = "TIPO_NOM";
    private static final String SUSTITUIR_TERMINI = "TERMINI";
    private static final String SUSTITUIR_CONSERVACION_LIST = "CONS_LIST";
    private static final String SUSTITUIR_ELIMINACION_LIST = "ELIM_LIST";

    /**
     * Siempre tendra preferencia la accion dictaminada si esta informada, en caso contrario en funcion del tipo de
     * dictamen, se calculara
     *
     * @param serie
     * @param dictamen
     * @return
     */
    public String rellenarOCalcularAccionDictaminada(Seriedocumental serie, Dictamen dictamen){
        if (StringUtils.trimToNull(dictamen.getAcciodictaminada()) != null) {
            return dictamen.getAcciodictaminada();
        } else {
            // Si no esta informada la calculamos
            return this.calcularAccionDictaminada(dictamen);
        }
    }

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
                    // Se descartan los tipos documentales con fecha fin
                    if(!d.isObsolete()) {
                        // El plazo se saca del tipo documental, si no esta informado, se deja vaci
                        String plazo = null;
                        if (StringUtils.trimToNull(d.getTermini()) != null) {
                            plazo = getPlazoComoTexto(d.getTermini());
                        } else {
                            plazo = getPlazoComoTexto(dictamen.getTermini());
                        }
                        // Si conservable = false, es eliminacion, si = true es conservacion
                        if (BigDecimal.ZERO.equals(d.getConservable())) {
                            if (StringUtils.trimToNull(plazo) != null) {
                                eliminacion.add(PLANTILLA_TIPO_EP_TIPO_DOCUMENTAL.replace(SUSTITUIR_TIPO_NOM, d.getAchTipusdocumental().getNom()).replace(SUSTITUIR_TERMINI, plazo));
                            } else {
                                eliminacion.add(PLANTILLA_TIPO_EP_TIPO_DOCUMENTAL_SIN_TERMINI.replace(SUSTITUIR_TIPO_NOM, d.getAchTipusdocumental().getNom()));
                            }
                        } else {
                            if (StringUtils.trimToNull(plazo) != null) {
                                conservacion.add(PLANTILLA_TIPO_EP_TIPO_DOCUMENTAL.replace(SUSTITUIR_TIPO_NOM, d.getAchTipusdocumental().getNom()).replace(SUSTITUIR_TERMINI, plazo));
                            } else {
                                conservacion.add(PLANTILLA_TIPO_EP_TIPO_DOCUMENTAL_SIN_TERMINI.replace(SUSTITUIR_TIPO_NOM, d.getAchTipusdocumental().getNom()));
                            }
                        }
                    }
                }
                return PLANTILLA_TIPO_EP.replace(SUSTITUIR_TIPO_NOM, dictamen.getAchTipusdictamen().getNomcas())
                        .replace(SUSTITUIR_CONSERVACION_LIST, listToString(conservacion))
                        .replace(SUSTITUIR_ELIMINACION_LIST, listToString(eliminacion));
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
        if (lista == null || lista.isEmpty()) {
            return "";
        } else {
            String resultado = "";
            for (String entry : lista) {
                resultado += entry;
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
        if (StringUtils.trimToNull(termino) == null) {
            return null;
        }

        UnidadPlazo unidad = extraerUnidadPlazo(termino);
        if (UnidadPlazo.A == unidad) {
            return termino.replace(UnidadPlazo.A.toString(), " año(s)");
        } else if (UnidadPlazo.M == unidad) {
            return termino.replace(UnidadPlazo.M.toString(), " mes(es)");
        } else if (UnidadPlazo.DN == unidad) {
            // Para el caso en el que el dia unicamente sea "D" en vez de "DN"
            if(Character.isDigit(termino.toCharArray()[termino.length()-2])){
                return termino.replace("D", " día(s)");
            }else {
                return termino.replace(UnidadPlazo.DN.toString(), " día(s)");
            }
        } else if (UnidadPlazo.H == unidad) {
            return termino.replace(UnidadPlazo.H.toString(), " hora(s)");
        } else if (UnidadPlazo.DH == unidad) {
            return termino.replace(UnidadPlazo.DH.toString(), " día(s) habil(es)");
        }
        return null;
    }

    /**
     * - Si el dictamen es 'PD' (pendiente de dictamen), coger el atributo termini de la tabla dictamen
     * (puede ser NULL o tener dato) - Es decir, no hace falta calcularlo.
     * - Si el dictamen es 'CP' (conservación permanente), coger el atributo termini de la tabla dictamen
     * (puede ser NULL o tener dato) - Es decir, no hace falta calcularlo.
     * - Si el dictamen es 'ET', coger el atributo termini de la tabla dictamen (es obligatorio tener)
     * - Es decir, devolver un error si el termino del dictamen es nulo.
     * - Si el dictamen es 'EP', puede estar el plazo general del dictamen, así como plazos específicos por tipo documental
     * (columna termini de la tabla ACH_dictamen_tipusdocumental). - Para calcularlo, la propuesta es coger de Archium
     * el mayor de todos los plazos. MAX(dictamen_tipusdoc.termini de todos los tipos documentales).
     *
     * @param dictamen
     * @return
     */
    public String calcularPlazoAccionDictaminada(Dictamen dictamen) throws I18NException {
        if (TipoDictamen.PD == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            return dictamen.getTermini();
        } else if (TipoDictamen.CP == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            return dictamen.getTermini();
        } else if (TipoDictamen.ET == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            if(StringUtils.trimToNull(dictamen.getTermini()) == null) {
                log.error("Error de validacion del dictamen de la serie. Es obligatorio informar el plazo de la accion dictaminada para " +
                        "el tipo ET. Id dictamen, codigo = [" + dictamen.getId() + "," + dictamen.getCodi() + "]");
                throw new I18NException("nuevodictamen.validate.tipo.et.accionDictaminada", this.getClass().getSimpleName());
            }else{
                return dictamen.getTermini();
            }
        } else if (TipoDictamen.EP == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            return getPlazoMayor(dictamen.getAchDictamenTipusdocumentals(), dictamen.getTermini());
        }

        return null;
    }

    /**
     * El String de plazo lleva junto la unidad, hace falta descomponerlo para saber cuál es el mayor
     * Obtiene el plazo mayor de todos los tipos documentales
     *
     * @param tiposDoc
     * @param termini
     * @return
     */
    private String getPlazoMayor(List<DictamenTipusdocumental> tiposDoc, String termini) {
        Integer valorMax = extraerValorPlazo(termini);
        if (valorMax == null) {
            valorMax = -1;
        }
        UnidadPlazo unidadMax = extraerUnidadPlazo(termini);
        for (DictamenTipusdocumental t : tiposDoc) {
            // Descartamos los tipos documentales con fecha fin
            if(!t.isObsolete()) {
                Integer valor = extraerValorPlazo(t.getTermini());
                UnidadPlazo unidad = extraerUnidadPlazo(t.getTermini());
                if (valorMax == -1) {
                    unidadMax = unidad;
                    valorMax = valor;
                } else if ((valor != null && unidad != null)
                        && valorMax < valor && !unidadMax.isGreaterThan(unidad)) {
                    unidadMax = unidad;
                    valorMax = valor;
                }
            }
        }
        return valorMax + unidadMax.toString();
    }

    /**
     * Extrae la unidad del plazo, la cual sera el ultimo caracter del String
     *
     * @param plazo
     * @return
     */
    public UnidadPlazo extraerUnidadPlazo(String plazo) {
        if (StringUtils.trimToNull(plazo) != null) {
            // Para los casos de DN y DH, la unidad ocupa los dos ultimos caracteres, por lo tanto, si el penultimo
            // caracter es un numero, es que no se trata de ninguna de estas dos unidades
            if(Character.isDigit(plazo.toCharArray()[plazo.length()-2])) {
                return UnidadPlazo.getUnidad(plazo.substring(plazo.length() - 1, plazo.length()));
            }else{
                return UnidadPlazo.getUnidad(plazo.substring(plazo.length() - 2, plazo.length()));
            }
        } else {
            return null;
        }
    }

    /**
     * Extrae el valor del plazo, el cual sera todo el String menos el ultimo caracter
     *
     * @param plazo
     * @return
     */
    public Integer extraerValorPlazo(String plazo) {
        if (StringUtils.trimToNull(plazo) != null) {
            try {
                return Integer.valueOf(plazo.substring(0, plazo.length() - 1));
            }catch (NumberFormatException e){
                // Para los casos de DH y DN, ocupara los dos ultimos caracteres del String
                return Integer.valueOf(plazo.substring(0, plazo.length() - 2));
            }
        } else {
            return null;
        }
    }


    /**
     * En la definición de la serie, se utiliza la tabla ach_limitacio_normativa_serie para almacenar las distintas causas de limitación (las causas por las que se limita el acceso) así como las normativas aplicables para cada una de estas causas.
     * Se trata de una tabla cuya PK es: serie + causaLimitacion + normativa.
     * Por tanto, dada la serie a migrar a Alfresco:
     * - Las causas de limitación a transferir son las distintas causas de limitación presentes en esta tabla para esa serie.
     * - Las normativas son todas las que aparecen para la serie en cuestión y para cada causa de limitación.
     * <p>
     * Como propiedades basta con pasar las causas limitacion ya que es lo que realmente interesa a nivel archivistico,
     * en la generación del documento XML guardaremos la relacion entre causas limitacion y normativas
     * <p>
     * Excluir las limitaciones que tengan puesto fecha de fin NOT NULL anterior a la fecha actual (fecha pasada, que haya expirado).
     *
     * @param serie
     * @return
     */
    public List<String> extraerCodigoLimitacion(Seriedocumental serie) {
        // Si no tiene lista de limitaciones no se pueden extraer ni normativas ni causas limitacion
        if (serie.getAchLimitacioNormativaSeries() == null || serie.getAchLimitacioNormativaSeries().isEmpty()) {
            return null;
        }
        List<String> list = new ArrayList<>();
        // Extraemos las causas limitacion y series, relacionandolas para mantener la correspondencia
        for (LimitacioNormativaSerie lm : serie.getAchLimitacioNormativaSeries()) {
            // Se excluyen las causas limitacion obsoletas (con fi informada y anterior a fecha actual
            if (lm.getId().getSeriedocumentalId().equals(serie.getId()) && !lm.isObsolete()) {
                list.add(lm.getAchCausalimitacio().getCodi());
            }
        }
        return list;
    }


    /**
     * SerieDocumental.valoracio[fi IS NULL].valorPrimari.tipusValor.nomcas
     * <p>
     * Dada la serie, escoge la valoración vigente (sin fecha de fin, sólo debería haber una, en caso de haber varias,
     * nos quedamos con la mas reciente) y coge todos los valores primarios (metadato múltiple).
     *
     * @param serie
     * @param serieWs
     * @return
     */
    public void extraerValoresYPlazo(Seriedocumental serie, Serie serieWs) throws I18NException {
        serieWs.setTipoValor(new ArrayList<>());
        List<String> plazos = new ArrayList<>();

        Valoracio valoracionActiva = extraerValoracionActiva(serie.getAchValoracios());

        if (valoracionActiva != null) {
            serieWs.setValorSecundario(ValorSecundario.getValorSecundario(valoracionActiva.getAchValorsecundari().getNomcas()));
            for (Valorprimari p : valoracionActiva.getAchValorprimaris()) {
                TipoValor valor = TipoValor.getTipoValor(p.getAchTipusvalor().getNomcas());
                if (valor != null) {
                    serieWs.getTipoValor().add(valor);
                    plazos.add(p.getTermini());
                }
            }
            // De todos los plazos del valor primario, el termino sera el mayor de ellos
            String resellado = getResellado(plazos);
            if (resellado != null) {
                Integer value = this.extraerValorPlazo(resellado);
                UnidadPlazo unidad = this.extraerUnidadPlazo(resellado);
                serieWs.setResellado(value);
                serieWs.setUnidadResellado(unidad);
            }
        }

    }

    /**
     * Valoracion activa de una serie es la que tiene fi == null, solo deberia haber una, pero como se puede dar el caso de que haya varias
     * ya que Archium no lo controla, cogeremos la mas reciente.
     *
     * @param achValoracios
     * @return
     */
    public Valoracio extraerValoracionActiva(List<Valoracio> achValoracios) {
        Valoracio valoracionActiva = null;
        for (Valoracio v : achValoracios) {
            if (!v.isObsolete()) {
                if (valoracionActiva == null) {
                    // Si todavía no hay ninguna, se escoge esa
                    valoracionActiva = v;
                } else if (v.getInici().after(valoracionActiva.getInici())) {
                    // Si la fecha de inicio es mas cercana a la actual, se escoge
                    valoracionActiva = v;
                }
            }
        }
        return valoracionActiva;
    }

    /**
     * Devuelve el dictamen activo (estado = "vigent"), en caso de no haber ninguno en estado vigente, se considera activo
     * al dictamen mas reciente de los que esten en estado "esborrany"
     *
     * "La serie debe tener asociado un dictamen en estado ESBORRANY o VIGENT . Si hubiese un dictamen en estado VIGENT,
     * sólo habrá uno para la serie en todo momento y éste prevalece ante los demás (se verificarán los controles con los
     * datos de éste). Si hay varios dictámenes en ESBORRANY y ninguno en VIGENT, se considerará el más reciente de cara
     * a la verificación de datos."
     *
     * @param serie
     * @return
     */
    public Dictamen getDictamenActivo(Seriedocumental serie) {
        log.debug("Comprobamos cual es el dictamen activo para la serie");
        List<Dictamen> achDictamens = serie.getAchDictamens();

        // Si no tiene dictamenes....
        if (achDictamens == null || achDictamens.isEmpty()) {
            return null;
        }

        Dictamen recienteEsborrany = null;
        // Buscamos el dictamen activo
        for (Dictamen dic : achDictamens) {
            if(!dic.isObsolete()) {
                if (Constants.ArchiumConstants.DICTAMEN_ACTIVO.getValue().equalsIgnoreCase(dic.getEstat())) {
                    log.info("Dictamen activo de la serie [" + serie.getCodi() + "]: [" + dic.getCodi() + "] con estado " +
                            "[" + dic.getEstat() + "]");
                    return dic;
                } else if (Constants.ArchiumConstants.DICTAMEN_RECIENTE_ESTADO.getValue().equalsIgnoreCase(dic.getEstat())) {
                    if (recienteEsborrany == null) {
                        recienteEsborrany = dic;
                    } else if (dic.getInici().after(recienteEsborrany.getInici())) {
                        recienteEsborrany = dic;
                    }
                }
            }
        }

        // Si llega aqui es que ningun dictamen esta activo, procedemos a devolver el dictamen en estado esborrany mas
        // reciente. Si no hay ninguno este sera null, por lo que la serie no pasara las validaciones
        if(recienteEsborrany!=null){
            log.info("Dictamen activo de la serie [" + serie.getCodi() + "]: [" + recienteEsborrany.getCodi() + "] con estado " +
                    "[" + recienteEsborrany.getEstat() + "]");
        }
        return recienteEsborrany;
    }

    /**
     * El resellado es el mayor de los plazos de los valores primarios de la serie, se entiende el resellado el periodo
     * durante el cual se tiene que resellar o revalidar la firma electronica
     *
     * @param plazo
     * @return
     */
    private String getResellado(List<String> plazo) {
        if (plazo == null || plazo.isEmpty()) {
            return null;
        }
        Integer valorMax = -1;
        UnidadPlazo unidadMax = null;
        for (String x : plazo) {
            Integer valor = this.extraerValorPlazo(x);
            UnidadPlazo unidad = this.extraerUnidadPlazo(x);
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

    /**
     * Obtiene un mapa en el que guarda todas las normativas para cada causa limitacion
     *
     * @param serie
     * @return
     */
    public Map<String, HashSet<String>> getNormativaLimitacion(Seriedocumental serie) {
        Map<String, HashSet<String>> map = new HashMap<>();
        for (LimitacioNormativaSerie lm : serie.getAchLimitacioNormativaSeries()) {
            if (lm.getAchSeriedocumental().getId().equals(serie.getId())) {
                if(!lm.isObsolete()) {
                    if (map.get(lm.getAchCausalimitacio().getCodi()) == null) {
                        HashSet<String> list = new HashSet<String>();
                        // TODO : Cambiar estos dos get a getNomcas en el futuro
                        list.add(lm.getAchNormativa().getNom());
                        map.put(lm.getAchCausalimitacio().getCodi(), list);
                    } else {
                        HashSet<String> list = map.get(lm.getAchCausalimitacio().getCodi());
                        list.add(lm.getAchNormativa().getNom());
                        map.put(lm.getAchCausalimitacio().getCodi(), list);
                    }
                }
            }
        }
        return map;
    }
}

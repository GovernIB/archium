package es.caib.archium.utils;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.LimitacioNormativaSerie;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.Valoracio;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;
import java.io.ByteArrayOutputStream;
import java.math.BigDecimal;
import java.util.*;

/**
 * Debido a la perdida de informacion de la serie en GDIB en el envio solo de ciertas propiedades de la serie, siguiendo
 * la especificacion europea (https://dilcis.eu/specifications/common-specification), se crea un documento XML que informara
 * de todos los datos de esta serie, al crear la carpeta de la serie en GDIB, se guardara este documento en dicha carpeta,
 * lo que permitira consultar dicha informacion de la serie desde el share
 * <p>
 * La propuesta del formato del XML es la siguiente:
 *
 * <serie codigo_clasificacion="SD09998" documento_esencial="Sí">
 * 	<funcion codigo="EDU2300">Educació secundària obligatòria (ESO) i Batxillerat</funcion>
 * 	<denominacion_clase>Serie test 9998...</denominacion_clase>
 * 	<tipo_clasificacion>Funcional</tipo_clasificacion>
 * 	<lopd>Medio</lopd>
 * 	<confidencialidad>Medio</confidencialidad>
 * 	<acceso>
 * 		<tipo_acceso>Parcialmente restringido</tipo_acceso>
 * 		<limitacion>
 * 			<causa_limitacion codigo="A">
 * 				<normativa_limitacion>Normativa 1</normativa_limitacion>
 * 				<normativa_limitacion>Normativa 2</normativa_limitacion>
 * 			</causa_limitacion>
 * 			<causa_limitacion codigo="B">
 * 				<normativa_limitacion>Normativa 1</normativa_limitacion>
 * 			</causa_limitacion>
 * 		</limitacion>
 * 	</acceso>
 * 	<valoracion>
 * 		<valor_secundario>No</valor_secundario>
 * 		<valoracion_primaria>
 * 			<valor_primario>
 * 				<tipo_valor>Administrativo</tipo_valor>
 * 				<plazo_valor>5</plazo_valor>
 * 				<plazo_valor_unidad>A</plazo_valor_unidad>
 * 			</valor_primario>
 * 		</valoracion_primaria>
 * 	<valoracion>
 * 	<dictamen tipo_dictamen="ET">
 * 		<accion_dictaminada>Eliminación total a los 6 meses de la finalización del expediente</accion_dictaminada>
 * 		<plazo_accion_dictaminada>6</plazo_accion_dictaminada>
 * 		<plazo_accion_dictaminada_unidad>M</plazo_accion_dictaminada_unidad>
 * 	</dictamen>
 * </serie>
 */
@ApplicationScoped
public class CreateSerieXMLUtils {

    Logger log = LoggerFactory.getLogger(CreateSerieXMLUtils.class);

    public static final String MIME_TYPE = "application/xml";
    public static final String ENCODING = "UTF-8";

    @Inject
    private CalculoUtils calculoUtils;

    public String createXMLDocument(Seriedocumental serie, Dictamen dictamen) throws ParserConfigurationException, TransformerException, I18NException {
        log.info("Se genera el documento xml con los datos de la serie...");
        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();


        // elemento raiz
        Document doc = docBuilder.newDocument();
        Element root = doc.createElement("serie");
        doc.appendChild(root);

        // Identificamos el codigo de clasificacion de la serie en la etiqueta
        Attr attrCodSerie = doc.createAttribute("codigo_clasificacion");
        attrCodSerie.setValue(serie.getCodi());
        root.setAttributeNode(attrCodSerie);

        // Identificamos si es serie esencial en la etiqueta
        Attr attrEsencial = doc.createAttribute("documento_esencial");
        if (BigDecimal.ONE.equals(dictamen.getSerieessencial())) {
            attrEsencial.setValue("Sí");
        } else {
            attrEsencial.setValue("No");
        }
        root.setAttributeNode(attrEsencial);

        // Codigo y nombre de la funcion
        Element funcionCodigo = doc.createElement("funcion");
        // TODO : Cambiar a getNomcas en el futuro cuando se traduzcan
        funcionCodigo.appendChild(doc.createTextNode(serie.getAchFuncio().getNom()));
        root.appendChild(funcionCodigo);

        // Añadimos el codigo de la funcion como atributo
        Attr attrCodFuncion = doc.createAttribute("codigo");
        attrCodFuncion.setValue(serie.getAchFuncio().getCodi());
        funcionCodigo.setAttributeNode(attrCodFuncion);


        // Nombre de la clase (denominacion_clase)
        Element denominacionClase = doc.createElement("denominacion_clase");
        denominacionClase.appendChild(doc.createTextNode(serie.getNomcas()));
        root.appendChild(denominacionClase);

        // Tipo clasificacion (Se aplica valor por defecto)
        Element tipoClasificacion = doc.createElement("tipo_clasificacion");
        tipoClasificacion.appendChild(doc.createTextNode(Constants.ArchiumConstants.TIPO_CLASIFICACION_VALOR_POR_DEFECTO.getValue()));
        root.appendChild(tipoClasificacion);

        // LOPD
        Element lopd = doc.createElement("lopd");
        lopd.appendChild(doc.createTextNode(dictamen.getAchLopd().getNomcas()));
        root.appendChild(lopd);

        // Confidencialidad
        Element confidencialidad = doc.createElement("confidencialidad");
        confidencialidad.appendChild(doc.createTextNode(dictamen.getAchEn().getNomcas()));
        root.appendChild(confidencialidad);

        // Acceso (<serie><acceso>)
        Element rootAcceso = doc.createElement("acceso");
        root.appendChild(rootAcceso);

        // Tipo acceso
        Element tipoAcceso = doc.createElement("tipo_acceso");
        tipoAcceso.appendChild(doc.createTextNode(dictamen.getAchTipusacce().getNomcas()));
        rootAcceso.appendChild(tipoAcceso);

        // condicion reutilizacion
        Element condicionReutilizacion = doc.createElement("condicion_reutilizacion");
        if(StringUtils.trimToNull(dictamen.getCondicioreutilitzacio())!=null) {
            condicionReutilizacion.appendChild(doc.createTextNode(dictamen.getCondicioreutilitzacio()));
        }
        rootAcceso.appendChild(condicionReutilizacion);

        // Limitacion (<serie><acceso><limitacion>)
        Element rootLimitacion = doc.createElement("limitacion");
        rootAcceso.appendChild(rootLimitacion);

        // Obtenemos la lista completa de normativas para cada causa limitacion de la serie
        Map<String, HashSet<String>> map = getNormativaLimitacion(serie);

        // Creamos la estructura de limitaciones
        for (Map.Entry<String, HashSet<String>> entry : map.entrySet()) {
            // Causa limitacion (<serie><acceso><limitacion><causa_limitacion>
            Element causaLimitacionRoot = doc.createElement("causa_limitacion");
            rootLimitacion.appendChild(causaLimitacionRoot);

            // Atributo codigo de la causa limitacion
            Attr attrCodCausaLimitacion = doc.createAttribute("codigo");
            attrCodCausaLimitacion.setValue(entry.getKey());
            causaLimitacionRoot.setAttributeNode(attrCodCausaLimitacion);

            // Normativas para cada causa limitacion
            entry.getValue().forEach(x -> {
                Element normativaLimitacion = doc.createElement("normativa_limitacion");
                normativaLimitacion.appendChild(doc.createTextNode(x));
                causaLimitacionRoot.appendChild(normativaLimitacion);
            });
        }

        // valoracion (<serie><valoracion>)
        Element rootValoracion = doc.createElement("valoracion");
        root.appendChild(rootValoracion);

        // Obtenemos la valoracion vigente
        Valoracio valoracion = getValoracionVigente(serie.getAchValoracios());

        // valoracion secundario
        Element valoracionSecundario = doc.createElement("valoracion_secundario");
        valoracionSecundario.appendChild(doc.createTextNode(valoracion.getAchValorsecundari().getNomcas()));
        rootValoracion.appendChild(valoracionSecundario);

        // valoracion_primaria (<serie><valoracion><valoracion_primaria>)
        Element rootValoracionPrimaria = doc.createElement("valoracion_primaria");
        rootValoracion.appendChild(rootValoracionPrimaria);

//         Creamos la lista de etiquetas <valor_primario>
        for (var entry : valoracion.getAchValorprimaris()) {
            // valoracion_primaria (<serie><valoracion><valoracion_primaria><valor_primario>)
            Element rootValorPrimario = doc.createElement("valor_primario");
            rootValoracionPrimaria.appendChild(rootValorPrimario);

            // valoracion secundario
            Element tipoValor = doc.createElement("tipo_valor");
            tipoValor.appendChild(doc.createTextNode(entry.getAchTipusvalor().getNomcas()));
            rootValorPrimario.appendChild(tipoValor);

            // Extraemos el número y la unidad
            String unidad = UnidadPlazo.getCSGDUnidad(entry.getTermini().substring(entry.getTermini().length() - 1, entry.getTermini().length()));
            String valor = entry.getTermini().substring(0, entry.getTermini().length() - 1);

            // plazo valor (termino)
            Element plazoValor = doc.createElement("plazo_valor");
            plazoValor.appendChild(doc.createTextNode(valor));
            rootValorPrimario.appendChild(plazoValor);

            // plazo_valor_unidad
            Element plazoValorUnidad = doc.createElement("plazo_valor_unidad");
            plazoValorUnidad.appendChild(doc.createTextNode(unidad));
            rootValorPrimario.appendChild(plazoValorUnidad);
        }

        // Dictamen (<serie><dictamen>)
        Element rootDictamen = doc.createElement("dictamen");
        root.appendChild(rootDictamen);

        // Identificamos el codigo del tipo de dictamen
        Attr attrTipoDictamen = doc.createAttribute("tipo_dictamen");
        attrTipoDictamen.setValue(dictamen.getAchTipusdictamen().getCodi());
        rootDictamen.setAttributeNode(attrTipoDictamen);

        // Accion dictaminada
        Element accionDictaminada = doc.createElement("accion_dictaminada");
        String ad = rellenarOCalcularAccionDictaminada(serie,dictamen);
        if(StringUtils.trimToNull(ad)!=null) {
            accionDictaminada.appendChild(doc.createTextNode(ad));
        }
        rootDictamen.appendChild(accionDictaminada);

        String plazo = null;
        if(StringUtils.trimToNull(dictamen.getTermini())!=null){
            plazo = dictamen.getTermini();
        }else{
            plazo = calcularPlazoAccionDictaminada(dictamen);
        }
        String unidad = null;
        String valor = null;
        if(StringUtils.trimToNull(plazo)!=null) {
            // Extraemos el número y la unidad
            unidad = UnidadPlazo.getCSGDUnidad(dictamen.getTermini().substring(dictamen.getTermini().length() - 1, dictamen.getTermini().length()));
            valor = dictamen.getTermini().substring(0, dictamen.getTermini().length() - 1);
        }


        // Plazo accion dictaminada
        Element plazoAccionDictaminada = doc.createElement("plazo_accion_dictaminada");
        if(StringUtils.trimToNull(valor)!=null) {
            plazoAccionDictaminada.appendChild(doc.createTextNode(valor));
        }
        rootDictamen.appendChild(plazoAccionDictaminada);

        // Plazo accion dictaminada unidad
        Element plazoAccionDictaminadaUnidad = doc.createElement("plazo_accion_dictaminada_unidad");
        if(StringUtils.trimToNull(unidad)!=null) {
            plazoAccionDictaminadaUnidad.appendChild(doc.createTextNode(unidad));
        }
        rootDictamen.appendChild(plazoAccionDictaminadaUnidad);


        // Preparamos para exportar como fichero el contenido creado
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(baos);
        transformer.transform(source, result);

        log.info("Generado documento xml con los datos de la serie, se guarda codificado en base64");
        // Transformamos el resultado a un String codificado en base64
        return Base64.getEncoder().encodeToString(baos.toByteArray());
    }

    /**
     * La accion dictaminada se obtiene directamente del atributo, pero si está vacía, habrá que calcularla:
     * <p>
     * Como se ha visto en las verificaciones de coherencia entre serie y dictamen, puede darse el caso en que no se
     * tenga directamente el campo de «acción dictaminada» rellenado para un dictamen sino que se calcula a partir de
     * otros campos. Esto, en parte, es deseable de cara a poder automatizar al máximo las tareas de conservación en base
     * al dictamen (y evitar posibles errores de introducción de datos).
     * La lógica sería:
     * 1.	Si tenemos acción dictaminada (campo de la tabla dictamen), ésta prevalece y el metadato se corresponde con
     * el campo acciodictaminada. Se supone que, en este caso, el gestor ha querido sobreescribir la acción «calculada»
     * para expresar alguna tarea específica.
     * 2.	Si no tenemos acción dictaminada:
     * a)	Si tipus dictamen = 'CP', la acción dictaminada "calculada" (metadato) sería "Conservación permanente" (tipusdictamen.nomcas)
     * b)	Si tipus dictamen = 'ET', la acción dictaminada estará vacía en Archium, pero es obligatorio disponer de
     * termini en el dictamen. La acción dictaminada "calculada" (metadato) sería: <tipusdictamen.nomcas> «a los »
     * <dictamen.termini>  « de la finalización del expediente». Ejemplo: «Eliminación total a los 4 años de la finalización del expediente».
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
     * @param serieWs
     * @param dictamen
     */
    private String rellenarOCalcularAccionDictaminada(Seriedocumental serie, Dictamen dictamen) {
        if (StringUtils.trimToNull(dictamen.getAcciodictaminada()) != null) {
            return dictamen.getAcciodictaminada();
        } else {
            // Si no esta informada la calculamos
            return this.calculoUtils.calcularAccionDictaminada(dictamen);
        }
    }

    /**
     * Se extrae valor y unidad del termino del dictamen. Si este esta en blanco se calcula:
     * <p>
     * La mayoría de los casos estarán cubiertos con equivalencia directa con el campo termini de la tabla de dictámenes.
     * No obstante, hay situaciones en los que el plazo de la acción dictaminada se debe calcular:
     * •	Si es un dictamen de tipo «pendent de dictamen», puede o no haber plazo.
     * •	Si es un dictamen de tipo «conservación permanente», no debería haber plazo.
     * •	Si es un dictamen de eliminación parcial, podemos tener diferentes plazos en juego. Cuál de todos ellos poner?
     * La propuesta sería coger de Archium el mayor de todos los plazos entre el plazo del dictamen y los plazos específicos de los tipos documentales.
     *
     * @param serieWs
     * @param dictamen
     */
    private String calcularPlazoAccionDictaminada(Dictamen dictamen) throws I18NException {
        if (StringUtils.trimToNull(dictamen.getTermini()) == null) {
            // Si no esta informado, se calcula
            return this.calculoUtils.calcularPlazoAccionDictaminada(dictamen);
        }
        return null;
    }

    /**
     * Valoracion activa de una serie es la que tiene fi == null, solo deberia haber una, pero como se puede dar el caso de que haya varias
     * ya que Archium no lo controla, cogeremos la mas reciente.
     *
     * @param achValoracios
     * @return
     */
    private Valoracio getValoracionVigente(List<Valoracio> achValoracios) {
        Valoracio valoracionActiva = null;
        for (Valoracio v : achValoracios) {
            if (v.getFi() == null) {
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
     * Obtiene un mapa en el que guarda todas las normativas para cada causa limitacion
     *
     * @param serie
     * @return
     */
    private Map<String, HashSet<String>> getNormativaLimitacion(Seriedocumental serie) {
        Map<String, HashSet<String>> map = new HashMap<>();
        for (LimitacioNormativaSerie lm : serie.getAchLimitacioNormativaSeries()) {
            if (lm.getAchSeriedocumental().getId().equals(serie.getId())) {
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
        return map;
    }
}

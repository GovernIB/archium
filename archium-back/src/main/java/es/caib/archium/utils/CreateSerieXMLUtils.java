package es.caib.archium.utils;

import es.caib.archium.commons.utils.Constants;
import es.caib.archium.persistence.model.*;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.ejb.Stateless;
import javax.mail.util.ByteArrayDataSource;
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
 * <serie codigo_clasificacion="SD12345" documento_esencial="Sí">
 * 	<denominacion_clase>Nombre de la serie</denominacion_clase>
 * 	<tipo_clasificacion>Funcional</tipo_clasificacion>
 * 	<lopd>Básico</lopd>
 * 	<confidencialidad>Bajo</confidencialidad>
 * 	<acceso>
 * 		<tipo_acceso>Parcialmente restringido</tipo_acceso>
 * 		<condicion_reutilizacion>Versión anonimizada</condicion_reutilizacion>
 * 		<limitacion>
 * 			<causa_limitacion codigo="A">
 * 				<normativa_limitacion>Real Decreto...</normativa_limitacion>
 * 				<normativa_limitacion>Ley 14/...</normativa_limitacion>
 * 			</causa_limitacion>
 * 			<causa_limitacion codigo="B">
 * 				<normativa_limitacion>Ley Orgánica...</normativa_limitacion>
 * 			</causa_limitacion>
 * 		</limitacion>
 * 	</acceso>
 * 	<valoracion>
 * 		<valor_secundario>Sin cobertura de calificación</valor_secundario>
 * 		<valoracion_primaria>
 * 			<valor_primario>
 * 				<tipo_valor>Administrativo</tipo_valor>
 * 				<plazo_valor>5</plazo_valor>
 * 				<plazo_valor_unidad>A</plazo_valor_unidad>
 * 			</valor_primario>
 * 			<valor_primario>
 * 				<tipo_valor>Jurídico</tipo_valor>
 * 				<plazo_valor>15</plazo_valor>
 * 				<plazo_valor_unidad>A</plazo_valor_unidad>
 * 			</valor_primario>
 * 		</valoracion_primaria>
 * 	<valoracion>
 * 	<dictamen tipo_dictamen="EP">
 * 		<accion_dictaminada>Eliminación parcial. Conservación: Resolución (5 años). Eliminación: Solicitud (3 años).</accion_dictaminada>
 * 		<plazo_accion_dictaminada>5</plazo_accion_dictaminada>
 * 		<plazo_accion_dictaminada_unidad>A</plazo_accion_dictaminada_unidad>
 * 	</dictamen>
 * </serie>
 */
@Stateless
public class CreateSerieXMLUtils {

    public DataHandler createXMLDocument(Seriedocumental serie, Dictamen dictamen) throws ParserConfigurationException, TransformerException {
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

        // Nombre de la clase (denominacion_clase)
        Element denominacionClase = doc.createElement("denominacion_clase");
        denominacionClase.appendChild(doc.createTextNode(serie.getNom()));
        root.appendChild(denominacionClase);

        // Tipo clasificacion (Se aplica valor por defecto)
        Element tipoClasificacion = doc.createElement("tipo_clasificacion");
        tipoClasificacion.appendChild(doc.createTextNode(Constants.ArchiumConstants.TIPO_CLASIFICACION_VALOR_POR_DEFECTO.getValue()));
        root.appendChild(tipoClasificacion);

        // LOPD
        Element lopd = doc.createElement("lopd");
        lopd.appendChild(doc.createTextNode(dictamen.getAchLopd().getNom()));
        root.appendChild(lopd);

        // Confidencialidad
        Element confidencialidad = doc.createElement("confidencialidad");
        confidencialidad.appendChild(doc.createTextNode(dictamen.getAchEn().getNom()));
        root.appendChild(confidencialidad);

        // Acceso (<serie><acceso>)
        Element rootAcceso = doc.createElement("acceso");
        root.appendChild(rootAcceso);

        // Tipo acceso
        Element tipoAcceso = doc.createElement("tipo_acceso");
        tipoAcceso.appendChild(doc.createTextNode(dictamen.getAchTipusacce().getNom()));
        rootAcceso.appendChild(tipoAcceso);

        // condicion reutilizacion
        Element condicionReutilizacion = doc.createElement("condicion_reutilizacion");
        condicionReutilizacion.appendChild(doc.createTextNode(dictamen.getAchTipusacce().getNom()));
        rootAcceso.appendChild(condicionReutilizacion);

        // Limitacion (<serie><acceso><limitacion>)
        Element rootLimitacion = doc.createElement("limitacion");
        rootAcceso.appendChild(rootLimitacion);

        // Obtenemos la lista completa de normativas para cada causa limitacion de la serie
        Map<String, HashSet<String>> map = getNormativaLimitacion(serie);

        // Creamos la estructura de limitaciones
        for(Map.Entry<String,HashSet<String>> entry : map.entrySet()){
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
        valoracionSecundario.appendChild(doc.createTextNode(valoracion.getAchValorsecundari().getNom()));
        rootValoracion.appendChild(valoracionSecundario);

        // valoracion_primaria (<serie><valoracion><valoracion_primaria>)
        Element rootValoracionPrimaria = doc.createElement("valoracion_primaria");
        rootValoracion.appendChild(rootValoracionPrimaria);

        // Creamos la lista de etiquetas <valor_primario>
        for(var entry : valoracion.getAchValorprimaris()){
            // valoracion_primaria (<serie><valoracion><valoracion_primaria><valor_primario>)
            Element rootValorPrimario = doc.createElement("valor_primario");
            rootValoracionPrimaria.appendChild(rootValorPrimario);

            // valoracion secundario
            Element tipoValor = doc.createElement("tipo_valor");
            tipoValor.appendChild(doc.createTextNode(entry.getAchTipusvalor().getNom()));
            rootValorPrimario.appendChild(tipoValor);

            // Extraemos el número y la unidad
            String unidad = entry.getTermini().substring(entry.getTermini().length()-1,entry.getTermini().length());
            String valor = entry.getTermini().substring(0,entry.getTermini().length()-1);

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
        accionDictaminada.appendChild(doc.createTextNode(dictamen.getAcciodictaminada()));
        rootDictamen.appendChild(accionDictaminada);

        // Extraemos el número y la unidad
        String unidad = dictamen.getTermini().substring(dictamen.getTermini().length()-1,dictamen.getTermini().length());
        String valor = dictamen.getTermini().substring(0,dictamen.getTermini().length()-1);

        // Plazo accion dictaminada
        Element plazoAccionDictaminada = doc.createElement("plazo_accion_dictaminada");
        plazoAccionDictaminada.appendChild(doc.createTextNode(valor));
        rootDictamen.appendChild(plazoAccionDictaminada);

        // Plazo accion dictaminada unidad
        Element plazoAccionDictaminadaUnidad = doc.createElement("plazo_accion_dictaminada_unidad");
        plazoAccionDictaminadaUnidad.appendChild(doc.createTextNode(unidad));
        rootDictamen.appendChild(plazoAccionDictaminadaUnidad);




        // Preparamos para exportar como fichero el contenido creado
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(baos);
        transformer.transform(source, result);

        // Convertimos a un array binario el contenido creado
        byte[] content = baos.toByteArray();
        // Devolvemos este contenido como DataHandler
        DataSource dataSource = new ByteArrayDataSource(content, "application/xml");
        return new DataHandler(dataSource);
    }

    /**
     * Obtiene la valoracion vigente, esta es la que no tiene fecha fin (fi), solo hay una por cada serie
     *
     * @param achValoracios
     * @return
     */
    private Valoracio getValoracionVigente(List<Valoracio> achValoracios) {
        for(Valoracio v : achValoracios){
            if(v.getFi()==null){
                return v;
            }
        }
        return null;
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
                }else{
                    HashSet<String> list = map.get(lm.getAchCausalimitacio().getCodi());
                    list.add(lm.getAchNormativa().getNom());
                    map.put(lm.getAchCausalimitacio().getCodi(), list);
                }
            }
        }
        return map;
    }
}

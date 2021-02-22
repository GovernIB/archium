package es.caib.archium.utils;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.csgd.apirest.constantes.TipoDictamen;
import es.caib.archium.csgd.apirest.constantes.UnidadPlazo;
import es.caib.archium.persistence.model.*;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.w3c.dom.Attr;
import org.w3c.dom.Document;
import org.w3c.dom.Element;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
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
import java.io.IOException;
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
 * <funcion codigo="EDU2300">Educació secundària obligatòria (ESO) i Batxillerat</funcion>
 * <series_argen>
 * <serie_argen>A01</serie_argen>
 * <serie_argen>A02</serie_argen>
 * <serie_argen>A07</serie_argen>
 * </series_argen>
 * <usuarios_aplicaciones>
 * <usuario_aplicacion>RIPEA</usuario_aplicacion>
 * <usuario_aplicacion>REGISTRE</usuario_aplicacion>
 * </usuarios_aplicaciones>
 * <denominacion_clase>Serie test 9998...</denominacion_clase>
 * <tipo_clasificacion>Funcional</tipo_clasificacion>
 * <lopd>Medio</lopd>
 * <confidencialidad>Medio</confidencialidad>
 * <acceso>
 * <tipo_acceso>Parcialmente restringido</tipo_acceso>
 * <limitacion>
 * <causa_limitacion codigo="A">
 * <normativa_limitacion>Normativa 1</normativa_limitacion>
 * <normativa_limitacion>Normativa 2</normativa_limitacion>
 * </causa_limitacion>
 * <causa_limitacion codigo="B">
 * <normativa_limitacion>Normativa 1</normativa_limitacion>
 * </causa_limitacion>
 * </limitacion>
 * </acceso>
 * <valoracion>
 * <valor_secundario>No</valor_secundario>
 * <valoracion_primaria>
 * <valor_primario>
 * <tipo_valor>Administrativo</tipo_valor>
 * <plazo_valor>5</plazo_valor>
 * <plazo_valor_unidad>A</plazo_valor_unidad>
 * </valor_primario>
 * </valoracion_primaria>
 * <valoracion>
 * <dictamen tipo_dictamen="ET">
 * <accion_dictaminada>Eliminación total a los 6 meses de la finalización del expediente</accion_dictaminada>
 * <plazo_accion_dictaminada>6</plazo_accion_dictaminada>
 * <plazo_accion_dictaminada_unidad>M</plazo_accion_dictaminada_unidad>
 * <tipos_documentales>
 * <tipo_documental codigo="TD01-019" conservable="Sí">
 * <plazo_dictamen>4</plazo_dictamen>
 * <plazo_unidad_dictamen>A</plazo_unidad_dictamen>
 * </tipo_documental>
 * <tipo_documental codigo="TD01-021" conservable="Sí">
 * <plazo_dictamen>12</plazo_dictamen>
 * <plazo_unidad_dictamen>A</plazo_unidad_dictamen>
 * </tipo_documental>
 * <tipo_documental codigo="TD02-004" conservable="No">
 * <plazo_dictamen>2</plazo_dictamen>
 * <plazo_unidad_dictamen>A</plazo_unidad_dictamen>
 * </tipo_documental>
 * <tipo_documental codigo="TD06-004" conservable="Sí">
 * <plazo_dictamen />
 * <plazo_unidad_dictamen />
 * </tipo_documental>
 * </tipos_documentales>
 * </dictamen>
 * </serie>
 */
@ApplicationScoped
public class CreateSerieXMLUtils {

    Logger log = LoggerFactory.getLogger(CreateSerieXMLUtils.class);

    public static final String MIME_TYPE = "application/xml";
    public static final String ENCODING = "UTF-8";

    @Inject
    private CalculoUtils calculoUtils;

    public DataHandler createXMLDocument(Seriedocumental serie, Dictamen dictamen) throws ParserConfigurationException, TransformerException, I18NException {
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
        attrEsencial.setValue(BigDecimal.ONE.equals(dictamen.getSerieessencial()) ? "Sí" : "No");
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

        // Series argen
        Element rootSeriesArgen = doc.createElement("series_argen");
        root.appendChild(rootSeriesArgen);

        for (Serierelacionada entry : serie.getAchSerierelacionadas1()) {
            Element serieArgen = doc.createElement("serie_argen");
            serieArgen.appendChild(doc.createTextNode(entry.getAchSerieargen().getCodi()));
            rootSeriesArgen.appendChild(serieArgen);
        }

        // Aplicaciones
        Element rootUsuariosAplicacion = doc.createElement("usuarios_aplicaciones");
        root.appendChild(rootUsuariosAplicacion);

        for (AplicacioSerie entry : serie.getAchAplicacioSeries()) {
            Element usuarioAplicacion = doc.createElement("usuario_aplicacion");
            usuarioAplicacion.appendChild(doc.createTextNode(entry.getAchAplicacio().getNom()));
            rootUsuariosAplicacion.appendChild(usuarioAplicacion);
        }

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
        if (StringUtils.trimToNull(dictamen.getCondicioreutilitzacio()) != null) {
            condicionReutilizacion.appendChild(doc.createTextNode(dictamen.getCondicioreutilitzacio()));
        }
        rootAcceso.appendChild(condicionReutilizacion);

        // Limitacion (<serie><acceso><limitacion>)
        Element rootLimitacion = doc.createElement("limitacion");
        rootAcceso.appendChild(rootLimitacion);

        // Obtenemos la lista completa de normativas para cada causa limitacion de la serie
        Map<String, HashSet<String>> map = this.calculoUtils.getNormativaLimitacion(serie);

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
        Valoracio valoracion = this.calculoUtils.extraerValoracionActiva(serie.getAchValoracios());

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
            UnidadPlazo unidad = this.calculoUtils.extraerUnidadPlazo(entry.getTermini());
            String valor = String.valueOf(this.calculoUtils.extraerValorPlazo(entry.getTermini()));

            // plazo valor (termino)
            Element plazoValor = doc.createElement("plazo_valor");
            if (StringUtils.trimToNull(valor) != null) {
                plazoValor.appendChild(doc.createTextNode(valor));
            }
            rootValorPrimario.appendChild(plazoValor);

            // plazo_valor_unidad
            Element plazoValorUnidad = doc.createElement("plazo_valor_unidad");
            if (unidad != null) {
                plazoValorUnidad.appendChild(doc.createTextNode(unidad.toString()));
            }
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
        String ad = this.calculoUtils.rellenarOCalcularAccionDictaminada(serie, dictamen);
        if (StringUtils.trimToNull(ad) != null) {
            accionDictaminada.appendChild(doc.createTextNode(ad));
        }
        rootDictamen.appendChild(accionDictaminada);

        String plazo = this.calculoUtils.calcularPlazoAccionDictaminada(dictamen);

        UnidadPlazo unidad = null;
        Integer valor = null;
        if (StringUtils.trimToNull(plazo) != null) {
            // Extraemos el número y la unidad
            unidad = this.calculoUtils.extraerUnidadPlazo(plazo);
            valor = this.calculoUtils.extraerValorPlazo(plazo);
        }


        // Plazo accion dictaminada
        Element plazoAccionDictaminada = doc.createElement("plazo_accion_dictaminada");
        if (valor != null) {
            plazoAccionDictaminada.appendChild(doc.createTextNode(String.valueOf(valor)));
        }
        rootDictamen.appendChild(plazoAccionDictaminada);

        // Plazo accion dictaminada unidad
        Element plazoAccionDictaminadaUnidad = doc.createElement("plazo_accion_dictaminada_unidad");
        if (unidad != null) {
            plazoAccionDictaminadaUnidad.appendChild(doc.createTextNode(unidad.toString()));
        }
        rootDictamen.appendChild(plazoAccionDictaminadaUnidad);

        // Si el tipo de dictamen es EP, se debe añadir los tipos documentales
        if (TipoDictamen.EP == TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi())) {
            // Tipos documentales (<serie><dictamen><tipos_documentales>)
            Element rootTiposDocumentales = doc.createElement("tipos_documentales");
            rootDictamen.appendChild(rootTiposDocumentales);
            // Para cada tipo documental...
            dictamen.getAchDictamenTipusdocumentals().forEach(x -> {
                // Descartamos los tipos documentales con fecha fin
                if (!x.isObsolete()) {
                    // Tipo documental (<serie><dictamen><tipos_documentales><tipo_documental>)
                    Element rootTipoDocumental = doc.createElement("tipo_documental");
                    rootTiposDocumentales.appendChild(rootTipoDocumental);

                    // Atributo codigo del tipo documental
                    Attr attrCodTipoDoc = doc.createAttribute("codigo");
                    attrCodTipoDoc.setValue(x.getAchTipusdocumental().getCodi());
                    rootTipoDocumental.setAttributeNode(attrCodTipoDoc);

                    // Atributo conservable del tipo documental
                    Attr attrConservableTipoDoc = doc.createAttribute("conservable");
                    attrConservableTipoDoc.setValue(BigDecimal.ZERO.equals(x.getConservable()) ? "No" : "Sí");
                    rootTipoDocumental.setAttributeNode(attrConservableTipoDoc);

                    // Extraemos el número y la unidad
                    UnidadPlazo unidadTipo = this.calculoUtils.extraerUnidadPlazo(x.getTermini());
                    Integer valorTipo = this.calculoUtils.extraerValorPlazo(x.getTermini());


                    // plazo dictamen (termino tipo documental)
                    Element plazoDictamen = doc.createElement("plazo_dictamen");
                    if (valorTipo != null) {
                        plazoDictamen.appendChild(doc.createTextNode(String.valueOf(valorTipo)));
                    }
                    rootTipoDocumental.appendChild(plazoDictamen);

                    // plazo_unidad_dictamen
                    Element plazoDictamenUnidad = doc.createElement("plazo_unidad_dictamen");
                    if (unidadTipo != null) {
                        plazoDictamenUnidad.appendChild(doc.createTextNode(unidadTipo.toString()));
                    }
                    rootTipoDocumental.appendChild(plazoDictamenUnidad);
                }
            });
        }

        // Preparamos para exportar como fichero el contenido creado
        TransformerFactory transformerFactory = TransformerFactory.newInstance();
        Transformer transformer = transformerFactory.newTransformer();
        DOMSource source = new DOMSource(doc);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        StreamResult result = new StreamResult(baos);
        transformer.transform(source, result);

        log.info("Generado documento xml con los datos de la serie");

        // Convertimos a un array binario el contenido creado
        byte[] content = baos.toByteArray();
        // Devolvemos este contenido como DataHandler
        DataSource dataSource = new ByteArrayDataSource(content, "application/xml");
        return new DataHandler(dataSource);
    }

    /**
     * Transformamos el DataHandler a base64
     *
     * @param binaryContent
     * @return
     * @throws IOException
     */
    public String toBase64Content(DataHandler binaryContent) throws IOException {

        byte[] content = IOUtils.toByteArray(binaryContent.getInputStream());
        return Base64.getEncoder().encodeToString(content);
    }
}

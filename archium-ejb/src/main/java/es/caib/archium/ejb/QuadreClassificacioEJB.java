package es.caib.archium.ejb;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.events.Event;
import com.itextpdf.kernel.events.IEventHandler;
import com.itextpdf.kernel.events.PdfDocumentEvent;
import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.geom.PageSize;
import com.itextpdf.kernel.geom.Rectangle;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfDocumentInfo;
import com.itextpdf.kernel.pdf.PdfPage;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.action.PdfAction;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.AreaBreak;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Div;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.layout.LayoutContext;
import com.itextpdf.layout.layout.LayoutResult;
import com.itextpdf.layout.properties.BorderRadius;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TabAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import com.itextpdf.layout.renderer.ParagraphRenderer;
import com.itextpdf.layout.splitting.ISplitCharacters;
import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.model.Estat;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.commons.rest.RestConstants;
import es.caib.archium.commons.utils.I18nUtils;
import es.caib.archium.commons.utils.PdfUtils;
import es.caib.archium.commons.utils.StringUtilitats;
import es.caib.archium.ejb.objects.QuadreClassificacioDto;
import es.caib.archium.ejb.objects.SerieDto;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.ejb.utils.SqlUtils;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Quadreclassificacio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.Query;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.stream.Collectors;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class QuadreClassificacioEJB extends AbstractDAO<Quadreclassificacio, Long> implements QuadreClassificacioService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    PdfUtils pdfUtils;

    @Inject
    SqlUtils sqlUtils;

    @Inject
    StringUtilitats stringUtilitats;

    @Inject
    I18nUtils i18nUtils;

    ResourceBundle messagesES = ResourceBundle.getBundle("messages.ejb", Idioma.CASTELLA.getLocale());

    ResourceBundle messagesCA = ResourceBundle.getBundle("messages.ejb", Idioma.CATALA.getLocale());

    private String queryQdC = null;
    
    private ZonedDateTime now = ZonedDateTime.now();

    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @PostConstruct
    public void init() {
        log.debug("Iniciant quadreClassificacioEJB...");
        InputStream inputStream = QuadreClassificacioEJB.class.getResourceAsStream("/queries/qdc.sql");
        queryQdC = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        //log.debug("Query QDC = " + queryQdC);
    }

    @Override
    // Emprat des de l'API REST Externa (públic)
    @PermitAll
    public Long getQuadreClassificacioPerDefecte() {
        // TODO Pendent implementar quadre per defecte (implica modificar base de dades, columna amb check de només 1 quadre amb '1' i consultar aquest aquí...)
        return 2L;
    }

    @Override
    @PermitAll
    public List<QuadreClassificacioDto> getLlistaQuadreClassificacio(String estat, Idioma idioma) {
        Map<String, Object> filtres = new HashMap<>();
        if (estat != null) {
            filtres.put("estat", estat);
        }
        OrderBy orderById = new OrderBy("id", OrderType.ASC);


        List<Quadreclassificacio> qdcs = null;
        try {
            qdcs = this.findFiltered(filtres, orderById);
        } catch (I18NException e) {
            log.error("Problema cercant la llista de quadres de classificació per estat " + estat);
        }

        List<QuadreClassificacioDto> qdcsDto = new ArrayList<>();
        if (qdcs != null) {
            log.debug("Quadres trobats: " + qdcs.size());
            for (Quadreclassificacio qdc : qdcs) {
                if (idioma != null && Idioma.CASTELLA.getCodi().equalsIgnoreCase(idioma.getCodi())) {
                    qdcsDto.add(new QuadreClassificacioDto(qdc.getId().toString(), qdc.getNomcas(), qdc.getEstat(), qdc.getVersio(), RestConstants.sdf.format(qdc.getModificacio())));
                } else {
                    qdcsDto.add(new QuadreClassificacioDto(qdc.getId().toString(), qdc.getNom(), qdc.getEstat(), qdc.getVersio(), RestConstants.sdf.format(qdc.getModificacio())));
                }
            }
        }
        return qdcsDto;
    }

    @Override
    @PermitAll
    public List<SerieDto> getQuadreClassificacio(Long quadreId, Idioma idioma, Estat estat) {
        log.debug("Consultant dades del quadre de classificació. quadreId = " + quadreId + ". idioma = " + idioma + ". estat = " + estat);
        log.info("Entrando en el metodo getQuadreClassificacio de QuadreClassificacioEJB");

        Query queryQdC = entityManager.createNativeQuery(this.queryQdC);
        queryQdC.setParameter("quadre", quadreId);
        queryQdC.setParameter("estatSerie", estat != null ? estat.getValue().toLowerCase() : null /* : Estat.VIGENT.getValue().toLowerCase()*/);

        List<Object[]> items = queryQdC.getResultList();

        List<SerieDto> seriesDto = new ArrayList<>();


        for (Object[] a : items) {
            SerieDto serieDto = new SerieDto();
            serieDto.setOrdre(Long.valueOf(a[0].toString()));
            serieDto.setNivell(Long.valueOf(a[1].toString()));
            serieDto.setCodiFuncio(a[2].toString());
            if (Idioma.CATALA.equals(idioma)) {
                serieDto.setAmbitFuncional(stringUtilitats.nullSafe(a[3]));
                serieDto.setNomSerie(stringUtilitats.nullSafe(a[6]));
                //serieDto.setAcces(stringUtilitats.nullSafe(a[10]));
                //serieDto.setTipusDictamen(stringUtilitats.nullSafe(a[12]));
                serieDto.setTipusSerie(stringUtilitats.nullSafe(a[9]));
            } else {
                serieDto.setAmbitFuncional(stringUtilitats.nullSafe(a[4]));
                serieDto.setNomSerie(stringUtilitats.nullSafe(a[7]));
                //serieDto.setAcces(stringUtilitats.nullSafe(a[9]));
                //serieDto.setTipusDictamen(stringUtilitats.nullSafe(a[11]));
                serieDto.setTipusSerie(stringUtilitats.nullSafe(a[10]));
            }

            serieDto.setCodiSerie(stringUtilitats.nullSafe(a[5]));

//            a[8] = sqlUtils.convertClob(a[8]);
//            serieDto.setProcediments(StringEscapeUtils.unescapeXml(stringUtilitats.nullSafe(a[8])));
//            serieDto.setDataActualitzacio(stringUtilitats.nullSafe(a[13]));
//
//            a[14] = sqlUtils.convertClob(a[14]);
//            serieDto.setAplicacions(StringEscapeUtils.unescapeXml(stringUtilitats.nullSafe(a[14])));
//
//            a[15] = sqlUtils.convertClob(a[15]);
//            serieDto.setSeriesArgen(StringEscapeUtils.unescapeXml(stringUtilitats.nullSafe(a[15])));

            String enviat;
            if (serieDto.getCodiSerie() != null) {
                if (a[8] != null) {
                    switch (stringUtilitats.nullSafe(a[8])) {
                        case "0":
                            enviat = "No";
                            break;
                        case "1":
                            enviat = "Sí";
                            break;
                        default:
                            enviat = "NS/NC";
                    }
                } else {
                    enviat = "NS/NC";
                }
            } else {
                enviat = null;
            }
            serieDto.setEnviatSAT(enviat);



            log.debug("Serie: " + serieDto);
            seriesDto.add(serieDto);
        }
        log.info("Saliendo del metodo getQuadreClassificacio de QuadreClassificacioEJB");
        return seriesDto;
    }
    
    @Override
    @PermitAll
    public InputStream generaPdfQuadreQuassificacio(List<SerieDto> seriesDto, Idioma idioma) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Inicialitzam la taula de continguts
        List<SimpleEntry<String, SimpleEntry<String, Integer>>> toc = new ArrayList<>();

        try {
            PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
            PdfDocument document = new PdfDocument(pdfWriter);
            document.setDefaultPageSize(PageSize.A4.rotate());

            Document doc = new Document(document);

            PdfFont notoSans = pdfUtils.getFont("/font/NotoSans-Regular.ttf");

            PdfFont notoSansBold = pdfUtils.getFont("/font/NotoSans-Bold.ttf");
            doc.setFont(notoSans);
            doc.setFontSize(10);

            doc.setTopMargin(2.25F * (72 / 2.54F)); // 2,25cm
            doc.setBottomMargin(3F * (72 / 2.54F)); // 3cm
            doc.setLeftMargin(1.5F * (72 / 2.54F));   // 1,5 cm
            doc.setRightMargin(1.5F * (72 / 2.54F));  // 1,5cm

            i18nUtils.setLocale(idioma);
            ResourceBundle messages;
            if (idioma == Idioma.CASTELLA) {
                messages = messagesES;
            } else {
                messages = messagesCA;
            }

            PdfDocumentInfo info = document.getDocumentInfo();
            info.setTitle(messages.getString("qdc.metadada.titol"));
            info.setAuthor(messages.getString("qdc.metadada.autor"));
            info.setCreator("Archium");
            info.setSubject(messages.getString("qdc.metadada.titol"));

            document.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderFooterHandler(doc, messages));


            float[] pointColumnWidths = {1.5f, 4.5f, 0.3f, 1, 4.5f};
            Table taula = new Table(pointColumnWidths);
            taula.setWidth(UnitValue.createPercentValue(100));
            taula.setFixedLayout();

            ISplitCharacters noSplit = (text, glyphPos) -> false;

            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qdc.header.funcio.codi")).setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack())).setBorderBottom(new SolidBorder(1)));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qdc.header.funcio")).setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack())).setBorderBottom(new SolidBorder(1)));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qdc.header.gdib")).setHorizontalAlignment(HorizontalAlignment.CENTER)/*.setRotationAngle((3 * Math.PI) / 2).setSplitCharacters(noSplit)*/.setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack())).setBorderBottom(new SolidBorder(1)));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qdc.header.serie.codi")).setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack())).setBorderBottom(new SolidBorder(1)));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qdc.header.serie")).setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack())).setBorderBottom(new SolidBorder(1)));

            if (seriesDto != null) {

                Paragraph EMPTY_PARAGRAPH = getFormatedParagraph("");

                String lastFuncio = "";
                boolean nouAmbitFuncional;

                String lastTipusSerie = "";
                int indexTocLlistaSerie = -1;

                for (SerieDto serie : seriesDto) {

                    Cell codiFuncio = getFormatedCell();
                    Cell nomFuncio = getFormatedCell();

                    // Determinació de si és Àmbit funcional o funció (sub-funció)
                    if (1L == serie.getNivell()) {
                        codiFuncio.setFont(notoSansBold).setFontColor(pdfUtils.getColorBlue());
                        nomFuncio.setFont(notoSansBold).setFontColor(pdfUtils.getColorBlue());
                        nouAmbitFuncional = true;
                    } else {
                        codiFuncio.setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack());
                        nomFuncio.setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack());
                        nouAmbitFuncional = false;
                    }
                    codiFuncio.setFont(notoSansBold).setFontSize(Math.max(10 - (serie.getNivell() - 1), 8));
                    nomFuncio.setFont(notoSansBold).setFontSize(Math.max(10 - (serie.getNivell() - 1), 8));

                    Paragraph pCodiFuncio = new Paragraph().setPadding(0).setBorder(Border.NO_BORDER);
                    Paragraph pNomFuncio = new Paragraph().setPadding(0).setBorder(Border.NO_BORDER);
                    for (int i = 0; i < (serie.getNivell() - 1) * 6; i++) {
                        pCodiFuncio.add("\u00A0");
                        pNomFuncio.add("\u00A0");

                    }

                    Paragraph p;

                    // Control de si la funció és la mateixa

                    if (!serie.getCodiFuncio().equals(lastFuncio)) {

                        // Control del canvi de tipus de sèrie per a completar l'índex de continguts
                        if (!serie.getTipusSerie().equals(lastTipusSerie)) {
                            indexTocLlistaSerie++;

                            p = getFormatedParagraph(serie.getTipusSerie());
                            SimpleEntry<String, Integer> titlePage = new SimpleEntry<>(serie.getTipusSerie(), document.getNumberOfPages());
                            p

                                    .setKeepWithNext(true)
                                    .setDestination(serie.getFuncioCompleta())

                                    // Add the current page number to the table of contents list
                                    .setNextRenderer(new UpdatePageRenderer(p, titlePage));

                            toc.add(new SimpleEntry<>(serie.getTipusSerie(), titlePage));

                            lastTipusSerie = serie.getTipusSerie();
                        }

                        p = getFormatedParagraph(serie.getCodiFuncio());
                        if (nouAmbitFuncional) {
                            String line = serie.getFuncioCompleta();

                            SimpleEntry<String, Integer> titlePage = new SimpleEntry<>(line, document.getNumberOfPages());
                            p

                                    .setKeepWithNext(true)
                                    .setDestination(serie.getFuncioCompleta())

                                    // Add the current page number to the table of contents list
                                    .setNextRenderer(new UpdatePageRenderer(p, titlePage));

                            toc.add(new SimpleEntry<>(serie.getFuncioCompleta(), titlePage));
                        }
                        taula.addCell(codiFuncio.add(pCodiFuncio.add(p)));
                        taula.addCell(nomFuncio.add(pNomFuncio.add(getFormatedParagraph(serie.getAmbitFuncional()))));



                        lastFuncio = serie.getCodiFuncio();

                        // Afegim 4 caselles (de manera que les sèries vagin abaix de la funció)
                        if (serie.getCodiSerie() == null) {
                            log.debug("La serie " + serie + " té el codi de sèrie NULL! Afegint 3 espais (gdib, codi sd, sd)...");
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                        } else {
                            log.debug("La serie " + serie + " no té el codi de sèrie NULL! " + serie.getCodiSerie() + " = " + serie.getCodiSerie().length());
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                        }
                    } else {
                        log.debug("Deixant 2 espais perquè la darrera funció és com l'actual...");
                        taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                        taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                    }

                    if (serie.getCodiSerie() != null) {
                        // Afegir els valors de GDIB
                        log.debug("Serie: " + serie.getCodiSerie() + ". EnviatSAT: " + serie.getEnviatSAT());
                        switch (serie.getEnviatSAT()) {
                            case "No":
                                Div rectangle = new Div()
                                        .setHeight(10)
                                        .setWidth(10)
                                        .setBackgroundColor(pdfUtils.getColorRed())
                                        .setBorderRadius(new BorderRadius(5))
                                        .setBorder(new SolidBorder(pdfUtils.getColorRed(), 1));

                                taula.addCell(getFormatedCell().add(rectangle));
                                break;
                            case "Sí":
                                rectangle = new Div()
                                        .setHeight(10)
                                        .setWidth(10)
                                        .setBackgroundColor(pdfUtils.getColorGreen())
                                        .setBorderRadius(new BorderRadius(5))
                                        .setBorder(new SolidBorder(pdfUtils.getColorGreen(), 1));
                                taula.addCell(getFormatedCell().add(rectangle));
                                break;
                            default:
                                taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                        }
                        taula.addCell(getFormatedCell().add(getFormatedParagraph(serie.getCodiSerie()).setFontSize(8).setFontColor(pdfUtils.getColorBlue())));
                        taula.addCell(getFormatedCell().add(getFormatedParagraph(serie.getNomSerie()).setFontSize(8).setFontColor(pdfUtils.getColorBlue())));
                    }
                }
            }

            doc.add(taula);

            doc.add(new AreaBreak());

            // int tocPageNumber = document.getNumberOfPages();
            // Create table of contents
            Paragraph p = new Paragraph(messages.getString("qdc.header.toc"))
                    .setFont(notoSansBold)
                    .setDestination("toc");
            doc.add(p);

            List<TabStop> tabStops = new ArrayList<>();
            tabStops.add(new TabStop(PageSize.A4.getHeight() - 15, TabAlignment.RIGHT, new DottedLine()));


            for (int i = 0; i < toc.size(); i++) {
                SimpleEntry<String, SimpleEntry<String, Integer>> entry = toc.get(i);
                SimpleEntry<String, Integer> text = entry.getValue();

                if (0 == text.getValue()) { // Cas pels tipus de sèrie
                    p = new Paragraph()
                            .add(text.getKey())
                            .setAction(PdfAction.createGoTo(toc.get(i+1).getKey()))  // Es posa com a acció el següent ítem (la primera funció d'aquell nou tipus de sèrie)
                            .setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack()).setFontSize(12);
                } else {
                    p = new Paragraph()
                            .addTabStops(tabStops)
                            .add(text.getKey())
                            .add(new Tab())
                            .add(String.valueOf(text.getValue()))
                            .setAction(PdfAction.createGoTo(entry.getKey()))
                            .setFont(notoSansBold).setFontColor(pdfUtils.getColorBlue());
                }
                doc.add(p);
            }

            // Move the table of contents to the first page

            //document.movePage(tocPageNumber, 1);

            // Add page labels
            /*document.getPage(1).setPageLabel(PageLabelNumberingStyle.UPPERCASE_LETTERS,
                    null, 1);
            document.getPage(2).setPageLabel(PageLabelNumberingStyle.DECIMAL_ARABIC_NUMERALS,
                    null, 1);*/




            document.close();
            byteArrayOutputStream.write(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
    
    protected class HeaderFooterHandler implements IEventHandler {
        protected Document doc;

        private ResourceBundle messages;

        public HeaderFooterHandler(Document doc, ResourceBundle messages) {
            this.doc = doc;
            this.messages = messages;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfDocument pdfDoc = ((PdfDocumentEvent) event).getDocument();
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            InputStream is = TaulaValoracioEJB.class.getResourceAsStream("/img/goib-arxiu.jpg");
            byte[] targetArray;
            try {
                targetArray = new byte[is.available()];
                is.read(targetArray);
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ImageData data = ImageDataFactory.create(targetArray);
            // Creating an Image object
            Image logo = new Image(data);
            logo.scaleToFit(50, 50);
            logo.setTextAlignment(TextAlignment.LEFT);
            logo.setFixedPosition(doc.getLeftMargin(), pageSize.getTop() - 60);

            PdfFont notoSans = pdfUtils.getFont("/font/NotoSans-Regular.ttf");

            PdfFont notoSansBold = pdfUtils.getFont("/font/NotoSans-Bold.ttf");

            Div rectangleVermell = new Div()
                    .setHeight(5)
                    .setWidth(5)
                    .setBackgroundColor(pdfUtils.getColorRed())
                    .setBorderRadius(new BorderRadius(5))
                    .setFixedPosition(pageSize.getWidth() - doc.getRightMargin() - 255, pageSize.getBottom() + 52, UnitValue.POINT)
                    .setBorder(new SolidBorder(pdfUtils.getColorRed(), 1));

            Div rectangleVerd = new Div()
                    .setHeight(5)
                    .setWidth(5)
                    .setBackgroundColor(pdfUtils.getColorGreen())
                    .setBorderRadius(new BorderRadius(5))
                    .setFixedPosition(pageSize.getWidth() - doc.getRightMargin() - 255, pageSize.getBottom() + 65, UnitValue.POINT)
                    .setBorder(new SolidBorder(pdfUtils.getColorGreen(), 1));


            ZonedDateTime madrid = now.withZoneSameInstant(ZoneId.of("Europe/Madrid"));

            new Canvas(pdfCanvas, pageSize).setFont(notoSansBold).setFontColor(pdfUtils.getColorOficial()).setFontSize(10)
                    //header
                    .add(logo)
                    .showTextAligned(messages.getString("qdc.autor"), pageSize.getWidth() - doc.getRightMargin(), pageSize.getTop() - 20, TextAlignment.RIGHT, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned(messages.getString("qdc.titol"), pageSize.getWidth() / 2, pageSize.getTop() - 20, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0)
                    .setFontSize(8)
                    .setFont(notoSans)
                    .showTextAligned(messages.getString("qdc.versio") + madrid.format(formatter), pageSize.getWidth() - doc.getRightMargin(), pageSize.getTop() - 30, TextAlignment.RIGHT, VerticalAlignment.MIDDLE, 0)
                    //footer
                    .setFontSize(8)
                    .setFontColor(pdfUtils.getColorBlack())
                    .showTextAligned(messages.getString("qdc.footer.departament"), doc.getLeftMargin(), doc.getBottomMargin() - 20, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned("C/ de l'Uruguai, s/n (Velòdrom Illes Balears)", doc.getLeftMargin(), doc.getBottomMargin() - 30, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned("07010 Palma", doc.getLeftMargin(), doc.getBottomMargin() - 40, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
                    .setFontColor(pdfUtils.getColorOficial())
                    .setFont(notoSansBold)
                    .showTextAligned("" + pdfDoc.getPageNumber(page), pageSize.getWidth() - doc.getRightMargin(), doc.getBottomMargin() - 20, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned("arxiu.caib.es", doc.getLeftMargin(), doc.getBottomMargin() - 50, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
                    .setFontColor(pdfUtils.getColorBlack())
                    .setFont(notoSans)
                    .add(rectangleVerd)
                    .add(rectangleVermell)
                    .showTextAligned(messages.getString("qdc.llegenda"), pageSize.getWidth() - doc.getRightMargin() - 250, doc.getBottomMargin() - 30, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

        }
    }


    //LOS METODOS XXXXNUEVO SE HAN AÑADIDO TRAS LA INTEGRACIÓN DEL CÓDIGO DEL CLIENTE
    //NO SE SOBREESCRIBEN LOS ANTERIORES YA QUE SE LLAMAN DESDE EL MODULO FRONT EN LOS CONTROLLERS

    @Override
    public InputStream generaPdfQuadreQuassificacioNuevo(List<SerieDto> seriesDto, String titol, Idioma idioma) {
    	log.info("Entrando en el metodo generaPdfQuadreQuassificacioNuevo de QuadreClassificacioEJB");

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Inicialitzam la taula de continguts
        List<SimpleEntry<String, SimpleEntry<String, Integer>>> toc = new ArrayList<>();

        try {
            PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
            PdfDocument document = new PdfDocument(pdfWriter);
            document.setDefaultPageSize(PageSize.A4.rotate());

            Document doc = new Document(document);

            PdfFont notoSans = pdfUtils.getFont("/font/NotoSans-Regular.ttf");

            PdfFont notoSansBold = pdfUtils.getFont("/font/NotoSans-Bold.ttf");
            doc.setFont(notoSans);
            doc.setFontSize(10);

            doc.setTopMargin(2.25F * (72 / 2.54F)); // 2,25cm
            doc.setBottomMargin(3F * (72 / 2.54F)); // 3cm
            doc.setLeftMargin(1.5F * (72 / 2.54F));   // 1,5 cm
            doc.setRightMargin(1.5F * (72 / 2.54F));  // 1,5cm

            i18nUtils.setLocale(idioma);
            ResourceBundle messages;
            if (idioma.equals(Idioma.CATALA)) {
                messages = messagesCA;
            } else {
                messages = messagesES;
            }

            PdfDocumentInfo info = document.getDocumentInfo();
            info.setTitle(messages.getString("qdc.metadada.titol") + " - " + titol);
            info.setAuthor(messages.getString("qdc.metadada.autor"));
            info.setCreator("Archium");
            info.setSubject(messages.getString("qdc.metadada.titol") + " - " + titol);

            document.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderFooterHandlerNuevo(doc, titol, messages));


            float[] pointColumnWidths = {1.5f, 4.5f, 0.3f, 1, 4.5f};
            Table taula = new Table(pointColumnWidths);
            taula.setWidth(UnitValue.createPercentValue(100));
            taula.setFixedLayout();

            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qdc.header.funcio.codi")).setBackgroundColor(pdfUtils.getColorBlue()).setFont(notoSansBold).setFontColor(pdfUtils.getColorWhite())));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qdc.header.funcio")).setBackgroundColor(pdfUtils.getColorBlue()).setFont(notoSansBold).setFontColor(pdfUtils.getColorWhite())));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qdc.header.gdib"))).setBackgroundColor(pdfUtils.getColorBlue()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFont(notoSansBold).setFontColor(pdfUtils.getColorWhite()));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qdc.header.serie.codi")).setBackgroundColor(pdfUtils.getColorBlue()).setFont(notoSansBold).setFontColor(pdfUtils.getColorWhite())));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qdc.header.serie")).setBackgroundColor(pdfUtils.getColorBlue()).setFont(notoSansBold).setFontColor(pdfUtils.getColorWhite())));

            taula.setBorder(new SolidBorder(1));

            if (seriesDto != null) {

                Paragraph EMPTY_PARAGRAPH = getFormatedParagraph("");

                String lastFuncio = "";
                boolean nouAmbitFuncional;

                String lastTipusSerie = "";

                Set<SerieDto> serieDtoSet = new LinkedHashSet<>(seriesDto);

                for (SerieDto serie : serieDtoSet) {

                    Cell codiFuncio = getFormatedCell();
                    Cell nomFuncio = getFormatedCell();

                    // Determinació de si és Àmbit funcional o funció (sub-funció)
                    if (1L == serie.getNivell()) {
                        codiFuncio.setFont(notoSansBold).setFontColor(pdfUtils.getColorBlue());
                        nomFuncio.setFont(notoSansBold).setFontColor(pdfUtils.getColorBlue());
                        nouAmbitFuncional = true;
                    } else {
                        codiFuncio.setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack());
                        nomFuncio.setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack());
                        nouAmbitFuncional = false;
                    }
                    codiFuncio.setFont(notoSansBold).setFontSize(Math.max(10 - (serie.getNivell() - 1), 8));
                    nomFuncio.setFont(notoSansBold).setFontSize(Math.max(10 - (serie.getNivell() - 1), 8));

                    Paragraph pCodiFuncio = new Paragraph().setPadding(0).setBorder(Border.NO_BORDER);
                    Paragraph pNomFuncio = new Paragraph().setPadding(0).setBorder(Border.NO_BORDER);
                    for (int i = 0; i < (serie.getNivell() - 1) * 6; i++) {
                        pCodiFuncio.add("\u00A0");
                        pNomFuncio.add("\u00A0");

                    }

                    Paragraph p;

                    // Control de si la funció és la mateixa

                    if (!serie.getCodiFuncio().equals(lastFuncio)) {

                        // Control del canvi de tipus de sèrie per a completar l'índex de continguts
                        if (!serie.getTipusSerie().equals(lastTipusSerie)) {

                            p = getFormatedParagraph(serie.getTipusSerie());

                            String titolTipusSerie;
                            switch (serie.getTipusSerie()) {
                                case "Gestió":
                                    titolTipusSerie = "SÈRIES DOCUMENTALS DE GESTIÓ O COMUNES";
                                    break;
                                case "Gestión":
                                    titolTipusSerie = "SERIES DOCUMENTALES DE GESTIÓN O COMUNES";
                                    break;
                                case "Explotació":
                                    titolTipusSerie = "SÈRIES DOCUMENTALS D'EXPLOTACIÓ O ESPECÍFIQUES";
                                    break;
                                default:
                                    titolTipusSerie = "SERIES DOCUMENTALES DE EXPLOTACIÓN O ESPECÍFICAS";
                                    break;
                            }

                            SimpleEntry<String, Integer> titlePage = new SimpleEntry<>(titolTipusSerie, document.getNumberOfPages());
                            p

                                    .setKeepWithNext(true)
                                    .setDestination(serie.getFuncioCompleta())

                                    // Add the current page number to the table of contents list
                                    .setNextRenderer(new UpdatePageRenderer(p, titlePage));

                            toc.add(new SimpleEntry<>(titolTipusSerie, titlePage));

                            lastTipusSerie = serie.getTipusSerie();
                        }

                        p = getFormatedParagraph(serie.getCodiFuncio());
                        if (nouAmbitFuncional) {
                            String line = serie.getFuncioCompleta();

                            SimpleEntry<String, Integer> titlePage = new SimpleEntry<>(line, document.getNumberOfPages());
                            p

                                    .setKeepWithNext(true)
                                    .setDestination(serie.getFuncioCompleta())

                                    // Add the current page number to the table of contents list
                                    .setNextRenderer(new UpdatePageRenderer(p, titlePage));

                            toc.add(new SimpleEntry<>(serie.getFuncioCompleta(), titlePage));
                        }
                        taula.addCell(codiFuncio.add(pCodiFuncio.add(p)));
                        taula.addCell(nomFuncio.add(pNomFuncio.add(getFormatedParagraph(serie.getAmbitFuncional()))));


                        lastFuncio = serie.getCodiFuncio();

                        // Afegim 4 caselles (de manera que les sèries vagin abaix de la funció)
                        if (serie.getCodiSerie() == null) {

                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                        } else {

                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                            taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                        }
                    } else {
                        taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                        taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                    }

                    if (serie.getCodiSerie() != null) {
                        // Afegir els valors de GDIB

                        switch (serie.getEnviatSAT()) {
                            case "No":
                                /*Div rectangle = new Div()
                                        .setHeight(10)
                                        .setWidth(10)
                                        .setBackgroundColor(pdfUtils.getColorRed())
                                        .setBorderRadius(new BorderRadius(5))
                                        .setBorder(new SolidBorder(pdfUtils.getColorRed(), 1));

                                taula.addCell(getFormatedCell().add(rectangle));*/
                                taula.addCell(getFormatedCell().add(getFormatedParagraph("P").setFontColor(pdfUtils.getColorRed()).setFontSize(8)));
                                break;
                            case "Sí":
                                /*rectangle = new Div()
                                        .setHeight(10)
                                        .setWidth(10)
                                        .setBackgroundColor(pdfUtils.getColorGreen())
                                        .setBorderRadius(new BorderRadius(5))
                                        .setBorder(new SolidBorder(pdfUtils.getColorGreen(), 1));
                                taula.addCell(getFormatedCell().add(rectangle));*/
                                taula.addCell(getFormatedCell().add(getFormatedParagraph("D").setFontColor(pdfUtils.getColorGreen()).setFontSize(8)));
                                break;
                            default:
                                taula.addCell(getFormatedCell().add(EMPTY_PARAGRAPH));
                                //taula.addCell(getFormatedCell().add(getFormatedParagraph("I").setFontColor(pdfUtils.getColorGrey()).setFontSize(8)));
                        }
                        taula.addCell(getFormatedCell().add(getFormatedParagraph(serie.getCodiSerie()).setFontSize(8).setFontColor(pdfUtils.getColorBlack())));
                        taula.addCell(getFormatedCell().add(getFormatedParagraph(serie.getNomSerie()).setFontSize(8).setFontColor(pdfUtils.getColorBlack())));
                    }
                }
            }

            doc.add(taula);

            doc.add(new AreaBreak());

            // int tocPageNumber = document.getNumberOfPages();
            // Create table of contents
            Paragraph p = new Paragraph(messages.getString("qdc.header.toc"))
                    .setFont(notoSansBold)
                    .setDestination("toc");
            doc.add(p);

            List<TabStop> tabStops = new ArrayList<>();
            tabStops.add(new TabStop(PageSize.A4.getHeight() - 15, TabAlignment.RIGHT, new DottedLine()));


            for (int i = 0; i < toc.size(); i++) {
                SimpleEntry<String, SimpleEntry<String, Integer>> entry = toc.get(i);
                SimpleEntry<String, Integer> text = entry.getValue();

                if (0 == text.getValue()) { // Cas pels tipus de sèrie
                    p = new Paragraph()
                            .add(text.getKey())
                            // TODO: Estaba con get(i+1)
                            .setAction(PdfAction.createGoTo(toc.get(i).getKey()))  // Es posa com a acció el següent ítem (la primera funció d'aquell nou tipus de sèrie)
                            .setFont(notoSansBold).setFontColor(pdfUtils.getColorWhite()).setFontSize(12).setBackgroundColor(pdfUtils.getColorBlue());
                } else {
                    p = new Paragraph()
                            .addTabStops(tabStops)
                            .add(text.getKey())
                            .add(new Tab())
                            .add(String.valueOf(text.getValue()))
                            .setAction(PdfAction.createGoTo(entry.getKey()))
                            .setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack());
                }
                doc.add(p);
            }

            // Move the table of contents to the first page

            //document.movePage(tocPageNumber, 1);

            // Add page labels
            /*document.getPage(1).setPageLabel(PageLabelNumberingStyle.UPPERCASE_LETTERS,
                    null, 1);
            document.getPage(2).setPageLabel(PageLabelNumberingStyle.DECIMAL_ARABIC_NUMERALS,
                    null, 1);*/


            document.close();
            byteArrayOutputStream.write(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    	log.info("Saliendo del metodo generaPdfQuadreQuassificacioNuevo de QuadreClassificacioEJB");
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    private Cell getFormatedCell() {
        return new Cell().setPadding(0).setMargin(0).setBorder(Border.NO_BORDER);
    }

    private Paragraph getFormatedParagraph(String paragraph) {
        return new Paragraph(paragraph != null ? paragraph : "").setPadding(0).setMargin(0).setMultipliedLeading(1f).setSpacingRatio(1).setBorder(Border.NO_BORDER);
    }

    
    protected class HeaderFooterHandlerNuevo implements IEventHandler {

        protected Document doc;
        private String titol;
        private ResourceBundle messages;

        public HeaderFooterHandlerNuevo(Document doc, String titol, ResourceBundle messages) {
            this.doc = doc;
            this.titol = titol;
            this.messages = messages;
        }

        @Override
        public void handleEvent(Event event) {
            PdfDocumentEvent docEvent = (PdfDocumentEvent) event;
            PdfPage page = docEvent.getPage();
            Rectangle pageSize = page.getPageSize();
            PdfDocument pdfDoc = docEvent.getDocument();
            PdfCanvas pdfCanvas = new PdfCanvas(page.newContentStreamBefore(), page.getResources(), pdfDoc);

            InputStream is = QuadreClassificacioEJB.class.getResourceAsStream("/img/goib-arxiu-2.jpg");
            byte[] targetArray;
            try {
                targetArray = new byte[is.available()];
                is.read(targetArray);
                is.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }

            ImageData data = ImageDataFactory.create(targetArray);
            Image logo = new Image(data);
            logo.scaleToFit(90, 90);
            logo.setTextAlignment(TextAlignment.LEFT);
            logo.setFixedPosition(doc.getLeftMargin(), pageSize.getTop() - 60);

            PdfFont notoSans = pdfUtils.getFont("/font/NotoSans-Regular.ttf");
            PdfFont notoSansBold = pdfUtils.getFont("/font/NotoSans-Bold.ttf");

            Div rectangleVermell = new Div()
                    .setHeight(5)
                    .setWidth(5)
                    .setBackgroundColor(pdfUtils.getColorRed())
                    .setBorderRadius(new BorderRadius(5))
                    .setFixedPosition(pageSize.getWidth() - doc.getRightMargin() - 255, pageSize.getBottom() + 52, UnitValue.POINT)
                    .setBorder(new SolidBorder(pdfUtils.getColorRed(), 1));

            Div rectangleVerd = new Div()
                    .setHeight(5)
                    .setWidth(5)
                    .setBackgroundColor(pdfUtils.getColorGreen())
                    .setBorderRadius(new BorderRadius(5))
                    .setFixedPosition(pageSize.getWidth() - doc.getRightMargin() - 255, pageSize.getBottom() + 65, UnitValue.POINT)
                    .setBorder(new SolidBorder(pdfUtils.getColorGreen(), 1));

            ZonedDateTime madrid = now.withZoneSameInstant(ZoneId.of("Europe/Madrid"));

            new Canvas(pdfCanvas, pageSize)
                    .setFont(notoSansBold).setFontColor(pdfUtils.getColorOficial()).setFontSize(10)
                    // header
                    .add(logo)
                    .showTextAligned(messages.getString("qdc.autor"), pageSize.getWidth() - doc.getRightMargin(), pageSize.getTop() - 20, TextAlignment.RIGHT, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned(messages.getString("qdc.titol"), pageSize.getWidth() / 2, pageSize.getTop() - 20, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0)
                    .setFontSize(8).setFont(notoSans)
                    .showTextAligned(this.titol, pageSize.getWidth() / 2, pageSize.getTop() - 30, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned(messages.getString("qdc.versio") + madrid.format(formatter), pageSize.getWidth() - doc.getRightMargin(), pageSize.getTop() - 30, TextAlignment.RIGHT, VerticalAlignment.MIDDLE, 0)
                    // footer
                    .setFontSize(8)
                    .setFontColor(pdfUtils.getColorBlack())
                    .showTextAligned("C/ de l'Uruguai, s/n (Velòdrom Illes Balears)", doc.getLeftMargin(), doc.getBottomMargin() - 30, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned("07010 Palma", doc.getLeftMargin(), doc.getBottomMargin() - 40, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
                    .setFontColor(pdfUtils.getColorOficial()).setFont(notoSansBold)
                    .showTextAligned("" + pdfDoc.getPageNumber(page), pageSize.getWidth() - doc.getRightMargin(), doc.getBottomMargin() - 20, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned("arxiu.caib.es", doc.getLeftMargin(), doc.getBottomMargin() - 50, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
                    .setFontColor(pdfUtils.getColorBlack()).setFont(notoSans);
                    //.add(rectangleVerd)
                    //.add(rectangleVermell)
                    //.showTextAligned(messages.getString("qdc.llegenda"), pageSize.getWidth() - doc.getRightMargin() - 250, doc.getBottomMargin() - 30, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);
        }
    }

    private static class UpdatePageRenderer extends ParagraphRenderer {
        protected SimpleEntry<String, Integer> entry;

        public UpdatePageRenderer(Paragraph modelElement, SimpleEntry<String, Integer> entry) {
            super(modelElement);
            this.entry = entry;
        }

        @Override
        public LayoutResult layout(LayoutContext layoutContext) {
            LayoutResult result = super.layout(layoutContext);
            entry.setValue(layoutContext.getArea().getPageNumber());
            return result;
        }
    }
}

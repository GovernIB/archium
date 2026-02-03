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
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DottedBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.model.Estat;
import es.caib.archium.commons.utils.I18nUtils;
import es.caib.archium.commons.utils.PdfUtils;
import es.caib.archium.commons.utils.StringUtilitats;
import es.caib.archium.ejb.objects.QuadreTipusDocumentalsDto;
import es.caib.archium.ejb.objects.TipusDocumentalDto;
import es.caib.archium.ejb.service.QuadreTipusDocumentalService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.QuadreTipusDocumental;

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
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class QuadreTipusDocumentalEJB extends AbstractDAO<QuadreTipusDocumental, Long> implements QuadreTipusDocumentalService {

    @Inject
    private PdfUtils pdfUtils;

    @Inject
    private I18nUtils i18nUtils;

    @Inject
    private StringUtilitats stringUtilitats;

    private ResourceBundle messagesES = ResourceBundle.getBundle("messages.ejb", Idioma.CASTELLA.getLocale());

    private ResourceBundle messagesCA = ResourceBundle.getBundle("messages.ejb", Idioma.CATALA.getLocale());

    private String queryQdT;

    @PostConstruct
    public void init() {
        InputStream inputStream = DictamenEJB.class.getResourceAsStream("/queries/qdt.sql");
        queryQdT = new BufferedReader(
                new InputStreamReader(inputStream, StandardCharsets.UTF_8))
                .lines()
                .collect(Collectors.joining("\n"));
        //log.debug("Query quadre de tipus = " + queryQdT);
    }

    @Override
    @PermitAll
    public QuadreTipusDocumental getQuadreTipusDocumentalPerDefecte() {
        return this.getReference(getQuadreTipusDocumentalPerDefecteId());
    }

    @Override
    @PermitAll
    public Long getQuadreTipusDocumentalPerDefecteId() {
        // TODO Deute tècnic: Pendent implementar quadre per defecte mitjançant base de dades
        return 1L;
    }

    @Override
    @PermitAll
    public QuadreTipusDocumentalsDto getQuadreTipusDocumental(Long quadreId, Idioma idioma, Estat estat) {
        log.debug("Consultant dades del quadre de tipus documentals. quadreId = " + quadreId + ". idioma = " + idioma + ". estat = " + estat);
        Query queryQdT = entityManager.createNativeQuery(this.queryQdT);
        queryQdT.setParameter("codiQuadre", quadreId);
        queryQdT.setParameter("estatTd", estat != null ? estat.getValue().toLowerCase() : "");

        List<Object[]> items = queryQdT.getResultList();

        QuadreTipusDocumentalsDto quadreTD = new QuadreTipusDocumentalsDto();


        for (Object[] a : items) {
            TipusDocumentalDto tdDto = new TipusDocumentalDto();
            tdDto.setCodi(a[0].toString());
            if (Idioma.CASTELLA.equals(idioma)) {
                tdDto.setNom(stringUtilitats.nullSafe(a[2]));
                tdDto.setNomNti(stringUtilitats.nullSafe(a[5]));
                tdDto.setDefinicio(stringUtilitats.nullSafe(a[7]));
            } else {
                tdDto.setNom(stringUtilitats.nullSafe(a[1]));
                tdDto.setNomNti(stringUtilitats.nullSafe(a[4]));
                tdDto.setDefinicio(stringUtilitats.nullSafe(a[6]));
            }
            tdDto.setCodiNti(stringUtilitats.nullSafe(a[3]));

            quadreTD.afegirTipus(tdDto);
        }
        return quadreTD;
    }

    // Data actual per posar a la versió
    private ZonedDateTime now = ZonedDateTime.now();

    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    @Override
    @PermitAll
    public InputStream generaPdfQuadreTipusDocumentals(QuadreTipusDocumentalsDto quadreTipusDocumentalDto, Idioma idioma) {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
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
            info.setTitle(messages.getString("qtd.metadada.titol"));
            info.setAuthor(messages.getString("qtd.metadada.autor"));
            info.setCreator("Archium");
            info.setSubject(messages.getString("qtd.metadada.titol"));

            document.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderFooterHandler(doc, messages, pdfUtils, now, formatter));


            float[] pointColumnWidths = {0.7f, 0.7f, 0.7f, 0.7f, 5};
            Table taula = new Table(pointColumnWidths);
            taula.setWidth(UnitValue.createPercentValue(100));
            taula.setFixedLayout();
            taula.setFontColor(pdfUtils.getColorBlack());
            taula.setFontSize(8);
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qtd.header.td.codi")).setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack())));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qtd.header.td")).setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack())));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qtd.header.nti.codi")).setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack())));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qtd.header.nti")).setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack())));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qtd.header.definicio")).setFont(notoSansBold).setFontColor(pdfUtils.getColorBlack())));

            if (quadreTipusDocumentalDto.getTipus() != null) {
                for (TipusDocumentalDto td : quadreTipusDocumentalDto.getTipus()) {

                    taula.addCell(getFormatedCell().add(getFormatedParagraph(pdfUtils.tractarCampStr(td.getCodi()))));
                    taula.addCell(getFormatedCell().add(getFormatedParagraph(pdfUtils.tractarCampStr(td.getNom()))));
                    taula.addCell(getFormatedCell().add(getFormatedParagraph(pdfUtils.tractarCampStr(td.getCodiNti()))));
                    taula.addCell(getFormatedCell().add(getFormatedParagraph(pdfUtils.tractarCampStr(td.getNomNti()))));
                    taula.addCell(getFormatedCell().add(getFormatedParagraph(pdfUtils.tractarCampStr(td.getDefinicio())).setTextAlignment(TextAlignment.JUSTIFIED)));
                }
            }
            doc.add(taula);

            document.close();
            byteArrayOutputStream.write(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    public Cell getFormatedCell() {
        DottedBorder db = new DottedBorder(pdfUtils.getColorOficial(), 1);
        return new Cell().setPadding(0).setMargin(0).setBorder(Border.NO_BORDER).setBorderBottom(db);
    }

    public Paragraph getFormatedParagraph(String paragraph) {
        return new Paragraph(paragraph != null ? paragraph : "").setPadding(0).setMargin(0).setMultipliedLeading(1f).setSpacingRatio(1).setBorder(Border.NO_BORDER);
    }

    public static class HeaderFooterHandler implements IEventHandler {
        protected Document doc;

        private ResourceBundle messages;
        private PdfUtils pdfUtils;
        private ZonedDateTime now;
        protected DateTimeFormatter formatter;

        public HeaderFooterHandler(Document doc, ResourceBundle messages, PdfUtils pdfUtils, ZonedDateTime now, DateTimeFormatter formatter) {
            this.doc = doc;
            this.messages = messages;
            this.pdfUtils = pdfUtils;
            this.now = now;
            this.formatter = formatter;
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
            Image logo = new Image(data);
            logo.scaleToFit(50, 50);
            logo.setTextAlignment(TextAlignment.LEFT);
            logo.setFixedPosition(doc.getLeftMargin(), pageSize.getTop() - 60);

            PdfFont notoSans = pdfUtils.getFont("/font/NotoSans-Regular.ttf");

            PdfFont notoSansBold = pdfUtils.getFont("/font/NotoSans-Bold.ttf");

            ZonedDateTime madrid = now.withZoneSameInstant(ZoneId.of("Europe/Madrid"));

            new Canvas(pdfCanvas, pageSize).setFont(notoSansBold).setFontColor(pdfUtils.getColorOficial()).setFontSize(10)
                    //header
                    .add(logo)
                    .showTextAligned(messages.getString("qtd.autor"), pageSize.getWidth() - doc.getRightMargin(), pageSize.getTop() - 20, TextAlignment.RIGHT, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned(messages.getString("qtd.titol"), pageSize.getWidth() / 2, pageSize.getTop() - 20, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0)
                    .setFontSize(8)
                    .setFont(notoSans)
                    .showTextAligned(messages.getString("qtd.versio") + madrid.format(formatter), pageSize.getWidth() - doc.getRightMargin(), pageSize.getTop() - 30, TextAlignment.RIGHT, VerticalAlignment.MIDDLE, 0)
                    //footer
                    .setFontSize(8)
                    .setFontColor(pdfUtils.getColorBlack())
                    .showTextAligned(messages.getString("qdc.footer.departament"), doc.getLeftMargin(), doc.getBottomMargin() - 20, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned("C/ de l'Uruguai, s/n (Velòdrom Illes Balears)", doc.getLeftMargin(), doc.getBottomMargin() - 30, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned("07010 Palma", doc.getLeftMargin(), doc.getBottomMargin() - 40, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
                    .setFontColor(pdfUtils.getColorOficial())
                    .setFont(notoSansBold)
                    .showTextAligned("" + pdfDoc.getPageNumber(page), pageSize.getWidth() - doc.getRightMargin(), doc.getBottomMargin() - 20, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned("arxiu.caib.es", doc.getLeftMargin(), doc.getBottomMargin() - 50, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

        }
    }
}
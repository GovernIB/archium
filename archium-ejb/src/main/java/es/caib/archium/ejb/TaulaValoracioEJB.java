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
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.kernel.pdf.canvas.PdfCanvas;
import com.itextpdf.kernel.pdf.canvas.draw.SolidLine;
import com.itextpdf.kernel.pdf.extgstate.PdfExtGState;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.LineSeparator;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Tab;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.element.Text;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.model.Estat;
import es.caib.archium.commons.utils.PdfUtils;
import es.caib.archium.ejb.objects.Dir3;
import es.caib.archium.ejb.service.DictamenService;
import es.caib.archium.ejb.service.Dir3Service;
import es.caib.archium.ejb.service.TaulaValoracioService;
import es.caib.archium.persistence.model.Dictamen;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TaulaValoracioEJB implements TaulaValoracioService {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    DictamenService dictamenEJB;

    @Inject
    Dir3Service dir3EJB;

    @Inject
    PdfUtils pdfUtils;


    @Override
    public InputStream getTaulaValoracioPdf(Long idDictamen) {
        Dictamen d = dictamenEJB.findById(idDictamen);
        InputStream taulaValoracioPdf = null;

        if (d != null && Estat.VIGENT.equals(Estat.getEstat(d.getEstat()))) {
            // TODO Accedir al gestor documental per retornar la taula de valoració!
            taulaValoracioPdf = generateTaulaValoracioPdf(idDictamen);
        } else {
            taulaValoracioPdf = generateTaulaValoracioPdf(idDictamen);
        }
        return taulaValoracioPdf;
    }


    private InputStream generateTaulaValoracioPdf(Long idDictamen) {

        log.debug("Generant PDF de taula de valoració per al dictamen " + idDictamen);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        try {

            // Query específica per obtenir totes les dades necessàries per a generar la taula de valoració
            Object[] dadesTaula = dictamenEJB.getDadesTaulaValoracio(idDictamen).get(0);
            Estat estatDictamen = Estat.getEstat((String) dadesTaula[ESTAT]);

            PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);
            PdfDocument document = new PdfDocument(pdfWriter);
            document.setDefaultPageSize(PageSize.A4);

            Document doc = new Document (document);

            PdfFont notoSans = pdfUtils.getFont("/font/NotoSans-Regular.ttf");

            PdfFont notoSansBold = pdfUtils.getFont("/font/NotoSans-Bold.ttf");
            doc.setFont(notoSans);
            doc.setFontSize(10);

            doc.setTopMargin(2.25F * (72/2.54F)); // 2,25cm
            doc.setBottomMargin(3F * (72/2.54F)); // 3cm
            doc.setLeftMargin(1.5F * (72/2.54F));   // 1,5 cm
            doc.setRightMargin(1.5F * (72/2.54F));  // 1,5cm

            PdfDocumentInfo info = document.getDocumentInfo();
            if (isEstatProposta(estatDictamen)) {
                info.setTitle("Proposta de taula de valoració: " + dadesTaula[GUARDAR_COMO]);
            } else {
                info.setTitle("Taula de valoració: " + dadesTaula[GUARDAR_COMO]);
            }
            info.setAuthor("Servei d'Arxiu i Gestió Documental (CAIB)");
            info.setCreator("Archium");

            document.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderFooterHandler(doc));

            // Títol
            Paragraph titol = new Paragraph("Comissió Interinsular Qualificadora de Documents\nGovern de les Illes Balears\n").setFontSize(14).setMultipliedLeading(0.5f);
            titol.setTextAlignment(TextAlignment.CENTER);

            SolidLine line = new SolidLine(1f);
            line.setColor(pdfUtils.getColorOficial());

            LineSeparator sep1 = new LineSeparator(line);
            sep1.setWidth(UnitValue.createPercentValue(90));
            sep1.setMarginTop(5);

            // sep1.setOffset(-2);
            titol.add(sep1);

            LineSeparator sep2 = new LineSeparator(line);
            // sep2.setOffset(-5);
            titol.add(sep2);
            doc.add(titol);


            Paragraph subtitol;
            // Subtítol
            if (isEstatProposta(estatDictamen)) {
                subtitol = new Paragraph("PROPOSTA DE TAULA D'AVALUACIÓ DOCUMENTAL").setFont(notoSansBold).setFontSize(11);
            } else {
                subtitol = new Paragraph("TAULA D'AVALUACIÓ DOCUMENTAL").setFont(notoSansBold).setFontSize(11);
            }
            subtitol.setTextAlignment(TextAlignment.CENTER);
            doc.add(subtitol);

            Table taulaTaula = new Table(1);
            taulaTaula.setMarginBottom(20);
            taulaTaula.setWidth(UnitValue.createPercentValue(70));
            taulaTaula.setHorizontalAlignment(HorizontalAlignment.CENTER);
            //taulaTaula.setWidth(PageSize.A4.getWidth() - doc.getRightMargin() - doc.getLeftMargin());
            //taulaTaula.setSpacingAfter(15);

            Paragraph p = new Paragraph();
            p.add(new Text("Taula: ").setBold());
            p.add(new Text(dadesTaula[TAULA_AVALUACIO].toString()));

            Cell cell = new Cell().add(p);
            taulaTaula.addCell(cell);
            doc.add(taulaTaula);


            Table taulaResum = new Table(1);
            taulaResum.setWidth(UnitValue.createPercentValue(70));
            taulaResum.setHorizontalAlignment(HorizontalAlignment.CENTER);

            p = new Paragraph();
            p.add(new Text("Sèrie documental: ").setBold());
            p.add(pdfUtils.tractarCamp(dadesTaula, NOM_SERIE));
            p.add("\n");
            p.add(new Text("Codi i títol: ").setBold());
            p.add(new Text(dadesTaula[CODI_SERIE] + " - " + dadesTaula[NOM_SERIE]));
            p.add("\n");
            p.add(new Text("Categoria: ").setBold());
            p.add(pdfUtils.tractarCamp(dadesTaula, TIPUS_SERIE));
            p.add("\n");
            p.add(new Text("Funció: ").setBold());
            p.add(pdfUtils.tractarCamp(dadesTaula, MATERIA_AKA_FUNCIO));
            cell = new Cell().add(p);
            taulaResum.addCell(cell);
            doc.add(taulaResum);

            p = new Paragraph();
            p.setTextAlignment(TextAlignment.JUSTIFIED);
            p.setMarginBottom(10);
            p.add(new Text("Funció administrativa: ").setBold());
            p.add(new Text(dadesTaula[DESCRIPCIO_SERIE].toString().replace("\n", " ").replace("\r", "")));
            doc.add(p);

            p = new Paragraph();
            p.setMultipliedLeading(1);
            p.setTextAlignment(TextAlignment.JUSTIFIED);
            p.setMarginBottom(10);
            p.add(new Text("Marc legal:\n").setBold());
            p.add(pdfUtils.tractarCamp(dadesTaula, MARC_LEGAL));
            doc.add(p);

            p = new Paragraph();
            p.setMultipliedLeading(1);
            p.setTextAlignment(TextAlignment.JUSTIFIED);
            p.setMarginBottom(10);
            p.add(new Text("Documents que formen l'expedient. Tipologia documental:\n").setBold());
            String documentsObligatoris = pdfUtils.tractarCampStr(dadesTaula, DOCUMENTS_OBLIGATORIS);
            documentsObligatoris = documentsObligatoris.concat(pdfUtils.tractarCampStr(dadesTaula, DOCUMENTS_OBLIGATORIS_AGRUPACIONS));
            if (documentsObligatoris.trim().length() < 1) {
                documentsObligatoris = pdfUtils.tractarCampStr(dadesTaula, DOCUMENTS_OBLIGATORIS_COMUNS);
                if (documentsObligatoris == null || documentsObligatoris.trim().length() < 1) {
                    p.add("No s'han identificat");
                } else {
                    String[] partsDocumentsObligatoris = documentsObligatoris.split("@tab@");
                    for (String part : partsDocumentsObligatoris) {
                        p.add(part);
                        p.add(new Tab());
                    }
                }
            } else {
                p.add(documentsObligatoris);
            }
            doc.add(p);

            p = new Paragraph();
            p.setMultipliedLeading(1);
            p.setTextAlignment(TextAlignment.JUSTIFIED);
            p.setMarginBottom(10);
            p.add(new Text("Documents que poden formar part de l'expedient. Tipologia documental:\n").setBold());
            String documentsOpcionals = pdfUtils.tractarCampStr(dadesTaula, DOCUMENTS_OPCIONALS);
            documentsOpcionals = documentsOpcionals.concat(pdfUtils.tractarCampStr(dadesTaula, DOCUMENTS_OPCIONALS_AGRUPACIONS));
            if (documentsOpcionals.trim().length() < 1) {
                documentsOpcionals = pdfUtils.tractarCampStr(dadesTaula, DOCUMENTS_OPCIONALS_COMUNS);

                if (documentsOpcionals == null || documentsOpcionals.trim().length() < 1) {
                    p.add("No s'han identificat");
                } else {
                    String[] partsDocumentsOpcionals = documentsOpcionals.split("@tab@");
                    for (String part : partsDocumentsOpcionals) {
                        p.add(part);
                        p.add(new Tab());
                    }
                }
            } else {
                p.add(documentsOpcionals);
            }

            doc.add(p);

            p = new Paragraph();
            p.setMultipliedLeading(1);
            p.setTextAlignment(TextAlignment.JUSTIFIED);
            p.setMarginBottom(10);
            p.add(new Text("Aplicació informàtica relacionada:\n").setBold());
            p.add(pdfUtils.tractarCamp(dadesTaula, APLICACIONS));
            doc.add(p);

            p = new Paragraph();
            p.setMultipliedLeading(1);
            p.setTextAlignment(TextAlignment.JUSTIFIED);
            p.setMarginBottom(10);
            p.add(new Text("Sèries relacionades:\n").setBold());
            String seriesRelacionades = pdfUtils.tractarCampStr(dadesTaula, SERIES_RELACIONADES);
            if (seriesRelacionades == null || seriesRelacionades.trim().length() < 1) {
                p.add("No s'han identificat");
            } else {
                p.add(pdfUtils.tractarCamp(dadesTaula, SERIES_RELACIONADES));
            }
            doc.add(p);


            p = new Paragraph();
            p.setMultipliedLeading(1);
            p.setTextAlignment(TextAlignment.JUSTIFIED);
            p.setMarginBottom(10);
            p.add(new Text("Sèries recapitulatives:\n").setBold());
            String seriesRecapitulatives = pdfUtils.tractarCampStr(dadesTaula, DOCUMENTS_RECAPITULATIUS);
            if (seriesRecapitulatives == null || seriesRecapitulatives.trim().length() < 1) {
                p.add("No s'han identificat");
            } else {
                p.add(pdfUtils.tractarCamp(dadesTaula, DOCUMENTS_RECAPITULATIUS));
            }
            doc.add(p);


            Table taulaAvaluacio = new Table(1);
            taulaAvaluacio.setWidth(UnitValue.createPercentValue(70));
            taulaAvaluacio.setHorizontalAlignment(HorizontalAlignment.CENTER);

            p = new Paragraph();
            p.add(new Text("AVALUACIÓ:\n").setBold()).setMultipliedLeading(1.5f);
            p.add(new Text("Resolució: ").setBold());
            p.add(pdfUtils.tractarCamp(dadesTaula, RESOLUCIO_AKA_ACCIO_DICTAMINADA));
            p.add(new Text("\nAccés: ").setBold());
            p.add(pdfUtils.tractarCamp(dadesTaula, ACCES_SERIE));
            p.add(new Text("\nNivell protecció de dades RGPDP: ").setBold());
            p.add(pdfUtils.tractarCamp(dadesTaula, NIVELL_LOPD));
            p.add(new Text("\nNivell confidencialitat ENS: ").setBold());
            p.add(pdfUtils.tractarCamp(dadesTaula, NIVELL_ENS));

            cell = new Cell().add(p);
            taulaAvaluacio.addCell(cell);
            doc.add(taulaAvaluacio);


            p = new Paragraph();
            p.setMultipliedLeading(1);
            p.setTextAlignment(TextAlignment.JUSTIFIED);
            p.setMarginBottom(10);
            p.add(new Text("Fonamentació jurídica:\n").setBold());
            p.add(pdfUtils.tractarCamp(dadesTaula, FONAMENTACIO_JURIDICA));
            doc.add(p);

            p = new Paragraph();
            p.setMultipliedLeading(1);
            p.setTextAlignment(TextAlignment.JUSTIFIED);
            p.setMarginBottom(10);
            p.add(new Text("Organisme promotor: ").setBold());
            String promotor = getPromotor(pdfUtils.tractarCampStr(dadesTaula, DIR3_PROMOTOR));
            if (promotor != null) {
                p.add(promotor);
            }
            doc.add(p);

            // ACH-022 Si l'estat NO és ni vigent ni obsolet, no treure data aprovació ni de publicació
            if (!isEstatProposta(estatDictamen)) {
                p = new Paragraph();
                p.setMultipliedLeading(1);
                p.setTextAlignment(TextAlignment.JUSTIFIED);
                p.setMarginBottom(10);
                p.add(new Text("Data d'aprovació:\n").setBold());
                p.add(dadesTaula[DATA_APROVACIO] != null ? dadesTaula[DATA_APROVACIO].toString() : "");
                doc.add(p);

                p = new Paragraph();
                p.setMultipliedLeading(1);
                p.setTextAlignment(TextAlignment.JUSTIFIED);
                p.setMarginBottom(10);
                p.add(new Text("Publicació:\n\n").setBold());
                p.add(dadesTaula[DATA_PUBLICACIO] != null ? dadesTaula[DATA_PUBLICACIO].toString() : "");
                doc.add(p);
            }

            line = new SolidLine(1f);
            line.setColor(pdfUtils.getColorBlack());

            sep1 = new LineSeparator(line);
            sep1.setWidth(UnitValue.createPercentValue(90));
            sep1.setMarginTop(5);
            doc.add(sep1);

            document.close();
            byteArrayOutputStream.write(byteArrayOutputStream.toByteArray());

        } catch (Exception e) {
            log.error("Error generant la taula de valoracio del dictamen " + idDictamen, e);
            throw new RuntimeException(e);
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }

    protected void guardaTaulaVigent(Long idDictamen) throws Exception {
        // TODO ACH-031
        // Implica controlar els estats dels dictàmens de la sèrie
        // Implica controlar que tingui normativa d'aprovació/publicació i data d'aprovació...
        // Implica generar la taula pdf novament (està preparada per l'estat Vigent)
        // Implica guardar en l'Alfresco i substituir per la versió proposta
    }
    protected void guardaTaulaObsolet(Long idDictamen) throws Exception {

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        PdfWriter pdfWriter = new PdfWriter(byteArrayOutputStream);

        Object[] dadesTaula = dictamenEJB.getDadesTaulaValoracio(idDictamen).get(0);

        // TODO ACH-032
        // TODO Obtenir PDF del gestor documental (idDictamen)
        PdfDocument pdfDoc = new PdfDocument(new PdfReader("SRC"), pdfWriter);

        Document doc = new Document(pdfDoc);

        PdfFont notoSansBold = pdfUtils.getFont("/font/NotoSans-Bold.ttf");

        Paragraph paragraph = new Paragraph("Obsolet des de " + dadesTaula[DATA_OBSOLET_FI])
                .setFont(notoSansBold)
                .setFontSize(30);

        PdfExtGState gs1 = new PdfExtGState().setFillOpacity(0.5f);

        // Implement transformation matrix usage in order to scale image
        for (int i = 1; i <= pdfDoc.getNumberOfPages(); i++) {

            PdfPage pdfPage = pdfDoc.getPage(i);
            Rectangle pageSize = pdfPage.getPageSizeWithRotation();

            // When "true": in case the page has a rotation, then new content will be automatically rotated in the
            // opposite direction. On the rotated page this would look as if new content ignores page rotation.
            pdfPage.setIgnorePageRotationForContent(true);

            float x = (pageSize.getLeft() + pageSize.getRight()) / 2;
            float y = (pageSize.getTop() + pageSize.getBottom()) / 2;
            PdfCanvas over = new PdfCanvas(pdfDoc.getPage(i));
            over.saveState();
            over.setExtGState(gs1);

            doc.showTextAligned(paragraph, x, y, i, TextAlignment.CENTER, VerticalAlignment.TOP, 0);
            over.restoreState();
        }
        doc.close();
    }

    private ZonedDateTime now = ZonedDateTime.now();

    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

    protected class HeaderFooterHandler implements IEventHandler {
        protected Document doc;

        public HeaderFooterHandler(Document doc) {
            this.doc = doc;
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

            ZonedDateTime madrid = now.withZoneSameInstant(ZoneId.of("Europe/Madrid"));

            new Canvas(pdfCanvas, pageSize).setFont(notoSansBold).setFontColor(pdfUtils.getColorOficial()).setFontSize(10)
                    //header
                    .add(logo)
                    .showTextAligned("Servei d'Arxiu i Gestió Documental (CAIB)", pageSize.getWidth() - doc.getRightMargin(), pageSize.getTop() - 20, TextAlignment.RIGHT, VerticalAlignment.MIDDLE, 0)
                    //.showTextAligned("Proposta de taula de valoració", pageSize.getWidth() / 2, pageSize.getTop() - 20, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0)
                    .setFontSize(8)
                    .setFont(notoSans)
                    .showTextAligned("Versió: "+ madrid.format(formatter), pageSize.getWidth() - doc.getRightMargin(), pageSize.getTop() - 30, TextAlignment.RIGHT, VerticalAlignment.MIDDLE, 0)
                    //footer
                    .setFontSize(8)
                    .setFontColor(pdfUtils.getColorBlack())
                    .showTextAligned("C/ de l'Uruguai, s/n (Velòdrom Illes Balears)", doc.getLeftMargin(), doc.getBottomMargin() - 20, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned("07010 Palma", doc.getLeftMargin(), doc.getBottomMargin() - 30, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0)
                    .setFontColor(pdfUtils.getColorOficial())
                    .setFont(notoSansBold)
                    .showTextAligned("" + pdfDoc.getPageNumber(page), pageSize.getWidth() - doc.getRightMargin(), doc.getBottomMargin() - 20, TextAlignment.CENTER, VerticalAlignment.MIDDLE, 0)
                    .showTextAligned("arxiu.caib.es", doc.getLeftMargin(), doc.getBottomMargin() - 40, TextAlignment.LEFT, VerticalAlignment.MIDDLE, 0);

        }
    }

    private String getPromotor(String codiDir3Promotor) {
        String promotor = null;
        try {
            Dir3 unitat = dir3EJB.findByCodi(codiDir3Promotor);
            if (unitat != null) {
                promotor = unitat.getNom();
            }
        } catch (I18NException e) {

        }
        return promotor;
    }

    /**
     * Retorna si l'estat del dictamen ha de sortir amb l'etiqueta de "Proposta de "...
     * Si no és el cas, sortirà sense "Proposta de " i amb les dates de publicació / aprovació.
     * En principi, només els estats Vigent i Obsolet són els que han de sortir sense proposta.
     *
     * @param estatDictamen
     * @return
     */
    private boolean isEstatProposta(Estat estatDictamen) {
       return !estatDictamen.equals(Estat.VIGENT) && !estatDictamen.equals(Estat.OBSOLET);
    }

    private static final int TAULA_AVALUACIO = 0;
    private static final int CODI_SERIE = 1;
    private static final int NOM_SERIE = 2;
    private static final int TIPUS_SERIE = 3;
    private static final int DESCRIPCIO_SERIE = 4;
    private static final int MATERIA_AKA_FUNCIO = 5;
    private static final int MARC_LEGAL = 6;
    private static final int DOCUMENTS_OBLIGATORIS_COMUNS = 7;
    private static final int DOCUMENTS_OBLIGATORIS = 8;

    private static final int DOCUMENTS_OBLIGATORIS_AGRUPACIONS = 9;
    private static final int APLICACIONS = 10;
    private static final int SERIES_RELACIONADES = 11;
    private static final int DOCUMENTS_RECAPITULATIUS = 12;
    private static final int RESOLUCIO_AKA_ACCIO_DICTAMINADA = 13;
    private static final int ACCES_SERIE = 14;
    private static final int NIVELL_LOPD = 15;
    private static final int NIVELL_ENS = 16;
    private static final int FONAMENTACIO_JURIDICA = 17;
    private static final int DIR3_PROMOTOR = 18;
    private static final int ID = 19;
    private static final int DATA_APROVACIO = 20;
    private static final int DATA_ACTUALITZACIO = 21;
    private static final int GUARDAR_COMO = 22;
    private static final int DOCUMENTS_OPCIONALS_COMUNS = 23;
    private static final int DOCUMENTS_OPCIONALS = 24;

    private static final int DOCUMENTS_OPCIONALS_AGRUPACIONS = 25;
    private static final int ESTAT = 26;
    private static final int DATA_OBSOLET_FI = 27;
    private static final int DATA_PUBLICACIO = 28;
}

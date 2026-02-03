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
import com.itextpdf.kernel.pdf.canvas.draw.DottedLine;
import com.itextpdf.layout.Canvas;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.borders.Border;
import com.itextpdf.layout.borders.DottedBorder;
import com.itextpdf.layout.borders.SolidBorder;
import com.itextpdf.layout.element.Cell;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.TabStop;
import com.itextpdf.layout.element.Table;
import com.itextpdf.layout.properties.HorizontalAlignment;
import com.itextpdf.layout.properties.TabAlignment;
import com.itextpdf.layout.properties.TextAlignment;
import com.itextpdf.layout.properties.UnitValue;
import com.itextpdf.layout.properties.VerticalAlignment;
import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.utils.I18nUtils;
import es.caib.archium.commons.utils.PdfUtils;
import es.caib.archium.commons.utils.StringUtilitats;
import es.caib.archium.ejb.service.TipusDocumentalExportService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipusDocumental;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;


@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusDocumentalExportarEJB extends AbstractDAO<TipusDocumental, Long> implements TipusDocumentalExportService {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	private ResourceBundle messagesCA = ResourceBundle.getBundle("messages.ejb", Idioma.CATALA.getLocale());
    private ResourceBundle messagesES = ResourceBundle.getBundle("messages.ejb", Idioma.CASTELLA.getLocale());
	
	@Inject
    private PdfUtils pdfUtils;
	
	@Inject
    private I18nUtils i18nUtils;
	
	@Inject
    private StringUtilitats stringUtilitats;
	
	 // Data actual per posar a la versió
    private ZonedDateTime now = ZonedDateTime.now();

    protected DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
    
	/**
	 * Método que construye un mapa con varios filtros para cribar tipos documentales
	 * @param código del tipo documental
	 * @param nom del tipo documental
	 * @param estat del tipo documental
	 */
	@Override
	public List<TipusDocumental> findByFilters(String codi, String nom, String estat) throws I18NException {
		
		Map<String, Object> filtres = new HashMap<>();
	    
	    if (codi != null && !codi.trim().isEmpty()) {
	        filtres.put("codi", codi); 
	    }
	    
	    if (nom != null && !nom.trim().isEmpty()) {
	        filtres.put("nom", nom); 
	    }
	    
	    if (estat != null && !estat.trim().isEmpty()) {
	        filtres.put("estat", estat);  
	    }
	    
	    return filtres.isEmpty() 
	        ? findAll(new OrderBy("codi")) 
	        : findFiltered(filtres, new OrderBy("codi"));
		
	}
    
    private Cell getFormatedCell() {
        DottedBorder db = new DottedBorder(pdfUtils.getColorOficial(), 1);
        return new Cell().setPadding(0).setMargin(0).setBorder(Border.NO_BORDER).setBorderBottom(db);
    }

    private Paragraph getFormatedParagraph(String paragraph) {
        return new Paragraph(paragraph != null ? paragraph : "").setPadding(0).setMargin(0).setMultipliedLeading(1f).setSpacingRatio(1).setBorder(Border.NO_BORDER);
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

            InputStream is = TaulaValoracioEJB.class.getResourceAsStream("/img/goib-arxiu-2.jpg");
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

        
	/**
	 * Método que genera un pdf a partir de un listado de tipos documentales filtrados (o no)
	 */
	@Override
    @PermitAll
    public InputStream generaPdfTipusDocumentals(List<TipusDocumental> llista, Idioma idioma) {
		
		// Seleccionar el ResourceBundle según el idioma
        ResourceBundle messages = idioma.equals(Idioma.CASTELLA) ? messagesES : messagesCA;
        boolean esEspanol = idioma.equals(Idioma.CASTELLA);

		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        // Inicialitzam la taula de continguts
        List<AbstractMap.SimpleEntry<String, AbstractMap.SimpleEntry<String, Integer>>> toc = new ArrayList<>();

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

            PdfDocumentInfo info = document.getDocumentInfo();
            //info.setTitle(messages.getString("qtd.metadada.titol") + " - " + titol);
            info.setAuthor(messages.getString("qtd.metadada.autor"));
            info.setCreator("Archium");
            //info.setSubject(messages.getString("qtd.metadada.titol") + " - " + titol);

            document.addEventHandler(PdfDocumentEvent.END_PAGE, new HeaderFooterHandler(doc, messages));


            float[] pointColumnWidths = {0.7f, 1.3f, 4f};
            Table taula = new Table(pointColumnWidths);
            taula.setWidth(UnitValue.createPercentValue(100));
            taula.setFixedLayout();

            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(messages.getString("qtd.header.td.codi")).setBackgroundColor(pdfUtils.getColorBlue()).setFont(notoSansBold).setFontColor(pdfUtils.getColorWhite())));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(
                    esEspanol ? messages.getString("qtd.header.td.nomca") : messages.getString("qtd.header.td.nom")).setBackgroundColor(pdfUtils.getColorBlue()).setFont(notoSansBold).setFontColor(pdfUtils.getColorWhite())));
            taula.addHeaderCell(getFormatedCell().add(getFormatedParagraph(
                    esEspanol ? messages.getString("qtd.header.td.definicioca") : messages.getString("qtd.header.td.definicio")).setBackgroundColor(pdfUtils.getColorBlue()).setHorizontalAlignment(HorizontalAlignment.CENTER).setFont(notoSansBold).setFontColor(pdfUtils.getColorWhite())));

            taula.setBorder(new SolidBorder(1));

            for (TipusDocumental td: llista) {
            	
            	taula.addCell(getFormatedCell().add(getFormatedParagraph(pdfUtils.tractarCampStr(td.getCodi()))));
                
                String nomValue = esEspanol ? td.getNomcas() : td.getNom();
                taula.addCell(getFormatedCell().add(getFormatedParagraph(pdfUtils.tractarCampStr(nomValue))));
                
                String definicioValue = esEspanol ? td.getDefiniciocas() : td.getDefinicio();
                taula.addCell(getFormatedCell().add(getFormatedParagraph(pdfUtils.tractarCampStr(definicioValue)).setTextAlignment(TextAlignment.JUSTIFIED)));
            
            }
            doc.add(taula);

            List<TabStop> tabStops = new ArrayList<>();
            tabStops.add(new TabStop(PageSize.A4.getHeight() - 15, TabAlignment.RIGHT, new DottedLine()));

            document.close();
            byteArrayOutputStream.write(byteArrayOutputStream.toByteArray());

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    }
	
	
	/**
	 * Método que genera un csv a partir de un listado de tipos documentales filtrados (o no)
	 */
    @Override
    public InputStream generaCsvTipusDocumentalsFiltrats(List<TipusDocumental> tipusDocumentals) { 
        
        ResourceBundle messages = messagesCA;
    	
    	ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        
        try (OutputStreamWriter writer = new OutputStreamWriter(byteArrayOutputStream, StandardCharsets.UTF_8)) {
            
            // Cabecera CSV
            writer.write("\"" + messages.getString("qtd.header.td.codi") + "\";");
            writer.write("\"" + messages.getString("qtd.header.td.nom") + "\";");
            writer.write("\"" + messages.getString("qtd.header.td.nomca") + "\";");
            writer.write("\"" + messages.getString("qtd.header.td.definicio") + "\";");
            writer.write("\"" + messages.getString("qtd.header.td.definicioca")+ "\";");
            writer.write("\"" + messages.getString("qtd.header.td.observacions")+ "\";");
            writer.write("\"" + messages.getString("qtd.header.td.fi")+ "\";");
            writer.write("\"" + messages.getString("qtd.header.td.inici")+ "\";");
            writer.write("\"" + messages.getString("qtd.header.td.modificacio")+ "\";");
            writer.write("\"" + messages.getString("qtd.header.td.canviEstat")+ "\";");
            writer.write("\"" + messages.getString("qtd.header.td.usuariInici")+ "\";");
            writer.write("\"" + messages.getString("qtd.header.td.usuariModificacio")+ "\";");
            writer.write("\"" + messages.getString("qtd.header.td.usuariCanviEstat")+ "\";");
            writer.write("\"" + messages.getString("qtd.header.td.observacions") + "\"\n");
            
            // Datos
            for (TipusDocumental td : tipusDocumentals) {
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getCodi()) + "\";");
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getNom()) + "\";");
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getNomcas()) + "\";");
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getDefinicio()) + "\";");
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getDefiniciocas()) + "\";");
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getObservacions()) + "\";");
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getFi()) + "\";");
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getInici()) + "\";");
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getModificacio()) + "\";");
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getCanviEstat()) + "\";");
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getUsuariInici()) + "\";");
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getUsuariModificacio()) + "\";");
                writer.write("\"" + stringUtilitats.nullToEmpty(td.getUsuariCanviEstat()) + "\"\n");
            }
            
            writer.flush();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        
        return new ByteArrayInputStream(byteArrayOutputStream.toByteArray());
    	
    }
    
    private String trobarIdioma(String idioma) {
    	
    	// Validar idioma (por defecto català)
        if (idioma == null || (!idioma.equals("ca") && !idioma.equals("es"))) {
            idioma = "ca";
        }
        
        return idioma;
    	
    }
	
}
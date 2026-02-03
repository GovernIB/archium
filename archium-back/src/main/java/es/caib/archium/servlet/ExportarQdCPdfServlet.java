package es.caib.archium.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import javax.ejb.EJB;
import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.model.Estat;
import es.caib.archium.commons.utils.PdfUtils;
import es.caib.archium.ejb.QuadreClassificacioEJB;
import es.caib.archium.ejb.objects.SerieDto;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.ejb.service.TipusDocumentalExportService;

@WebServlet("/exportarQuadreClassificacioPdf")
public class ExportarQdCPdfServlet extends HttpServlet {

	private static final long serialVersionUID = 1L;

	protected final Logger log = LoggerFactory.getLogger(this.getClass());
    
    @Inject
    QuadreClassificacioService quadreClassificacioService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        log.info("Accediendo al metodo doGet de ExportarQdCPdfServlet");

        String titol = "Consejo Económico y Social de las Illes Balears"; //Se inicializa como el caso Castellano y quadreId = 4L
    	Long quadreId = Long.valueOf(request.getParameter("quadreId"));
    	String idioma_str = request.getParameter("idioma");
    	Idioma idioma;
    	
    	if (idioma_str.equals("ca")) {
    		idioma = Idioma.CATALA;
    	} else {
    		idioma = Idioma.CASTELLA;
    	}
    	
    	if (idioma.equals(Idioma.CATALA) && quadreId == 2L) {
        	titol = "Administració de la Comunitat Autònoma de les Illes Balears";
    	} else if (idioma.equals(Idioma.CASTELLA) && quadreId == 2L) {
    		titol = "Administración de la Comunidad Autónoma de las Illes Balears";
    	} else if (idioma.equals(Idioma.CATALA) && quadreId == 3L) {
        	titol = "Consell Consultiu de les Illes Balears";
    	} else if (idioma.equals(Idioma.CASTELLA) && quadreId == 3L) {
    		titol = "Consejo Consultivo de las Illes Balears";
    	} else if (idioma.equals(Idioma.CATALA) && quadreId == 4L) {
    		titol = "Consell Econòmic i Social de les Illes Balears";
    	}
    	
    	List<SerieDto> series = quadreClassificacioService.getQuadreClassificacio(quadreId, idioma, null);
        
        // Generar PDF en memoria
        InputStream pdfStream = quadreClassificacioService.generaPdfQuadreQuassificacioNuevo(series, titol, idioma);

        // Ajustar cabecera HTTP
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"quadre_de_classificacio_" +
                LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".pdf\"");

        // Enviar los bytes al navegador
        byte[] buffer = new byte[10240];
        try (InputStream input = pdfStream; OutputStream output = response.getOutputStream()) {
            for (int length = 0; (length = input.read(buffer)) > 0; ) {
                output.write(buffer, 0, length);
            }
        }
        log.info("Saliendo del metodo doGet de ExportarQdCPdfServlet");
    }
}

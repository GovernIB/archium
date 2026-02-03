package es.caib.archium.servlet;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Collections;
import java.util.List;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.ejb.service.TipusDocumentalExportService;
import es.caib.archium.persistence.model.TipusDocumental;

@WebServlet("/exportarTipusDocumentalsPdf")
public class ExportarTipusDocumentalsPdf extends HttpServlet {

    private static final long serialVersionUID = 1L;

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    TipusDocumentalExportService tipusDocumentalExportService;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String codi = request.getParameter("codi");
        String nom = request.getParameter("nom");
        String estat = request.getParameter("estat");
        String idioma_str = request.getParameter("idioma");

        // Llamamos al servicio para obtener los tipos documentales filtrados
        List<TipusDocumental> tipusFiltrats = Collections.emptyList();
        try {
            tipusFiltrats = tipusDocumentalExportService.findByFilters(codi, nom, estat);
        } catch (I18NException e) {
            log.error("Error obteniendo los tipos documentales filtrados para exportar a pdf", e);
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, 
                "Error obtenint les dades per exportar");
            return;
        }
        
        Idioma idioma;
    	
    	if (idioma_str.equals("ca")) {
    		idioma = Idioma.CATALA;
    	} else {
    		idioma = Idioma.CASTELLA;
    	}

        // Generamos PDF
        InputStream is = tipusDocumentalExportService.generaPdfTipusDocumentals(tipusFiltrats, idioma);

        // Configuramos respuesta
        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=\"tipus_documentals_" +
            LocalDate.now().format(DateTimeFormatter.ofPattern("yyyyMMdd")) + ".pdf\"");
        
        // Enviamos el pdf al navegador
        byte[] buffer = new byte[10240];
        try (InputStream input = is; OutputStream output = response.getOutputStream()) {
            for (int length = 0; (length = input.read(buffer)) > 0; ) {
                output.write(buffer, 0, length);
            }
        }
    }
}
package es.caib.archium.front.controllers;

import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.model.Estat;
import es.caib.archium.ejb.objects.SerieDto;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.List;

public class CalendariConservacioPdf extends HttpServlet {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    // TODO PENDENT Fer la integració archium-plugin d'arxiu per obtenir els dictàmens vigents d'alfresco
    // El problema és que cada dictamen té un enllaç a la taula de valoració aprovada (ara mateix aquest enllaç s'agafa d'ARGEN, però l'hauria d'agafar d'Alfresco


    @Inject
    QuadreClassificacioService quadreClassificacioEJB;

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("application/pdf");

        // En primer lloc s'obtenen les dades del quadre de classificació a partir dels paràmetres
        String quadre = request.getParameter("quadre");
        Long quadreId;
        String idiomaRequest = request.getParameter("idioma");
        String estatRequest = request.getParameter("estat");

        // Por defecto, el cuadro por defecto. Si podemos convertir el cuadro, pues el que especifican
        if (quadre == null) {
            quadreId = quadreClassificacioEJB.getQuadreClassificacioPerDefecte();
        } else {
            try {
                quadreId = Long.parseLong(quadre);
            } catch (NumberFormatException e) {
                quadreId = quadreClassificacioEJB.getQuadreClassificacioPerDefecte();
            }
        }

        Estat estat;
        if (estatRequest == null) {
            estat = Estat.VIGENT;
        } else {
            estat = Estat.getEstat(estatRequest);
        }

        Idioma idioma = Idioma.getIdioma(idiomaRequest);
        List<SerieDto> seriesDto = quadreClassificacioEJB.getQuadreClassificacio(quadreId, idioma, estat);
        InputStream is = quadreClassificacioEJB.generaPdfQuadreQuassificacio(seriesDto, idioma);

        //response.setContentLength(getContentLength());
        byte[] buffer = new byte[10240];
        try (
                InputStream input = is;
                OutputStream output = response.getOutputStream();
        ) {
            for (int length = 0; (length = input.read(buffer)) > 0; ) {
                output.write(buffer, 0, length);
            }
        }
    }
}

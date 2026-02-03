package es.caib.archium.commons.rest;

import javax.ws.rs.core.MediaType;
import java.text.SimpleDateFormat;

public interface RestConstants {

    /**
     * Propietat del request per emmagatzemar el locale actual de la petició.
     */
    String REQUEST_LOCALE = "es.caib.archium.commons.rest.requestLocale";

    /**
     * Paràmetre del context definit al web.xml amb la llista de locales soportats.
     */
    String SUPPORTED_LOCALES = "es.caib.archium.commons.rest.supportedLocales";


    String APPLICATION_JSON_UTF_8 = MediaType.APPLICATION_JSON + ";charset=UTF-8";

    String APPLICATION_PDF = "application/pdf";

    final SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
}

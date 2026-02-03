package es.caib.archium.commons.utils;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.text.Normalizer;


@Named("stringUtilitats")
@ApplicationScoped
public class StringUtilitats {

    public String llevarAccents(String str) {
        String cadena = "";
        if (str != null) {
            cadena = Normalizer.normalize(str, Normalizer.Form.NFD);
            cadena = cadena.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
        }
        return cadena;
    }

    public String nullSafe(Object str) {
        return str != null ? str.toString() : null;
    }
    
    public String nullToEmpty(Object str) {
        return str != null ? str.toString() : "";
    }
}

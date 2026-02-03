package es.caib.archium.commons.utils;

import es.caib.archium.commons.i18n.Idioma;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Named;
import java.util.Locale;
import java.util.ResourceBundle;

@Named("i18nUtils")
@ApplicationScoped
public class I18nUtils {

    public Locale setLocale(Idioma idioma) {
        Locale locale;
        if (idioma != null) {
            locale = new Locale(idioma.getCodi(), idioma.getCountry());
        } else {
            locale = new Locale(Idioma.idiomaPerDefecte().getCodi(), Idioma.idiomaPerDefecte().getCountry());
        }
        Locale.setDefault(locale);
        ResourceBundle.clearCache();
        return locale;
    }
}

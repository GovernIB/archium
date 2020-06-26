package es.caib.archium.utils;

import java.util.Locale;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.i18n.I18NTranslator;

public class FrontExceptionTranslate {
    public static final I18NTranslator translator = new I18NTranslator(
            new String[]{"archium.exceptions.Exceptions"});

    public static String translate(boolean useCodeIfNotExist, Locale loc, String code, String... args) {
        return translator.translate(useCodeIfNotExist, loc, code, args);
    }

    public static String translate(Locale loc, String code, String... args) {
        return translator.translate(loc, code, args);
    }

    public static String translate(String valueIfNotExist, Locale loc, String code, String... args) {
        return translator.translate(valueIfNotExist, loc, code, args);
    }

    public static String translate(I18NException e, Locale locale) {
        return translator.translate(e, locale);
    }
}

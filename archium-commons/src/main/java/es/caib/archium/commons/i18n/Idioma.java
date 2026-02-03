package es.caib.archium.commons.i18n;

import es.caib.archium.commons.model.Estat;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Locale;

/**
 * Conjunt d'idiomes suportats a l'aplicació
 */
public enum Idioma {
    CASTELLA("es", "ES"), CATALA("ca", "ES");

    private String codi;
    private String country;

    private Locale locale;

    Idioma(String codi, String country) {
        this.codi = codi;
        this.country = country;
        this.locale = new Locale(codi, country);
    }

    public String getCodi() {
        return codi;
    }

    public String getCountry() { return country; }

    public Locale getLocale() { return locale; }

    public static Idioma getIdioma(String idioma) {
        if (idioma == null || idioma.length() == 0) {
            return null;
        }
        List<Idioma> idiomes = new ArrayList<>(Arrays.asList(values()));
        for (Idioma e : idiomes) {
            if (e.getCodi().equalsIgnoreCase(idioma)) {
                return e;
            }
        }
        return null;
    }

    @Override
    public String toString() {
        return codi + "-" + country;
    }

    public static Idioma idiomaPerDefecte() {
        return CATALA;
    }

}

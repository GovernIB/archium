package es.caib.archium.commons.model;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Estat {

    ESBORRANY("Esborrany"),
    REVISAT("Revisat"),
    PUBLICABLE("Publicable"),
    VIGENT("Vigent"),
    OBSOLET("Obsolet");

    private final String value;

    Estat(String value) {
        this.value = value;
    }

    public static Estat getEstat(String estat) {
        if (estat == null || estat.length() == 0) {
            return null;
        }
        List<Estat> estats = new ArrayList<>(Arrays.asList(values()));
        for (Estat e : estats) {
            if (e.getValue().equalsIgnoreCase(estat)) {
                return e;
            }
        }
        return null;
    }

    public String getValue() {
        return value;
    }

    public static boolean isSincronitzable(String estat) {
        return isSincronitzable(getEstat(estat));
    }

    /**
     * En teoria, només hauríem de permetre sincronitzar cap a Alfresco aquells elements que estiguin en estat Publicable.
     * La sincronització faria canviar l'estat a Vigent.
     *
     * @param estat
     * @return
     */
    public static boolean isSincronitzable(Estat estat) {
        //return ESBORRANY == estat || VIGENT == estat;
        return PUBLICABLE == estat;
    }

    public String toString() {
        return value;
    }
}
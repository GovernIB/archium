package es.caib.archium.utils;

import java.util.Arrays;
import java.util.List;

/**
 * A = Año
 * M = Mes
 * S = Semana
 * D = Dia
 * H = Hora
 *
 */
public enum UnidadPlazo {
    A(0),M(1),S(2),D(3),H(4);

    int value;
    UnidadPlazo(int value){
        this.value = value;
    }

    public int getValue() {
        return value;
    }

    public boolean isGreaterThan(UnidadPlazo unidad){
        return this.getValue() < unidad.getValue();
    }

    public static UnidadPlazo getUnidad(String unidad){
        List<UnidadPlazo> values = Arrays.asList(UnidadPlazo.values());

        for(UnidadPlazo u : values){
            if(u.toString().equalsIgnoreCase(unidad)){
                return u;
            }
        }
        return null;
    }

    /**
     * Actualmente en Archium no se hace la distincion entre día natural y día habil, por lo tanto de forma temporal, siempre
     * que la unidad sea dia, enviaremos a GDIB "DN" (Dia Natural)
     *
     * @param unidad
     * @return
     */
    public static String getCSGDUnidad(String unidad){
        UnidadPlazo u = getUnidad(unidad);
        if(u==null){
            return null;
        }
        if(D == u){
            return "DN";
        }else{
            return u.toString();
        }
    }
}

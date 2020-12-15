package es.caib.archium.csgd.apirest.constantes;

import java.util.Arrays;
import java.util.List;

/**
 * A = Año
 * M = Mes
 * S = Semana
 * DH = Dia Habil
 * DN = Dia Natural
 * H = Hora
 *
 * Por el momento Archium solo distingue dia, por lo que lo traduciremos siempre como DN en este caso
 * El valor numerico corresponde en orden inverso al que equivale a mayor plazo temporal, que utilizaremos para sacar
 * el mayor plazo en algunas operaciones
 */
public enum UnidadPlazo {
    A(0),M(1),S(2),DH(3),DN(4),H(5);

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

        // Por el momento Archium solo distingue dia, por lo que lo traduciremos siempre como Dia Natural en este caso
        if("D".equalsIgnoreCase(unidad)){
            return UnidadPlazo.DN;
        }

        for(UnidadPlazo u : values){
            if(u.toString().equalsIgnoreCase(unidad)){
                return u;
            }
        }
        return null;
    }
}

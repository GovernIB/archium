package es.caib.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TipoClasificacion {
    SIA("SIA"),FUNCIONAL("FUNCIONAL");

    private String value;

    TipoClasificacion(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static TipoClasificacion getTipoClasificacion(String tipoClasificacion) {
        if(StringUtils.isEmpty(tipoClasificacion)) {
            return null;
        }
        List<TipoClasificacion> tipos = new ArrayList<>(Arrays.asList(values()));
        //TODO: Cuales serán los estados válidos? en qué idioma estarán?
        for(TipoClasificacion tc : tipos){
            if(tc.getValue().equalsIgnoreCase(tipoClasificacion)){
                return tc;
            }
        }
        return null;
    }
}

package es.caib.archium.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Estado {

    ESBORRANY("ESBORRAYNY"),
    REVISAT("REVISAT"),
    PUBLICABLE("PUBLICABLE"),
    VIGENT("VIGENT"),
    OBSOLET("OBSOLET");

    private String value;

    Estado(String value){
        this.value = value;
    }

    public static Estado getEstado(String estat) {
        if(StringUtils.isEmpty(estat)) {
            return null;
        }
        List<Estado> estados = new ArrayList<>(Arrays.asList(values()));
        //TODO: Cuales serán los estados válidos? en qué idioma estarán?
        for(Estado e : estados){
            if(e.getValue().equalsIgnoreCase(estat)){
                return e;
            }
        }
        return null;
    }

    @JsonValue
    public String getValue() {
        return value;
    }
}

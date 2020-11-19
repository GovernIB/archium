package es.caib.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Estado {

    ESBORRANY("ESBORRANY"),
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

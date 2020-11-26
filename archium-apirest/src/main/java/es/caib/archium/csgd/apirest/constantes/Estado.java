package es.caib.archium.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Estado {

    ESBORRANY("Esborrany"),
    REVISAT("Revisat"),
    PUBLICABLE("Publicable"),
    VIGENT("Vigent"),
    OBSOLET("Obsolet");

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

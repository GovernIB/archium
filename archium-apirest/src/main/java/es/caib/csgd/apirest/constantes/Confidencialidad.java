package es.caib.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Confidencialidad {
    BAJO("BAJO"),MEDIO("MEDIO"),ALTO("ALTO");

    private String value;

    Confidencialidad(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static Confidencialidad getConfidencialidad(String confidencialidad) {
        if(StringUtils.isEmpty(confidencialidad)) {
            return null;
        }
        List<Confidencialidad> confidencialidades = new ArrayList<>(Arrays.asList(values()));
        //TODO: Cuales serán los estados válidos? en qué idioma estarán?
        for(Confidencialidad c : confidencialidades){
            if(c.getValue().equalsIgnoreCase(confidencialidad)){
                return c;
            }
        }
        return null;
    }
}

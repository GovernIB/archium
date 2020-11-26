package es.caib.archium.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum Confidencialidad {
    BAJO("Bajo"),MEDIO("Medio"),ALTO("Alto");

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
        for(Confidencialidad c : confidencialidades){
            if(c.getValue().equalsIgnoreCase(confidencialidad)){
                return c;
            }
        }
        return null;
    }
}

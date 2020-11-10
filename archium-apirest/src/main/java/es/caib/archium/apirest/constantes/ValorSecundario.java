package es.caib.archium.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ValorSecundario {
    SI("Sí"),NO("No"),NO_COBERTURA("Sin cobertura de calificación");

    private String value;

    ValorSecundario(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static ValorSecundario getValorSecundario(String confidencialidad) {
        if(StringUtils.isEmpty(confidencialidad)) {
            return null;
        }
        List<ValorSecundario> confidencialidades = new ArrayList<>(Arrays.asList(values()));
        //TODO: Cuales serán los estados válidos? en qué idioma estarán?
        for(ValorSecundario c : confidencialidades){
            if(c.getValue().equalsIgnoreCase(confidencialidad)){
                return c;
            }
        }
        return null;
    }
}

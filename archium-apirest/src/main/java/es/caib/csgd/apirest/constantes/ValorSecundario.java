package es.caib.csgd.apirest.constantes;

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

    public static ValorSecundario getValorSecundario(String valorSecundario) {
        if(StringUtils.isEmpty(valorSecundario)) {
            return null;
        }
        List<ValorSecundario> valores = new ArrayList<>(Arrays.asList(values()));
        //TODO: Cuales serán los estados válidos? en qué idioma estarán?
        for(ValorSecundario vs : valores){
            if(vs.getValue().equalsIgnoreCase(valorSecundario)){
                return vs;
            }
        }
        return null;
    }
}

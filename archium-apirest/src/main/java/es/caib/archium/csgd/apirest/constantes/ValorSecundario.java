package es.caib.archium.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum ValorSecundario {
    SI("Si"),NO("No"),NO_COBERTURA("Sin cobertura de calificacion");

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
        for(ValorSecundario vs : valores){
            // Quitamos los posibles acentos para realizar la comparacion
            if(vs.getValue().equalsIgnoreCase(valorSecundario.replace("í","i").replace("ó","o"))){
                return vs;
            }
        }
        return null;
    }
}

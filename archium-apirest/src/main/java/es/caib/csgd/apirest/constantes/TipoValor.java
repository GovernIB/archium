package es.caib.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TipoValor {
    ADMINISTRATIVO("ADMINISTRATIVO"),FISCAL("FISCAL"),JURIDICO("JURIDICO"),OTROS("OTROS");

    private String value;

    TipoValor(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static TipoValor getTipoValor(String tipoValor) {
        if(StringUtils.isEmpty(tipoValor)) {
            return null;
        }
        List<TipoValor> valores = new ArrayList<>(Arrays.asList(values()));
        //TODO: Cuales serán los estados válidos? en qué idioma estarán?
        for(TipoValor tv : valores){
            if(tv.getValue().equalsIgnoreCase(tipoValor)){
                return tv;
            }
        }
        return null;
    }
}

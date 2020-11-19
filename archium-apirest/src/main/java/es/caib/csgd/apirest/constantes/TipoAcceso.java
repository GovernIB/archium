package es.caib.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TipoAcceso {
    LIBRE("LIBRE"),LIMITADO("LIMITADO");

    private String value;

    TipoAcceso(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static TipoAcceso getTipoAcceso(String tipoAcceso) {
        if(StringUtils.isEmpty(tipoAcceso)) {
            return null;
        }
        List<TipoAcceso> tipos = new ArrayList<>(Arrays.asList(values()));
        //TODO: Cuales serán los estados válidos? en qué idioma estarán?
        for(TipoAcceso ta : tipos){
            if(ta.getValue().equalsIgnoreCase(tipoAcceso)){
                return ta;
            }
        }
        return null;
    }
}

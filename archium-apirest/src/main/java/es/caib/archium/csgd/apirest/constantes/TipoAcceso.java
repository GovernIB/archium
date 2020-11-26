package es.caib.archium.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TipoAcceso {
    LIBRE("Libre"),LIMITADO("Limitado"),PARCIALMENTE_RESTRINGIDO("Parcialmente limitado");

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
        for(TipoAcceso ta : tipos){
            if(ta.getValue().equalsIgnoreCase(tipoAcceso)){
                return ta;
            }
        }
        return null;
    }
}

package es.caib.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum CausaLimitacion {
    CODIGO_CAUSA_LIMITACION_A("A"),
    CODIGO_CAUSA_LIMITACION_B("B"),
    CODIGO_CAUSA_LIMITACION_C("C"),
    CODIGO_CAUSA_LIMITACION_D("D"),
    CODIGO_CAUSA_LIMITACION_E("E"),
    CODIGO_CAUSA_LIMITACION_F("F"),
    CODIGO_CAUSA_LIMITACION_G("G"),
    CODIGO_CAUSA_LIMITACION_H("H"),
    CODIGO_CAUSA_LIMITACION_I("I"),
    CODIGO_CAUSA_LIMITACION_J("J"),
    CODIGO_CAUSA_LIMITACION_K("K"),
    CODIGO_CAUSA_LIMITACION_L("L"),
    CODIGO_CAUSA_LIMITACION_M("M");

    private String value;

    CausaLimitacion(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static CausaLimitacion getCausaLimitacion(String causa) {
        if(StringUtils.isEmpty(causa)) {
            return null;
        }
        List<CausaLimitacion> limitacioens = new ArrayList<>(Arrays.asList(values()));
        //TODO: Cuales serán los estados válidos? en qué idioma estarán?
        for(CausaLimitacion cl : limitacioens){
            if(cl.getValue().equalsIgnoreCase(causa)){
                return cl;
            }
        }
        return null;
    }
}

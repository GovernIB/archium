package es.caib.archium.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum LOPD {
    BASICO("BASICO"),MEDIO("MEDIO"),ALTO("ALTO");

    private String value;

    LOPD(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static LOPD getLopd(String lopd) {
        if(StringUtils.isEmpty(lopd)) {
            return null;
        }
        List<LOPD> lopds = new ArrayList<>(Arrays.asList(values()));
        //TODO: Cuales serán los estados válidos? en qué idioma estarán?
        for(LOPD l : lopds){
            if(l.getValue().equalsIgnoreCase(lopd)){
                return l;
            }
        }
        return null;
    }
}

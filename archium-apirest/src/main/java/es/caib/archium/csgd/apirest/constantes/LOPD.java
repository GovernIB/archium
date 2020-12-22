package es.caib.archium.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum LOPD {
    BASICO("Basico"),MEDIO("Medio"),ALTO("Alto");

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
        for(LOPD l : lopds){
            if(l.getValue().equalsIgnoreCase(lopd.replace("á","a"))){
                return l;
            }
        }
        return null;
    }
}

package es.caib.archium.csgd.apirest.constantes;

import com.fasterxml.jackson.annotation.JsonValue;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public enum TipoDictamen {
    CP("CP"), EP("EP"), ET("ET"), PD("PD");

    private String value;

    TipoDictamen(String value){
        this.value = value;
    }

    @JsonValue
    public String getValue() {
        return value;
    }

    public static TipoDictamen getTipoDictamen(String tipoDictamen) {
        if(StringUtils.isEmpty(tipoDictamen)) {
            return null;
        }
        List<TipoDictamen> tipos = new ArrayList<>(Arrays.asList(values()));
        //TODO: Cuales serán los estados válidos? en qué idioma estarán?
        for(TipoDictamen td : tipos){
            if(td.getValue().equalsIgnoreCase(tipoDictamen)){
                return td;
            }
        }
        return null;
    }
}

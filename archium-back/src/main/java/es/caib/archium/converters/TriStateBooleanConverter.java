package es.caib.archium.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("TriStateBooleanConverter")
public class TriStateBooleanConverter implements Converter<Boolean> {

    @Override
    public Boolean getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return null;
        }
        switch (value) {
            case "0": return null;
            case "1": return Boolean.TRUE;
            case "2": return Boolean.FALSE;
            default: throw new ConverterException();
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Boolean value) {
        if (value == null) {
            return "0";
        }
        return value ? "1" : "2";
    }

}

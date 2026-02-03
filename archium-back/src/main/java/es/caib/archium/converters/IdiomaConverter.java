package es.caib.archium.converters;

import es.caib.archium.commons.i18n.Idioma;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("IdiomaConverter")
public class IdiomaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
        return Idioma.getIdioma(newValue);
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {

    	if (object == null) {
            return "";
        }
        if (object instanceof Idioma) {
        	Idioma idioma = (Idioma) object;
            return idioma.getCodi();
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid Idioma"));
        }
    }
}
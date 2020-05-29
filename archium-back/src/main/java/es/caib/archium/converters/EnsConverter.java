package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import es.caib.archium.controllers.DictamenController;
import es.caib.archium.objects.DictamenObject;
import es.caib.archium.objects.EnsObject;

@FacesConverter("EnsConverter")
public class EnsConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
	    if(newValue == null)
	    return null;
	    DictamenController data = context.getApplication().evaluateExpressionGet(context, "#{dictamenController}", DictamenController.class);
	    for(EnsObject compLovDtgrid : data.getListaEns())
	    {
	    	if(compLovDtgrid.getId()== Long.parseLong((newValue),10))
	    		return compLovDtgrid;
	    }
	    	throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to DictamenObject", newValue)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
    	if (object == null) {
            return "";
        }
        if (object instanceof EnsObject) {
        	EnsObject quadre= (EnsObject) object;
            Long name = quadre.getId();
            return name.toString();
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid Ens"));
        }
    }
}
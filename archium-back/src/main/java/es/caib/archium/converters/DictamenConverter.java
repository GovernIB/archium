package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import es.caib.archium.controllers.DictamenController;
import es.caib.archium.objects.DictamenObject;

@FacesConverter("DictamenConverter")
public class DictamenConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
	    if(newValue == null)
	    return null;
	    DictamenController data = context.getApplication().evaluateExpressionGet(context, "#{dictamenController}", DictamenController.class);
	    for(DictamenObject compLovDtgrid : data.getListaDictamen())
	    {
		    if(compLovDtgrid.getId().equals(newValue))
		    	return compLovDtgrid;
	    }
	    	throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to DictamenObject", newValue)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
    	if (object == null) {
            return "";
        }
        if (object instanceof DictamenObject) {
        	DictamenObject quadre= (DictamenObject) object;
            Long name = quadre.getId();
            return name.toString();
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid quadre"));
        }
    }
}
package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import es.caib.archium.controllers.DictamenController;
import es.caib.archium.objects.DictamenObject;
import es.caib.archium.objects.LopdObject;

@FacesConverter("LopdConverter")
public class LopdConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
	    if(newValue == null)
	    return null;
	    DictamenController data = context.getApplication().evaluateExpressionGet(context, "#{dictamenController}", DictamenController.class);
	    for(LopdObject compLovDtgrid : data.getListaLopd())
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
        if (object instanceof LopdObject) {
        	LopdObject quadre= (LopdObject) object;
            Long name = quadre.getId();
            return name.toString();
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid LOPD"));
        }
    }
}
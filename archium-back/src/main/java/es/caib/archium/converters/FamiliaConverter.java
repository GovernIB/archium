package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import es.caib.archium.controllers.DictamenController;
import es.caib.archium.controllers.ProcedimentController;
import es.caib.archium.objects.DictamenObject;
import es.caib.archium.objects.FamiliaprocedimentObject;
import es.caib.archium.objects.NormativaAprobacioObject;

@FacesConverter("FamiliaConverter")
public class FamiliaConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
	    if(newValue == null)
	    return null;
	    
	    final Long id = Long.valueOf(newValue);
	    ProcedimentController data = context.getApplication().evaluateExpressionGet(context, "#{procedimentController}", ProcedimentController.class);
	    for(FamiliaprocedimentObject compLovDtgrid : data.getListaFamiliaprocediment())
	    {
	    	if(compLovDtgrid.getId().equals(id))
		    	return compLovDtgrid;
	    }
	    	throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to DictamenObject", newValue)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
    	if (object == null) {
            return "";
        }
        if (object instanceof FamiliaprocedimentObject) {
        	FamiliaprocedimentObject quadre= (FamiliaprocedimentObject) object;
            Long name = quadre.getId();
            return name.toString();
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid Familia de procediment "));
        }
    }
}
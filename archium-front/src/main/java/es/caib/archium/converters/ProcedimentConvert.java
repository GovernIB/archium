package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import es.caib.archium.controllers.ProcedimentController;
import es.caib.archium.objects.ProcedimentObject;

@FacesConverter("ProcedimentConvert")
public class ProcedimentConvert implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
	    // System.out.println("GET AS OBJECT ");
	    if(newValue == null)
	    	return null;
	    ProcedimentController data = context.getApplication().evaluateExpressionGet(context, "#{procedimentController}", ProcedimentController.class);
	    for(ProcedimentObject compLovDtgrid : data.getListaProcediment())
	    {
	    	if(compLovDtgrid.getId()== Long.parseLong((newValue),10))
	    		return compLovDtgrid;
	    }
	    	throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to ProcedimentObject", newValue)));
	    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
   	 //System.out.println("GET AS STRING");
    	if (object == null) {
            return "";
        }
        if (object instanceof ProcedimentObject) {
        	ProcedimentObject quadre= (ProcedimentObject) object;
            Long name = quadre.getId();
            //System.out.println("Ens Object :" + name);
            return name.toString();
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid Procediments"));
        }
    }
}
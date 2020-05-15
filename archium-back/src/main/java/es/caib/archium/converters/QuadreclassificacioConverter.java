package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import es.caib.archium.controllers.QuadreController;
import es.caib.archium.objects.QuadreObject;

@FacesConverter("QuadreclassificacioConverter")
public class QuadreclassificacioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
	    // System.out.println("GET AS OBJECT ");
	    if(newValue == null)
	    return null;
	    QuadreController data = context.getApplication().evaluateExpressionGet(context, "#{quadreController}", QuadreController.class);
	    for(QuadreObject compLovDtgrid : data.getListaCuadros())
	    {
		    if(compLovDtgrid.getNom().equals(newValue))
		    return compLovDtgrid;
	    }
	    	throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to QuadreObject", newValue)));

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, Object object) {
   	 //System.out.println("GET AS STRING");
    	if (object == null) {
            return "";
        }
        if (object instanceof QuadreObject) {
        	QuadreObject quadre= (QuadreObject) object;
            String name = quadre.getNom();
            //System.out.println("Quadre :" + name);
            return name;
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid quadre"));
        }
    }
    
}
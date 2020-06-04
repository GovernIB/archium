package es.caib.archium.converters;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import es.caib.archium.controllers.FuncionesController;
import es.caib.archium.controllers.NuevaSerieController;
import es.caib.archium.objects.AplicacionObject;
import es.caib.archium.objects.CausaLimitacioObject;
import es.caib.archium.objects.FuncioObject;;

@FacesConverter("causaLimitacioConverter")
public class CausaLimitacioConverter implements Converter{

	 @Override
	    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
	    	if ((null == newValue) || (newValue.trim().isEmpty())) {
	            // Return null
	            return null;
	        }
	    	
	        try {
	            // Try to parse the value as long
	            final Long clId = Long.valueOf(newValue);
	            
	            NuevaSerieController data = context.getApplication().evaluateExpressionGet(context, "#{nuevaSerieController}", NuevaSerieController.class);
	            for(CausaLimitacioObject cl : data.getListaCausaLimitacio())
	            {
	                if(cl.getId().equals(clId)) {
	                	return cl;
	                }
	            }
				
	        } catch (final NumberFormatException ex) {
	            // Throw again
	            throw new ConverterException(ex);
	        } catch(final Exception e) {
	        	throw new ConverterException(e);
	        }

	    	return null;
	    }

	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object object) {

	    	if (object == null) {
	            return "";
	        }
	        if (object instanceof CausaLimitacioObject) {
	        	CausaLimitacioObject cl = (CausaLimitacioObject) object;
	            return String.valueOf(cl.getId());
	        } else {
	            throw new ConverterException(new FacesMessage(object + " is not a valid causa limitacio"));
	        }
	    }
}

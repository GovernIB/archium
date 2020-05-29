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
import es.caib.archium.controllers.ProcedimentController;
import es.caib.archium.objects.MateriaObject;
import es.caib.archium.objects.TipuDocumentalObject;
import es.caib.archium.objects.TipusSerieObject;

@FacesConverter("tiposDocumentalRelacionadoConverter")
public class TiposDocumentalRelacionadoConverter implements Converter{

		@Override
	    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
	    	if ((null == newValue) || (newValue.trim().isEmpty())) {
	            return null;
	        }
	    	
	        try {
	            final Long tdId = Long.valueOf(newValue);
	            
	            ProcedimentController data = context.getApplication().evaluateExpressionGet(context, "#{procedimentController}", ProcedimentController.class);
	            for(TipuDocumentalObject td : data.getListatipoDocumental())
	            {
	                if(td.getId()==tdId) {
	                	return td;
	                }
	            }
				
	        } catch (final NumberFormatException ex) {
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
	        if (object instanceof TipuDocumentalObject) {
	        	TipuDocumentalObject td= (TipuDocumentalObject) object;
	            return String.valueOf(td.getId());
	        } else {
	            throw new ConverterException(new FacesMessage(object + " is not a valid tipu tipu documental"));
	        }
	    }
}
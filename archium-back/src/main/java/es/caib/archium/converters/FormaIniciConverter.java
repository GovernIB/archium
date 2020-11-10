package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import es.caib.archium.controllers.ProcedimentController;
import es.caib.archium.objects.FormainiciObject;

@FacesConverter("FormaIniciConverter")
public class FormaIniciConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
    	if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }
    	
        try {
            final Long funcioId = Long.valueOf(newValue);
            ProcedimentController data = context.getApplication().evaluateExpressionGet(context, "#{procedimentController}", ProcedimentController.class);
            for(FormainiciObject funcio : data.getListaFormainici())
            {
                if(funcio.getId().equals(funcioId)) {
                	return funcio;
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
        if (object instanceof FormainiciObject) {
        	FormainiciObject funcio = (FormainiciObject) object;
            return String.valueOf(funcio.getId());
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid funcio"));
        }
    }
}
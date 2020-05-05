package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import es.caib.archium.controllers.FuncionesController;
import es.caib.archium.controllers.ProcedimentController;
import es.caib.archium.objects.FormainiciObject;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.Quadreclassificacio;

@FacesConverter("FormaIniciConverter")
public class FormaIniciConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
    	if ((null == newValue) || (newValue.trim().isEmpty())) {
            // Return null
            return null;
        }
    	
        try {
            // Try to parse the value as long
            final Long funcioId = Long.valueOf(newValue);
            ProcedimentController data = context.getApplication().evaluateExpressionGet(context, "#{procedimentController}", ProcedimentController.class);
            for(FormainiciObject funcio : data.getListaFormainici())
            {
                if(funcio.getId()==funcioId) {
                	return funcio;
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
        if (object instanceof FormainiciObject) {
        	FormainiciObject funcio = (FormainiciObject) object;
            return String.valueOf(funcio.getId());
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid funcio"));
        }
    }
}
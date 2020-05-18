package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import es.caib.archium.controllers.FuncionesController;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.Quadreclassificacio;

@FacesConverter("FuncioConverter")
public class FuncioConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
    	if ((null == newValue) || (newValue.trim().isEmpty())) {
            // Return null
            return null;
        }
    	
        try {
            // Try to parse the value as long
            final Long funcioId = Long.valueOf(newValue);
            
            FuncionesController data = context.getApplication().evaluateExpressionGet(context, "#{funciones}", FuncionesController.class);
            for(FuncioObject funcio : data.getListaFunciones())
            {
                if(funcio.getId().longValue()==funcioId.longValue()) {
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
        if (object instanceof FuncioObject) {
        	FuncioObject funcio = (FuncioObject) object;
            return String.valueOf(funcio.getId());
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid funcio"));
        }
    }
}
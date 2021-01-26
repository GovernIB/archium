package es.caib.archium.converters;

import es.caib.archium.controllers.FuncionesController;
import es.caib.archium.objects.FuncioObject;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

@FacesConverter("FuncioGenericConverter")
public class FuncioGenericConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
    	if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }
    	
        try {
            final Long funcioId = Long.valueOf(newValue);
            
            FuncionesController data = context.getApplication().evaluateExpressionGet(context, "#{funciones}", FuncionesController.class);
            return data.getServicesFunciones().findById(funcioId);

        } catch (final NumberFormatException ex) {
            throw new ConverterException(ex);
        } catch(final Exception e) {
        	throw new ConverterException(e);
        }

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
package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import es.caib.archium.controllers.ProcedimentController;
import es.caib.archium.objects.NivellelectronicObject;
import es.caib.archium.objects.SerieDocumentalObject;

@FacesConverter("SerieDocumentalConverter")
public class SerieDocumentalConverter implements Converter {

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
            for(SerieDocumentalObject funcio : data.getListaSerieDocumental())
            {
                if(funcio.getSerieId()==funcioId) {
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
        if (object instanceof SerieDocumentalObject) {
        	SerieDocumentalObject funcio = (SerieDocumentalObject) object;
            return String.valueOf(funcio.getSerieId());
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid Serie Documental"));
        }
    }
}
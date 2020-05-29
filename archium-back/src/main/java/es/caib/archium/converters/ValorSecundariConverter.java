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
import es.caib.archium.objects.TipusSerieObject;
import es.caib.archium.objects.ValorSecundariObject;
import es.caib.archium.objects.tipusPublicObject;

@FacesConverter("valorSecundariConverter")
public class ValorSecundariConverter implements Converter{

	@Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
    	if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }
    	
        try {
            final Long id = Long.valueOf(newValue);
            
            NuevaSerieController data = context.getApplication().evaluateExpressionGet(context, "#{nuevaSerieController}", NuevaSerieController.class);
            for(ValorSecundariObject vs : data.getListaValoresSecundarios())
            {
                if(vs.getId()==id) {
                	return vs;
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
        if (object instanceof ValorSecundariObject) {
        	ValorSecundariObject vs= (ValorSecundariObject) object;
            return String.valueOf(vs.getId());
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid valor secundari"));
        }
    }
}
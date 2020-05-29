package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import es.caib.archium.controllers.FuncionesController;
import es.caib.archium.objects.TipusSerieObject;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.Quadreclassificacio;
import es.caib.archium.persistence.model.Tipusserie;

@FacesConverter("FuncioTipusserieConverter")
public class FuncioTipusserieConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
    	if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }
    	
        try {
            final Long tipusSerieId = Long.valueOf(newValue);
            
            FuncionesController data = context.getApplication().evaluateExpressionGet(context, "#{funciones}", FuncionesController.class);
            for(TipusSerieObject tipusserie : data.getListaTipusserie())
            {
                if(tipusserie.getId()==tipusSerieId) {
                	return tipusserie;
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
        if (object instanceof TipusSerieObject) {
        	TipusSerieObject tipusserie= (TipusSerieObject) object;
            return String.valueOf(tipusserie.getId());
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid tipu serie"));
        }
    }
}
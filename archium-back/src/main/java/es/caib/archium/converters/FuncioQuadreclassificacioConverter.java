package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.caib.archium.controllers.FuncionesController;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.persistence.model.Quadreclassificacio;

@FacesConverter("FuncioQuadreclassificacioConverter")
public class FuncioQuadreclassificacioConverter implements Converter {
	
    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
    	if ((null == newValue) || (newValue.trim().isEmpty())) {
            // Return null
            return null;
        }
    	
        try {
            // Try to parse the value as long
            final Long quadreId = Long.valueOf(newValue);
            
            FuncionesController data = context.getApplication().evaluateExpressionGet(context, "#{funciones}", FuncionesController.class);
            for(QuadreObject quadre : data.getListaCuadrosClasificacion())
            {
                if(quadre.getId()==quadreId) {
                	return quadre;
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
   	 //System.out.println("GET AS STRING");

    	if (object == null) {
            return "";
        }
        if (object instanceof QuadreObject) {
        	QuadreObject quadre= (QuadreObject) object;
            String name = quadre.getNom();
            //System.out.println("Quadre :" + name);
            return String.valueOf(quadre.getId());
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid quadre"));
        }
    }
}
package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import es.caib.archium.controllers.DictamenController;
import es.caib.archium.objects.DictamenObject;
import es.caib.archium.objects.SerieDocumentalObject;

@FacesConverter("SeriesConverter")
public class SeriesConverter implements Converter {

    @Override
    public Object getAsObject(FacesContext context, UIComponent component, String newValue) {
    	if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }
    	
    	final Long id = Long.valueOf(newValue);
    	
    	try {    		
    		DictamenController data = context.getApplication().evaluateExpressionGet(context, "#{dictamenController}", DictamenController.class);
		    for(SerieDocumentalObject compLovDtgrid : data.getListaDocumental())
		    {
			    if(compLovDtgrid.getSerieId().equals(id))
			    	return compLovDtgrid;
		    }
		    	throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to SeriesObject", newValue)));
    	
	    } catch (final NumberFormatException ex) {
	        // Throw again
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

        if (object instanceof SerieDocumentalObject) {
        	SerieDocumentalObject quadre= (SerieDocumentalObject) object;
            Long name = quadre.getSerieId();
        	//Long name = quadre.getId();
            return name.toString();
        } else {
            throw new ConverterException(new FacesMessage(object + " is not a valid serie documental"));
        }
    }
}
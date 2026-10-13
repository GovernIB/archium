package es.caib.archium.converters;

import es.caib.archium.controllers.EditarSerieController;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.services.SerieFrontService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value="SerieDocumentalConverter", managed = true)
public class SerieDocumentalConverter implements Converter<SerieDocumentalObject> {

    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    private SerieFrontService serieFrontService;
    

    @Override
    public SerieDocumentalObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }

        try {
        	return serieFrontService.findById(Long.valueOf(newValue));
        	
        } catch (final Exception ex) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to QuadreObject", newValue)), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, SerieDocumentalObject object) {

        if (object == null) {
            return "";
        }
        return String.valueOf(object.getSerieId());
    }
}

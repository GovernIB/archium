package es.caib.archium.converters;

import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.services.SerieFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value="SeriesConverter", managed = true)
public class SeriesConverter implements Converter<SerieDocumentalObject> {

	@Inject
	private SerieFrontService serieFrontService;

    @Override
    public SerieDocumentalObject getAsObject(FacesContext context, UIComponent component, String newValue) {
    	if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }

		try {
			final Long id = Long.valueOf(newValue);
			return serieFrontService.findById(id);
		} catch (Exception e) {
			throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to SeriesObject", newValue)), e);
		}
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, SerieDocumentalObject serie) {
    	if (serie == null) {
            return "";
        }

        return serie.getSerieId().toString();
    }
}
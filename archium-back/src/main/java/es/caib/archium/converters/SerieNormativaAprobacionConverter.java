package es.caib.archium.converters;

import es.caib.archium.objects.NormativaAprobacioObject;
import es.caib.archium.services.SerieFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "serieNormativaAprobacionConverter", managed = true)
public class SerieNormativaAprobacionConverter implements Converter<NormativaAprobacioObject> {

    @Inject
    private SerieFrontService serieFrontService;

    @Override
    public NormativaAprobacioObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue == null) {
            return null;
        }

        try {
            final Long id = Long.valueOf(newValue);
            return serieFrontService.getNormativaById(id);
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to NormativaAprobacioObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, NormativaAprobacioObject normativa) {
        if (normativa == null) {
            return "";
        }

        return normativa.getId().toString();
    }
}
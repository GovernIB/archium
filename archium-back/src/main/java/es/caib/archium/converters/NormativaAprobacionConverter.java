package es.caib.archium.converters;

import es.caib.archium.objects.NormativaAprobacioObject;
import es.caib.archium.services.DictamenFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value="NormativaConverter", managed = true)
public class NormativaAprobacionConverter implements Converter<NormativaAprobacioObject> {

    @Inject
    private DictamenFrontService dictamenFrontService;

    @Override
    public NormativaAprobacioObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue == null) {
            return null;
        }

        try {
            final Long id = Long.valueOf(newValue);
            return dictamenFrontService.findNormativaAprovacioById(id);
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to DictamenObject", newValue)), e);
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
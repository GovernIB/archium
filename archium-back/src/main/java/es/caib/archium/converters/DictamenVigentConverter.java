package es.caib.archium.converters;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.DictamenObject;
import es.caib.archium.services.DictamenFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "DictamenVigentConverter", managed = true)
public class DictamenVigentConverter implements Converter<DictamenObject> {

    @Inject
    private DictamenFrontService dictamenFrontService;

    @Override
    public DictamenObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue == null) {
            return null;
        }

        try {
            final Long id = Long.valueOf(newValue);
            return dictamenFrontService.findById(id);
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to DictamenObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, DictamenObject object) {
        if (object == null) {
            return "";
        }
        return object.getId().toString();
    }
}
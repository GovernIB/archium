package es.caib.archium.converters;

import es.caib.archium.objects.LopdObject;
import es.caib.archium.services.DictamenFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value="LopdConverter", managed = true)
public class LopdConverter implements Converter<LopdObject> {

    @Inject
    private DictamenFrontService dictamenFrontService;

    @Override
    public LopdObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue == null) {
            return null;
        }

        try {
            final Long id = Long.valueOf(newValue);
            return dictamenFrontService.findLopdById(id);
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to LopdObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, LopdObject lopd) {
        if (lopd == null) {
            return "";
        }

        return lopd.getId().toString();
    }
}
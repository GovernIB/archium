package es.caib.archium.converters;

import es.caib.archium.objects.TipuAccesObject;
import es.caib.archium.services.DictamenFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value="TipusAccesConverter", managed = true)
public class TipuAccesConverter implements Converter<TipuAccesObject> {

    @Inject
    private DictamenFrontService dictamenFrontService;

    @Override
    public TipuAccesObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue == null)
            return null;

        try {
            final Long id = Long.valueOf(newValue);
            return dictamenFrontService.findTipusAccesById(id);
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to DictamenObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, TipuAccesObject tipusAcces) {
        if (tipusAcces == null) {
            return "";
        }
        return tipusAcces.getId().toString();
    }
}
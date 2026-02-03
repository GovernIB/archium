package es.caib.archium.converters;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.TipuDictamenObject;
import es.caib.archium.services.DictamenFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "TipusDictamenConverter", managed = true)
public class TipuDictamenConverter implements Converter<TipuDictamenObject> {

    @Inject
    private DictamenFrontService dictamenFrontService;

    @Override
    public TipuDictamenObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue == null) {
            return null;
        }

        try {
            final Long id = Long.valueOf(newValue);
            return dictamenFrontService.findTipusDictamenById(id);
        } catch (I18NException e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to TipuDictamenObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, TipuDictamenObject tipusDictamen) {
        if (tipusDictamen == null) {
            return "";
        }

        return tipusDictamen.getId().toString();
    }
}
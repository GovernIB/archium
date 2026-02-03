package es.caib.archium.converters;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.EnsObject;
import es.caib.archium.services.DictamenFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value="EnsConverter", managed = true)
public class EnsConverter implements Converter<EnsObject> {

    @Inject
    private DictamenFrontService dictamenFrontService;

    @Override
    public EnsObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue == null) {
            return null;
        }

        try {
            final Long id = Long.valueOf(newValue);
            return dictamenFrontService.findEnsById(id);
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to EnsObject", newValue)));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, EnsObject ens) {
        if (ens == null) {
            return "";
        }
        return ens.getId().toString();
    }
}
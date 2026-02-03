package es.caib.archium.converters;

import es.caib.archium.objects.CausaLimitacioObject;
import es.caib.archium.services.SerieFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "causaLimitacioConverter", managed = true)
public class CausaLimitacioConverter implements Converter<CausaLimitacioObject> {

    @Inject
    private SerieFrontService serviceFrontService;

    @Override
    public CausaLimitacioObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }

        try {
            final Long clId = Long.valueOf(newValue);
            return serviceFrontService.getListaCausaLimitacioById(clId);
        } catch (final Exception e) {
            throw new ConverterException(new FacesMessage(newValue + " is not a valid causa limitacio"), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, CausaLimitacioObject cl) {

        if (cl == null) {
            return "";
        }
        return String.valueOf(cl.getId());
    }
}

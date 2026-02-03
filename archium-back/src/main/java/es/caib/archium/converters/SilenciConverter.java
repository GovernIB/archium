package es.caib.archium.converters;

import es.caib.archium.objects.SilenciObject;
import es.caib.archium.services.ProcedimentFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value="SilenciConverter", managed = true)
public class SilenciConverter implements Converter<SilenciObject> {

    @Inject
    private ProcedimentFrontService procedimentFrontService;

    @Override
    public SilenciObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }

        try {
            final Long silenciId = Long.valueOf(newValue);
            procedimentFrontService.findSilenciById(silenciId);
        } catch (final Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to SilenciObject", newValue)), e);
        }

        return null;
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, SilenciObject silenci) {

        if (silenci == null) {
            return "";
        }

        return String.valueOf(silenci.getId());
    }
}
package es.caib.archium.converters;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import es.caib.archium.objects.ProcedimentVisorObject;
import es.caib.archium.services.ProcedimentFrontService;

@FacesConverter(value = "ProcedimentVisorConverter", managed = true)
public class ProcedimentVisorConverter implements Converter<ProcedimentVisorObject> {

    @Inject
    private ProcedimentFrontService procedimentFrontService;

    @Override
    public ProcedimentVisorObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue == null) {
            return null;
        }

        try {
            final Long id = Long.valueOf(newValue);
            return procedimentFrontService.findProcedimentVisorById(id);
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to ProcedimentVisorObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ProcedimentVisorObject procediment) {
        if (procediment == null) {
            return "";
        }
        return procediment.getId().toString();
    }
}
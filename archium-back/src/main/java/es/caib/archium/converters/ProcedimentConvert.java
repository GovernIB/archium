package es.caib.archium.converters;

import es.caib.archium.objects.ProcedimentObject;
import es.caib.archium.services.ProcedimentFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "ProcedimentConvert", managed = true)
public class ProcedimentConvert implements Converter<ProcedimentObject> {

    @Inject
    private ProcedimentFrontService procedimentFrontService;

    @Override
    public ProcedimentObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue == null) {
            return null;
        }

        try {
            final Long id = Long.valueOf(newValue);
            return procedimentFrontService.findProcedimentById(id);
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to ProcedimentObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ProcedimentObject procediment) {
        if (procediment == null) {
            return "";
        }
        return procediment.getId().toString();
    }
}
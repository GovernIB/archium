package es.caib.archium.converters;

import es.caib.archium.objects.FormainiciObject;
import es.caib.archium.services.ProcedimentFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value="FormaIniciConverter", managed = true)
public class FormaIniciConverter implements Converter<FormainiciObject> {

    @Inject
    private ProcedimentFrontService procedimentFrontService;

    @Override
    public FormainiciObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }

        try {
            final Long formaInici = Long.valueOf(newValue);
            return procedimentFrontService.findFormaIniciById(formaInici);
        } catch (final Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to FormainiciObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, FormainiciObject formaInici) {

        if (formaInici == null) {
            return "";
        }
        return String.valueOf(formaInici.getId());
    }
}
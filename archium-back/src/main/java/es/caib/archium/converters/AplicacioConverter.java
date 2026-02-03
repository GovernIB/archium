package es.caib.archium.converters;

import es.caib.archium.objects.AplicacioObject;
import es.caib.archium.services.ProcedimentFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "AplicacioConverter", managed = true)
public class AplicacioConverter implements Converter<AplicacioObject> {

    @Inject
    private ProcedimentFrontService procedimentFrontService;

    @Override
    public AplicacioObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if ((null == newValue) || (newValue.trim().isEmpty())) {
            // Return null
            return null;
        }

        try {
            // Try to parse the value as long
            final Long aplicacioId = Long.valueOf(newValue);
            return procedimentFrontService.findAplicacioById(aplicacioId);
        } catch (final Exception ex) {
            throw new ConverterException(new FacesMessage(newValue + " is not a valid Aplicacio"), ex);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, AplicacioObject aplicacio) {

        if (aplicacio == null) {
            return "";
        }
        return String.valueOf(aplicacio.getId());
    }
}
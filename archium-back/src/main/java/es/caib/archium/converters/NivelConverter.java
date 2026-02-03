package es.caib.archium.converters;

import es.caib.archium.objects.NivellelectronicObject;
import es.caib.archium.services.ProcedimentFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "NivelConverter", managed = true)
public class NivelConverter implements Converter<NivellelectronicObject> {

    @Inject
    private ProcedimentFrontService procedimentFrontService;

    @Override
    public NivellelectronicObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }

        try {
            final Long nivellId = Long.valueOf(newValue);
            return procedimentFrontService.findNivellElectronicById(nivellId);
        } catch (final Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to NivellelectronicObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, NivellelectronicObject nivell) {

        if (nivell == null) {
            return "";
        }
        return String.valueOf(nivell.getId());
    }
}
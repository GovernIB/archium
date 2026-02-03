package es.caib.archium.converters;

import es.caib.archium.objects.QuadreObject;
import es.caib.archium.services.QuadreFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "FuncioQuadreclassificacioConverter", managed = true)
public class FuncioQuadreclassificacioConverter implements Converter<QuadreObject> {

    @Inject
    private QuadreFrontService quadreFrontService;

    @Override
    public QuadreObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }

        try {
            final Long quadreId = Long.valueOf(newValue);
            return quadreFrontService.getQuadreById(quadreId);
        } catch (final Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to QuadreObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, QuadreObject quadre) {

        if (quadre == null) {
            return "";
        }

        return String.valueOf(quadre.getId());
    }
}
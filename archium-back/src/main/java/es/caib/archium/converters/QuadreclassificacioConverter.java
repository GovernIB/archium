package es.caib.archium.converters;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.services.QuadreFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "QuadreclassificacioConverter", managed = true)
public class QuadreclassificacioConverter implements Converter<QuadreObject> {

    @Inject
    private QuadreFrontService quadreFrontService;

    @Override
    public QuadreObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue == null) {
            return null;
        }

        try {
            return quadreFrontService.findQuadreByNom(newValue);
        } catch (I18NException e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to QuadreObject", newValue)));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, QuadreObject quadre) {
        if (quadre == null) {
            return "";
        }
        return quadre.getNom();
    }

}
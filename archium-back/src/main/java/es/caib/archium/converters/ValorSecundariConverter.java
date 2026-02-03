package es.caib.archium.converters;

import es.caib.archium.objects.ValorSecundariObject;
import es.caib.archium.services.SerieFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "valorSecundariConverter", managed = true)
public class ValorSecundariConverter implements Converter<ValorSecundariObject> {

    @Inject
    private SerieFrontService serieFrontService;

    @Override
    public ValorSecundariObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }

        try {
            final Long id = Long.valueOf(newValue);
            return serieFrontService.findValorSecundariById(id);
        } catch (final Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to ValorSecundariObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, ValorSecundariObject valorSecundari) {

        if (valorSecundari == null) {
            return "";
        }
        return String.valueOf(valorSecundari.getId());
    }
}
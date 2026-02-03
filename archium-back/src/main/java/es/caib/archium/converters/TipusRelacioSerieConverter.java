package es.caib.archium.converters;

import es.caib.archium.objects.TipusRelacioSerieObject;
import es.caib.archium.services.SerieFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "tipusRelacioSerieConverter", managed = true)
public class TipusRelacioSerieConverter implements Converter<TipusRelacioSerieObject> {

    @Inject
    private SerieFrontService serieFrontService;

    @Override
    public TipusRelacioSerieObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue == null) {
            return null;
        }


        try {
            final Long id = Long.valueOf(newValue);
            for (TipusRelacioSerieObject tipRel : serieFrontService.getListaTipusRelacio()) {
                if (tipRel.getId().equals(id))
                    return tipRel;
            }
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to TipusRelacioSerieObject", newValue)), e);
        }
        throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to TipusRelacioSerieObject", newValue)));
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, TipusRelacioSerieObject tipusRelacio) {
        if (tipusRelacio == null) {
            return "";
        }

        return tipusRelacio.getId().toString();
    }
}
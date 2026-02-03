package es.caib.archium.converters;

import es.caib.archium.ejb.service.TipusNormativaService;
import es.caib.archium.persistence.model.TipusNormativa;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.inject.Inject;
import javax.inject.Named;

// ReflectionaDynaFormModelBuilder no és capaç d'instanciar el converter si té l'anotació FacesConverter...
// Es canvia l'annotació per Named

//@FacesConverter(value = "TipusNormativaConverter", managed = true)
@Named
public class TipusNormativaConverter implements Converter<TipusNormativa> {

    @Inject
    TipusNormativaService tipusNormativaService;

    @Override
    public TipusNormativa getAsObject(FacesContext facesContext,
                                      UIComponent component, String value) {
        try {
            return  tipusNormativaService.findById(Long.parseLong((value),10));
        } catch (NumberFormatException e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to TipusNormativa", value)), e);
        }
    }

    @Override
    public String getAsString(FacesContext facesContext,
                              UIComponent component, TipusNormativa value) {
        return (value != null && value.getId() != null) ? value.getId().toString() : null;
    }
}

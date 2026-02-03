package es.caib.archium.converters;

import es.caib.archium.ejb.service.TipusNtiService;
import es.caib.archium.persistence.model.TipusNti;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

//@FacesConverter(value = "TipusNtiConverter", managed = true)
@Named
public class TipusNtiConverter implements Converter<TipusNti> {

    @Inject
    TipusNtiService tipusNtiService;

    @Override
    public TipusNti getAsObject(FacesContext facesContext,
                                UIComponent component, String value) {

        return tipusNtiService.findById(Long.parseLong((value),10));
    }

    @Override
    public String getAsString(FacesContext facesContext,
                              UIComponent component, TipusNti value) {
        return (value != null && value.getId() != null) ? value.getId().toString() : null;
    }
}

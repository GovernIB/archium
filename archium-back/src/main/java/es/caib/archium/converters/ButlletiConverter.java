package es.caib.archium.converters;

import es.caib.archium.ejb.service.ButlletiService;
import es.caib.archium.persistence.model.Butlleti;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

/*@FacesConverter(value="ButlletiConverter", managed = true)
public class ButlletiConverter implements Converter<Butlleti> {

    @Inject
    ButlletiService butlletiService;

    @Override
    public Butlleti getAsObject(FacesContext facesContext,
                                UIComponent component, String value) {

        return  butlletiService.findById(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext facesContext,
                              UIComponent component, Butlleti value) {
        return value != null && value.getId() != null ? value.getId().toString() : null;
    }
}*/
@Named
public class ButlletiConverter implements Converter<Butlleti> {

    @Inject
    ButlletiService butlletiService;

    @Override
    public Butlleti getAsObject(FacesContext facesContext,
                                UIComponent component, String value) {

        return  butlletiService.findById(Long.parseLong(value));
    }

    @Override
    public String getAsString(FacesContext facesContext,
                              UIComponent component, Butlleti value) {
        return value != null && value.getId() != null ? value.getId().toString() : null;
    }
}


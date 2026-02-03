package es.caib.archium.converters;

import es.caib.archium.ejb.service.QuadreTipusDocumentalService;
import es.caib.archium.persistence.model.QuadreTipusDocumental;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.inject.Inject;
import javax.inject.Named;

//@FacesConverter(value = "QuadreTipusDocumentalConverter", managed = true)
@Named
public class QuadreTipusDocumentalConverter implements Converter<QuadreTipusDocumental> {

    @Inject
    QuadreTipusDocumentalService quadreTipusDocumentalService;

    @Override
    public QuadreTipusDocumental getAsObject(FacesContext facesContext,
                                UIComponent component, String value) {

        return quadreTipusDocumentalService.findById(Long.parseLong((value),10));
    }

    @Override
    public String getAsString(FacesContext facesContext,
                              UIComponent component, QuadreTipusDocumental value) {
        return value != null && value.getId() != null ? value.getId().toString() : null;
    }
}

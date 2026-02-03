package es.caib.archium.converters;

import es.caib.archium.objects.FamiliaprocedimentObject;
import es.caib.archium.services.ProcedimentFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "FamiliaConverter", managed = true)
public class FamiliaConverter implements Converter<FamiliaprocedimentObject> {

    @Inject
    private ProcedimentFrontService procedimentFrontService;

    @Override
    public FamiliaprocedimentObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if (newValue == null) {
            return null;
        }

        try {
            final Long id = Long.valueOf(newValue);
            return procedimentFrontService.findFamiliaprocedimentById(id);
        } catch (Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to FamiliaprocedimentObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, FamiliaprocedimentObject familia) {
        if (familia == null) {
            return "";
        }
        return familia.getId().toString();
    }
}
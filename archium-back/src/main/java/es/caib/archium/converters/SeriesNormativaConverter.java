package es.caib.archium.converters;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.NormativaAprobacioObject;
import es.caib.archium.services.SerieFrontService;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

import java.util.List;

@FacesConverter(value = "seriesNormativaConverter", managed = true)
public class SeriesNormativaConverter implements Converter<NormativaAprobacioObject> {
	
	@Inject
	private SerieFrontService service;

    @Override
    public NormativaAprobacioObject getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.isEmpty()) {
            return null;
        }

        try {
           
            Long id = Long.valueOf(value);

            return service.getNormativaById(id);

        } catch (NumberFormatException | I18NException e) {
            throw new ConverterException(new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error de conversión", "ID no válido"));
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, NormativaAprobacioObject value) {
        if (value == null || value.getId() == null) {
            return "";
        }
        return String.valueOf(value.getId());
    }
}

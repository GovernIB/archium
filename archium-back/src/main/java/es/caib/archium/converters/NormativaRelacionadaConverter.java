package es.caib.archium.converters;

import es.caib.archium.objects.NormativaObject;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.List;

@FacesConverter("NormativaRelacionadaConverter")
public class NormativaRelacionadaConverter implements Converter<NormativaObject> {

    @Override
    public NormativaObject getAsObject(FacesContext context, UIComponent component, String value) {
        return getObjectFromUIPickListComponent(component, value);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, NormativaObject normativa) {
        if (normativa == null) {
            return "";
        }
        return normativa.getId().toString();
    }

    @SuppressWarnings("unchecked")
    private NormativaObject getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<NormativaObject> dualList;
        try {
            dualList = (DualListModel<NormativaObject>) ((PickList) component).getValue();
            NormativaObject resource = getObjectFromList(dualList.getSource(), Long.valueOf(value));
            if (resource == null) {
                resource = getObjectFromList(dualList.getTarget(), Long.valueOf(value));
            }

            return resource;
        } catch (ClassCastException | NumberFormatException cce) {
            throw new ConverterException();
        }
    }

    private NormativaObject getObjectFromList(final List<?> list, final Long identifier) {
        for (final Object object : list) {
            final NormativaObject resource = (NormativaObject) object;
            if (resource.getId().equals(identifier)) {
                return resource;
            }
        }
        return null;
    }
}
package es.caib.archium.converters;

import es.caib.archium.objects.tipusPublicObject;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.List;

@FacesConverter("tipusPublicConverter")
public class TipusPublicConverter implements Converter<tipusPublicObject> {

    @Override
    public tipusPublicObject getAsObject(FacesContext context, UIComponent component, String value) {
        return getObjectFromUIPickListComponent(component, value);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, tipusPublicObject tipusPublic) {
        if (tipusPublic == null) {
            return "";
        }
        return tipusPublic.getId().toString();
    }

    @SuppressWarnings("unchecked")
    private tipusPublicObject getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<tipusPublicObject> dualList;
        try {
            dualList = (DualListModel<tipusPublicObject>) ((PickList) component).getValue();
            tipusPublicObject resource = getObjectFromList(dualList.getSource(), Long.valueOf(value));
            if (resource == null) {
                resource = getObjectFromList(dualList.getTarget(), Long.valueOf(value));
            }

            return resource;
        } catch (ClassCastException | NumberFormatException cce) {
            throw new ConverterException();
        }
    }

    private tipusPublicObject getObjectFromList(final List<?> list, final Long identifier) {
        for (final Object object : list) {
            final tipusPublicObject resource = (tipusPublicObject) object;
            if (resource.getId().equals(identifier)) {
                return resource;
            }
        }
        return null;
    }
}
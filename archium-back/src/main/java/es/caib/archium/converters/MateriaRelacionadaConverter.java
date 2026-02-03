package es.caib.archium.converters;

import es.caib.archium.objects.MateriaObject;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.List;

@FacesConverter("materiaRelacionadaConverter")
public class MateriaRelacionadaConverter implements Converter<MateriaObject> {

    @Override
    public MateriaObject getAsObject(FacesContext context, UIComponent component, String value) {
        return getObjectFromUIPickListComponent(component, value);

    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, MateriaObject materia) {
        if (materia == null) {
            return "";
        }
        return materia.getId().toString();
    }

    @SuppressWarnings("unchecked")
    private MateriaObject getObjectFromUIPickListComponent(UIComponent component, String value) {
        final DualListModel<MateriaObject> dualList;
        try {
            dualList = (DualListModel<MateriaObject>) ((PickList) component).getValue();
            MateriaObject resource = getObjectFromList(dualList.getSource(), Long.valueOf(value));
            if (resource == null) {
                resource = getObjectFromList(dualList.getTarget(), Long.valueOf(value));
            }

            return resource;
        } catch (ClassCastException | NumberFormatException cce) {
            throw new ConverterException();
        }
    }

    private MateriaObject getObjectFromList(final List<?> list, final Long identifier) {
        for (final Object object : list) {
            final MateriaObject resource = (MateriaObject) object;
            if (resource.getId().equals(identifier)) {
                return resource;
            }
        }
        return null;
    }
}
package es.caib.archium.converters;

import es.caib.archium.objects.AplicacionObject;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.List;

;

@FacesConverter("aplicacionesConverter")
public class AplicacionesConverter implements Converter<AplicacionObject>{

	@Override
	public AplicacionObject getAsObject(FacesContext context, UIComponent component, String value) {
		return getObjectFromUIPickListComponent(component, value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, AplicacionObject value) {
		String string;
		if (value == null) {
			string = "";
		} else {
			string = String.valueOf(value.getId());
			
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private AplicacionObject getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<AplicacionObject> dualList;
		try {
			dualList = (DualListModel<AplicacionObject>) ((PickList) component).getValue();
			AplicacionObject resource = getObjectFromList(dualList.getSource(), Long.valueOf(value));
			if (resource == null) {
				resource = getObjectFromList(dualList.getTarget(), Long.valueOf(value));
			}

			return resource;
		} catch (ClassCastException | NumberFormatException cce) {
			throw new ConverterException();
		}
	}

	private AplicacionObject getObjectFromList(final List<?> list, final Long identifier) {
		for (final Object object : list) {
			final AplicacionObject resource = (AplicacionObject) object;
			if (resource.getId().equals(identifier)) {
				return resource;
			}
		}
		return null;
	}
}

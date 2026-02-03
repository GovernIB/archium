package es.caib.archium.converters;

import es.caib.archium.objects.CausaLimitacioObject;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.List;

@FacesConverter("causasRelacionadasConverter")
public class CausasRelacionadasConverter implements Converter<CausaLimitacioObject> {

	@Override
	public CausaLimitacioObject getAsObject(FacesContext context, UIComponent component, String value) {
		return getObjectFromUIPickListComponent(component, value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, CausaLimitacioObject value) {
		String string;
		if (value == null) {
			string = "";
		} else {
			string = String.valueOf(value.getId());
			
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private CausaLimitacioObject getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<CausaLimitacioObject> dualList;
		try {
			dualList = (DualListModel<CausaLimitacioObject>) ((PickList) component).getValue();
			CausaLimitacioObject resource = getObjectFromList(dualList.getSource(), Long.valueOf(value));
			if (resource == null) {
				resource = getObjectFromList(dualList.getTarget(), Long.valueOf(value));
			}

			return resource;
		} catch (ClassCastException | NumberFormatException cce) {
			throw new ConverterException(cce);
		}
	}

	private CausaLimitacioObject getObjectFromList(final List<?> list, final Long identifier) {
		for (final Object object : list) {
			final CausaLimitacioObject resource = (CausaLimitacioObject) object;
			if (resource.getId().equals(identifier)) {
				return resource;
			}
		}
		return null;
	}
}
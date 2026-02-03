package es.caib.archium.converters;

import es.caib.archium.objects.NormativaAprobacioObject;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.List;

@FacesConverter("seriesNormativaConverter")
public class SeriesNormativaConverter implements Converter<NormativaAprobacioObject> {

	@Override
	public NormativaAprobacioObject getAsObject(FacesContext context, UIComponent component, String value) {
		return getObjectFromUIPickListComponent(component, value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, NormativaAprobacioObject value) {
		String string;
		if (value == null) {
			string = "";
		} else {
			string = String.valueOf(value.getId());
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private NormativaAprobacioObject getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<NormativaAprobacioObject> dualList;
		try {
			dualList = (DualListModel<NormativaAprobacioObject>) ((PickList) component).getValue();
			NormativaAprobacioObject resource = getObjectFromList(dualList.getSource(), Long.valueOf(value));
			if (resource == null) {
				resource = getObjectFromList(dualList.getTarget(), Long.valueOf(value));
			}

			return resource;
		} catch (ClassCastException | NumberFormatException cce) {
			throw new ConverterException();
		}
	}

	private NormativaAprobacioObject getObjectFromList(final List<?> list, final Long identifier) {
		for (final Object object : list) {
			final NormativaAprobacioObject resource = (NormativaAprobacioObject) object;
			if (resource.getId().equals(identifier)) {
				return resource;
			}
		}
		return null;
	}
}
package es.caib.archium.converters;

import es.caib.archium.objects.ProcedimentObject;
import es.caib.archium.objects.SerieDocumentalObject;
import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import java.util.List;

@FacesConverter("procedimentsRelacionatsConverter")
public class ProcedimentsRelacionatsConverter implements Converter<ProcedimentObject>{

	@Override
	public ProcedimentObject getAsObject(FacesContext context, UIComponent component, String value) {
		return getObjectFromUIPickListComponent(component, value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, ProcedimentObject value) {
		String string;
		if (value == null) {
			string = "";
		} else {
			string = String.valueOf(value.getId());
			
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private ProcedimentObject getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<SerieDocumentalObject> dualList;
		try {
			dualList = (DualListModel<SerieDocumentalObject>) ((PickList) component).getValue();
			ProcedimentObject resource = getObjectFromList(dualList.getSource(), Long.valueOf(value));
			if (resource == null) {
				resource = getObjectFromList(dualList.getTarget(), Long.valueOf(value));
			}
			return resource;
		} catch (ClassCastException | NumberFormatException cce) {
			throw new ConverterException();
		}
	}

	private ProcedimentObject getObjectFromList(final List<?> list, final Long identifier) {
		for (final Object object : list) {
			final ProcedimentObject resource = (ProcedimentObject) object;
			if (resource.getId().equals(identifier)) {
				return resource;
			}
		}
		return null;
	}
}

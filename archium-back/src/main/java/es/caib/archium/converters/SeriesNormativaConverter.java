package es.caib.archium.converters;

import java.util.List;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import es.caib.archium.controllers.DictamenController;
import es.caib.archium.objects.AplicacionObject;
import es.caib.archium.objects.DictamenObject;
import es.caib.archium.objects.NormativaAprobacioObject;
import es.caib.archium.objects.NormativaObject;

@FacesConverter("seriesNormativaConverter")
public class SeriesNormativaConverter implements Converter<NormativaAprobacioObject> {

	@Override
	public NormativaAprobacioObject getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
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
		} catch (ClassCastException cce) {
			throw new ConverterException();
		} catch (NumberFormatException nfe) {
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
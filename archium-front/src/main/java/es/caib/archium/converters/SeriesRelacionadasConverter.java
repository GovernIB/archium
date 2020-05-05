package es.caib.archium.converters;

import java.util.List;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;

import org.primefaces.component.picklist.PickList;
import org.primefaces.model.DualListModel;

import es.caib.archium.objects.SerieDocumentalObject;

@FacesConverter("seriesRelacionadasConverter")
public class SeriesRelacionadasConverter implements Converter<SerieDocumentalObject>{

	@Override
	public SerieDocumentalObject getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
		return getObjectFromUIPickListComponent(component, value);
	}

	@Override
	public String getAsString(FacesContext context, UIComponent component, SerieDocumentalObject value) {
		String string;
		if (value == null) {
			string = "";
		} else {
			string = String.valueOf(value.getSerieId());
			
		}
		return string;
	}

	@SuppressWarnings("unchecked")
	private SerieDocumentalObject getObjectFromUIPickListComponent(UIComponent component, String value) {
		final DualListModel<SerieDocumentalObject> dualList;
		try {
			dualList = (DualListModel<SerieDocumentalObject>) ((PickList) component).getValue();
			SerieDocumentalObject resource = getObjectFromList(dualList.getSource(), Long.valueOf(value));
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

	private SerieDocumentalObject getObjectFromList(final List<?> list, final Long identifier) {
		for (final Object object : list) {
			final SerieDocumentalObject resource = (SerieDocumentalObject) object;
			if (resource.getSerieId().equals(identifier)) {
				return resource;
			}
		}
		return null;
	}
}

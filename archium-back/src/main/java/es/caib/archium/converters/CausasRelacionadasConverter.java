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
import es.caib.archium.objects.CausaLimitacioObject;
import es.caib.archium.objects.DictamenObject;
import es.caib.archium.objects.SerieArgenObject;
import es.caib.archium.objects.SerieDocumentalObject;

@FacesConverter("causasRelacionadasConverter")
public class CausasRelacionadasConverter implements Converter<CausaLimitacioObject> {

	@Override
	public CausaLimitacioObject getAsObject(FacesContext context, UIComponent component, String value) {
		// TODO Auto-generated method stub
		System.out.println("VALUE: " + value);
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
		System.out.println("STRING: " + string);
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
		} catch (ClassCastException cce) {
			throw new ConverterException();
		} catch (NumberFormatException nfe) {
			throw new ConverterException();
		}
	}

	private CausaLimitacioObject getObjectFromList(final List<?> list, final Long identifier) {
		for (final Object object : list) {
			final CausaLimitacioObject resource = (CausaLimitacioObject) object;
			System.out.println("CONVERTER : " + resource.getNom());
			if (resource.getId().equals(identifier)) {
				return resource;
			}
		}
		return null;
	}
}
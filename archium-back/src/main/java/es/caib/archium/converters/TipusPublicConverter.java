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

import es.caib.archium.objects.tipusPublicObject;

@FacesConverter("tipusPublicConverter")
public class TipusPublicConverter implements Converter{

	    @Override
	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	    	return getObjectFromUIPickListComponent(component, value);
	    	
		}

	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object object) {
	    	if (object == null) {
	            return "";
	        }
	        if (object instanceof tipusPublicObject) {
	        	tipusPublicObject quadre= (tipusPublicObject) object;
	            Long name = quadre.getId();
	            return name.toString();
	        } else {
	            throw new ConverterException(new FacesMessage(object + " is not a valid tipu Public "));
	        }
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
			} catch (ClassCastException cce) {
				throw new ConverterException();
			} catch (NumberFormatException nfe) {
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
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

import es.caib.archium.objects.MateriaObject;

@FacesConverter("materiaRelacionadaConverter")
public class MateriaRelacionadaConverter implements Converter{

	    @Override
	    public Object getAsObject(FacesContext context, UIComponent component, String value) {
	    	// TODO Auto-generated method stub
	    	return getObjectFromUIPickListComponent(component, value);
	    	
		}

	    @Override
	    public String getAsString(FacesContext context, UIComponent component, Object object) {
	   	 //System.out.println("GET AS STRING");
	    	if (object == null) {
	            return "";
	        }
	        if (object instanceof MateriaObject) {
	        	MateriaObject quadre= (MateriaObject) object;
	            Long name = quadre.getId();
	            //System.out.println("Ens Object :" + name);
	            return name.toString();
	        } else {
	            throw new ConverterException(new FacesMessage(object + " is not a valid MateriaO "));
	        }
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
			} catch (ClassCastException cce) {
				throw new ConverterException();
			} catch (NumberFormatException nfe) {
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
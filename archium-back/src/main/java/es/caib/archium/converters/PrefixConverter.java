package es.caib.archium.converters;

import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.FacesConverter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@FacesConverter("prefixConverter")
public class PrefixConverter implements Converter<String> {
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Override
    public String getAsObject(FacesContext context, UIComponent component, String value) {
        if (value == null || value.trim().isEmpty()) {
            return null;
        }
        
        // Limpiar el prefijo automáticamente para el guardado: "99 - Altres" -> "99"
        if (value.contains(" - ")) {
            String codigo = value.split(" - ")[0].trim();
            log.info("PrefixConverter - Limpiando: {} -> {}",value,codigo);
            return codigo;
        }
        
        return value.trim();
    }
	
	@Override
    public String getAsString(FacesContext context, UIComponent component, String value) {
        if (value == null) {
            return "";
        }
        return value;
    }
	
	
}

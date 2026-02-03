package es.caib.archium.converters;

import es.caib.archium.objects.FuncioObject;
import es.caib.archium.services.FuncioFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value="FuncioConverter", managed = true)
public class FuncioConverter implements Converter<FuncioObject> {

    @Inject
    private FuncioFrontService funcioFrontService;

    @Override
    public FuncioObject getAsObject(FacesContext context, UIComponent component, String newValue) {
    	if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }
        try {
            final Long funcioId = Long.valueOf(newValue);
            return funcioFrontService.findById(funcioId);
        } catch(final Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to FuncioObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, FuncioObject funcio) {

    	if (funcio == null) {
            return "";
        }
        return String.valueOf(funcio.getId());
    }
}
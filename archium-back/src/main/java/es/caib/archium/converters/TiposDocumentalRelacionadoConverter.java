package es.caib.archium.converters;

import es.caib.archium.objects.TipuDocumentalObject;
import es.caib.archium.services.ProcedimentFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value="tiposDocumentalRelacionadoConverter", managed = true)
public class TiposDocumentalRelacionadoConverter implements Converter<TipuDocumentalObject> {

    @Inject
    private ProcedimentFrontService procedimentFrontService;

    @Override
    public TipuDocumentalObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }

        try {
            final Long tdId = Long.valueOf(newValue);
            return procedimentFrontService.findTipusDocumentalById(tdId);
        } catch (final Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to TipuDocumentalObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, TipuDocumentalObject tipusDocumental) {

        if (tipusDocumental == null) {
            return "";
        }

        return String.valueOf(tipusDocumental.getId());
    }
}
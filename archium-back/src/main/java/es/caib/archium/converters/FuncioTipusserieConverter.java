package es.caib.archium.converters;

import es.caib.archium.objects.TipusSerieObject;
import es.caib.archium.services.FuncioFrontService;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.inject.Inject;

@FacesConverter(value = "FuncioTipusserieConverter", managed = true)
public class FuncioTipusserieConverter implements Converter<TipusSerieObject> {

    @Inject
    private FuncioFrontService funcioFrontService;

    @Override
    public TipusSerieObject getAsObject(FacesContext context, UIComponent component, String newValue) {
        if ((null == newValue) || (newValue.trim().isEmpty())) {
            return null;
        }

        try {
            final Long tipusSerieId = Long.valueOf(newValue);
            return funcioFrontService.findTipusSerieById(tipusSerieId);
        } catch (final Exception e) {
            throw new ConverterException(new FacesMessage(String.format("Cannot convert %s to TipusSerieObject", newValue)), e);
        }
    }

    @Override
    public String getAsString(FacesContext context, UIComponent component, TipusSerieObject tipusSerie) {

        if (tipusSerie == null) {
            return "";
        }
        return String.valueOf(tipusSerie.getId());
    }
}
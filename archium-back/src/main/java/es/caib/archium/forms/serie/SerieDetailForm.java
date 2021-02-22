package es.caib.archium.forms.serie;


import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.forms.serie.comun.AccesoForm;
import es.caib.archium.forms.serie.comun.DictamenForm;
import es.caib.archium.forms.serie.comun.ValoracionForm;
import es.caib.archium.objects.FuncioObject;
import org.apache.commons.lang3.StringUtils;

import javax.activation.DataHandler;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class SerieDetailForm implements Serializable {

    private String codigo;
    private String esencial;
    private String parentFunctionCode;
    private String parentFunctionName;
    private String denominacionClase;
    private String tipoClasificacion;
    private String lopd;
    private String confidencialidad;
    private String usuariosAplicacion;
    private String seriesArgen;
    private AccesoForm acceso;
    private ValoracionForm valoracion;
    private DictamenForm dictamen;
    private DataHandler content;

    public String getCodigo() {
        return codigo;
    }

    public void setCodigo(String codigo) {
        this.codigo = codigo;
    }

    public String getEsencial() {
        return this.esencial;
    }

    public void setEsencial(String esencial) {
        this.esencial = esencial;
    }

    public DataHandler getContent() {
        return content;
    }

    public void setContent(DataHandler content) {
        this.content = content;
    }

    public String getParentFunctionCode() {
        return parentFunctionCode;
    }

    public void setParentFunctionCode(String parentFunctionCode) {
        this.parentFunctionCode = parentFunctionCode;
    }

    public String getParentFunctionName() {
        return parentFunctionName;
    }

    public void setParentFunctionName(String parentFunctionName) {
        this.parentFunctionName = parentFunctionName;
    }

    public String getTipoClasificacion() {
        return tipoClasificacion;
    }

    public void setTipoClasificacion(String tipoClasificacion) {
        this.tipoClasificacion = tipoClasificacion;
    }

    public String getLopd() {
        return lopd;
    }

    public void setLopd(String lopd) {
        this.lopd = lopd;
    }

    public String getConfidencialidad() {
        return confidencialidad;
    }

    public void setConfidencialidad(String confidencialidad) {
        this.confidencialidad = confidencialidad;
    }

    public AccesoForm getAcceso() {
        return acceso;
    }

    public void setAcceso(AccesoForm acceso) {
        this.acceso = acceso;
    }

    public ValoracionForm getValoracion() {
        return valoracion;
    }

    public void setValoracion(ValoracionForm valoracion) {
        this.valoracion = valoracion;
    }

    public DictamenForm getDictamen() {
        return dictamen;
    }

    public void setDictamen(DictamenForm dictamen) {
        this.dictamen = dictamen;
    }

    public String getDenominacionClase() {
        return denominacionClase;
    }

    public void setDenominacionClase(String denominacionClase) {
        this.denominacionClase = denominacionClase;
    }

    public String getUsuariosAplicacion() {
        return usuariosAplicacion;
    }

    public void setUsuariosAplicacion(String usuariosAplicacion) {
        this.usuariosAplicacion = usuariosAplicacion;
    }

    public String getSeriesArgen() {
        return seriesArgen;
    }

    public void setSeriesArgen(String seriesArgen) {
        this.seriesArgen = seriesArgen;
    }

    public void converter(Serie serieWs, FuncioObject parentFunction) {
        this.parentFunctionCode = parentFunction == null ? campoVacio() : parentFunction.getCodi();
        this.parentFunctionName = parentFunction == null ? campoVacio() : parentFunction.getNom();
        this.denominacionClase = serieWs.getDenominacionClase() == null ? campoVacio() : serieWs.getDenominacionClase();
        this.tipoClasificacion = serieWs.getTipoClasificacion() == null ? campoVacio() : serieWs.getTipoClasificacion();
        this.lopd = serieWs.getLopd() == null ? campoVacio() : serieWs.getLopd().getValue();
        this.confidencialidad = serieWs.getConfidencialidad() == null ? campoVacio() : serieWs.getConfidencialidad().getValue();
        this.codigo = serieWs.getCodigo() == null ? campoVacio() : serieWs.getCodigo();
        this.esencial = serieWs.getEsencial() == null ? "No" : serieWs.getEsencial() == true ? "Sí" : "No";

        if (serieWs.getSeriesArgen() != null && !serieWs.getSeriesArgen().isEmpty()) {
            this.seriesArgen = serieWs.getSeriesArgen().stream().collect(Collectors.joining(", "));
        }

        if (serieWs.getUsuariosAplicacion() != null && !serieWs.getUsuariosAplicacion().isEmpty()) {
            this.usuariosAplicacion = serieWs.getUsuariosAplicacion().stream().collect(Collectors.joining(", "));
        }
        AccesoForm acceso = new AccesoForm();
        acceso.setTipoAcceso(serieWs.getTipoAcceso() == null ? campoVacio() : serieWs.getTipoAcceso().getValue());
        acceso.setCondicionReutilizacion(serieWs.getCondicionReutilizacion() == null ? campoVacio() : serieWs.getCondicionReutilizacion());
        if (serieWs.getCodigoLimitacion() != null && !serieWs.getCodigoLimitacion().isEmpty()) {
            // Pasamos a un set para quitar las limitaciones repetidas y despues creamos el string separado por ','
            acceso.setCausaLimitacion(serieWs.getCodigoLimitacion().stream().collect(Collectors.toSet()).stream().collect(Collectors.joining(", ")));
        } else {
            acceso.setCausaLimitacion(campoVacio());
        }
        this.acceso = acceso;

        ValoracionForm valoracion = new ValoracionForm();
        valoracion.setValoracionSecundaria(serieWs.getValorSecundario() == null ? campoVacio() : serieWs.getValorSecundario().getValue());
        if (serieWs.getTipoValor() != null && !serieWs.getTipoValor().isEmpty()) {
            valoracion.setValoracionesPrimarias(serieWs.getTipoValor().stream().map(x -> x.getValue()).collect(Collectors.joining(", ")));
        } else {
            valoracion.setValoracionesPrimarias(campoVacio());
        }
        this.valoracion = valoracion;

        DictamenForm dictamen = new DictamenForm();
        dictamen.setTipoDictamen(serieWs.getTipoDictamen() == null ? campoVacio() : serieWs.getTipoDictamen().getValue());
        dictamen.setAccionDictaminada(serieWs.getAccionDictaminada() == null ? campoVacio() : serieWs.getAccionDictaminada());
        if (serieWs.getPlazoAccionDictaminada() != null) {
            dictamen.setPlazo(serieWs.getPlazoAccionDictaminada() + "" + serieWs.getUnidadPlazoAccionDictaminada());
        } else {
            dictamen.setPlazo(campoVacio());
        }
        this.dictamen = dictamen;
    }

    private String campoVacio() {
        return "-";
    }
}

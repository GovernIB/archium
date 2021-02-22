package es.caib.archium.csgd.apirest.facade.pojos;

import es.caib.archium.csgd.apirest.constantes.*;

import javax.activation.DataHandler;
import java.util.List;

public class Serie extends Nodo{

    private String denominacionClase;
    private LOPD lopd;
    private Confidencialidad confidencialidad;
    private TipoAcceso tipoAcceso;
    private List<String> codigoLimitacion;
    private List<String> usuariosAplicacion;
    private List<String> seriesArgen;
    private String condicionReutilizacion;
    private List<TipoValor> tipoValor;
    private ValorSecundario valorSecundario;
    private TipoDictamen tipoDictamen;
    private String accionDictaminada;
    private Integer plazoAccionDictaminada;
    private UnidadPlazo unidadPlazoAccionDictaminada;
    private Boolean isEsencial;
    private String tipoClasificacion;
    private Integer resellado;
    private UnidadPlazo unidadResellado;
    private String content;
    private String mimeType;
    private String encoding;
    private DataHandler binaryContent;

    public List<String> getUsuariosAplicacion() {
        return usuariosAplicacion;
    }

    public void setUsuariosAplicacion(List<String> usuariosAplicacion) {
        this.usuariosAplicacion = usuariosAplicacion;
    }

    public List<String> getSeriesArgen() {
        return seriesArgen;
    }

    public void setSeriesArgen(List<String> seriesArgen) {
        this.seriesArgen = seriesArgen;
    }

    public DataHandler getBinaryContent() {
        return binaryContent;
    }

    public void setBinaryContent(DataHandler binaryContent) {
        this.binaryContent = binaryContent;
    }

    public UnidadPlazo getUnidadPlazoAccionDictaminada() {
        return unidadPlazoAccionDictaminada;
    }

    public void setUnidadPlazoAccionDictaminada(UnidadPlazo unidadPlazoAccionDictaminada) {
        this.unidadPlazoAccionDictaminada = unidadPlazoAccionDictaminada;
    }

    public UnidadPlazo getUnidadResellado() {
        return unidadResellado;
    }

    public void setUnidadResellado(UnidadPlazo unidadResellado) {
        this.unidadResellado = unidadResellado;
    }

    public String getDenominacionClase() {
        return denominacionClase;
    }

    public void setDenominacionClase(String denominacionClase) {
        this.denominacionClase = denominacionClase;
    }

    public String getEncoding() {
        return encoding;
    }

    public void setEncoding(String encoding) {
        this.encoding = encoding;
    }

    public String getMimeType() {
        return mimeType;
    }

    public void setMimeType(String mimeType) {
        this.mimeType = mimeType;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LOPD getLopd() {
        return lopd;
    }

    public void setLopd(LOPD lopd) {
        this.lopd = lopd;
    }

    public Confidencialidad getConfidencialidad() {
        return confidencialidad;
    }

    public void setConfidencialidad(Confidencialidad confidencialidad) {
        this.confidencialidad = confidencialidad;
    }

    public TipoAcceso getTipoAcceso() {
        return tipoAcceso;
    }

    public void setTipoAcceso(TipoAcceso tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public String getCondicionReutilizacion() {
        return condicionReutilizacion;
    }

    public void setCondicionReutilizacion(String condicionReutilizacion) {
        this.condicionReutilizacion = condicionReutilizacion;
    }

    public List<TipoValor> getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(List<TipoValor> tipoValor) {
        this.tipoValor = tipoValor;
    }

    public ValorSecundario getValorSecundario() {
        return valorSecundario;
    }

    public void setValorSecundario(ValorSecundario valorSecundario) {
        this.valorSecundario = valorSecundario;
    }

    public TipoDictamen getTipoDictamen() {
        return tipoDictamen;
    }

    public void setTipoDictamen(TipoDictamen tipoDictamen) {
        this.tipoDictamen = tipoDictamen;
    }

    public String getAccionDictaminada() {
        return accionDictaminada;
    }

    public void setAccionDictaminada(String accionDictaminada) {
        this.accionDictaminada = accionDictaminada;
    }

    public Integer getPlazoAccionDictaminada() {
        return plazoAccionDictaminada;
    }

    public void setPlazoAccionDictaminada(Integer plazoAccionDictaminada) {
        this.plazoAccionDictaminada = plazoAccionDictaminada;
    }

    public Boolean getEsencial() {
        return isEsencial;
    }

    public void setEsencial(Boolean esencial) {
        isEsencial = esencial;
    }

    public String getTipoClasificacion() {
        return tipoClasificacion;
    }

    public void setTipoClasificacion(String tipoClasificacion) {
        this.tipoClasificacion = tipoClasificacion;
    }

    public Integer getResellado() {
        return resellado;
    }

    public void setResellado(Integer resellado) {
        this.resellado = resellado;
    }

    public List<String> getCodigoLimitacion() {
        return codigoLimitacion;
    }

    public void setCodigoLimitacion(List<String> codigoLimitacion) {
        this.codigoLimitacion = codigoLimitacion;
    }
}

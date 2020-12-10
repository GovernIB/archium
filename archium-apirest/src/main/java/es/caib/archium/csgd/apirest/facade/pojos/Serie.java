package es.caib.archium.csgd.apirest.facade.pojos;

import es.caib.archium.csgd.apirest.constantes.*;

import java.util.List;

public class Serie extends Nodo{

    private String denominacionClase;
    private LOPD lopd;
    private Confidencialidad confidencialidad;
    private TipoAcceso tipoAcceso;
    private List<String> codigoLimitacion;
    private String condicionReutilizacion;
    private List<TipoValor> tipoValor;
    private String valorSecundario;
    private TipoDictamen tipoDictamen;
    private String accionDictaminada;
    private Integer plazoAccionDictaminada;
    private String unidadPlazoAccionDictaminada;
    private Boolean isEsencial;
    private String tipoClasificacion;
    private Integer resellado;
    private String unidadResellado;
    private String content;
    private String mimeType;
    private String encoding;

    public String getUnidadPlazoAccionDictaminada() {
        return unidadPlazoAccionDictaminada;
    }

    public void setUnidadPlazoAccionDictaminada(String unidadPlazoAccionDictaminada) {
        this.unidadPlazoAccionDictaminada = unidadPlazoAccionDictaminada;
    }

    public String getUnidadResellado() {
        return unidadResellado;
    }

    public void setUnidadResellado(String unidadResellado) {
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

    public String getValorSecundario() {
        return valorSecundario;
    }

    public void setValorSecundario(String valorSecundario) {
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

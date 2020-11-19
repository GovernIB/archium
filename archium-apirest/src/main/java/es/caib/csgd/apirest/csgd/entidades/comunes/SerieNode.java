package es.caib.csgd.apirest.csgd.entidades.comunes;

public class SerieNode {

    private String codigoClasificacion;
    private String lopd;
    private String confidencialidad;
    private String tipoAcceso;
    private String causaLimitacion;
    private String normativa;
    private String condicionReutilizacion;
    private String tipoValor;
    private Long termino;
    private String valorSecundario;
    private String tipoDictamen;
    private String accionDictaminada;
    private Long terminoAccionDictaminada;
    private Boolean isEsencial;
    private String tipoClasificacion;
    private Boolean resellado;

    public String getCodigoClasificacion() {
        return codigoClasificacion;
    }

    public void setCodigoClasificacion(String codigoClasificacion) {
        this.codigoClasificacion = codigoClasificacion;
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

    public String getTipoAcceso() {
        return tipoAcceso;
    }

    public void setTipoAcceso(String tipoAcceso) {
        this.tipoAcceso = tipoAcceso;
    }

    public String getCausaLimitacion() {
        return causaLimitacion;
    }

    public void setCausaLimitacion(String causaLimitacion) {
        this.causaLimitacion = causaLimitacion;
    }

    public String getNormativa() {
        return normativa;
    }

    public void setNormativa(String normativa) {
        this.normativa = normativa;
    }

    public String getCondicionReutilizacion() {
        return condicionReutilizacion;
    }

    public void setCondicionReutilizacion(String condicionReutilizacion) {
        this.condicionReutilizacion = condicionReutilizacion;
    }

    public String getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(String tipoValor) {
        this.tipoValor = tipoValor;
    }

    public Long getTermino() {
        return termino;
    }

    public void setTermino(Long termino) {
        this.termino = termino;
    }

    public String getValorSecundario() {
        return valorSecundario;
    }

    public void setValorSecundario(String valorSecundario) {
        this.valorSecundario = valorSecundario;
    }

    public String getTipoDictamen() {
        return tipoDictamen;
    }

    public void setTipoDictamen(String tipoDictamen) {
        this.tipoDictamen = tipoDictamen;
    }

    public String getAccionDictaminada() {
        return accionDictaminada;
    }

    public void setAccionDictaminada(String accionDictaminada) {
        this.accionDictaminada = accionDictaminada;
    }

    public Long getTerminoAccionDictaminada() {
        return terminoAccionDictaminada;
    }

    public void setTerminoAccionDictaminada(Long terminoAccionDictaminada) {
        this.terminoAccionDictaminada = terminoAccionDictaminada;
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

    public Boolean getResellado() {
        return resellado;
    }

    public void setResellado(Boolean resellado) {
        this.resellado = resellado;
    }
}

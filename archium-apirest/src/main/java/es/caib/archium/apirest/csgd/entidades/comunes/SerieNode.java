package es.caib.archium.apirest.csgd.entidades.comunes;

import es.caib.archium.apirest.constantes.*;

public class SerieNode {

    private String codigoCuadro;
    private String codigoClasificacion;
    private LOPD lopd;
    private Confidencialidad confidencialidad;
    private TipoAcceso tipoAcceso;
    private CausaLimitacion causaLimitacion;
    private String normativa;
    private String condicionReutilizacion;
    private TipoValor tipoValor;
    private Long termino;
    private ValorSecundario valorSecundario;
    private TipoDictamen tipoDictamen;
    private String accionDictaminada;
    private Long terminoAccionDictaminada;
    private Boolean isEsencial;
    private TipoClasificacion tipoClasificacion;
    private Boolean resellado;

    public String getCodigoCuadro() {
        return codigoCuadro;
    }

    public void setCodigoCuadro(String codigoCuadro) {
        this.codigoCuadro = codigoCuadro;
    }

    public String getCodigoClasificacion() {
        return codigoClasificacion;
    }

    public void setCodigoClasificacion(String codigoClasificacion) {
        this.codigoClasificacion = codigoClasificacion;
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

    public CausaLimitacion getCausaLimitacion() {
        return causaLimitacion;
    }

    public void setCausaLimitacion(CausaLimitacion causaLimitacion) {
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

    public TipoValor getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(TipoValor tipoValor) {
        this.tipoValor = tipoValor;
    }

    public Long getTermino() {
        return termino;
    }

    public void setTermino(Long termino) {
        this.termino = termino;
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

    public TipoClasificacion getTipoClasificacion() {
        return tipoClasificacion;
    }

    public void setTipoClasificacion(TipoClasificacion tipoClasificacion) {
        this.tipoClasificacion = tipoClasificacion;
    }

    public Boolean getResellado() {
        return resellado;
    }

    public void setResellado(Boolean resellado) {
        this.resellado = resellado;
    }
}

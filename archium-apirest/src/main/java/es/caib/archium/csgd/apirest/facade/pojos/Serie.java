package es.caib.archium.csgd.apirest.facade.pojos;

import es.caib.archium.csgd.apirest.constantes.*;

import javax.activation.DataHandler;
import java.util.List;

public class Serie {

    private String codigoClasificacion;
    private LOPD lopd;
    private Confidencialidad confidencialidad;
    private TipoAcceso tipoAcceso;
    private List<String> causaLimitacion;
    private String condicionReutilizacion;
    private List<TipoValor> tipoValor;
    private List<Long> termino;
    private List<String> valorSecundario;
    private TipoDictamen tipoDictamen;
    private String accionDictaminada;
    private Long terminoAccionDictaminada;
    private Boolean isEsencial;
    private String tipoClasificacion;
    private Long resellado;
    private DataHandler content;

    public DataHandler getContent() {
        return content;
    }

    public void setContent(DataHandler content) {
        this.content = content;
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

    public List<String> getValorSecundario() {
        return valorSecundario;
    }

    public void setValorSecundario(List<String> valorSecundario) {
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

    public String getTipoClasificacion() {
        return tipoClasificacion;
    }

    public void setTipoClasificacion(String tipoClasificacion) {
        this.tipoClasificacion = tipoClasificacion;
    }

    public Long getResellado() {
        return resellado;
    }

    public void setResellado(Long resellado) {
        this.resellado = resellado;
    }

    public List<String> getCausaLimitacion() {
        return causaLimitacion;
    }

    public void setCausaLimitacion(List<String> causaLimitacion) {
        this.causaLimitacion = causaLimitacion;
    }

    public List<Long> getTermino() {
        return termino;
    }

    public void setTermino(List<Long> termino) {
        this.termino = termino;
    }
}

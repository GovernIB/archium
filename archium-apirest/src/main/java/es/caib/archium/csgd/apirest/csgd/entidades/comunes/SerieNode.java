package es.caib.archium.csgd.apirest.csgd.entidades.comunes;

import javax.activation.DataHandler;
import java.util.List;

public class SerieNode {

    private String codigoClasificacion;
    private String lopd;
    private String confidencialidad;
    private String tipoAcceso;
    private List<String> causaLimitacion;
    private String condicionReutilizacion;
    private List<String> tipoValor;
    private List<Long> termino;
    private List<String> valorSecundario;
    private String tipoDictamen;
    private String accionDictaminada;
    private Long terminoAccionDictaminada;
    private Boolean isEsencial;
    private String tipoClasificacion;
    private Long resellado;
    private DataHandler content;

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

    public List<String> getCausaLimitacion() {
        return causaLimitacion;
    }

    public void setCausaLimitacion(List<String> causaLimitacion) {
        this.causaLimitacion = causaLimitacion;
    }

    public String getCondicionReutilizacion() {
        return condicionReutilizacion;
    }

    public void setCondicionReutilizacion(String condicionReutilizacion) {
        this.condicionReutilizacion = condicionReutilizacion;
    }

    public List<String> getTipoValor() {
        return tipoValor;
    }

    public void setTipoValor(List<String> tipoValor) {
        this.tipoValor = tipoValor;
    }

    public List<Long> getTermino() {
        return termino;
    }

    public void setTermino(List<Long> termino) {
        this.termino = termino;
    }

    public List<String> getValorSecundario() {
        return valorSecundario;
    }

    public void setValorSecundario(List<String> valorSecundario) {
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

    public Long getResellado() {
        return resellado;
    }

    public void setResellado(Long resellado) {
        this.resellado = resellado;
    }

    public DataHandler getContent() {
        return content;
    }

    public void setContent(DataHandler content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "SerieNode{" +
                "codigoClasificacion='" + codigoClasificacion + '\'' +
                ", lopd='" + lopd + '\'' +
                ", confidencialidad='" + confidencialidad + '\'' +
                ", tipoAcceso='" + tipoAcceso + '\'' +
                ", causaLimitacion=" + causaLimitacion +
                ", condicionReutilizacion='" + condicionReutilizacion + '\'' +
                ", tipoValor=" + tipoValor +
                ", termino=" + termino +
                ", valorSecundario=" + valorSecundario +
                ", tipoDictamen='" + tipoDictamen + '\'' +
                ", accionDictaminada='" + accionDictaminada + '\'' +
                ", terminoAccionDictaminada=" + terminoAccionDictaminada +
                ", isEsencial=" + isEsencial +
                ", tipoClasificacion='" + tipoClasificacion + '\'' +
                ", resellado=" + resellado +
                ", content?=" + content!=null ? "true" : "false" +
                '}';
    }
}

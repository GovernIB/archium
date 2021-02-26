package es.caib.archium.csgd.apirest.facade.pojos;

import es.caib.archium.csgd.apirest.constantes.*;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.Node;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RootNode;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.SerieNode;
import es.caib.archium.csgd.apirest.utils.Constantes;

import javax.activation.DataHandler;
import java.util.Arrays;
import java.util.List;

public class Serie extends Nodo {

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

    @Override
    public void converter(Node nodo) {
        if(nodo!=null) {
            SerieNode dto = (SerieNode) nodo;
            // Utilizo el objeto aux para evitar los nullPointers en los casteos
            Object aux = null;
            this.codigo = dto.getName();
            this.nodeId = dto.getId();
            aux = getProperty(dto.getMetadataCollection(), Constantes.DENOMINACION_CLASE_QNAME);
            this.denominacionClase = aux == null ? null : (String) aux;
            aux = getProperty(dto.getMetadataCollection(), Constantes.LOPD_QNAME);
            this.lopd = aux == null ? null : LOPD.getLopd((String) aux);
            aux = getProperty(dto.getMetadataCollection(), Constantes.CONFIDENCIALIDAD_QNAME);
            this.confidencialidad = aux == null ? null : Confidencialidad.getConfidencialidad((String) aux);
            aux = getProperty(dto.getMetadataCollection(), Constantes.TIPO_ACCESO_QNAME);
            this.tipoAcceso = aux == null ? null : TipoAcceso.getTipoAcceso((String) aux);
            aux = getProperty(dto.getMetadataCollection(), Constantes.CODIGO_CAUSA_LIMITACION_QNAME);
            this.codigoLimitacion = aux == null ? null : aux instanceof String ? Arrays.asList((String) aux) : (List<String>) aux;
            aux = getProperty(dto.getMetadataCollection(), Constantes.USUARIO_APLICACION_QNAME);
            this.usuariosAplicacion = aux == null ? null : aux instanceof String ? Arrays.asList((String) aux) : (List<String>) aux;
            aux = getProperty(dto.getMetadataCollection(), Constantes.SERIE_ARGEN_QNAME);
            this.seriesArgen = aux == null ? null : aux instanceof String ? Arrays.asList((String) aux) : (List<String>) aux;
            aux = getProperty(dto.getMetadataCollection(), Constantes.CONDICION_REUTILIZACION_QNAME);
            this.condicionReutilizacion = aux == null ? null : (String) aux;
            aux = getProperty(dto.getMetadataCollection(), Constantes.TIPO_VALOR_QNAME);
            this.tipoValor = aux == null ? null : aux instanceof String ? Arrays.asList(TipoValor.getTipoValor((String) aux)) : (List<TipoValor>) aux;
            aux = getProperty(dto.getMetadataCollection(), Constantes.VALOR_SECUNDARIO_QNAME);
            this.valorSecundario = aux == null ? null : ValorSecundario.getValorSecundario((String) aux);
            aux = getProperty(dto.getMetadataCollection(), Constantes.TIPO_DICTAMEN_QNAME);
            this.tipoDictamen = aux == null ? null : TipoDictamen.getTipoDictamen((String) aux);
            aux = getProperty(dto.getMetadataCollection(), Constantes.ACCION_DICTAMINADA_QNAME);
            this.accionDictaminada = aux == null ? null : (String) aux;
            aux = getProperty(dto.getMetadataCollection(), Constantes.PLAZO_ACCION_DICTAMINADA_QNAME);
            this.plazoAccionDictaminada = aux == null ? null : (Integer) aux;
            aux = getProperty(dto.getMetadataCollection(), Constantes.PLAZO_UNIDAD_ACCION_DICTAMINADA_QNAME);
            this.unidadPlazoAccionDictaminada = aux == null ? null : UnidadPlazo.getUnidad((String) aux);
            aux = getProperty(dto.getMetadataCollection(), Constantes.DOCUMENTO_ESENCIAL_QNAME);
            this.isEsencial = aux == null ? null : (Boolean) aux;
            aux = getProperty(dto.getMetadataCollection(), Constantes.TIPO_CLASIFICACION_QNAME);
            this.tipoClasificacion = aux == null ? null : (String) aux;
            aux = getProperty(dto.getMetadataCollection(), Constantes.PLAZO_RESELLADO_QNAME);
            this.resellado = aux == null ? null : (Integer) aux;
            aux = getProperty(dto.getMetadataCollection(), Constantes.PLAZO_UNIDAD_RESELLADO_QNAME);
            this.unidadResellado = aux == null ? null : UnidadPlazo.getUnidad((String) aux);
            if (dto.getBinaryContent() != null) {
                this.content = dto.getBinaryContent().getContent();
                this.mimeType = dto.getBinaryContent().getMimetype();
                this.encoding = dto.getBinaryContent().getEncoding();
            }
        }
    }
}

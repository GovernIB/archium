package es.caib.archium.ejb.objects;

import io.swagger.v3.oas.annotations.media.Schema;
import java.util.Objects;

@Schema(name = "SerieDocumental")
public class SerieDto {
    String codiFuncio;
    String ambitFuncional;
    String codiSerie;
    String nomSerie;
    Long ordre;
    Long nivell;
    String procediments;
    String aplicacions;
    String acces;
    String tipusDictamen;
    String dataActualitzacio;

    String seriesArgen;

    String enviatSAT;

    // Indica si la sèrie és de gestió o d'explotació
    String tipusSerie;

    public SerieDto() {

    }

    public SerieDto(String codiSerie, String nomSerie) {
        this.codiSerie = codiSerie;
        this.nomSerie = nomSerie;
    }

    public String getCodiFuncio() {
        return codiFuncio;
    }

    public void setCodiFuncio(String codiFuncio) {
        this.codiFuncio = codiFuncio;
    }

    public String getAmbitFuncional() {
        return ambitFuncional;
    }

    public void setAmbitFuncional(String ambitFuncional) {
        this.ambitFuncional = ambitFuncional;
    }

    public String getCodiSerie() {
        return codiSerie;
    }

    public void setCodiSerie(String codiSerie) {
        this.codiSerie = codiSerie;
    }

    public String getNomSerie() {
        return nomSerie;
    }

    public void setNomSerie(String nomSerie) {
        this.nomSerie = nomSerie;
    }

    public Long getOrdre() {
        return ordre;
    }

    public void setOrdre(Long ordre) {
        this.ordre = ordre;
    }

    public Long getNivell() {
        return nivell;
    }

    public void setNivell(Long nivell) {
        this.nivell = nivell;
    }

    public String getProcediments() {
        return procediments;
    }

    public void setProcediments(String procediments) {
        this.procediments = procediments;
    }

    public String getAcces() {
        return acces;
    }

    public void setAcces(String acces) {
        this.acces = acces;
    }

    public String getTipusDictamen() {
        return tipusDictamen;
    }

    public void setTipusDictamen(String tipusDictamen) {
        this.tipusDictamen = tipusDictamen;
    }

    public String getDataActualitzacio() {
        return dataActualitzacio;
    }

    public void setDataActualitzacio(String dataActualitzacio) {
        this.dataActualitzacio = dataActualitzacio;
    }

    public String getId() {
        return codiFuncio + (codiSerie != null ? "_" + codiSerie : "");
    }

    public String getAplicacions() {
        return aplicacions;
    }

    public void setAplicacions(String aplicacions) {
        this.aplicacions = aplicacions;
    }

    public String getSeriesArgen() {
        return seriesArgen;
    }

    public String getFuncioCompleta() {
        return codiFuncio + " - " + ambitFuncional;
    }

    public void setSeriesArgen(String seriesArgen) {
        this.seriesArgen = seriesArgen;
    }

    public String getEnviatSAT() {
        return enviatSAT;
    }

    public void setEnviatSAT(String enviatSAT) {
        this.enviatSAT = enviatSAT;
    }

    public String getTipusSerie() {
        return tipusSerie;
    }

    public void setTipusSerie(String tipusSerie) {
        this.tipusSerie = tipusSerie;
    }

    @Override
    public String toString() {
        return "SerieDto{" +
                "codiFuncio='" + codiFuncio + '\'' +
                ", ambitFuncional='" + ambitFuncional + '\'' +
                ", codiSerie='" + codiSerie + '\'' +
                ", nomSerie='" + nomSerie + '\'' +
                ", ordre=" + ordre +
                ", nivell=" + nivell +
                ", procediments='" + procediments + '\'' +
                ", aplicacions='" + aplicacions + '\'' +
                ", enviatSAT='" + enviatSAT + '\'' +
                ", acces='" + acces + '\'' +
                ", tipusDictamen='" + tipusDictamen + '\'' +
                ", dataActualitzacio='" + dataActualitzacio + '\'' +
                ", seriesArgen='" + seriesArgen + '\'' +
                ", tipusSerie='" + tipusSerie + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SerieDto serieDto = (SerieDto) o;
        return Objects.equals(getCodiFuncio(), serieDto.getCodiFuncio()) && Objects.equals(getCodiSerie(), serieDto.getCodiSerie());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getCodiFuncio(), getCodiSerie());
    }
}

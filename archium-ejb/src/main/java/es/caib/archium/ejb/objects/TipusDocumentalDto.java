package es.caib.archium.ejb.objects;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TipusDocumental")
public class TipusDocumentalDto {
    String codi;
    String nom;
    String codiNti;
    String nomNti;
    String definicio;

    private String estat;

    private String dataActualitzacio;

    public TipusDocumentalDto() {

    }

    public TipusDocumentalDto(String codi, String nom, String codiNti, String nomNti, String definicio, String estat, String dataActualitzacio) {
        this.codi = codi;
        this.nom = nom;
        this.codiNti = codiNti;
        this.nomNti = nomNti;
        this.definicio = definicio;
        this.estat = estat;
        this.dataActualitzacio = dataActualitzacio;
    }

    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getCodiNti() {
        return codiNti;
    }

    public void setCodiNti(String codiNti) {
        this.codiNti = codiNti;
    }

    public String getNomNti() {
        return nomNti;
    }

    public void setNomNti(String nomNti) {
        this.nomNti = nomNti;
    }

    public String getDefinicio() {
        return definicio;
    }

    public void setDefinicio(String definicio) {
        this.definicio = definicio;
    }

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public String getDataActualitzacio() {
        return dataActualitzacio;
    }

    public void setDataActualitzacio(String dataActualitzacio) {
        this.dataActualitzacio = dataActualitzacio;
    }
}

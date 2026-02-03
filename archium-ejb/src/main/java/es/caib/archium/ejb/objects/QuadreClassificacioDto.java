package es.caib.archium.ejb.objects;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.ArrayList;
import java.util.List;

@Schema(name = "QuadreClassificacio")
public class QuadreClassificacioDto {

    private String codi;

    private String nom;

    private String estat;

    private String versio;

    private String dataActualitzacio;

    public QuadreClassificacioDto(String codi, String nom, String estat, String versio, String dataActualitzacio) {
        this.codi = codi;
        this.nom = nom;
        this.estat = estat;
        this.versio = versio;
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

    public String getEstat() {
        return estat;
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public String getVersio() {
        return versio;
    }

    public void setVersio(String versio) {
        this.versio = versio;
    }

    public String getDataActualitzacio() {
        return dataActualitzacio;
    }

    public void setDataActualitzacio(String dataActualitzacio) {
        this.dataActualitzacio = dataActualitzacio;
    }

    @Override
    public String toString() {
        return "QuadreClassificacio {" +
                "codi=" + codi +
                "nom=" + nom +
                "estat=" + estat +
                "darreraModificacio=" + dataActualitzacio +
                "versio=" + versio +
                "}";
    }
}

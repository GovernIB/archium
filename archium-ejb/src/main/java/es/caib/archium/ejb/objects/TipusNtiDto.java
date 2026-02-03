package es.caib.archium.ejb.objects;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "TipusDocumentalNti")
public class TipusNtiDto {
    String codi;

    String nom;

    String categoria;

    public TipusNtiDto() {

    }

    public TipusNtiDto(String codi, String nom, String categoria) {
        this.codi = codi;
        this.nom = nom;
        this.categoria = categoria;
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

    public String getCategoria() {
        return categoria;
    }

    public void setCategoria(String categoria) {
        this.categoria = categoria;
    }

    @Override
    public String toString() {
        return "TipusDocumentalNti{"
                + "codi=" + codi
                + ", nom=" + nom
                + ", categoria=" + categoria
                + "}";
    }
}

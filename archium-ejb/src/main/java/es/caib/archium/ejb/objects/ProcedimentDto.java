package es.caib.archium.ejb.objects;

import io.swagger.v3.oas.annotations.media.Schema;

import java.util.List;

@Schema(name = "Procediment")
public class ProcedimentDto {

    private String codiRolsac;

    private String codiSia;

    private String nom;

    private String objecte;

    private String estat;

    private String codiSerie;

    private List<TipusDocumentalProcedimentDto> tipusDocumentals;

    private String dataActualitzacio;

    public ProcedimentDto() {
    }

    public ProcedimentDto(String codiRolsac, String codiSia, String nom, String objecte, String estat, String codiSerie, List<TipusDocumentalProcedimentDto> tipusDocumentals, String dataActualitzacio) {
        this.codiRolsac = codiRolsac;
        this.codiSia = codiSia;
        this.nom = nom;
        this.objecte = objecte;
        this.estat = estat;
        this.codiSerie = codiSerie;
        this.tipusDocumentals = tipusDocumentals;
        this.dataActualitzacio = dataActualitzacio;
    }

    public String getCodiRolsac() {
        return codiRolsac;
    }

    public void setCodiRolsac(String codiRolsac) {
        this.codiRolsac = codiRolsac;
    }

    public String getCodiSia() {
        return codiSia;
    }

    public void setCodiSia(String codiSia) {
        this.codiSia = codiSia;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getObjecte() {
        return objecte;
    }

    public void setObjecte(String objecte) {
        this.objecte = objecte;
    }

    public String getCodiSerie() {
        return codiSerie;
    }

    public void setCodiSerie(String codiSerie) {
        this.codiSerie = codiSerie;
    }

    public List<TipusDocumentalProcedimentDto> getTipusDocumentals() {
        return tipusDocumentals;
    }

    public void setTipusDocumentals(List<TipusDocumentalProcedimentDto> tipusDocumentals) {
        this.tipusDocumentals = tipusDocumentals;
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

    @Override
    public String toString() {
        return "Procediment{"
                + "codiRolsac=" + codiRolsac
                + ", codiSia=" + codiSia
                + ", nom=" + nom
                + ", objecte=" + objecte
                + ", estat=" + estat
                + ", codiSerie=" + codiSerie
                + ", tipusDocumentals =" + tipusDocumentals
                + ", darreraModificacio=" + dataActualitzacio
                + "}";
    }
}

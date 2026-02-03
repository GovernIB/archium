package es.caib.archium.ejb.objects;

import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "CondicionsTipusDocumental")
public class TipusDocumentalProcedimentDto {
    TipusDocumentalDto tipusDocumental;

    boolean obligatori;

    boolean multiple;

    public TipusDocumentalProcedimentDto() {
    }

    public TipusDocumentalProcedimentDto(TipusDocumentalDto tipusDocumental, boolean obligatori, boolean multiple) {
        this.tipusDocumental = tipusDocumental;
        this.obligatori = obligatori;
        this.multiple = multiple;
    }

    public TipusDocumentalDto getTipusDocumental() {
        return tipusDocumental;
    }

    public void setTipusDocumental(TipusDocumentalDto tipusDocumental) {
        this.tipusDocumental = tipusDocumental;
    }

    public boolean isObligatori() {
        return obligatori;
    }

    public void setObligatori(boolean obligatori) {
        this.obligatori = obligatori;
    }

    public boolean isMultiple() {
        return multiple;
    }

    public void setMultiple(boolean multiple) {
        this.multiple = multiple;
    }

    @Override
    public String toString() {
        return "TipusDocumental{"
                + "codi=" + (tipusDocumental != null ? tipusDocumental.getCodi() : null)
                + ", nom=" + (tipusDocumental != null ? tipusDocumental.getNom() : null)
                + ", codiNti=" + (tipusDocumental != null ? tipusDocumental.getCodiNti() : null)
                + ", nomNti=" + (tipusDocumental != null ? tipusDocumental.getNomNti() : null)
                + ", definicio=" + (tipusDocumental != null ? tipusDocumental.getDefinicio() : null)
                + ", estat=" + (tipusDocumental != null ? tipusDocumental.getEstat() : null)
                + ", darreraModificacio=" + (tipusDocumental != null ? tipusDocumental.getDataActualitzacio() : null)
                + ", obligatori=" + obligatori
                + "}";
    }
}

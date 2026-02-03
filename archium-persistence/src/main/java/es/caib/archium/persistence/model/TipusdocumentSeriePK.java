package es.caib.archium.persistence.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;
import java.util.Objects;

/**
 * The primary key class for the ACH_TIPUSDOCUMENT_SERIE database table.
 */
@Embeddable
public class TipusdocumentSeriePK implements Serializable {
    //default serial version id, required for serializable classes.
    private static final long serialVersionUID = 1L;

    @Column(name = "SERIEDOCUMENTAL_ID", insertable=false, updatable=false)
    private Long serieDocumentalId;

    @Column(name = "TIPUSDOCUMENTAL_ID", insertable=false, updatable=false)
    private Long tipusdocumentalId;

    public TipusdocumentSeriePK() {
    }

    public Long getSerieDocumentalId() {
        return this.serieDocumentalId;
    }

    public void setSerieDocumentalId(Long procedimentId) {
        this.serieDocumentalId = procedimentId;
    }

    public Long getTipusdocumentalId() {
        return this.tipusdocumentalId;
    }

    public void setTipusdocumentalId(Long tipusdocumentalId) {
        this.tipusdocumentalId = tipusdocumentalId;
    }

    public boolean equals(Object other) {
        if (this == other) {
            return true;
        }
        if (!(other instanceof TipusdocumentSeriePK)) {
            return false;
        }
        TipusdocumentSeriePK castOther = (TipusdocumentSeriePK) other;
        return
                (Objects.equals(this.serieDocumentalId, castOther.serieDocumentalId))
                        && (Objects.equals(this.tipusdocumentalId, castOther.tipusdocumentalId));
    }

    public int hashCode() {
        final int prime = 31;
        int hash = 17;
        hash = hash * prime + ((int) (this.serieDocumentalId ^ (this.serieDocumentalId >>> 32)));
        hash = hash * prime + ((int) (this.tipusdocumentalId ^ (this.tipusdocumentalId >>> 32)));

        return hash;
    }

    @Override
    public String toString() {
        return "TipusdocumentSeriePK [serieDocumentalId=" +
                serieDocumentalId +
                ", tipusdocumentalId=" +
                tipusdocumentalId +
                "]";
    }
}
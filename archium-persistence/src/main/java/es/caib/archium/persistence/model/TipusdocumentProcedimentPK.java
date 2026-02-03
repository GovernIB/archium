package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ACH_TIPUSDOCUMENT_PROCEDIMENT database table.
 * 
 */
@Embeddable
public class TipusdocumentProcedimentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PROCEDIMENT_ID", insertable=false, updatable=false)
	private Long procedimentId;

	@Column(name="TIPUSDOCUMENTAL_ID", insertable=false, updatable=false)
	private Long tipusdocumentalId;

	public TipusdocumentProcedimentPK() {
	}
	public Long getProcedimentId() {
		return this.procedimentId;
	}
	public void setProcedimentId(Long procedimentId) {
		this.procedimentId = procedimentId;
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
		if (!(other instanceof TipusdocumentProcedimentPK)) {
			return false;
		}
		TipusdocumentProcedimentPK castOther = (TipusdocumentProcedimentPK)other;
		return 
			(this.procedimentId == castOther.procedimentId)
			&& (this.tipusdocumentalId == castOther.tipusdocumentalId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.procedimentId ^ (this.procedimentId >>> 32)));
		hash = hash * prime + ((int) (this.tipusdocumentalId ^ (this.tipusdocumentalId >>> 32)));
		
		return hash;
	}
}
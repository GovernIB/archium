package es.caib.archium.persistence.model;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import java.io.Serializable;

/**
 * The primary key class for the ACH_PROCEDIMENT_SERIE database table.
 * 
 */
@Embeddable
public class ProcedimentSeriePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PROCEDIMENT_ID", insertable=false, updatable=false)
	private Long procedimentId;

	@Column(name="SERIEDOCUMENTAL_ID", insertable=false, updatable=false)
	private Long seriedocumentalId;

	public ProcedimentSeriePK() {
	}
	public Long getProcedimentId() {
		return this.procedimentId;
	}
	public void setProcedimentId(Long procedimentId) {
		this.procedimentId = procedimentId;
	}

	public Long getSeriedocumentalId() {
		return seriedocumentalId;
	}

	public void setSeriedocumentalId(Long seriedocumentalId) {
		this.seriedocumentalId = seriedocumentalId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ProcedimentSeriePK)) {
			return false;
		}
		ProcedimentSeriePK castOther = (ProcedimentSeriePK)other;
		return 
			(this.procedimentId == castOther.procedimentId)
			&& (this.seriedocumentalId == castOther.seriedocumentalId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.procedimentId ^ (this.procedimentId >>> 32)));
		hash = hash * prime + ((int) (this.seriedocumentalId ^ (this.seriedocumentalId >>> 32)));
		
		return hash;
	}
}
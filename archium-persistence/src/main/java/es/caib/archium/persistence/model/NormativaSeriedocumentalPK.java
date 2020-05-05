package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ACH_NORMATIVA_SERIEDOCUMENTAL database table.
 * 
 */
@Embeddable
public class NormativaSeriedocumentalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SERIEDOCUMENTAL_ID", insertable=false, updatable=false)
	private Long seriedocumentalId;

	@Column(name="NORMATIVA_ID", insertable=false, updatable=false)
	private Long normativaId;

	public NormativaSeriedocumentalPK() {
	}
	public Long getSeriedocumentalId() {
		return this.seriedocumentalId;
	}
	public void setSeriedocumentalId(Long seriedocumentalId) {
		this.seriedocumentalId = seriedocumentalId;
	}
	public Long getNormativaId() {
		return this.normativaId;
	}
	public void setNormativaId(Long normativaId) {
		this.normativaId = normativaId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof NormativaSeriedocumentalPK)) {
			return false;
		}
		NormativaSeriedocumentalPK castOther = (NormativaSeriedocumentalPK)other;
		return 
			(this.seriedocumentalId == castOther.seriedocumentalId)
			&& (this.normativaId == castOther.normativaId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.seriedocumentalId ^ (this.seriedocumentalId >>> 32)));
		hash = hash * prime + ((int) (this.normativaId ^ (this.normativaId >>> 32)));
		
		return hash;
	}
}
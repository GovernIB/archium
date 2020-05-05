package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ACH_LIMITACIO_NORMATIVA_SERIE database table.
 * 
 */
@Embeddable
public class LimitacioNormativaSeriePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SERIEDOCUMENTAL_ID", insertable=false, updatable=false)
	private Long seriedocumentalId;

	@Column(name="NORMATIVA_ID", insertable=false, updatable=false)
	private Long normativaId;

	@Column(name="CAUSALIMITACIO_ID", insertable=false, updatable=false)
	private Long causalimitacioId;

	public LimitacioNormativaSeriePK() {
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
	public Long getCausalimitacioId() {
		return this.causalimitacioId;
	}
	public void setCausalimitacioId(Long causalimitacioId) {
		this.causalimitacioId = causalimitacioId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof LimitacioNormativaSeriePK)) {
			return false;
		}
		LimitacioNormativaSeriePK castOther = (LimitacioNormativaSeriePK)other;
		return 
			(this.seriedocumentalId == castOther.seriedocumentalId)
			&& (this.normativaId == castOther.normativaId)
			&& (this.causalimitacioId == castOther.causalimitacioId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.seriedocumentalId ^ (this.seriedocumentalId >>> 32)));
		hash = hash * prime + ((int) (this.normativaId ^ (this.normativaId >>> 32)));
		hash = hash * prime + ((int) (this.causalimitacioId ^ (this.causalimitacioId >>> 32)));
		
		return hash;
	}
}
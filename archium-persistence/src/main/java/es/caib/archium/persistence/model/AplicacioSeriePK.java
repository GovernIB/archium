package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ACH_APLICACIO_SERIE database table.
 * 
 */
@Embeddable
public class AplicacioSeriePK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="SERIEDOCUMENTAL_ID",insertable=false,updatable=false)
	private Long seriedocumentalId;

	@Column(name="APLICACIO_ID",insertable=false,updatable=false)
	private Long aplicacioId;

	public AplicacioSeriePK() {
	}
	public Long getSeriedocumentalId() {
		return this.seriedocumentalId;
	}
	public void setSeriedocumentalId(Long seriedocumentalId) {
		this.seriedocumentalId = seriedocumentalId;
	}
	public Long getAplicacioId() {
		return this.aplicacioId;
	}
	public void setAplicacioId(Long aplicacioId) {
		this.aplicacioId = aplicacioId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof AplicacioSeriePK)) {
			return false;
		}
		AplicacioSeriePK castOther = (AplicacioSeriePK)other;
		return 
			(this.seriedocumentalId == castOther.seriedocumentalId)
			&& (this.aplicacioId == castOther.aplicacioId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.seriedocumentalId ^ (this.seriedocumentalId >>> 32)));
		hash = hash * prime + ((int) (this.aplicacioId ^ (this.aplicacioId >>> 32)));
		
		return hash;
	}
}
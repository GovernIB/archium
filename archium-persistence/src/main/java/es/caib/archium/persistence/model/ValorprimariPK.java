package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ACH_VALORPRIMARI database table.
 * 
 */
@Embeddable
public class ValorprimariPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="VALORACIO_ID", insertable=false, updatable=false)
	private Long valoracioId;

	@Column(name="TIPUSVALOR_ID", insertable=false, updatable=false)
	private Long tipusvalorId;

	public ValorprimariPK() {
	}
	public Long getValoracioId() {
		return this.valoracioId;
	}
	public void setValoracioId(Long valoracioId) {
		this.valoracioId = valoracioId;
	}
	public Long getTipusvalorId() {
		return this.tipusvalorId;
	}
	public void setTipusvalorId(Long tipusvalorId) {
		this.tipusvalorId = tipusvalorId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ValorprimariPK)) {
			return false;
		}
		ValorprimariPK castOther = (ValorprimariPK)other;
		return 
			(this.valoracioId == castOther.valoracioId)
			&& (this.tipusvalorId == castOther.tipusvalorId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.valoracioId ^ (this.valoracioId >>> 32)));
		hash = hash * prime + ((int) (this.tipusvalorId ^ (this.tipusvalorId >>> 32)));
		
		return hash;
	}
}
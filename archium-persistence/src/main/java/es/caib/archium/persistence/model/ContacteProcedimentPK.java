package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ACH_CONTACTE_PROCEDIMENT database table.
 * 
 */
@Embeddable
public class ContacteProcedimentPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="PROCEDIMENT_ID", insertable=false, updatable=false)
	private Long procedimentId;

	@Column(name="TIPUSCONTACTE_ID", insertable=false, updatable=false)
	private Long tipuscontacteId;

	public ContacteProcedimentPK() {
	}
	public Long getProcedimentId() {
		return this.procedimentId;
	}
	public void setProcedimentId(Long procedimentId) {
		this.procedimentId = procedimentId;
	}
	public Long getTipuscontacteId() {
		return this.tipuscontacteId;
	}
	public void setTipuscontacteId(Long tipuscontacteId) {
		this.tipuscontacteId = tipuscontacteId;
	}

	public boolean equals(Object other) {
		if (this == other) {
			return true;
		}
		if (!(other instanceof ContacteProcedimentPK)) {
			return false;
		}
		ContacteProcedimentPK castOther = (ContacteProcedimentPK)other;
		return 
			(this.procedimentId == castOther.procedimentId)
			&& (this.tipuscontacteId == castOther.tipuscontacteId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.procedimentId ^ (this.procedimentId >>> 32)));
		hash = hash * prime + ((int) (this.tipuscontacteId ^ (this.tipuscontacteId >>> 32)));
		
		return hash;
	}
}
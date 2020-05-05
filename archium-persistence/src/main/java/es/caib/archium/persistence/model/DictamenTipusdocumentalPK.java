package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;

/**
 * The primary key class for the ACH_DICTAMEN_TIPUSDOCUMENTAL database table.
 * 
 */
@Embeddable
public class DictamenTipusdocumentalPK implements Serializable {
	//default serial version id, required for serializable classes.
	private static final long serialVersionUID = 1L;

	@Column(name="DICTAMEN_ID", insertable=false, updatable=false)
	private Long dictamenId;

	@Column(name="TIPUSDOCUMENTAL_ID", insertable=false, updatable=false)
	private Long tipusdocumentalId;

	public DictamenTipusdocumentalPK() {
	}
	public Long getDictamenId() {
		return this.dictamenId;
	}
	public void setDictamenId(Long dictamenId) {
		this.dictamenId = dictamenId;
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
		if (!(other instanceof DictamenTipusdocumentalPK)) {
			return false;
		}
		DictamenTipusdocumentalPK castOther = (DictamenTipusdocumentalPK)other;
		return 
			(this.dictamenId == castOther.dictamenId)
			&& (this.tipusdocumentalId == castOther.tipusdocumentalId);
	}

	public int hashCode() {
		final int prime = 31;
		int hash = 17;
		hash = hash * prime + ((int) (this.dictamenId ^ (this.dictamenId >>> 32)));
		hash = hash * prime + ((int) (this.tipusdocumentalId ^ (this.tipusdocumentalId >>> 32)));
		
		return hash;
	}
}
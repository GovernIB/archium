package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ACH_NORMATIVA_SERIEDOCUMENTAL database table.
 * 
 */
@Entity
@Table(name="ACH_NORMATIVA_SERIEDOCUMENTAL")
@NamedQuery(name="NormativaSeriedocumental.findAll", query="SELECT n FROM NormativaSeriedocumental n")
public class NormativaSeriedocumental implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private NormativaSeriedocumentalPK id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date inici;

	//bi-directional many-to-one association to Normativa
	@ManyToOne
	@MapsId("normativaId")
	@JoinColumn(name="NORMATIVA_ID" ,insertable=false,updatable=false)
	private Normativa achNormativa;

	//bi-directional many-to-one association to Seriedocumental
	@ManyToOne
	@MapsId("seriedocumentalId")
	@JoinColumn(name="SERIEDOCUMENTAL_ID"  ,insertable=false,updatable=false)
	private Seriedocumental achSeriedocumental;

	public NormativaSeriedocumental() {
	}

	public NormativaSeriedocumentalPK getId() {
		return this.id;
	}

	public void setId(NormativaSeriedocumentalPK id) {
		this.id = id;
	}

	public Date getFi() {
		return this.fi;
	}

	public void setFi(Date fi) {
		this.fi = fi;
	}

	public Date getInici() {
		return this.inici;
	}

	public void setInici(Date inici) {
		this.inici = inici;
	}

	public Normativa getAchNormativa() {
		return this.achNormativa;
	}

	public void setAchNormativa(Normativa achNormativa) {
		this.achNormativa = achNormativa;
	}

	public Seriedocumental getAchSeriedocumental() {
		return this.achSeriedocumental;
	}

	public void setAchSeriedocumental(Seriedocumental achSeriedocumental) {
		this.achSeriedocumental = achSeriedocumental;
	}

}
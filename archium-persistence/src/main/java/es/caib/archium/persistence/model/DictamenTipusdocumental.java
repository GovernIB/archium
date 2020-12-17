package es.caib.archium.persistence.model;

import es.caib.archium.persistence.funcional.ObsolescenteAbstract;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;


/**
 * The persistent class for the ACH_DICTAMEN_TIPUSDOCUMENTAL database table.
 * 
 */
@Entity
@Table(name="ACH_DICTAMEN_TIPUSDOCUMENTAL")
@NamedQuery(name="DictamenTipusdocumental.findAll", query="SELECT d FROM DictamenTipusdocumental d")
public class DictamenTipusdocumental extends ObsolescenteAbstract {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private DictamenTipusdocumentalPK id;

	private BigDecimal conservable;

	@Temporal(TemporalType.DATE)
	private Date fi;

	@Temporal(TemporalType.DATE)
	private Date inici;

	private String termini;

	//bi-directional many-to-one association to Dictamen
	@ManyToOne
	@JoinColumn(name="DICTAMEN_ID"  ,insertable=false,updatable=false)
	private Dictamen achDictamen;

	//bi-directional many-to-one association to Tipusdictamen
	@ManyToOne
	@JoinColumn(name="TIPUSDICTAMEN_ID")
	private Tipusdictamen achTipusdictamen;

	//bi-directional many-to-one association to Tipusdocumental
	@ManyToOne
	@JoinColumn(name="TIPUSDOCUMENTAL_ID"  ,insertable=false,updatable=false)
	private Tipusdocumental achTipusdocumental;

	public DictamenTipusdocumental() {
	}

	public DictamenTipusdocumentalPK getId() {
		return this.id;
	}

	public void setId(DictamenTipusdocumentalPK id) {
		this.id = id;
	}

	public BigDecimal getConservable() {
		return this.conservable;
	}

	public void setConservable(BigDecimal conservable) {
		this.conservable = conservable;
	}

	@Override
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

	public String getTermini() {
		return this.termini;
	}

	public void setTermini(String termini) {
		this.termini = termini;
	}

	public Dictamen getAchDictamen() {
		return this.achDictamen;
	}

	public void setAchDictamen(Dictamen achDictamen) {
		this.achDictamen = achDictamen;
	}

	public Tipusdictamen getAchTipusdictamen() {
		return this.achTipusdictamen;
	}

	public void setAchTipusdictamen(Tipusdictamen achTipusdictamen) {
		this.achTipusdictamen = achTipusdictamen;
	}

	public Tipusdocumental getAchTipusdocumental() {
		return this.achTipusdocumental;
	}

	public void setAchTipusdocumental(Tipusdocumental achTipusdocumental) {
		this.achTipusdocumental = achTipusdocumental;
	}


}
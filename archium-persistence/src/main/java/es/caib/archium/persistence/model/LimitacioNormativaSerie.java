package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ACH_LIMITACIO_NORMATIVA_SERIE database table.
 * 
 */
@Entity
@Table(name="ACH_LIMITACIO_NORMATIVA_SERIE")
@NamedQuery(name="LimitacioNormativaSerie.findAll", query="SELECT l FROM LimitacioNormativaSerie l")
public class LimitacioNormativaSerie implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private LimitacioNormativaSeriePK id;

	@Temporal(TemporalType.DATE)
	private Date fi;

	@Temporal(TemporalType.DATE)
	private Date inici;

	//bi-directional many-to-one association to Causalimitacio
	@ManyToOne
	@MapsId("causalimitacioId")
	@JoinColumn(name="CAUSALIMITACIO_ID",insertable=false,updatable=false)
	private Causalimitacio achCausalimitacio;

	//bi-directional many-to-one association to Normativa
	@ManyToOne
	@MapsId("normativaId")
	@JoinColumn(name="NORMATIVA_ID" ,insertable=false,updatable=false)
	private Normativa achNormativa;

	//bi-directional many-to-one association to Seriedocumental
	@ManyToOne
	@MapsId("seriedocumentalId")
	@JoinColumn(name="SERIEDOCUMENTAL_ID" ,insertable=false,updatable=false)
	private Seriedocumental achSeriedocumental;

	public LimitacioNormativaSerie() {
	}

	public LimitacioNormativaSeriePK getId() {
		return this.id;
	}

	public void setId(LimitacioNormativaSeriePK id) {
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

	public Causalimitacio getAchCausalimitacio() {
		return this.achCausalimitacio;
	}

	public void setAchCausalimitacio(Causalimitacio achCausalimitacio) {
		this.achCausalimitacio = achCausalimitacio;
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
package es.caib.archium.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACH_VALORACIO database table.
 * 
 */
@Entity
@Table(name="ACH_VALORACIO")
@NamedQuery(name="Valoracio.findAll", query="SELECT v FROM Valoracio v")
public class Valoracio implements Serializable {
	private static final long serialVersionUID = 1L;

	
	@Id
	@SequenceGenerator(name="ACH_VALORACIO_ID_GENERATOR", sequenceName="ACH_VALORACIO_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_VALORACIO_ID_GENERATOR")
	private Long id;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date inici;

	//bi-directional many-to-one association to Seriedocumental
	@ManyToOne
	@JoinColumn(name="SERIEDOCUMENTAL_ID")
	private Seriedocumental achSeriedocumental;

	//bi-directional many-to-one association to Valorsecundari
	@ManyToOne
	@JoinColumn(name="VALORSECUNDARI_ID")
	private ValorSecundari achValorsecundari;

	//bi-directional many-to-one association to Valorprimari
	@OneToMany(mappedBy="achValoracio",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<Valorprimari> achValorprimaris;

	public Valoracio() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
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

	public Seriedocumental getAchSeriedocumental() {
		return this.achSeriedocumental;
	}

	public void setAchSeriedocumental(Seriedocumental achSeriedocumental) {
		this.achSeriedocumental = achSeriedocumental;
	}

	public ValorSecundari getAchValorsecundari() {
		return this.achValorsecundari;
	}

	public void setAchValorsecundari(ValorSecundari achValorsecundari) {
		this.achValorsecundari = achValorsecundari;
	}

	public List<Valorprimari> getAchValorprimaris() {
		return this.achValorprimaris;
	}

	public void setAchValorprimaris(List<Valorprimari> achValorprimaris) {
		this.achValorprimaris = achValorprimaris;
	}

	public Valorprimari addAchValorprimari(Valorprimari achValorprimari) {
		getAchValorprimaris().add(achValorprimari);
		achValorprimari.setAchValoracio(this);

		return achValorprimari;
	}

	public Valorprimari removeAchValorprimari(Valorprimari achValorprimari) {
		getAchValorprimaris().remove(achValorprimari);
		achValorprimari.setAchValoracio(null);

		return achValorprimari;
	}

}
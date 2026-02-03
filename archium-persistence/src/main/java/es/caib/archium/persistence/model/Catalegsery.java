package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACH_CATALEGSERIES database table.
 * 
 */
@Entity
@Table(name="ACH_CATALEGSERIES")
@NamedQuery(name="Catalegsery.findAll", query="SELECT c FROM Catalegsery c")
public class Catalegsery implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_CATALEGSERIES_ID_GENERATOR", sequenceName="ACH_CATALEGSERIES_SEQ" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_CATALEGSERIES_ID_GENERATOR")
	private Long id;

	private String estat;

	@Temporal(TemporalType.DATE)
	private Date fi;

	@Temporal(TemporalType.DATE)
	private Date inici;

	private String nom;

	private String nomcas;

	private String versio;

	//bi-directional many-to-one association to Seriedocumental
	@OneToMany(mappedBy="achCatalegsery")
	private List<Seriedocumental> achSeriedocumentals;

	public Catalegsery() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstat() {
		return this.estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
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

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNomcas() {
		return this.nomcas;
	}

	public void setNomcas(String nomcas) {
		this.nomcas = nomcas;
	}

	public String getVersio() {
		return this.versio;
	}

	public void setVersio(String versio) {
		this.versio = versio;
	}

	public List<Seriedocumental> getAchSeriedocumentals() {
		return this.achSeriedocumentals;
	}

	public void setAchSeriedocumentals(List<Seriedocumental> achSeriedocumentals) {
		this.achSeriedocumentals = achSeriedocumentals;
	}

	public Seriedocumental addAchSeriedocumental(Seriedocumental achSeriedocumental) {
		getAchSeriedocumentals().add(achSeriedocumental);
		achSeriedocumental.setAchCatalegsery(this);

		return achSeriedocumental;
	}

	public Seriedocumental removeAchSeriedocumental(Seriedocumental achSeriedocumental) {
		getAchSeriedocumentals().remove(achSeriedocumental);
		achSeriedocumental.setAchCatalegsery(null);

		return achSeriedocumental;
	}

}
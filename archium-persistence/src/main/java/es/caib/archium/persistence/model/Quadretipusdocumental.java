package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACH_QUADRETIPUSDOCUMENTAL database table.
 * 
 */
@Entity
@Table(name="ACH_QUADRETIPUSDOCUMENTAL")
@NamedQuery(name="Quadretipusdocumental.findAll", query="SELECT q FROM Quadretipusdocumental q")
public class Quadretipusdocumental implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_QUADRETIPUSDOCUMENTAL_ID_GENERATOR", sequenceName="ACH_QUADRETIPUSDOCUMENTAL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_QUADRETIPUSDOCUMENTAL_ID_GENERATOR")
	private Long id;

	private String estat;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date inici;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modificacio;

	private String nom;

	private String nomcas;

	private String versio;

	//bi-directional many-to-one association to Tipusdocumental
	@OneToMany(mappedBy="achQuadretipusdocumental")
	private List<Tipusdocumental> achTipusdocumentals;

	public Quadretipusdocumental() {
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

	public Date getModificacio() {
		return this.modificacio;
	}

	public void setModificacio(Date modificacio) {
		this.modificacio = modificacio;
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

	public List<Tipusdocumental> getAchTipusdocumentals() {
		return this.achTipusdocumentals;
	}

	public void setAchTipusdocumentals(List<Tipusdocumental> achTipusdocumentals) {
		this.achTipusdocumentals = achTipusdocumentals;
	}

	public Tipusdocumental addAchTipusdocumental(Tipusdocumental achTipusdocumental) {
		getAchTipusdocumentals().add(achTipusdocumental);
		achTipusdocumental.setAchQuadretipusdocumental(this);

		return achTipusdocumental;
	}

	public Tipusdocumental removeAchTipusdocumental(Tipusdocumental achTipusdocumental) {
		getAchTipusdocumentals().remove(achTipusdocumental);
		achTipusdocumental.setAchQuadretipusdocumental(null);

		return achTipusdocumental;
	}

}
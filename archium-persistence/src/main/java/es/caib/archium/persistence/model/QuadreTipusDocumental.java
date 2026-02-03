package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;
import java.util.Objects;


/**
 * The persistent class for the ACH_QUADRETIPUSDOCUMENTAL database table.
 * 
 */
@Entity
@Table(name="ACH_QUADRETIPUSDOCUMENTAL")
@NamedQuery(name="Quadretipusdocumental.findAll", query="SELECT q FROM QuadreTipusDocumental q")
public class QuadreTipusDocumental implements Serializable, Comparable<QuadreTipusDocumental> {
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
	@OneToMany(mappedBy="quadreTipusDocumental")
	private List<TipusDocumental> achTipusdocumentals;

	public QuadreTipusDocumental() {
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

	public List<TipusDocumental> getAchTipusdocumentals() {
		return this.achTipusdocumentals;
	}

	public void setAchTipusdocumentals(List<TipusDocumental> achTipusdocumentals) {
		this.achTipusdocumentals = achTipusdocumentals;
	}

	public TipusDocumental addAchTipusdocumental(TipusDocumental achTipusdocumental) {
		getAchTipusdocumentals().add(achTipusdocumental);
		achTipusdocumental.setQuadreTipusDocumental(this);

		return achTipusdocumental;
	}

	public TipusDocumental removeAchTipusdocumental(TipusDocumental achTipusdocumental) {
		getAchTipusdocumentals().remove(achTipusdocumental);
		achTipusdocumental.setQuadreTipusDocumental(null);

		return achTipusdocumental;
	}

	@Override
	public String toString() {
		return String.valueOf(id);
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		QuadreTipusDocumental that = (QuadreTipusDocumental) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public int compareTo(QuadreTipusDocumental that) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		//this optimization is usually worthwhile, and can
		//always be added
		if (this == that) return EQUAL;

		if (that == null) {
			return EQUAL;
		}
		return this.nom.compareTo(that.nom);
	}

}
package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;
import java.util.Objects;


/**
 * The persistent class for the ACH_TIPUSNTI database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSNTI")
@TaulaMestra(excludedFields = {"id", "achTipusdocumentals", "achCategorianti"})
@NamedQuery(name="Tipusnti.findAll", query="SELECT t FROM TipusNti t")
public class TipusNti implements Serializable, Comparable<TipusNti> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSNTI_ID_GENERATOR", sequenceName="ACH_TIPUSNTI_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSNTI_ID_GENERATOR")
	private Long id;

	@NotNull
	private String codi;

	@NotNull
	private String nom;

	@NotNull
	private String nomcas;

	//bi-directional many-to-one association to Tipusdocumental
	@OneToMany(mappedBy="tipusNti")
	private List<TipusDocumental> achTipusdocumentals;

	//bi-directional many-to-one association to Categorianti
	@ManyToOne
	@JoinColumn(name="CATEGORIANTI_ID")
	private CategoriaNti achCategorianti;

	public TipusNti() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodi() {
		return this.codi;
	}

	public void setCodi(String codi) {
		this.codi = codi;
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

	public List<TipusDocumental> getAchTipusdocumentals() {
		return this.achTipusdocumentals;
	}

	public void setAchTipusdocumentals(List<TipusDocumental> achTipusdocumentals) {
		this.achTipusdocumentals = achTipusdocumentals;
	}

	public TipusDocumental addAchTipusdocumental(TipusDocumental achTipusdocumental) {
		getAchTipusdocumentals().add(achTipusdocumental);
		achTipusdocumental.setTipusNti(this);

		return achTipusdocumental;
	}

	public TipusDocumental removeAchTipusdocumental(TipusDocumental achTipusdocumental) {
		getAchTipusdocumentals().remove(achTipusdocumental);
		achTipusdocumental.setTipusNti(null);

		return achTipusdocumental;
	}

	public CategoriaNti getAchCategorianti() {
		return this.achCategorianti;
	}

	public void setAchCategorianti(CategoriaNti achCategorianti) {
		this.achCategorianti = achCategorianti;
	}

	@Override
	public String toString() {
		return getCodi() + " (" + getNom() + ")";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TipusNti that = (TipusNti) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public int compareTo(TipusNti that) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		//this optimization is usually worthwhile, and can
		//always be added
		if (this == that) return EQUAL;

		if (that == null) {
			return EQUAL;
		}
		return this.codi.compareTo(that.codi);
	}

}
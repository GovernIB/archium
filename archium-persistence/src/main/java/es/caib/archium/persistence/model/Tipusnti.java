package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACH_TIPUSNTI database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSNTI")
@NamedQuery(name="Tipusnti.findAll", query="SELECT t FROM Tipusnti t")
public class Tipusnti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSNTI_ID_GENERATOR", sequenceName="ACH_TIPUSNTI_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSNTI_ID_GENERATOR")
	private Long id;

	private String codi;

	private String nom;

	private String nomcas;

	//bi-directional many-to-one association to Tipusdocumental
	@OneToMany(mappedBy="achTipusnti")
	private List<Tipusdocumental> achTipusdocumentals;

	//bi-directional many-to-one association to Categorianti
	@ManyToOne
	@JoinColumn(name="CATEGORIANTI_ID")
	private Categorianti achCategorianti;

	public Tipusnti() {
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

	public List<Tipusdocumental> getAchTipusdocumentals() {
		return this.achTipusdocumentals;
	}

	public void setAchTipusdocumentals(List<Tipusdocumental> achTipusdocumentals) {
		this.achTipusdocumentals = achTipusdocumentals;
	}

	public Tipusdocumental addAchTipusdocumental(Tipusdocumental achTipusdocumental) {
		getAchTipusdocumentals().add(achTipusdocumental);
		achTipusdocumental.setAchTipusnti(this);

		return achTipusdocumental;
	}

	public Tipusdocumental removeAchTipusdocumental(Tipusdocumental achTipusdocumental) {
		getAchTipusdocumentals().remove(achTipusdocumental);
		achTipusdocumental.setAchTipusnti(null);

		return achTipusdocumental;
	}

	public Categorianti getAchCategorianti() {
		return this.achCategorianti;
	}

	public void setAchCategorianti(Categorianti achCategorianti) {
		this.achCategorianti = achCategorianti;
	}

}
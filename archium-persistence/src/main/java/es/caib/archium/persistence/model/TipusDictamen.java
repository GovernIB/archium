package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * The persistent class for the ACH_TIPUSDICTAMEN database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSDICTAMEN")
@TaulaMestra(excludedFields = {"id", "achDictamens", "achDictamenTipusdocumentals"})
@NamedQuery(name="Tipusdictamen.findAll", query="SELECT t FROM TipusDictamen t")
public class TipusDictamen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSDICTAMEN_ID_GENERATOR", sequenceName="ACH_TIPUSDICTAMEN_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSDICTAMEN_ID_GENERATOR")
	private Long id;

	@NotNull
	private String codi;

	@NotNull
	private String nom;

	@NotNull
	private String nomcas;

	//bi-directional many-to-one association to Dictamen
	@OneToMany(mappedBy="achTipusdictamen")
	private List<Dictamen> achDictamens;

	//bi-directional many-to-one association to DictamenTipusdocumental
	@OneToMany(mappedBy="achTipusdictamen")
	private List<DictamenTipusdocumental> achDictamenTipusdocumentals;

	public TipusDictamen() {
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

	public List<Dictamen> getAchDictamens() {
		return this.achDictamens;
	}

	public void setAchDictamens(List<Dictamen> achDictamens) {
		this.achDictamens = achDictamens;
	}

	public Dictamen addAchDictamen(Dictamen achDictamen) {
		getAchDictamens().add(achDictamen);
		achDictamen.setAchTipusdictamen(this);

		return achDictamen;
	}

	public Dictamen removeAchDictamen(Dictamen achDictamen) {
		getAchDictamens().remove(achDictamen);
		achDictamen.setAchTipusdictamen(null);

		return achDictamen;
	}

	public List<DictamenTipusdocumental> getAchDictamenTipusdocumentals() {
		return this.achDictamenTipusdocumentals;
	}

	public void setAchDictamenTipusdocumentals(List<DictamenTipusdocumental> achDictamenTipusdocumentals) {
		this.achDictamenTipusdocumentals = achDictamenTipusdocumentals;
	}

	public DictamenTipusdocumental addAchDictamenTipusdocumental(DictamenTipusdocumental achDictamenTipusdocumental) {
		getAchDictamenTipusdocumentals().add(achDictamenTipusdocumental);
		achDictamenTipusdocumental.setAchTipusdictamen(this);

		return achDictamenTipusdocumental;
	}

	public DictamenTipusdocumental removeAchDictamenTipusdocumental(DictamenTipusdocumental achDictamenTipusdocumental) {
		getAchDictamenTipusdocumentals().remove(achDictamenTipusdocumental);
		achDictamenTipusdocumental.setAchTipusdictamen(null);

		return achDictamenTipusdocumental;
	}

}
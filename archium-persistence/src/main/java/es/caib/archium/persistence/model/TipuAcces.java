package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * The persistent class for the ACH_TIPUSACCES database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSACCES")
@TaulaMestra(excludedFields = {"id", "achDictamens"})
@NamedQuery(name="Tipusacce.findAll", query="SELECT t FROM TipuAcces t")
public class TipuAcces implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSACCES_ID_GENERATOR", sequenceName="ACH_TIPUSACCES_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSACCES_ID_GENERATOR")
	private Long id;

	@NotNull
	private String nom;

	@NotNull
	private String nomcas;

	//bi-directional many-to-one association to Dictamen
	@OneToMany(mappedBy="achTipusacce")
	private List<Dictamen> achDictamens;

	public TipuAcces() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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
		achDictamen.setAchTipusacce(this);

		return achDictamen;
	}

	public Dictamen removeAchDictamen(Dictamen achDictamen) {
		getAchDictamens().remove(achDictamen);
		achDictamen.setAchTipusacce(null);

		return achDictamen;
	}

}
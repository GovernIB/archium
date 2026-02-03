package es.caib.archium.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the ACH_ENS database table.
 * 
 */
@Entity
@Table(name="ACH_ENS")
@TaulaMestra(excludedFields = {"id", "achDictamens"})
@NamedQuery(name="Ens.findAll", query="SELECT e FROM Ens e")
public class Ens implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_ENS_ID_GENERATOR", sequenceName="ACH_ENS_SEQ" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_ENS_ID_GENERATOR")
	private Long id;

	@NotNull
	private String descripcio;

	@NotNull
	private String descripciocas;

	@NotNull
	private String nom;

	@NotNull
	private String nomcas;

	//bi-directional many-to-one association to Dictamen
	@OneToMany(mappedBy="achEn")
	private List<Dictamen> achDictamens;

	public Ens() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OrdreVisual(ordre = 300)
	public String getDescripcio() {
		return this.descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	@OrdreVisual(ordre = 400)
	public String getDescripciocas() {
		return this.descripciocas;
	}

	public void setDescripciocas(String descripciocas) {
		this.descripciocas = descripciocas;
	}

	@OrdreVisual(ordre = 100)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OrdreVisual(ordre = 200)
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
		achDictamen.setAchEn(this);

		return achDictamen;
	}

	public Dictamen removeAchDictamen(Dictamen achDictamen) {
		getAchDictamens().remove(achDictamen);
		achDictamen.setAchEn(null);

		return achDictamen;
	}

}
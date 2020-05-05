package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACH_LOPD database table.
 * 
 */
@Entity
@Table(name="ACH_LOPD")
@NamedQuery(name="Lopd.findAll", query="SELECT l FROM Lopd l")
public class Lopd implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_LOPD_ID_GENERATOR", sequenceName="ACH_LOPD_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_LOPD_ID_GENERATOR")
	private Long id;

	private String descripcio;

	private String descripciocas;

	private String nom;

	private String nomcas;

	//bi-directional many-to-one association to Dictamen
	@OneToMany(mappedBy="achLopd")
	private List<Dictamen> achDictamens;

	public Lopd() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getDescripcio() {
		return this.descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getDescripciocas() {
		return this.descripciocas;
	}

	public void setDescripciocas(String descripciocas) {
		this.descripciocas = descripciocas;
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
		achDictamen.setAchLopd(this);

		return achDictamen;
	}

	public Dictamen removeAchDictamen(Dictamen achDictamen) {
		getAchDictamens().remove(achDictamen);
		achDictamen.setAchLopd(null);

		return achDictamen;
	}

}
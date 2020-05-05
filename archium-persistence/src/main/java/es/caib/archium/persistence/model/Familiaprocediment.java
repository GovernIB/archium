package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACH_FAMILIAPROCEDIMENT database table.
 * 
 */
@Entity
@Table(name="ACH_FAMILIAPROCEDIMENT")
@NamedQuery(name="Familiaprocediment.findAll", query="SELECT f FROM Familiaprocediment f")
public class Familiaprocediment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	private Long id;

	private String nom;

	//bi-directional many-to-one association to Procediment
	@OneToMany(mappedBy="achFamiliaprocediment")
	private List<Procediment> achProcediments;

	public Familiaprocediment() {
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

	public List<Procediment> getAchProcediments() {
		return this.achProcediments;
	}

	public void setAchProcediments(List<Procediment> achProcediments) {
		this.achProcediments = achProcediments;
	}

	public Procediment addAchProcediment(Procediment achProcediment) {
		getAchProcediments().add(achProcediment);
		achProcediment.setAchFamiliaprocediment(this);

		return achProcediment;
	}

	public Procediment removeAchProcediment(Procediment achProcediment) {
		getAchProcediments().remove(achProcediment);
		achProcediment.setAchFamiliaprocediment(null);

		return achProcediment;
	}

}
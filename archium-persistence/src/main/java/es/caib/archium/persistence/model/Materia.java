package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACH_MATERIA database table.
 * 
 */
@Entity
@Table(name="ACH_MATERIA")
@NamedQuery(name="Materia.findAll", query="SELECT m FROM Materia m")
public class Materia implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_MATERIA_ID_GENERATOR", sequenceName="ACH_MATERIA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_MATERIA_ID_GENERATOR")
	private Long id;

	private String nom;

	//bi-directional many-to-many association to Procediment
	@ManyToMany(mappedBy="achMaterias")
	private List<Procediment> achProcediments;

	public Materia() {
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

}
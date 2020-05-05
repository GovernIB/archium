package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACH_FORMAINICI database table.
 * 
 */
@Entity
@Table(name="ACH_FORMAINICI")
@NamedQuery(name="Formainici.findAll", query="SELECT f FROM Formainici f")
public class Formainici implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_FORMAINICI_ID_GENERATOR", sequenceName="ACH_FORMAINICI_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_FORMAINICI_ID_GENERATOR")
	private Long id;

	private String descripcio;

	private String nom;

	//bi-directional many-to-one association to Procediment
	@OneToMany(mappedBy="achFormainici")
	private List<Procediment> achProcediments;

	public Formainici() {
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
		achProcediment.setAchFormainici(this);

		return achProcediment;
	}

	public Procediment removeAchProcediment(Procediment achProcediment) {
		getAchProcediments().remove(achProcediment);
		achProcediment.setAchFormainici(null);

		return achProcediment;
	}

}
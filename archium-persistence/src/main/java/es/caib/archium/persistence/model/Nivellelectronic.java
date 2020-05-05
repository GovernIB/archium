package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACH_NIVELLELECTRONIC database table.
 * 
 */
@Entity
@Table(name="ACH_NIVELLELECTRONIC")
@NamedQuery(name="Nivellelectronic.findAll", query="SELECT n FROM Nivellelectronic n")
public class Nivellelectronic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_NIVELLELECTRONIC_ID_GENERATOR", sequenceName="ACH_NIVELLELECTRONIC_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_NIVELLELECTRONIC_ID_GENERATOR")
	private Long id;

	private String descripcio;

	private String nom;

	//bi-directional many-to-one association to Procediment
	@OneToMany(mappedBy="achNivellelectronic")
	private List<Procediment> achProcediments;

	public Nivellelectronic() {
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
		achProcediment.setAchNivellelectronic(this);

		return achProcediment;
	}

	public Procediment removeAchProcediment(Procediment achProcediment) {
		getAchProcediments().remove(achProcediment);
		achProcediment.setAchNivellelectronic(null);

		return achProcediment;
	}

}
package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACH_SILENCI database table.
 * 
 */
@Entity
@Table(name="ACH_SILENCI")
@NamedQuery(name="Silenci.findAll", query="SELECT s FROM Silenci s")
public class Silenci implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_SILENCI_ID_GENERATOR", sequenceName="ACH_SILENCI_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_SILENCI_ID_GENERATOR")
	private Long id;

	private String nom;

	//bi-directional many-to-one association to Procediment
	@OneToMany(mappedBy="achSilenci")
	private List<Procediment> achProcediments;

	public Silenci() {
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
		achProcediment.setAchSilenci(this);

		return achProcediment;
	}

	public Procediment removeAchProcediment(Procediment achProcediment) {
		getAchProcediments().remove(achProcediment);
		achProcediment.setAchSilenci(null);

		return achProcediment;
	}

}
package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACH_TIPUSPUBLIC database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSPUBLIC")
@NamedQuery(name="Tipuspublic.findAll", query="SELECT t FROM Tipuspublic t")
public class Tipuspublic implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSPUBLIC_ID_GENERATOR", sequenceName="ACH_TIPUSPUBLIC_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSPUBLIC_ID_GENERATOR")
	private long id;

	private String descripcio;

	private String nom;

	//bi-directional many-to-many association to Procediment
	@ManyToMany(mappedBy="achTipuspublics")
	private List<Procediment> achProcediments;

	public Tipuspublic() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
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

}
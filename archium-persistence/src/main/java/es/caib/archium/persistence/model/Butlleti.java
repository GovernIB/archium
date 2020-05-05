package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ACH_BUTLLETI database table.
 * 
 */
@Entity
@Table(name="ACH_BUTLLETI")
@NamedQuery(name="Butlleti.findAll", query="SELECT b FROM Butlleti b")
public class Butlleti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_BUTLLETI_ID_GENERATOR", sequenceName="ACH_BUTLLETI_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_BUTLLETI_ID_GENERATOR")
	private String id;

	private String codi;

	private String descripcio;

	private String nom;

	public Butlleti() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCodi() {
		return this.codi;
	}

	public void setCodi(String codi) {
		this.codi = codi;
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

}
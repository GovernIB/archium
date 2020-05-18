package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ACH_TIPUSNORMATIVA database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSNORMATIVA")
@NamedQuery(name="Tipusnormativa.findAll", query="SELECT t FROM Tipusnormativa t")
public class Tipusnormativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSNORMATIVA_ID_GENERATOR", sequenceName="ACH_TIPUSNORMATIVA_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSNORMATIVA_ID_GENERATOR")
	private String id;

	private String nom;

	public Tipusnormativa() {
	}

	public String getId() {
		return this.id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

}
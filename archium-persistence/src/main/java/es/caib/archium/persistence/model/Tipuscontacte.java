package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACH_TIPUSCONTACTE database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSCONTACTE")
@NamedQuery(name="Tipuscontacte.findAll", query="SELECT t FROM Tipuscontacte t")
public class Tipuscontacte implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSCONTACTE_ID_GENERATOR", sequenceName="ACH_TIPUSCONTACTE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSCONTACTE_ID_GENERATOR")
	private Long id;

	private String nom;

	private String nomcas;

	//bi-directional many-to-one association to ContacteProcediment
	@OneToMany(mappedBy="achTipuscontacte")
	private List<ContacteProcediment> achContacteProcediments;

	public Tipuscontacte() {
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

	public String getNomcas() {
		return this.nomcas;
	}

	public void setNomcas(String nomcas) {
		this.nomcas = nomcas;
	}

	public List<ContacteProcediment> getAchContacteProcediments() {
		return this.achContacteProcediments;
	}

	public void setAchContacteProcediments(List<ContacteProcediment> achContacteProcediments) {
		this.achContacteProcediments = achContacteProcediments;
	}

	public ContacteProcediment addAchContacteProcediment(ContacteProcediment achContacteProcediment) {
		getAchContacteProcediments().add(achContacteProcediment);
		achContacteProcediment.setAchTipuscontacte(this);

		return achContacteProcediment;
	}

	public ContacteProcediment removeAchContacteProcediment(ContacteProcediment achContacteProcediment) {
		getAchContacteProcediments().remove(achContacteProcediment);
		achContacteProcediment.setAchTipuscontacte(null);

		return achContacteProcediment;
	}

}
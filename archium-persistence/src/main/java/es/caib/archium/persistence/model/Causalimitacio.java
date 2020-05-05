package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.Max;

import java.util.List;


/**
 * The persistent class for the ACH_CAUSALIMITACIO database table.
 * 
 */
@Entity
@Table(name="ACH_CAUSALIMITACIO")
@NamedQuery(name="Causalimitacio.findAll", query="SELECT c FROM Causalimitacio c")
public class Causalimitacio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_CAUSALIMITACIO_ID_GENERATOR", sequenceName="ACH_CAUSALIMITACIO_SEQ" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_CAUSALIMITACIO_ID_GENERATOR")
	private Long id;

	private String codi;
	
	private String nom;

	private String nomcas;

	//bi-directional many-to-one association to LimitacioNormativaSerie
	@OneToMany(mappedBy="achCausalimitacio")
	private List<LimitacioNormativaSerie> achLimitacioNormativaSeries;

	public Causalimitacio() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodi() {
		return this.codi;
	}

	public void setCodi(String codi) {
		this.codi = codi;
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

	public List<LimitacioNormativaSerie> getAchLimitacioNormativaSeries() {
		return this.achLimitacioNormativaSeries;
	}

	public void setAchLimitacioNormativaSeries(List<LimitacioNormativaSerie> achLimitacioNormativaSeries) {
		this.achLimitacioNormativaSeries = achLimitacioNormativaSeries;
	}

	public LimitacioNormativaSerie addAchLimitacioNormativaSery(LimitacioNormativaSerie achLimitacioNormativaSery) {
		getAchLimitacioNormativaSeries().add(achLimitacioNormativaSery);
		achLimitacioNormativaSery.setAchCausalimitacio(this);

		return achLimitacioNormativaSery;
	}

	public LimitacioNormativaSerie removeAchLimitacioNormativaSery(LimitacioNormativaSerie achLimitacioNormativaSery) {
		getAchLimitacioNormativaSeries().remove(achLimitacioNormativaSery);
		achLimitacioNormativaSery.setAchCausalimitacio(null);

		return achLimitacioNormativaSery;
	}

}
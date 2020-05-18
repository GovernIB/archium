package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACH_SERIEARGEN database table.
 * 
 */
@Entity
@Table(name="ACH_SERIEARGEN")
@NamedQuery(name="Serieargen.findAll", query="SELECT s FROM Serieargen s")
public class Serieargen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_SERIEARGEN_ID_GENERATOR", sequenceName="ACH_SERIEARGEN_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_SERIEARGEN_ID_GENERATOR")
	private Long id;

	private String codi;

	private String nom;

	//bi-directional many-to-many association to Seriedocumental
	@ManyToMany(mappedBy="achSerieargens")
	private List<Seriedocumental> achSeriedocumentals;

	//bi-directional many-to-one association to Serierelacionada
	@OneToMany(mappedBy="achSerieargen")
	private List<Serierelacionada> achSerierelacionadas;

	public Serieargen() {
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

	public List<Seriedocumental> getAchSeriedocumentals() {
		return this.achSeriedocumentals;
	}

	public void setAchSeriedocumentals(List<Seriedocumental> achSeriedocumentals) {
		this.achSeriedocumentals = achSeriedocumentals;
	}

	public List<Serierelacionada> getAchSerierelacionadas() {
		return this.achSerierelacionadas;
	}

	public void setAchSerierelacionadas(List<Serierelacionada> achSerierelacionadas) {
		this.achSerierelacionadas = achSerierelacionadas;
	}

	public Serierelacionada addAchSerierelacionada(Serierelacionada achSerierelacionada) {
		getAchSerierelacionadas().add(achSerierelacionada);
		achSerierelacionada.setAchSerieargen(this);

		return achSerierelacionada;
	}

	public Serierelacionada removeAchSerierelacionada(Serierelacionada achSerierelacionada) {
		getAchSerierelacionadas().remove(achSerierelacionada);
		achSerierelacionada.setAchSerieargen(null);

		return achSerierelacionada;
	}

}
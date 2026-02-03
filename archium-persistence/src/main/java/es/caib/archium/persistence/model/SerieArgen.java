package es.caib.archium.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.List;


/**
 * The persistent class for the ACH_SERIEARGEN database table.
 * 
 */
@Entity
@Table(name="ACH_SERIEARGEN")
@TaulaMestra(excludedFields = {"id", "achSeriedocumentals", "achSerierelacionadas"})
@NamedQuery(name="Serieargen.findAll", query="SELECT s FROM SerieArgen s")
public class SerieArgen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_SERIEARGEN_ID_GENERATOR", sequenceName="ACH_SERIEARGEN_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_SERIEARGEN_ID_GENERATOR")
	private Long id;

	@NotNull
	private String codi;

	@NotNull
	private String nom;

	//bi-directional many-to-many association to Seriedocumental
	@ManyToMany(mappedBy="achSerieargens")
	private List<Seriedocumental> achSeriedocumentals;

	//bi-directional many-to-one association to Serierelacionada
	@OneToMany(mappedBy="achSerieargen")
	private List<Serierelacionada> achSerierelacionadas;

	public SerieArgen() {
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
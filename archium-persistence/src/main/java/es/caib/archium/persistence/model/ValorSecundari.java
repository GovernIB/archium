package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * The persistent class for the ACH_VALORSECUNDARI database table.
 * 
 */
@Entity
@Table(name="ACH_VALORSECUNDARI")
@TaulaMestra(excludedFields = {"id", "achValoracios"})
@NamedQuery(name="Valorsecundari.findAll", query="SELECT v FROM ValorSecundari v")
public class ValorSecundari implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_VALORSECUNDARI_ID_GENERATOR", sequenceName="ACH_VALORSECUNDARI_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_VALORSECUNDARI_ID_GENERATOR")
	private Long id;

	@NotNull
	private String descripcio;

	private String descripciocas;

	@NotNull
	private String nom;

	private String nomcas;

	//bi-directional many-to-one association to Valoracio
	@OneToMany(mappedBy="achValorsecundari")
	private List<Valoracio> achValoracios;

	public ValorSecundari() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OrdreVisual(ordre = 300)
	public String getDescripcio() {
		return this.descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	@OrdreVisual(ordre = 400)
	public String getDescripciocas() {
		return this.descripciocas;
	}

	public void setDescripciocas(String descripciocas) {
		this.descripciocas = descripciocas;
	}

	@OrdreVisual(ordre = 100)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OrdreVisual(ordre = 200)
	public String getNomcas() {
		return this.nomcas;
	}

	public void setNomcas(String nomcas) {
		this.nomcas = nomcas;
	}

	public List<Valoracio> getAchValoracios() {
		return this.achValoracios;
	}

	public void setAchValoracios(List<Valoracio> achValoracios) {
		this.achValoracios = achValoracios;
	}

	public Valoracio addAchValoracio(Valoracio achValoracio) {
		getAchValoracios().add(achValoracio);
		achValoracio.setAchValorsecundari(this);

		return achValoracio;
	}

	public Valoracio removeAchValoracio(Valoracio achValoracio) {
		getAchValoracios().remove(achValoracio);
		achValoracio.setAchValorsecundari(null);

		return achValoracio;
	}

}
package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACH_TIPUSVALOR database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSVALOR")
@NamedQuery(name="Tipusvalor.findAll", query="SELECT t FROM Tipusvalor t")
public class Tipusvalor implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSVALOR_ID_GENERATOR", sequenceName="ACH_TIPUSVALOR_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSVALOR_ID_GENERATOR")
	private Long id;

	private String descripcio;

	private String descripciocas;

	private String nom;

	private String nomcas;

	//bi-directional many-to-one association to Valorprimari
	@OneToMany(mappedBy="achTipusvalor")
	private List<Valorprimari> achValorprimaris;

	public Tipusvalor() {
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

	public String getDescripciocas() {
		return this.descripciocas;
	}

	public void setDescripciocas(String descripciocas) {
		this.descripciocas = descripciocas;
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

	public List<Valorprimari> getAchValorprimaris() {
		return this.achValorprimaris;
	}

	public void setAchValorprimaris(List<Valorprimari> achValorprimaris) {
		this.achValorprimaris = achValorprimaris;
	}

	public Valorprimari addAchValorprimari(Valorprimari achValorprimari) {
		getAchValorprimaris().add(achValorprimari);
		achValorprimari.setAchTipusvalor(this);

		return achValorprimari;
	}

	public Valorprimari removeAchValorprimari(Valorprimari achValorprimari) {
		getAchValorprimaris().remove(achValorprimari);
		achValorprimari.setAchTipusvalor(null);

		return achValorprimari;
	}

}
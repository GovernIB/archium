package es.caib.archium.persistence.model;

import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


/**
 * The persistent class for the ACH_TIPUSRELACIO database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSRELACIO")
@TaulaMestra
@NamedQuery(name="TipusRelacioSerie.findAll", query="SELECT b FROM TipusRelacioSerie b")
public class TipusRelacioSerie implements Serializable, Comparable<TipusRelacioSerie> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSRELACIO_ID_GENERATOR", sequenceName="ACH_TIPUSRELACIO_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSRELACIO_ID_GENERATOR")
	private Long id;

	@NotNull
	private String codi;

	private String descripcio;

	@NotNull
	private String nom;

	@NotNull
	private String nomcas;

	private String descripciocas;

	public TipusRelacioSerie() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OrdreVisual(ordre = 100)
	public String getCodi() {
		return this.codi;
	}

	public void setCodi(String codi) {
		this.codi = codi;
	}

	@OrdreVisual(ordre = 400)
	public String getDescripcio() {
		return this.descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	@OrdreVisual(ordre = 200)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OrdreVisual(ordre = 300)
	public String getNomcas() {
		return nomcas;
	}

	public void setNomcas(String nomcas) {
		this.nomcas = nomcas;
	}

	@OrdreVisual(ordre = 500)
	public String getDescripciocas() {
		return descripciocas;
	}

	public void setDescripciocas(String descripciocas) {
		this.descripciocas = descripciocas;
	}

	@Override
	public String toString() {
		return nom;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TipusRelacioSerie tipusRelacio = (TipusRelacioSerie) o;
		return Objects.equals(codi, tipusRelacio.codi);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codi);
	}


	@Override
	public int compareTo(TipusRelacioSerie that) {
		final int EQUAL = 0;

		//this optimization is usually worthwhile, and can
		//always be added
		if (this == that) return EQUAL;

		if (that == null) {
			return EQUAL;
		}
		return this.codi.compareTo(that.codi);
	}
}
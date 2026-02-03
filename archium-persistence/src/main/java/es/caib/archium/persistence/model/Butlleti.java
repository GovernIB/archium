package es.caib.archium.persistence.model;

import java.io.Serializable;
import java.util.Objects;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the ACH_BUTLLETI database table.
 * 
 */
@Entity
@Table(name="ACH_BUTLLETI")
@TaulaMestra
@NamedQuery(name="Butlleti.findAll", query="SELECT b FROM Butlleti b")
public class Butlleti implements Serializable, Comparable<Butlleti> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_BUTLLETI_ID_GENERATOR", sequenceName="ACH_BUTLLETI_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_BUTLLETI_ID_GENERATOR")
	private Long id;

	@NotNull
	private String codi;

	@NotNull
	private String descripcio;

	@NotNull
	private String nom;

	public Butlleti() {
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

	@OrdreVisual(ordre = 300)
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

	@Override
	public String toString() {
		return nom;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Butlleti butlleti = (Butlleti) o;
		return Objects.equals(codi, butlleti.codi);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codi);
	}


	@Override
	public int compareTo(Butlleti that) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		//this optimization is usually worthwhile, and can
		//always be added
		if (this == that) return EQUAL;

		if (that == null) {
			return EQUAL;
		}
		return this.codi.compareTo(that.codi);
	}
}
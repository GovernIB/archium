package es.caib.archium.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;
import java.util.Objects;


/**
 * The persistent class for the ACH_ORGANCOLLEGIAT database table.
 * 
 */
@Entity
@Table(name="ACH_ORGANCOLLEGIAT")
@TaulaMestra
@NamedQuery(name="Organcollegiat.findAll", query="SELECT o FROM OrganCollegiat o")
public class OrganCollegiat implements Serializable, Comparable<OrganCollegiat> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_ORGANCOLLEGIAT_ID_GENERATOR", sequenceName="ACH_ORGANCOLLEGIAT_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_ORGANCOLLEGIAT_ID_GENERATOR")
	private Long id;

	@NotNull
	private String codi;

	@NotNull
	@Size(max=2000)
	private String nom;

	public OrganCollegiat() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodi() {
		return codi;
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

	@Override
	public String toString() {
		return this.nom;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrganCollegiat that = (OrganCollegiat) o;
		return Objects.equals(codi, that.codi);
	}

	@Override
	public int hashCode() {
		return Objects.hash(codi);
	}

	@Override
	public int compareTo(OrganCollegiat that) {
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
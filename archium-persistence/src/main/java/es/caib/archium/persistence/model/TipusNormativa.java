package es.caib.archium.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Objects;


/**
 * The persistent class for the ACH_TIPUSNORMATIVA database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSNORMATIVA")
@TaulaMestra
@NamedQuery(name="Tipusnormativa.findAll", query="SELECT t FROM TipusNormativa t")
public class TipusNormativa implements Serializable, Comparable<TipusNormativa> {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSNORMATIVA_ID_GENERATOR", sequenceName="ACH_TIPUSNORMATIVA_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSNORMATIVA_ID_GENERATOR")
	private Long id;

	@NotNull
	private String nom;

	public TipusNormativa() {
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

	@Override
	public String toString() {
		return this.nom;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TipusNormativa that = (TipusNormativa) o;
		return Objects.equals(id, that.id);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public int compareTo(TipusNormativa that) {
		final int BEFORE = -1;
		final int EQUAL = 0;
		final int AFTER = 1;

		//this optimization is usually worthwhile, and can
		//always be added
		if (this == that) return EQUAL;

		if (that == null) {
			return EQUAL;
		}
		return this.nom.compareTo(that.nom);
	}
}
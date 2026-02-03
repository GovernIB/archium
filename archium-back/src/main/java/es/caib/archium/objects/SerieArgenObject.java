package es.caib.archium.objects;

import es.caib.archium.persistence.model.SerieArgen;

import java.util.Objects;

public class SerieArgenObject {
	private Long id;
	private String codi;
	private String nom;
	public SerieArgenObject() {
	}
	public SerieArgenObject(Long id, String codi, String nom) {
		this.id = id;
		this.codi = codi;
		this.nom = nom;
	}

	public SerieArgenObject (SerieArgen serieArgen) {
		if (serieArgen != null) {
			this.id = serieArgen.getId();
			this.codi = serieArgen.getCodi();
			this.nom = serieArgen.getNom();
		}
	}

	public Long getId() {
		return id;
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
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	@Override
	public int hashCode() {
		return Objects.hash(codi, id, nom);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof SerieArgenObject)) {
			return false;
		}
		SerieArgenObject other = (SerieArgenObject) obj;
		return Objects.equals(codi, other.codi) && Objects.equals(id, other.id) && Objects.equals(nom, other.nom);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SerieArgenObject [id=");
		builder.append(id);
		builder.append(", codi=");
		builder.append(codi);
		builder.append(", nom=");
		builder.append(nom);
		builder.append("]");
		return builder.toString();
	}
	
	
}

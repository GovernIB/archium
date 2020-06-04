package es.caib.archium.objects;

import java.util.Objects;

import es.caib.archium.persistence.model.Silenci;

public class SilenciObject {
	private Long 	id;
	private String 	nom;
	public SilenciObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public SilenciObject(Long id, String nom) {
		super();
		this.id 	= id;
		this.nom 	= nom;
	}

	public SilenciObject(Silenci bd) {
		super();
		this.id 	= bd.getId();
		this.nom 	= bd.getNom();
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}

	@Override
	public int hashCode() {
		return Objects.hash(id);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SilenciObject other = (SilenciObject) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SilenciObject [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append("]");
		return builder.toString();
	}

	

	
}

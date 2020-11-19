package es.caib.archium.objects;

import java.util.Objects;

import es.caib.archium.persistence.model.Silenci;
import es.caib.archium.persistence.model.Tipuspublic;

public class tipusPublicObject {
	private Long 	id;
	private String 	nom;
	private String 	descripcio;
	
	public tipusPublicObject() {
		super();
	}
	public tipusPublicObject(Long id, String nom, String descripcio) {
		super();
		this.id = id;
		this.nom = nom;
		this.descripcio = descripcio;
	}
	
	public tipusPublicObject(Tipuspublic bd) {
		super();
		this.id 	= bd.getId();
		this.nom 	= bd.getNom();
		this.descripcio = bd.getDescripcio();
		
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
	public String getDescripcio() {
		return descripcio;
	}
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
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
		tipusPublicObject other = (tipusPublicObject) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("tipusPublicObject [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", descripcio=");
		builder.append(descripcio);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

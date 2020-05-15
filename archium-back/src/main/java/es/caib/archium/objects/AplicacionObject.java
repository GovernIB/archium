package es.caib.archium.objects;

import java.util.Objects;

import es.caib.archium.persistence.model.Aplicacio;
import es.caib.archium.persistence.model.Catalegsery;
import es.caib.archium.persistence.model.Tipusdictamen;

public class AplicacionObject {
	
	private Long id;
	private String nom;
	
	public AplicacionObject(Long id, String nom) {
		this.id = id;
		this.nom = nom;
	}
	
	public AplicacionObject() {
	}
	
	public AplicacionObject (Aplicacio dbAplicacio) {
		if(dbAplicacio!=null) {
			this.id = dbAplicacio.getId();
			this.nom = dbAplicacio.getNom();
		}
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
	
	public Aplicacio toDbObject() {
		Aplicacio db = new Aplicacio();
		db.setId(id);
		db.setNom(nom);
		return db;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, nom);
	}
	
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof AplicacionObject)) {
			return false;
		}
		AplicacionObject other = (AplicacionObject) obj;
		return Objects.equals(id, other.id) && Objects.equals(nom, other.nom);
	}
	
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AplicacionBean [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

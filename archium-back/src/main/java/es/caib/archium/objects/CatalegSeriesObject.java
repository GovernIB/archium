package es.caib.archium.objects;

import java.util.Objects;

import es.caib.archium.persistence.model.Catalegsery;

public class CatalegSeriesObject {

	private Long id;
	private String nom;
	private String nomCas;
	
	public CatalegSeriesObject() {
	}
	
	public CatalegSeriesObject(Long id, String nom, String nomCas) {
		this.id = id;
		this.nom = nom;
		this.nomCas = nomCas;
	}
	
	public CatalegSeriesObject (Catalegsery dbCatalegSery) {
		if(dbCatalegSery!=null) {
			this.id = dbCatalegSery.getId();
			this.nom = dbCatalegSery.getNom();
			this.nomCas = dbCatalegSery.getNomcas();
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
	
	public String getNomCas() {
		return nomCas;
	}
	
	public void setNomCas(String nomCas) {
		this.nomCas = nomCas;
	}
	
	public Catalegsery toDbObject() {
		Catalegsery db = new Catalegsery();
		db.setId(id);
		db.setNom(nom);
		db.setNomcas(nomCas);
		return db;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, nom, nomCas);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof CatalegSeriesObject)) {
			return false;
		}
		CatalegSeriesObject other = (CatalegSeriesObject) obj;
		return Objects.equals(id, other.id) && Objects.equals(nom, other.nom) && Objects.equals(nomCas, other.nomCas);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CatalegSeriesBean [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", nomCas=");
		builder.append(nomCas);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
}

package es.caib.archium.objects;

import java.util.Objects;

import es.caib.archium.persistence.model.ValorSecundari;

public class ValorSecundariObject {

	private Long 	id;	
	private String 	nom;
	private String 	nomCas;
	private String descripcio;
	private String descripcioCas;
	
	public ValorSecundariObject() {
		super();
	}
	
	
	
	public ValorSecundariObject(Long id, String nom, String nomCas, String descripcio, String descripcioCas) {
		super();
		this.id = id;
		this.nom = nom;
		this.nomCas = nomCas;
		this.descripcio = descripcio;
		this.descripcioCas = descripcioCas;
	}



	public ValorSecundariObject (ValorSecundari dbValorsecundari) {
		if(dbValorsecundari!=null) {
			this.id = dbValorsecundari.getId();
			this.nom = dbValorsecundari.getNom();
			this.nomCas = dbValorsecundari.getNomcas();
			this.descripcio = dbValorsecundari.getDescripcio();
			this.descripcioCas = dbValorsecundari.getDescripciocas();
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



	public String getDescripcio() {
		return descripcio;
	}



	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}



	public String getDescripcioCas() {
		return descripcioCas;
	}



	public void setDescripcioCas(String descripcioCas) {
		this.descripcioCas = descripcioCas;
	}



	public ValorSecundari toDbObject() {
		ValorSecundari db = new ValorSecundari();
		db.setId(id);
		db.setNom(nom);
		db.setNomcas(nomCas);
		db.setDescripcio(descripcio);
		db.setDescripciocas(descripcioCas);
		return db;
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
		ValorSecundariObject other = (ValorSecundariObject) obj;
		return Objects.equals(id, other.id);
	}



	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipuValorObject [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", nomCas=");
		builder.append(nomCas);
		builder.append(", descripcio=");
		builder.append(descripcio);
		builder.append(", descripcioCas=");
		builder.append(descripcioCas);
		builder.append("]");
		return builder.toString();
	}


}
package es.caib.archium.objects;

import es.caib.archium.persistence.model.Tipusacce;
import es.caib.archium.persistence.model.Tipusdictamen;
import es.caib.archium.persistence.model.Tipusvalor;
import es.caib.archium.persistence.model.Valorsecundari;

public class ValorSecundariObject {

	private Long 	id;	
	private String 	nom;
	private String 	nomCas;
	private String descripcio;
	private String descripcioCas;
	
	public ValorSecundariObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public ValorSecundariObject(Long id, String nom, String nomCas, String descripcio, String descripcioCas) {
		super();
		this.id = id;
		this.nom = nom;
		this.nomCas = nomCas;
		this.descripcio = descripcio;
		this.descripcioCas = descripcioCas;
	}



	public ValorSecundariObject (Valorsecundari dbValorsecundari) {
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



	public Valorsecundari toDbObject() {
		Valorsecundari db = new Valorsecundari();
		db.setId(id);
		db.setNom(nom);
		db.setNomcas(nomCas);
		db.setDescripcio(descripcio);
		db.setDescripciocas(descripcioCas);
		return db;
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



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
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
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}		
	
}
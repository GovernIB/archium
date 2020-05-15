package es.caib.archium.objects;

import es.caib.archium.persistence.model.Lopd;
import es.caib.archium.persistence.model.Tipusacce;

public class LopdObject {

	private Long 	id;
	
	private String 	nom;
	private String 	nomCas;
	private String 	descripcio;
	private String  descripcioCas;
	
	public LopdObject(Long id, String nom, String nomCas, String descripcio, String descripcioCas) {
		super();
		this.id = id;
		this.nom = nom;
		this.nomCas = nomCas;
		this.descripcio = descripcio;
		this.descripcioCas = descripcioCas;
	}

	public LopdObject() {
		super();
	}
	
	public LopdObject (Lopd dbTipusacce) {
		if(dbTipusacce!=null) {
			this.id = dbTipusacce.getId();
			this.nom = dbTipusacce.getNom();
			this.nomCas = dbTipusacce.getNomcas();
			this.descripcio = dbTipusacce.getDescripcio();
			this.descripcioCas = dbTipusacce.getDescripciocas();
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
	public String getnomCas() {
		return nomCas;
	}
	public void setnomCas(String nomCas) {
		this.nomCas = nomCas;
	}
	public String getDescripcio() {
		return descripcio;
	}
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
	public String getDescripciocas() {
		return descripcioCas;
	}
	public void setDescripciocas(String descripcioCas) {
		this.descripcioCas = descripcioCas;
	}
	
	public Lopd toDbObject() {
		Lopd db = new Lopd();
		db.setId(id);
		db.setNom(nom);
		db.setNomcas(nomCas);
		db.setDescripcio(descripcio);
		db.setDescripciocas(descripcioCas);
		return db;
	}
	
	@Override
	public String toString() {
		return "LopdObject [id=" + id + ", nom=" + nom + ", nomCas=" + nomCas + ", descripcio=" + descripcio
				+ ", descripcioCas=" + descripcioCas + "]";
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
		LopdObject other = (LopdObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
}
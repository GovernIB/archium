package es.caib.archium.objects;

import java.util.Objects;

import es.caib.archium.persistence.model.Tipusacce;
import es.caib.archium.persistence.model.Tipusdictamen;
import es.caib.archium.persistence.model.Tipusvalor;

public class TipuValorObject {

	private Long 	id;	
	private String 	nom;
	private String 	nomCas;
	private String descripcio;
	private String descripcioCas;
	
	public TipuValorObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	
	
	public TipuValorObject(Long id, String nom, String nomCas, String descripcio, String descripcioCas) {
		super();
		this.id = id;
		this.nom = nom;
		this.nomCas = nomCas;
		this.descripcio = descripcio;
		this.descripcioCas = descripcioCas;
	}



	public TipuValorObject (Tipusvalor dbTipusvalor) {
		if(dbTipusvalor!=null) {
			this.id = dbTipusvalor.getId();
			this.nom = dbTipusvalor.getNom();
			this.nomCas = dbTipusvalor.getNomcas();
			this.descripcio = dbTipusvalor.getDescripcio();
			this.descripcioCas = dbTipusvalor.getDescripciocas();
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
	
	public Tipusvalor toDbObject() {
		Tipusvalor db = new Tipusvalor();
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
		TipuValorObject other = (TipuValorObject) obj;
		return Objects.equals(id, other.id);
	}

	
	
}
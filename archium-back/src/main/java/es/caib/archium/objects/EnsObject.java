package es.caib.archium.objects;

import java.util.Objects;

import es.caib.archium.persistence.model.Ens;
import es.caib.archium.persistence.model.Lopd;

public class EnsObject {

	
	private Long 	id;
	private String 	nom;
	private String 	nomCas;
	private String 	descripcio;
	private String 	descripcioCas;
	
	public EnsObject() {
		super();
	}
	public EnsObject(Long id, String nom, String nomCas, String descripcio, String descripcioCas) {
		super();
		this.id = id;
		this.nom = nom;
		this.nomCas = nomCas;
		this.descripcio = descripcio;
		this.descripcioCas = descripcioCas;
	}
	
	public EnsObject (Ens dbEns) {
		if(dbEns!=null) {
			this.id = dbEns.getId();
			this.nom = dbEns.getNom();
			this.nomCas = dbEns.getNomcas();
			this.descripcio = dbEns.getDescripcio();
			this.descripcioCas = dbEns.getDescripciocas();
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
	
	public Ens toDbObject() {
		Ens db = new Ens();
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
		EnsObject other = (EnsObject) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EnsObject [id=");
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
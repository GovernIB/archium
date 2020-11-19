package es.caib.archium.objects;

import java.util.Objects;

import es.caib.archium.persistence.model.Tipusacce;
import es.caib.archium.persistence.model.Tipusdictamen;

public class TipuAccesObject {

	private Long 	id;	
	private String 	nom;
	private String 	nomCas;
	
	public TipuAccesObject() {
		super();
	}
	public TipuAccesObject(Long id, String nom, String nomCas) {
		super();
		this.id = id;
		this.nom = nom;
		this.nomCas = nomCas;
	}
	
	public TipuAccesObject (Tipusacce dbTipusacce) {
		if(dbTipusacce!=null) {
			this.id = dbTipusacce.getId();
			this.nom = dbTipusacce.getNom();
			this.nomCas = dbTipusacce.getNomcas();
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
	
	public Tipusacce toDbObject() {
		Tipusacce db = new Tipusacce();
		db.setId(id);
		db.setNom(nom);
		db.setNomcas(nomCas);
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
		TipuAccesObject other = (TipuAccesObject) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipuAccesObject [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", nomCas=");
		builder.append(nomCas);
		builder.append("]");
		return builder.toString();
	}
	
	
}
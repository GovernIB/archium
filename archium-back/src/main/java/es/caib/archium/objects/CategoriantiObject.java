package es.caib.archium.objects;

import java.util.Objects;

import es.caib.archium.persistence.model.Categorianti;

public class CategoriantiObject {
	
	private Long id;
	private String nom;
	private String nomCas;
	
	
	public CategoriantiObject() {
		super();
	}


	public CategoriantiObject(Long id, String nom, String nomCas) {
		super();
		this.id = id;
		this.nom = nom;
		this.nomCas = nomCas;
	}


	public CategoriantiObject(Categorianti bd) {
		this.id		= bd.getId();
		this.nom  	= bd.getNom();
		this.nomCas = bd.getNomcas();
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
		CategoriantiObject other = (CategoriantiObject) obj;
		return Objects.equals(id, other.id);
	}


	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CategoriantiObject [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", nomCas=");
		builder.append(nomCas);
		builder.append("]");
		return builder.toString();
	}

	
	
}

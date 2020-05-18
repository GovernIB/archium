package es.caib.archium.objects;

import es.caib.archium.persistence.model.Categorianti;

public class CategoriantiObject {
	
	private Long id;
	private String nom;
	private String nomCas;
	
	
	public CategoriantiObject() {
		super();
		// TODO Auto-generated constructor stub
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

	
}

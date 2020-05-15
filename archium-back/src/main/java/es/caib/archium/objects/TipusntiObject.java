package es.caib.archium.objects;

import es.caib.archium.persistence.model.Categorianti;
import es.caib.archium.persistence.model.Tipusnti;

public class TipusntiObject {
	
	private Long 			id;
	private String 			codi;
	private String 			nom;
	private String 			nomCas;
	private CategoriantiObject 	categorianti;
	
	public TipusntiObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TipusntiObject(Long id, String codi, String nom, String nomCas, CategoriantiObject categorianti) {
		super();
		this.id = id;
		this.codi = codi;
		this.nom = nom;
		this.nomCas = nomCas;
		this.categorianti = categorianti;
	}

	public TipusntiObject(Tipusnti i) {
		this.id				=i.getId();
		this.codi			= i.getCodi();
		this.nom			= i.getNom();
		this.nomCas 		= i.getNomcas();
		this.categorianti 	= new CategoriantiObject();
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodi() {
		return codi;
	}

	public void setCodi(String codi) {
		this.codi = codi;
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

	public CategoriantiObject getCategorianti() {
		return categorianti;
	}

	public void setCategorianti(CategoriantiObject categorianti) {
		this.categorianti = categorianti;
	}

}

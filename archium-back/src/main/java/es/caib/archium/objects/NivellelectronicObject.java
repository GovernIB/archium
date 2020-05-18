package es.caib.archium.objects;

import es.caib.archium.persistence.model.Nivellelectronic;

public class NivellelectronicObject {

	private Long 	id;
	private String 	nom;
	private String  descripcio;
	
	public NivellelectronicObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public NivellelectronicObject(Long id, String nom, String  descripcio) {
		super();
		this.id 	= id;
		this.nom 	= nom;
		this.descripcio = descripcio;
	}

	public NivellelectronicObject(Nivellelectronic bd) {
		super();
		this.id 	= bd.getId();
		this.nom 	= bd.getNom();
		this.descripcio = bd.getDescripcio();
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

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
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
		NivellelectronicObject other = (NivellelectronicObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

package es.caib.archium.objects;

import es.caib.archium.persistence.model.Materia;

public class MateriaObject {

	private Long 	id;
	private String 	nom;
	
	public MateriaObject(Materia bd) {
		this.id = bd.getId();
		this.nom =bd.getNom();
	}

	public MateriaObject() {
		super();
		// TODO Auto-generated constructor stub
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

	public MateriaObject(Long id, String nom) {
		super();
		this.id = id;
		this.nom = nom;
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
		MateriaObject other = (MateriaObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}

package es.caib.archium.objects;

import java.util.Objects;

public class OrganCollegiatObject {

	private Long 	id;
	private String codi;
	private String nom;

	public OrganCollegiatObject(Long id, String codi, String nom) {
		super();
		this.id=id;
		this.codi = codi;
		this.nom = nom;
	}

	public OrganCollegiatObject() {
		super();
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

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
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
		OrganCollegiatObject other = (OrganCollegiatObject) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		return "OrganCollegiatObject [id=" +
				id +
				", codi=" +
				codi +
				", nom=" +
				nom +
				"]";
	}
	
	
	
}

package es.caib.archium.objects;

import java.util.Objects;

public class Dir3Object {

	private String codi;
	private String nom;
	
	public Dir3Object(String codi, String nom) {
		super();
		this.codi = codi;
		this.nom = nom;
	}

	public Dir3Object() {
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

	

	@Override
	public int hashCode() {
		return Objects.hash(codi, nom);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Dir3Object other = (Dir3Object) obj;
		return Objects.equals(codi, other.codi) && Objects.equals(nom, other.nom);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dir3Object [codi=");
		builder.append(codi);
		builder.append(", nom=");
		builder.append(nom);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

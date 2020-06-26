package es.caib.archium.ejb.objects;

import java.util.Objects;

public class Dir3 {
	
	private String codi;
	private String nom;
	
	public Dir3(String codi, String nom) {
		super();
		this.codi = codi;
		this.nom = nom;
	}

	public Dir3() {
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
		Dir3 other = (Dir3) obj;
		return Objects.equals(codi, other.codi) && Objects.equals(nom, other.nom);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Dir3 [codi=");
		builder.append(codi);
		builder.append(", nom=");
		builder.append(nom);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

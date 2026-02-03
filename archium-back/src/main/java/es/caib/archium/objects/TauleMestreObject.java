package es.caib.archium.objects;

import java.util.Objects;

public class TauleMestreObject {

	private String nom;
	private String codi;
	private String descripcio;
	
	
	public TauleMestreObject() {
		super();
	}
	public TauleMestreObject(String nom, String codi, String descripcio) {
		super();
		this.nom = nom;
		this.codi = codi;
		this.descripcio = descripcio;
	}
	public String getNom() {
		return nom;
	}
	public void setNom(String nom) {
		this.nom = nom;
	}
	public String getCodi() {
		return codi;
	}
	public void setCodi(String codi) {
		this.codi = codi;
	}
	public String getDescripcio() {
		return descripcio;
	}
	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}
	@Override
	public int hashCode() {
		return Objects.hash(nom);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		TauleMestreObject other = (TauleMestreObject) obj;
		return Objects.equals(nom, other.nom);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TauleMestreObject [nom=");
		builder.append(nom);
		builder.append(", codi=");
		builder.append(codi);
		builder.append(", descripcio=");
		builder.append(descripcio);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

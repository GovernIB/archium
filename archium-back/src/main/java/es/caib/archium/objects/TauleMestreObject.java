package es.caib.archium.objects;

public class TauleMestreObject {

	private String nom;
	private String codi;
	private String descripcio;
	
	
	public TauleMestreObject() {
		super();
		// TODO Auto-generated constructor stub
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
		final int prime = 31;
		int result = 1;
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
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
		TauleMestreObject other = (TauleMestreObject) obj;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		return true;
	}
	
}

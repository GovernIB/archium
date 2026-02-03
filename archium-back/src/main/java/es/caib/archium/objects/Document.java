package es.caib.archium.objects;

import java.io.Serializable;

public class Document<T> implements Serializable, Comparable<Document<T>> {
		 
	private long id;

    private String codi;
     
    private String nom;
     
    private String type;

	private Boolean enviatSAT;

    private T object;

	public Document(long id, String codi, String nom, String type, T object) {
		this(id, codi, nom, type, null, object);
	}

    public Document(long id, String codi, String nom, String type, Boolean enviatSAT, T object) {
		super();
		this.id = id;
		this.codi = codi;
		this.nom = nom;
		this.type = type;
		this.enviatSAT = enviatSAT;
		this.object = object;
	}

	public Document() {
		super();
		// TODO Auto-generated constructor stub
	}

	public long getId() {
		return id;
	}

	public void setId(long id) {
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

	public String getType() {
		return type;
	}
	
	public void setType(String type) {
		this.type = type;
	}

	public T getObject() {
		return object;
	}

	public void setObject(T object) {
		this.object = object;
	}

	public Boolean getEnviatSAT() {
		return enviatSAT;
	}

	public void setEnviatSAT(Boolean enviatSAT) {
		this.enviatSAT = enviatSAT;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Document [id=");
		builder.append(id);
		builder.append(", codi=");
		builder.append(codi);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", type=");
		builder.append(type);
		builder.append("]");
		return builder.toString();
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((codi == null) ? 0 : codi.hashCode());
		result = prime * result + (int) (id ^ (id >>> 32));
		result = prime * result + ((nom == null) ? 0 : nom.hashCode());
		result = prime * result + ((type == null) ? 0 : type.hashCode());
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
		Document<T> other = (Document<T>) obj;
		if (codi == null) {
			if (other.codi != null)
				return false;
		} else if (!codi.equals(other.codi))
			return false;
		if (id != other.id)
			return false;
		if (nom == null) {
			if (other.nom != null)
				return false;
		} else if (!nom.equals(other.nom))
			return false;
		if (type == null) {
			if (other.type != null)
				return false;
		} else if (!type.equals(other.type))
			return false;
		return true;
	}

	public int compareTo(Document<T> document) {
        return this.getNom().compareTo(document.getNom());
    }
	
}

package es.caib.archium.objects;

import java.util.Objects;

import es.caib.archium.persistence.model.Causalimitacio;
import es.caib.archium.persistence.model.Normativa;

public class CausaLimitacioObject {
	private Long id;
	private String codi;
	private String nom;
	private String nomcas;
	
	public CausaLimitacioObject(Long id, String codi, String nom, String nomcas) {
		super();
		this.id = id;
		this.codi = codi;
		this.nom = nom;
		this.nomcas = nomcas;
	}

	public CausaLimitacioObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public CausaLimitacioObject(Causalimitacio dbCausaLimitacio) {
		if(dbCausaLimitacio!=null) {
			this.id = dbCausaLimitacio.getId();
			this.codi = dbCausaLimitacio.getCodi();
			this.nom = dbCausaLimitacio.getNom();
			this.nomcas = dbCausaLimitacio.getNomcas();
		}
	}

	public Long getId() {
		return id;
	}

	public String getCodi() {
		return codi;
	}

	public String getNom() {
		return nom;
	}

	public String getNomcas() {
		return nomcas;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public void setCodi(String codi) {
		this.codi = codi;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public void setNomcas(String nomcas) {
		this.nomcas = nomcas;
	}
	
	public Causalimitacio toDbObject() {
		Causalimitacio db = new Causalimitacio();
		db.setId(id);
		db.setCodi(codi);
		db.setNom(nom);
		db.setNomcas(nomcas);
		return db;
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
		CausaLimitacioObject other = (CausaLimitacioObject) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("CausaLimitacioObject [id=");
		builder.append(id);
		builder.append(", codi=");
		builder.append(codi);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", nomcas=");
		builder.append(nomcas);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

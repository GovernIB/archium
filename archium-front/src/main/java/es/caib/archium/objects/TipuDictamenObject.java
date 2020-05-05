package es.caib.archium.objects;

import es.caib.archium.persistence.model.Tipusdictamen;
import es.caib.archium.persistence.model.Tipusserie;

public class TipuDictamenObject {

	private Long 	id;
	
	private String 	nom;
	private String 	nomCas;
	private String 	codi;
	
	public TipuDictamenObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public TipuDictamenObject(Long id, String nom, String nomCas, String codi) {
		super();
		this.id = id;
		this.nom = nom;
		this.nomCas = nomCas;
		this.codi = codi;
	}
	
	public TipuDictamenObject(Tipusdictamen dbTipusdictamen) {
		if(dbTipusdictamen!=null) {
			this.id = dbTipusdictamen.getId();
			this.nom = dbTipusdictamen.getNom();
			this.nomCas = dbTipusdictamen.getNomcas();
		}
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
	public String getNomCas() {
		return nomCas;
	}
	public void setNomCas(String nomCas) {
		this.nomCas = nomCas;
	}
	public String getCodi() {
		return codi;
	}
	public void setCodi(String codi) {
		this.codi = codi;
	}
	
	public Tipusdictamen toDbObject() {
		Tipusdictamen db = new Tipusdictamen();
		db.setId(id);
		db.setNom(nom);
		db.setNomcas(nomCas);
		return db;
	}
	
	@Override
	public String toString() {
		return "TipuDictamenObject [id=" + id + ", nom=" + nom + ", nomCas=" + nomCas + ", codi=" + codi + "]";
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
		TipuDictamenObject other = (TipuDictamenObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
}
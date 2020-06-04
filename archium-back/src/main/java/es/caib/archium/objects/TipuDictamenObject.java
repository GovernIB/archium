package es.caib.archium.objects;

import java.util.Objects;

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
		TipuDictamenObject other = (TipuDictamenObject) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipuDictamenObject [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", nomCas=");
		builder.append(nomCas);
		builder.append(", codi=");
		builder.append(codi);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}
package es.caib.archium.objects;

import java.util.Objects;

import es.caib.archium.persistence.model.Tipusserie;

public class TipusSerieObject {

	private Long id;
	private String nom;
	private String nomCas;
	public TipusSerieObject() {
	}
	public TipusSerieObject(Long id, String nom, String nomCas) {
		this.id = id;
		this.nom = nom;
		this.nomCas = nomCas;
	}
	
	public TipusSerieObject (Tipusserie dbTipusserie) {
		if(dbTipusserie!=null) {
			this.id = dbTipusserie.getId();
			this.nom = dbTipusserie.getNom();
			this.nomCas = dbTipusserie.getNomcas();
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
	
	public Tipusserie toDbObject() {
		Tipusserie db = new Tipusserie();
		db.setId(id);
		db.setNom(nom);
		db.setNomcas(nomCas);
		return db;
	}
	
	@Override
	public int hashCode() {
		return Objects.hash(id, nom, nomCas);
	}
	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (!(obj instanceof TipusSerieObject)) {
			return false;
		}
		TipusSerieObject other = (TipusSerieObject) obj;
		return Objects.equals(id, other.id) && Objects.equals(nom, other.nom) && Objects.equals(nomCas, other.nomCas);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipusSerieBean [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", nomCas=");
		builder.append(nomCas);
		builder.append("]");
		return builder.toString();
	}

}

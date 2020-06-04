package es.caib.archium.objects;

import java.util.Date;
import java.util.Objects;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.persistence.model.Lopd;
import es.caib.archium.persistence.model.Quadreclassificacio;

public class QuadreObject {
	
	
	private Long 	id;
	private String 	nom;
	private String 	nomCas;
	private String 	estat;
	private Date 	inici;
	private Date 	modificacio;
	private String 	versio;
	private Date 	fi;

	
	
	public QuadreObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuadreObject(Long id, String nom, String nomCas, String estat, Date inici, Date modificacio, String versio,
			Date fi) {
		super();
		this.id = id;
		this.nom = nom;
		this.nomCas = nomCas;
		this.estat = estat;
		this.inici = inici;
		this.modificacio = modificacio;
		this.versio = versio;
		this.fi = fi;
	}
	
	public QuadreObject(Quadreclassificacio dbQuadre) {
		if(dbQuadre!=null) {
			this.id = dbQuadre.getId().longValue();
			this.nom = dbQuadre.getNom();
			this.nomCas = dbQuadre.getNomcas();
			this.estat = dbQuadre.getEstat();
			this.inici = dbQuadre.getInici();
			this.modificacio = dbQuadre.getModificacio();
			this.versio = dbQuadre.getVersio();
			this.fi = dbQuadre.getFi();
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
	
	public String getEstat() {
		return estat;
	}
	
	public void setEstat(String estat) {
		this.estat = estat;
	}
	
	public Date getInici() {
		return inici;
	}
	
	public void setInici(Date inici) {
		this.inici = inici;
	}
	
	public Date getModificacio() {
		return modificacio;
	}
	
	public void setModificacio(Date modificacio) {
		this.modificacio = modificacio;
	}
	
	public String getVersio() {
		return versio;
	}
	
	public void setVersio(String versio) {
		this.versio = versio;
	}
	
	public Date getFi() {
		return fi;
	}
	
	public void setFi(Date fi) {
		this.fi = fi;
	}
	
	public Quadreclassificacio toDbObject() {
		Quadreclassificacio db = new Quadreclassificacio();
		db.setId(id);
		db.setNom(nom);
		db.setNomcas(nomCas);
		db.setEstat(estat);
		db.setInici(inici);
		db.setModificacio(modificacio);
		db.setFi(fi);
		db.setVersio(versio);
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
		QuadreObject other = (QuadreObject) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("QuadreObject [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", nomCas=");
		builder.append(nomCas);
		builder.append(", estat=");
		builder.append(estat);
		builder.append(", inici=");
		builder.append(inici);
		builder.append(", modificacio=");
		builder.append(modificacio);
		builder.append(", versio=");
		builder.append(versio);
		builder.append(", fi=");
		builder.append(fi);
		builder.append("]");
		return builder.toString();
	}
	
	
	
	
	
}

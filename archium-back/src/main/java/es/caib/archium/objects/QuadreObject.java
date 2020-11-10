package es.caib.archium.objects;

import es.caib.archium.persistence.model.Quadreclassificacio;

import java.util.Date;
import java.util.Objects;

public class QuadreObject {
	
	
	private Long 	id;
	private String 	codi;
	private String 	nom;
	private String 	nomCas;
	private String 	estat;
	private Date 	inici;
	private Date 	modificacio;
	private String 	versio;
	private Date 	fi;
	private String 	nodeId;
	private Boolean isSynchronized;

	
	
	public QuadreObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public QuadreObject(Long id,String codi, String nom, String nomCas, String estat, Date inici, Date modificacio, String versio,
			Date fi, String nodeId, Boolean isSynchronized) {
		super();
		this.id = id;
		this.codi = codi;
		this.nom = nom;
		this.nomCas = nomCas;
		this.estat = estat;
		this.inici = inici;
		this.modificacio = modificacio;
		this.versio = versio;
		this.fi = fi;
		this.nodeId = nodeId;
		this.isSynchronized = isSynchronized;
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
			this.nodeId = dbQuadre.getNodeId();
			this.isSynchronized = dbQuadre.isSynchronized();
			this.codi = dbQuadre.getCodi();
		}
	}

	public String getCodi() {
		return codi;
	}

	public void setCodi(String codi) {
		this.codi = codi;
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

	public String getNodeId() {
		return nodeId;
	}

	public void setNodeId(String nodeId) {
		this.nodeId = nodeId;
	}

	public Boolean getSynchronized() {
		return isSynchronized;
	}

	public void setSynchronized(Boolean aSynchronized) {
		isSynchronized = aSynchronized;
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
		db.setNodeId(nodeId);
		db.setSynchronized(isSynchronized);
		db.setCodi(codi);
		return db;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		QuadreObject that = (QuadreObject) o;
		return Objects.equals(id, that.id) &&
				Objects.equals(codi, that.codi) &&
				Objects.equals(nom, that.nom) &&
				Objects.equals(nomCas, that.nomCas) &&
				Objects.equals(estat, that.estat) &&
				Objects.equals(inici, that.inici) &&
				Objects.equals(modificacio, that.modificacio) &&
				Objects.equals(versio, that.versio) &&
				Objects.equals(fi, that.fi) &&
				Objects.equals(nodeId, that.nodeId) &&
				Objects.equals(isSynchronized, that.isSynchronized);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, codi, nom, nomCas, estat, inici, modificacio, versio, fi, nodeId, isSynchronized);
	}


	@Override
	public String toString() {
		return "QuadreObject{" +
				"id=" + id +
				", codi='" + codi + '\'' +
				", nom='" + nom + '\'' +
				", nomCas='" + nomCas + '\'' +
				", estat='" + estat + '\'' +
				", inici=" + inici +
				", modificacio=" + modificacio +
				", versio='" + versio + '\'' +
				", fi=" + fi +
				", nodeId='" + nodeId + '\'' +
				", isSynchronized=" + isSynchronized +
				'}';
	}
}

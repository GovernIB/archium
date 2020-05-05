package es.caib.archium.objects;


import java.util.Date;

import es.caib.archium.persistence.model.Normativa;
import es.caib.archium.persistence.model.Tipusacce;

public class NormativaAprobacioObject {

	private Long 	id;
	private String 	codi;
	private String 	nom;
	private String 	nomCas;
	private String 	uri;
	private String 	urieli;
	private String 	urieliconsolidada;
	private Date 	vigor;
	private String 	estat;
	private Date 	derogacio;
	//private Long 	descripcio;
	private NormativaAprobacioObject 	normativaVigente;
	
	
	
	public NormativaAprobacioObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NormativaAprobacioObject(Long id, String codi, String nom, String nomCas, String uri, String urieli,
			String urieliconsolidada, Date vigor, String estat, Date derogacio, Long descripcio,
			NormativaAprobacioObject normativaVigente) {
		super();
		this.id = id;
		this.codi = codi;
		this.nom = nom;
		this.nomCas = nomCas;
		this.uri = uri;
		this.urieli = urieli;
		this.urieliconsolidada = urieliconsolidada;
		this.vigor = vigor;
		this.estat = estat;
		this.derogacio = derogacio;
		//this.descripcio = descripcio;
		this.normativaVigente = normativaVigente;
	}
	
	public NormativaAprobacioObject (Normativa dbNormativa) {
		if(dbNormativa!=null) {
			this.id = dbNormativa.getId();
			this.codi = dbNormativa.getCodi();
			this.nom = dbNormativa.getNom();
			this.nomCas = dbNormativa.getNomcas();
			this.uri = dbNormativa.getUri();
			this.urieli = dbNormativa.getUrieli();
			this.urieliconsolidada = dbNormativa.getUrieliconsolidada();
			this.vigor = dbNormativa.getVigor();
			this.estat = dbNormativa.getEstat();
			this.derogacio = dbNormativa.getDerogacio();
			//this.descripcio = dbNormativa.get;
			
			if(dbNormativa.getAchNormativa()!=null) {
				NormativaAprobacioObject n = new NormativaAprobacioObject();
				n.setId(dbNormativa.getId());
				n.setCodi(dbNormativa.getCodi());
				n.setNom(dbNormativa.getNom());
				this.normativaVigente = n;
			}
			
		}
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
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
	public String getnomCas() {
		return nomCas;
	}
	public void setnomCas(String nomCas) {
		this.nomCas = nomCas;
	}
	public String getUri() {
		return uri;
	}
	public void setUri(String uri) {
		this.uri = uri;
	}
	public String getUrieli() {
		return urieli;
	}
	public void setUrieli(String urieli) {
		this.urieli = urieli;
	}
	public String getUrieliconsolidada() {
		return urieliconsolidada;
	}
	public void setUrieliconsolidada(String urieliconsolidada) {
		this.urieliconsolidada = urieliconsolidada;
	}
	public Date getVigor() {
		return vigor;
	}
	public void setVigor(Date vigor) {
		this.vigor = vigor;
	}
	public String getEstat() {
		return estat;
	}
	public void setEstat(String estat) {
		this.estat = estat;
	}
	public Date getDerogacio() {
		return derogacio;
	}
	public void setDerogacio(Date derogacio) {
		this.derogacio = derogacio;
	}
	
	/*public Long getDescripcio() {
		return descripcio;
	}
	public void setDescripcio(Long descripcio) {
		this.descripcio = descripcio;
	}*/
	
	public NormativaAprobacioObject getNormativaVigente() {
		return normativaVigente;
	}
	public void setNormativaVigente(NormativaAprobacioObject normativaVigente) {
		this.normativaVigente = normativaVigente;
	}
	
	public Normativa toDbObject(Normativa n) {
		Normativa db = new Normativa();
		db.setId(id);
		db.setCodi(codi);
		db.setNom(nom);
		db.setNomcas(nomCas);
		db.setUri(uri);
		db.setUrieli(urieli);
		db.setUrieliconsolidada(urieliconsolidada);
		db.setVigor(vigor);
		db.setEstat(estat);
		db.setDerogacio(derogacio);
		//this.descripcio = dbNormativa.get;
		db.setAchNormativa(n);
		return db;
	}
	
	@Override
	public String toString() {
		return "NormativaAprobacionObject [id=" + id + ", codi=" + codi + ", nom=" + nom + ", nomCas=" + nomCas
				+ ", uri=" + uri + ", urieli=" + urieli + ", urieliconsolidada=" + urieliconsolidada + ", vigor="
				+ vigor + ", estat=" + estat + ", derogacio=" + derogacio + "]";
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
		NormativaAprobacioObject other = (NormativaAprobacioObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}
	
	
	
}
package es.caib.archium.objects;

import java.util.Date;
import java.util.Objects;

import es.caib.archium.persistence.model.Normativa;

public class NormativaObject {
	private Long 	id;
	private String 	codi;
	private String 	nom;
	private String 	nomMostrar;
	private String 	nomCas;
	private String 	uri;
	private String 	urieli;
	private String 	urieliconsolidada;
	private Date 	vigor;
	private String 	estat;
	private Date 	derogacio;
	//private Long 	descripcio;
	private NormativaAprobacioObject 	normativaVigente;
	
	
	
	public NormativaObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public NormativaObject(Long id, String codi, String nom, String nomCas, String uri, String urieli,
			String urieliconsolidada, Date vigor, String estat, Date derogacio, Long descripcio,
			NormativaAprobacioObject normativaVigente) {
		super();
		this.id = id;
		this.codi = codi;
		this.nom = nom;
		if (this.nom != null) {
			if (this.nom.length() < 150) {
				this.setNomMostrar(this.getNom());
			}
			else {
				this.setNomMostrar(this.getNom().substring(0, 150));
			}				
		}	
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
	
	public NormativaObject (Normativa dbNormativa) {
		if(dbNormativa!=null) {
			this.id = dbNormativa.getId();
			this.codi = dbNormativa.getCodi();
			this.nom = dbNormativa.getNom();
			if (this.nom != null) {
				if (this.nom.length() < 150) {
					this.setNomMostrar(this.getNom());
				}
				else {
					this.setNomMostrar(this.getNom().substring(0, 150));
				}				
			}
			
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
	
	
	public String getNomMostrar() {
		return nomMostrar;
	}
	public void setNomMostrar(String nomMostrar) {
		this.nomMostrar = nomMostrar;
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
		NormativaObject other = (NormativaObject) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("NormativaObject [id=");
		builder.append(id);
		builder.append(", codi=");
		builder.append(codi);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", nomMostrar=");
		builder.append(nomMostrar);
		builder.append(", nomCas=");
		builder.append(nomCas);
		builder.append(", uri=");
		builder.append(uri);
		builder.append(", urieli=");
		builder.append(urieli);
		builder.append(", urieliconsolidada=");
		builder.append(urieliconsolidada);
		builder.append(", vigor=");
		builder.append(vigor);
		builder.append(", estat=");
		builder.append(estat);
		builder.append(", derogacio=");
		builder.append(derogacio);
		builder.append(", normativaVigente=");
		builder.append(normativaVigente);
		builder.append("]");
		return builder.toString();
	}
	
	
}
	
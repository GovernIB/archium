package es.caib.archium.objects;

import java.util.Date;
import java.util.Objects;

import es.caib.archium.persistence.model.Aplicacio;

public class AplicacioObject {
	private Long 	id;
	private String 	nom;
	//private Estat estat; // cambio recuperarlo de una base de datos maestra.
	private String 	estat;
	private String 	perfil;
	private String 	uriDev;
	private String 	uriPro;
	private Date 	inici;
	private Date 	fi;
	//methods
	
	public AplicacioObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public AplicacioObject(Long id, String nom, String estat, String perfil, String uriDev, String uriPro, Date inici,
			Date fi) {
		super();
		this.id = id;
		this.nom = nom;
		this.estat = estat;
		this.perfil = perfil;
		this.uriDev = uriDev;
		this.uriPro = uriPro;
		this.inici = inici;
		this.fi = fi;
	}
	
	public AplicacioObject(Aplicacio bd) {
		super();
		this.id 		= bd.getId();
		this.nom 		= bd.getNom();
		this.estat 		= bd.getEstat();
		this.perfil 	= bd.getPerfil();
		this.uriDev 	= bd.getUridev();
		this.uriPro 	= bd.getUripro();
		this.inici 		= bd.getInici();
		this.fi 		= bd.getFi();
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

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public String getPerfil() {
		return perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getUriDev() {
		return uriDev;
	}

	public void setUriDev(String uriDev) {
		this.uriDev = uriDev;
	}

	public String getUriPro() {
		return uriPro;
	}

	public void setUriPro(String uriPro) {
		this.uriPro = uriPro;
	}

	public Date getInici() {
		return inici;
	}

	public void setInici(Date inici) {
		this.inici = inici;
	}

	public Date getFi() {
		return fi;
	}

	public void setFi(Date fi) {
		this.fi = fi;
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
		AplicacioObject other = (AplicacioObject) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("AplicacioObject [id=");
		builder.append(id);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", estat=");
		builder.append(estat);
		builder.append(", perfil=");
		builder.append(perfil);
		builder.append(", uriDev=");
		builder.append(uriDev);
		builder.append(", uriPro=");
		builder.append(uriPro);
		builder.append(", inici=");
		builder.append(inici);
		builder.append(", fi=");
		builder.append(fi);
		builder.append("]");
		return builder.toString();
	}

	


}

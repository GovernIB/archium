package es.caib.archium.objects;

import es.caib.archium.persistence.model.TipusDocumental;

import java.util.Date;
import java.util.Objects;

public class TipuDocumentalObject {

	private Long 			id;
	private String 			codi;
	private String 			nom;
	private String 			nomCas;
	private QuadreDocumentalObject 	quadreTipusDocumental;
	private TipusntiObject 	tipusnti;
	private String 			definicio;
	private String 			definicioCas;
	private String 			observacions;
	private Date 			inici;
	private Date 			modificacion;
	private String 			estat;
	private Date 			fi;
	
	

	public TipuDocumentalObject() {
		super();
	}

	public TipuDocumentalObject(TipusDocumental i) {
		this.id							= i.getId();
		this.codi						= i.getCodi();
		this.nom						= i.getNom();
		this.nomCas						= i.getNomcas();
		this.quadreTipusDocumental		= new QuadreDocumentalObject(i.getQuadreTipusDocumental());
		this.tipusnti					= new TipusntiObject(i.getTipusNti());
	  	this.definicio					= i.getDefinicio();
		this.definicioCas				= i.getDefiniciocas();
		//this.observacions				= i.getObservacions();
		this.inici						= i.getInici();
		this.modificacion				= i.getModificacio();
		this.estat						= i.getEstat();
		this.fi							= i.getFi();
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

	public String getNomCas() {
		return nomCas;
	}

	public void setNomCas(String nomCas) {
		this.nomCas = nomCas;
	}

	public QuadreDocumentalObject getQuadreTipusDocumental() {
		return quadreTipusDocumental;
	}

	public void setQuadreTipusDocumental(QuadreDocumentalObject quadreTipusDocumental) {
		this.quadreTipusDocumental = quadreTipusDocumental;
	}

	public TipusntiObject getTipusnti() {
		return tipusnti;
	}

	public void setTipusnti(TipusntiObject tipusnti) {
		this.tipusnti = tipusnti;
	}

	public String getDefinicio() {
		return definicio;
	}

	public void setDefinicio(String definicio) {
		this.definicio = definicio;
	}

	public String getDefinicioCas() {
		return definicioCas;
	}

	public void setDefinicioCas(String definicioCas) {
		this.definicioCas = definicioCas;
	}

	public String getObservacions() {
		return observacions;
	}

	public void setObservacions(String observacions) {
		this.observacions = observacions;
	}

	public Date getInici() {
		return inici;
	}

	public void setInici(Date inici) {
		this.inici = inici;
	}

	public Date getModificacion() {
		return modificacion;
	}

	public void setModificacion(Date modificacion) {
		this.modificacion = modificacion;
	}

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
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
		TipuDocumentalObject other = (TipuDocumentalObject) obj;
		return Objects.equals(id, other.id);
	}

	/**
	 * Convierte este DTO a entidad
	 * @return Entidad TipusDocumental
	 */
	public TipusDocumental toDbObject() {
		TipusDocumental entity = new TipusDocumental();
		entity.setId(this.id);
		entity.setCodi(this.codi);
		entity.setNom(this.nom);
		entity.setNomcas(this.nomCas);
		entity.setDefinicio(this.definicio);
		entity.setDefiniciocas(this.definicioCas);
		//entity.setObservacions(this.observacions);
		entity.setEstat(this.estat);
		entity.setInici(this.inici);
		entity.setModificacio(this.modificacion);
		entity.setFi(this.fi);

		// Relaciones
		if (this.quadreTipusDocumental != null) {
			entity.setQuadreTipusDocumental(this.quadreTipusDocumental.toQuadreTipusDocumentalDbObject());
		}
		if (this.tipusnti != null) {
			//entity.setTipusNti(this.tipusnti.toDbObject());
		}

		return entity;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipuDocumentalObject [id=");
		builder.append(id);
		builder.append(", codi=");
		builder.append(codi);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", nomCas=");
		builder.append(nomCas);
		builder.append(", quadreTipusDocumental=");
		builder.append(quadreTipusDocumental);
		builder.append(", tipusnti=");
		builder.append(tipusnti);
		builder.append(", definicio=");
		builder.append(definicio);
		builder.append(", definicioCas=");
		builder.append(definicioCas);
		builder.append(", observacions=");
		builder.append(observacions);
		builder.append(", inici=");
		builder.append(inici);
		builder.append(", modificacion=");
		builder.append(modificacion);
		builder.append(", estat=");
		builder.append(estat);
		builder.append(", fi=");
		builder.append(fi);
		builder.append("]");
		return builder.toString();
	}



}

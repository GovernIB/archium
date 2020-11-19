package es.caib.archium.objects;

import java.util.Date;
import java.util.Objects;

import es.caib.archium.persistence.model.Tipusdocumental;

public class TipuDocumentalObject {

	private Long 			id;
	private String 			codi;
	private String 			nom;
	private String 			nomCas;
	private QuadreDocumentalObject 	quadreTipusDocumental;
	private TipusntiObject 	tipusnti;
	private String 			definicio;
	private String 			definicioCas;
	private Date 			inici;
	private Date 			modificacion;
	private String 			estat;
	private Date 			fi;
	
	

	public TipuDocumentalObject() {
		super();
	}

	public TipuDocumentalObject(Tipusdocumental i) {
		this.id							= i.getId();
		this.codi						= i.getCodi();
		this.nom						= i.getNom();
		this.nomCas						= i.getNomcas();
		this.quadreTipusDocumental		= new QuadreDocumentalObject(i.getAchQuadretipusdocumental());
		this.tipusnti					= new TipusntiObject(i.getAchTipusnti());
	  	this.definicio					= i.getDefinicio();
		this.definicioCas				= i.getDefiniciocas() ;
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

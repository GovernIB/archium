package es.caib.archium.objects;

import java.math.BigDecimal;
import java.util.Date;
import java.util.Objects;
import java.util.ResourceBundle;

import es.caib.archium.persistence.model.*;

public class DictamenObject {
	private Long 	id;
	private SerieDocumentalObject 				serieDocumental;
	private TipuDictamenObject 			tipusdictamen;
	private NormativaAprobacioObject 	normativaAprovacio;
	private TipuAccesObject 			tipusAcces;
	private EnsObject 					ens;
	private LopdObject				 	lopd;
	
	private Boolean	serieEsencial;
	
	private String 	termini;
	private String 	condicioReutilitzacio;
	private String 	destinatarisRestrigits;
	private String 	accioDictaminada;   
    private String 	codi;
    private String 	estat;
	
	private Date 	inici;
	private Date 	aprovacio;
	private Date 	fi;
	
	ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
	
	public DictamenObject() {
		super();
		// TODO Auto-generated constructor stub
	}
	public DictamenObject(Long id, SerieDocumentalObject serieDocumental, TipuDictamenObject tipusdictamen,
			NormativaAprobacioObject normativaAprovacio, TipuAccesObject tipusAcces, EnsObject ens, LopdObject lopd,
			Boolean serieEsencial, String termini, String condicioReutilitzacio, String destinatarisRestrigits,
			String accioDictaminada, String codi, String estat, Date inici, Date aprovacio, Date fi) {
		super();
		this.id = id;
		this.serieDocumental = serieDocumental;
		this.tipusdictamen = tipusdictamen;
		this.normativaAprovacio = normativaAprovacio;
		this.tipusAcces = tipusAcces;
		this.ens = ens;
		this.lopd = lopd;
		this.serieEsencial = serieEsencial;
		this.termini = termini;
		this.condicioReutilitzacio = condicioReutilitzacio;
		this.destinatarisRestrigits = destinatarisRestrigits;
		this.accioDictaminada = accioDictaminada;
		this.estat = estat;
		this.codi = codi;
		this.inici = inici;
		this.aprovacio = aprovacio;
		this.fi = fi;
	}
	
	public DictamenObject(Dictamen dbDictamen) {
		
		if(dbDictamen!=null) {
			this.id = dbDictamen.getId();
			this.serieDocumental = new SerieDocumentalObject(dbDictamen.getAchSeriedocumental());
			this.tipusdictamen = new TipuDictamenObject(dbDictamen.getAchTipusdictamen());
			this.normativaAprovacio = new NormativaAprobacioObject(dbDictamen.getAchNormativa());
			this.tipusAcces = new TipuAccesObject(dbDictamen.getAchTipusacce());
			this.ens = new EnsObject(dbDictamen.getAchEn());
			this.lopd = new LopdObject(dbDictamen.getAchLopd());
			this.termini = dbDictamen.getTermini();
			this.condicioReutilitzacio = dbDictamen.getCondicioreutilitzacio();
			this.destinatarisRestrigits = dbDictamen.getDestinatarisrestringits();
			this.accioDictaminada = dbDictamen.getAcciodictaminada();
			this.inici = dbDictamen.getInici();
			this.aprovacio = dbDictamen.getAprovacio();
			this.fi = dbDictamen.getFi();
			this.codi = dbDictamen.getCodi();
			this.estat = dbDictamen.getEstat();
			
			if(dbDictamen.getSerieessencial()!=null) {
				this.serieEsencial = (dbDictamen.getSerieessencial().signum() == 1 ? true : false);
			} else {
				this.serieEsencial = null;
			}
			
		}
	}
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public SerieDocumentalObject getSerieDocumental() {
		return serieDocumental;
	}
	public void setSerieDocumental(SerieDocumentalObject serieDocumental) {
		this.serieDocumental = serieDocumental;
	}
	public TipuDictamenObject getTipusdictamen() {
		return tipusdictamen;
	}
	
	public void setTipusdictamen(TipuDictamenObject tipusdictamen) {
		this.tipusdictamen = tipusdictamen;
	}
	public NormativaAprobacioObject getNormativaAprovacio() {
		return normativaAprovacio;
	}
	public void setNormativaAprovacio(NormativaAprobacioObject normativaAprovacio) {
		this.normativaAprovacio = normativaAprovacio;
	}
	public TipuAccesObject getTipusAcces() {
		return tipusAcces;
	}
	public void setTipusAcces(TipuAccesObject tipusAcces) {
		this.tipusAcces = tipusAcces;
	}
	public EnsObject getEns() {
		return ens;
	}
	public void setEns(EnsObject ens) {
		this.ens = ens;
	}
	public LopdObject getLopd() {
		return lopd;
	}
	public void setLopd(LopdObject lopd) {
		this.lopd = lopd;
	}
	public Boolean getSerieEsencial() {
		return serieEsencial;
	}
	public void setSerieEsencial(Boolean serieEsencial) {
		this.serieEsencial = serieEsencial;
	}
	public String getTermini() {
		return termini;
	}
	public void setTermini(String termini) {
		this.termini = termini;
	}
	public String getCondicioReutilitzacio() {
		return condicioReutilitzacio;
	}
	public void setCondicioReutilitzacio(String condicioReutilitzacio) {
		this.condicioReutilitzacio = condicioReutilitzacio;
	}
	public String getDestinatarisRestrigits() {
		return destinatarisRestrigits;
	}
	public void setDestinatarisRestrigits(String destinatarisRestrigits) {
		this.destinatarisRestrigits = destinatarisRestrigits;
	}
	public String getAccioDictaminada() {
		return accioDictaminada;
	}
	public void setAccioDictaminada(String accioDictaminada) {
		this.accioDictaminada = accioDictaminada;
	}
	public Date getInici() {
		return inici;
	}
	public void setInici(Date inici) {
		this.inici = inici;
	}
	public Date getAprovacio() {
		return aprovacio;
	}
	public void setAprovacio(Date aprovacio) {
		this.aprovacio = aprovacio;
	}
	public Date getFi() {
		return fi;
	}
	public void setFi(Date fi) {
		this.fi = fi;
	}
	public String getEstat() {
		return estat;
	}
	public void setEstat(String estat) {
		this.estat = estat;
	}
	public String getCodi() {
		return codi;
	}
	public void setCodi(String codi) {
		this.codi = codi;
	}
	
	public String getTerminiFormat() {
		
		if (this.termini != null) {
			
			String formatTermini = "";
			String valueTermini	= this.termini.substring(0,this.termini.length() - 1);
			String a 	= this.termini.substring(this.termini.length() - 1, this.termini.length());
			if (a.equals("H"))
				formatTermini = messageBundle.getString("general.plazos.hores");
			if (a.equals("D"))
				formatTermini = messageBundle.getString("general.plazos.dies");
			if (a.equals("S"))
				formatTermini = messageBundle.getString("general.plazos.setmanes");
			if (a.equals("M"))
				formatTermini = messageBundle.getString("general.plazos.mesos");
			if (a.equals("A"))
				formatTermini = messageBundle.getString("general.plazos.anys");
			
			return valueTermini + " " + formatTermini;
			
		}
		
		return "";
	}
	
	public Dictamen toDbObject(Tipusdictamen td, Ens e, Lopd l, Normativa n, Seriedocumental s, Tipusacce t){
	
		Dictamen db = new Dictamen();
		if (id != null) {
			db.setId(id);
		}
		db.setAchTipusdictamen(td); 	
		db.setAchEn(e);						 			
		db.setAchLopd(l);								
		db.setAchNormativa(n);
		db.setAchSeriedocumental(s);
		db.setAchTipusacce(t);
		db.setAcciodictaminada(accioDictaminada);
		db.setAprovacio(aprovacio);
		db.setCondicioreutilitzacio(condicioReutilitzacio);
		db.setDestinatarisrestringits(destinatarisRestrigits);
		db.setFi(fi);
		db.setInici(inici);
		db.setTermini(termini);
		db.setCodi(codi);
		db.setEstat(estat);
		
		if(serieEsencial!=null) {
			db.setSerieessencial((serieEsencial ? new BigDecimal(1) : new BigDecimal(0)));
		}
		
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
		DictamenObject other = (DictamenObject) obj;
		return Objects.equals(id, other.id);
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("DictamenObject [id=");
		builder.append(id);
		builder.append(", serieDocumental=");
		builder.append(serieDocumental);
		builder.append(", tipusdictamen=");
		builder.append(tipusdictamen);
		builder.append(", normativaAprovacio=");
		builder.append(normativaAprovacio);
		builder.append(", tipusAcces=");
		builder.append(tipusAcces);
		builder.append(", ens=");
		builder.append(ens);
		builder.append(", lopd=");
		builder.append(lopd);
		builder.append(", serieEsencial=");
		builder.append(serieEsencial);
		builder.append(", termini=");
		builder.append(termini);
		builder.append(", condicioReutilitzacio=");
		builder.append(condicioReutilitzacio);
		builder.append(", destinatarisRestrigits=");
		builder.append(destinatarisRestrigits);
		builder.append(", accioDictaminada=");
		builder.append(accioDictaminada);
		builder.append(", codi=");
		builder.append(codi);
		builder.append(", estat=");
		builder.append(estat);
		builder.append(", inici=");
		builder.append(inici);
		builder.append(", aprovacio=");
		builder.append(aprovacio);
		builder.append(", fi=");
		builder.append(fi);
		builder.append("]");
		return builder.toString();
	}
	
	
}
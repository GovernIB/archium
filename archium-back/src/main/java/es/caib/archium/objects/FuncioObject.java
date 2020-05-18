package es.caib.archium.objects;

import java.math.BigDecimal;
import java.util.Date;

import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.Quadreclassificacio;
import es.caib.archium.persistence.model.Tipusserie;

public class FuncioObject {

	private Long id;
	private String codi;
	private String nom;
	private String nomcas;
	private String estat;
	private BigDecimal ordre;
	private Date inici;
	private Date modificacio;
	private Date fi;
	private TipusSerieObject tipoSerie;
	private QuadreObject codigoCuadro;
	private FuncioObject funcioPare;
	
	public FuncioObject(Long id, String codi, String nom, String nomcas, String estat, BigDecimal ordre, Date inici,
			Date modificacio, Date fi, TipusSerieObject tipoSerie, QuadreObject codigoCuadro,
			FuncioObject funcioPare) {
		super();
		this.id = id;
		this.codi = codi;
		this.nom = nom;
		this.nomcas = nomcas;
		this.estat = estat;
		this.ordre = ordre;
		this.inici = inici;
		this.modificacio = modificacio;
		this.fi = fi;
		this.tipoSerie = tipoSerie;
		this.codigoCuadro = codigoCuadro;
		this.funcioPare = funcioPare;
	}

	public FuncioObject() {
		super();
	}
	
	public FuncioObject (Funcio dbFuncio) {
		
		if(dbFuncio!=null) {
			
			this.id = dbFuncio.getId();
			this.codi = dbFuncio.getCodi();
			this.nom = dbFuncio.getNom();
			this.nomcas = dbFuncio.getNomcas();
			this.estat = dbFuncio.getEstat();
			this.ordre = dbFuncio.getOrdre();
			this.inici = dbFuncio.getInici();
			this.modificacio = dbFuncio.getModificacio();
			this.fi = dbFuncio.getFi();
			this.tipoSerie = new TipusSerieObject(dbFuncio.getAchTipusserie());
			this.codigoCuadro = new QuadreObject(dbFuncio.getAchQuadreclassificacio());
			
			
			if(dbFuncio.getAchFuncio()!=null) {
				FuncioObject fp = new FuncioObject();
				fp.setId(dbFuncio.getAchFuncio().getId());
				fp.setCodi(dbFuncio.getAchFuncio().getCodi());
				fp.setNom(dbFuncio.getAchFuncio().getNom());
				this.funcioPare = fp;
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

	public String getNomcas() {
		return nomcas;
	}

	public void setNomcas(String nomcas) {
		this.nomcas = nomcas;
	}

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public BigDecimal getOrdre() {
		return ordre;
	}

	public void setOrdre(BigDecimal ordre) {
		this.ordre = ordre;
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

	public Date getFi() {
		return fi;
	}

	public void setFi(Date fi) {
		this.fi = fi;
	}

	public TipusSerieObject getTipoSerie() {
		return tipoSerie;
	}

	public void setTipoSerie(TipusSerieObject tipoSerie) {
		this.tipoSerie = tipoSerie;
	}

	public QuadreObject getCodigoCuadro() {
		return codigoCuadro;
	}

	public void setCodigoCuadro(QuadreObject codigoCuadro) {
		this.codigoCuadro = codigoCuadro;
	}

	public FuncioObject getFuncioPare() {
		return funcioPare;
	}

	public void setFuncioPare(FuncioObject funcioPare) {
		this.funcioPare = funcioPare;
	}

	public Funcio toDbObject(Quadreclassificacio q, Funcio f, Tipusserie ts){
		Funcio db = new Funcio();
		db.setId(id);
		db.setCodi(codi);
		db.setNom(nom);
		db.setNomcas(nomcas);
		db.setEstat(estat);
		db.setOrdre(ordre);
		db.setInici(inici);
		db.setModificacio(modificacio);
		db.setFi(fi);
		db.setAchFuncio(f);
		db.setAchQuadreclassificacio(q);
		db.setAchTipusserie(ts);
		return db;
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
		FuncioObject other = (FuncioObject) obj;
		if (id == null) {
			if (other.id != null)
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("FuncioObject [id=");
		builder.append(id);
		builder.append(", codi=");
		builder.append(codi);
		builder.append(", nom=");
		builder.append(nom);
		builder.append(", nomcas=");
		builder.append(nomcas);
		builder.append(", estat=");
		builder.append(estat);
		builder.append(", ordre=");
		builder.append(ordre);
		builder.append(", inici=");
		builder.append(inici);
		builder.append(", modificacio=");
		builder.append(modificacio);
		builder.append(", fi=");
		builder.append(fi);
		builder.append(", tipoSerie=");
		builder.append(tipoSerie);
		builder.append(", codigoCuadro=");
		builder.append(codigoCuadro);
		builder.append(", funcioPare=");
		builder.append(funcioPare);
		builder.append("]");
		return builder.toString();
	}
	
	
	
}

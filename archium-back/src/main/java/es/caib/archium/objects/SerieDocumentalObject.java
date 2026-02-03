package es.caib.archium.objects;

import es.caib.archium.persistence.model.Catalegsery;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.TipusSerie;

import java.util.Objects;

public class SerieDocumentalObject {
	private Long serieId;
	private String codi;
	private String nom;
	private String nomCas;
	private Long catalegSeriId;

	private FuncioObject funcio;
	private String descripcio;
	private String descripcioCas;
	private String resumMigracio;

	private Boolean enviatSAT;

	private String dir3Promotor;
	private String estat;
	private Long tipusSerieId;
	private String codiIecisa;
	
	public SerieDocumentalObject() {
	}

	public SerieDocumentalObject(Seriedocumental dbSerie) {
		
		if(dbSerie!=null) {
			this.serieId = dbSerie.getId();
			this.codi = dbSerie.getCodi();
			this.nom = dbSerie.getNom();
			this.nomCas = dbSerie.getNomcas();
			this.catalegSeriId = dbSerie.getAchCatalegsery().getId();
			this.descripcio = dbSerie.getDescripcio();
			this.descripcioCas = dbSerie.getDescripciocas();
			this.resumMigracio = dbSerie.getResummigracio();
			this.enviatSAT = dbSerie.getEnviatSAT();
			this.dir3Promotor = dbSerie.getDir3Promotor();
			if(dbSerie.getAchTipusserie()!=null) {
				this.tipusSerieId = dbSerie.getAchTipusserie().getId();
			}
			this.codiIecisa = dbSerie.getCodiiecisa();
			this.funcio = new FuncioObject(dbSerie.getAchFuncio());
			this.estat = dbSerie.getEstat();
		}
			
	}

	public Long getSerieId() {
		return serieId;
	}

	public void setSerieId(Long serieId) {
		this.serieId = serieId;
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

	public Long getCatalegSeriId() {
		return catalegSeriId;
	}

	public void setCatalegSeriId(Long catalegSeriId) {
		this.catalegSeriId = catalegSeriId;
	}

	public FuncioObject getFuncio() {
		return funcio;
	}

	public void setFuncio(FuncioObject funcio) {
		this.funcio = funcio;
	}

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getDescripcioCas() {
		return descripcioCas;
	}

	public void setDescripcioCas(String descripcioCas) {
		this.descripcioCas = descripcioCas;
	}

	public String getResumMigracio() {
		return resumMigracio;
	}

	public void setResumMigracio(String resumMigracio) {
		this.resumMigracio = resumMigracio;
	}

	public String getDir3Promotor() {
		return dir3Promotor;
	}

	public void setDir3Promotor(String dir3Promotor) {
		this.dir3Promotor = dir3Promotor;
	}

	public Long getTipusSerieId() {
		return tipusSerieId;
	}

	public void setTipusSerieId(Long tipusSerieId) {
		this.tipusSerieId = tipusSerieId;
	}

	public String getCodiIecisa() {
		return codiIecisa;
	}

	public void setCodiIecisa(String codiIecisa) {
		this.codiIecisa = codiIecisa;
	}
	
	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public Boolean getEnviatSAT() {
		return enviatSAT;
	}

	public void setEnviatSAT(Boolean enviatSAT) {
		this.enviatSAT = enviatSAT;
	}

	public String getNomComplet() {
		return codi + " - " + nom;
	}

	public Seriedocumental toDbObject(TipusSerie ts, Catalegsery cs, Funcio f) {
		Seriedocumental db = new Seriedocumental();
		db.setId(serieId);
		db.setCodi(codi);
		db.setAchTipusserie(ts);
		db.setAchFuncio(f);
		db.setAchCatalegsery(cs);
		db.setDescripcio(descripcio);
		db.setDescripciocas(descripcioCas);
		db.setResummigracio(resumMigracio);
		db.setEnviatSAT(enviatSAT);
		db.setDir3Promotor(dir3Promotor);
		db.setCodiiecisa(codiIecisa);
		db.setNom(nom);
		db.setNomcas(nomCas);
		db.setEstat(estat);
		return db;
	}

	

	@Override
	public int hashCode() {
		return Objects.hash(serieId);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		SerieDocumentalObject other = (SerieDocumentalObject) obj;
		return Objects.equals(serieId, other.serieId);
	}

	@Override
	public String toString() {
		return "SerieDocumentalObject [serieId=" +
				serieId +
				", codi=" +
				codi +
				", nom=" +
				nom +
				", nomCas=" +
				nomCas +
				", catalegSeriId=" +
				catalegSeriId +
				", funcio=" +
				funcio +
				", descripcio=" +
				descripcio +
				", descripcioCas=" +
				descripcioCas +
				", resumMigracio=" +
				resumMigracio +
				", enviatSAT=" +
				enviatSAT +
				", dir3Promotor=" +
				dir3Promotor +
				", estat=" +
				estat +
				", tipusSerieId=" +
				tipusSerieId +
				", codiIecisa=" +
				codiIecisa +
				"]";
	}

}

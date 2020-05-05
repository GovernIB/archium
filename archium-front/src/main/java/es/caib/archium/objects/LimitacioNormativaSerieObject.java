package es.caib.archium.objects;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import es.caib.archium.persistence.model.Causalimitacio;
import es.caib.archium.persistence.model.LimitacioNormativaSerie;
import es.caib.archium.persistence.model.LimitacioNormativaSeriePK;
import es.caib.archium.persistence.model.Normativa;
import es.caib.archium.persistence.model.Seriedocumental;

public class LimitacioNormativaSerieObject {
	
	private Integer id;
	private SerieDocumentalObject seriedocumental;
	private NormativaAprobacioObject normativa;
	private CausaLimitacioObject causaLimitacio;
	private Date fi;
	private Date inici;
	
	public LimitacioNormativaSerieObject(SerieDocumentalObject seriedocumental, NormativaAprobacioObject normativa, CausaLimitacioObject causalimitacio, Date fi,
			Date inici) {
		super();
		this.seriedocumental = seriedocumental;
		this.normativa = normativa;
		this.causaLimitacio = causalimitacio;
		this.fi = fi;
		this.inici = inici;
	}

	public LimitacioNormativaSerieObject() {
		super();
	}	
	
	public LimitacioNormativaSerieObject(LimitacioNormativaSerie lnsDb) {
		this.seriedocumental = new SerieDocumentalObject(lnsDb.getAchSeriedocumental());
		this.normativa = new NormativaAprobacioObject(lnsDb.getAchNormativa());
		this.causaLimitacio = new CausaLimitacioObject(lnsDb.getAchCausalimitacio());
		this.fi = lnsDb.getFi();
		this.inici = lnsDb.getInici();
	}	
	
	

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public SerieDocumentalObject getSeriedocumental() {
		return seriedocumental;
	}

	public void setSeriedocumental(SerieDocumentalObject seriedocumental) {
		this.seriedocumental = seriedocumental;
	}

	public NormativaAprobacioObject getNormativa() {
		return normativa;
	}

	public void setNormativa(NormativaAprobacioObject normativa) {
		this.normativa = normativa;
	}

	public CausaLimitacioObject getCausaLimitacio() {
		return causaLimitacio;
	}

	public void setCausaLimitacio(CausaLimitacioObject causaLimitacio) {
		this.causaLimitacio = causaLimitacio;
	}

	public Date getFi() {
		return fi;
	}

	public void setFi(Date fi) {
		this.fi = fi;
	}

	public Date getInici() {
		return inici;
	}

	public void setInici(Date inici) {
		this.inici = inici;
	}
	
	public LimitacioNormativaSerie toDbObject() {
		LimitacioNormativaSeriePK dbPK = new LimitacioNormativaSeriePK();
		LimitacioNormativaSerie db = new LimitacioNormativaSerie();
		if(causaLimitacio!=null) {
			dbPK.setCausalimitacioId(causaLimitacio.getId());
		}
		
		if(normativa!=null) {
			dbPK.setNormativaId(normativa.getId());
		}
		
		if(seriedocumental!=null) {
			dbPK.setSeriedocumentalId(seriedocumental.getSerieId());
		}
		db.setId(dbPK);
		db.setFi(fi);
		db.setInici(inici);
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
		LimitacioNormativaSerieObject other = (LimitacioNormativaSerieObject) obj;
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
		builder.append("LimitacioNormativaSerieObject [id=");
		builder.append(id);
		builder.append(", seriedocumental=");
		builder.append(seriedocumental);
		builder.append(", normativa=");
		builder.append(normativa);
		builder.append(", causaLimitacio=");
		builder.append(causaLimitacio);
		builder.append(", fi=");
		builder.append(fi);
		builder.append(", inici=");
		builder.append(inici);
		builder.append("]");
		return builder.toString();
	}

	/*@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((causaLimitacio == null) ? 0 : causaLimitacio.hashCode());
		result = prime * result + ((normativa == null) ? 0 : normativa.hashCode());
		result = prime * result + ((seriedocumental == null) ? 0 : seriedocumental.hashCode());
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
		LimitacioNormativaSerieObject other = (LimitacioNormativaSerieObject) obj;
		if (causaLimitacio == null) {
			if (other.causaLimitacio != null)
				return false;
		} else if (!causaLimitacio.equals(other.causaLimitacio))
			return false;
		if (normativa == null) {
			if (other.normativa != null)
				return false;
		} else if (!normativa.equals(other.normativa))
			return false;
		if (seriedocumental == null) {
			if (other.seriedocumental != null)
				return false;
		} else if (!seriedocumental.equals(other.seriedocumental))
			return false;
		return true;
	}*/

	

	
	
	
	
}

package es.caib.archium.objects;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import org.primefaces.model.DualListModel;

import es.caib.archium.persistence.model.LimitacioNormativaSerie;
import es.caib.archium.persistence.model.LimitacioNormativaSeriePK;

public class LimitacioNormativaSerieObject {
	
	//private Integer id;
	private SerieDocumentalObject seriedocumental;
	private NormativaAprobacioObject normativa;
	private List<CausaLimitacioObject> listCausaLimitacio;
	private DualListModel<CausaLimitacioObject> dualListCausas;
	
	public LimitacioNormativaSerieObject(SerieDocumentalObject seriedocumental, NormativaAprobacioObject normativa, List<CausaLimitacioObject> listCausaLimitacio) {
		super();
		this.seriedocumental = seriedocumental;
		this.normativa = normativa;
		this.listCausaLimitacio = listCausaLimitacio;
	}

	public LimitacioNormativaSerieObject() {
		super();
		dualListCausas = new DualListModel<CausaLimitacioObject>();
	}	
	
	public LimitacioNormativaSerieObject(LimitacioNormativaSerie lnsDb) {
		this.seriedocumental = new SerieDocumentalObject(lnsDb.getAchSeriedocumental());
		this.normativa = new NormativaAprobacioObject(lnsDb.getAchNormativa());
	}	
	
	

	public DualListModel<CausaLimitacioObject> getDualListCausas() {
		return dualListCausas;
	}

	public void setDualListCausas(DualListModel<CausaLimitacioObject> dualListCausas) {
		this.dualListCausas = dualListCausas;
	}

	/*public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}*/

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

	
	
	public List<CausaLimitacioObject> getListCausaLimitacio() {
		return listCausaLimitacio;
	}

	public void setListCausaLimitacio(List<CausaLimitacioObject> listCausaLimitacio) {
		this.listCausaLimitacio = listCausaLimitacio;
	}
	
	public void addCausaLimitacio(CausaLimitacioObject causaLimitacio) {
		if(this.listCausaLimitacio==null) this.listCausaLimitacio = new ArrayList<CausaLimitacioObject>();
		this.listCausaLimitacio.add(causaLimitacio);
	}
	
	

	public LimitacioNormativaSerie toDbObject() {
		LimitacioNormativaSeriePK dbPK = new LimitacioNormativaSeriePK();
		LimitacioNormativaSerie db = new LimitacioNormativaSerie();
		
		if(normativa!=null) {
			dbPK.setNormativaId(normativa.getId());
		}
		
		if(seriedocumental!=null) {
			dbPK.setSeriedocumentalId(seriedocumental.getSerieId());
		}
		db.setId(dbPK);
		return db;
	}
	
	

	@Override
	public int hashCode() {
		return Objects.hash(normativa);
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
		return Objects.equals(normativa, other.normativa);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("LimitacioNormativaSerieObject [id=");
		//builder.append(id);
		builder.append(", seriedocumental=");
		builder.append(seriedocumental);
		builder.append(", normativa=");
		builder.append(normativa);
		builder.append(", listCausaLimitacio=");
		builder.append(listCausaLimitacio);
		builder.append("]");
		return builder.toString();
	}

	
	


	

	
	
	
	
}

package es.caib.archium.objects;

import es.caib.archium.persistence.model.ProcedimentSerie;
import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.TipusdocumentProcedimentPK;

import java.util.Objects;

public class ProcedimentSerieObject {

	private ProcedimentObject 		procediment;
	private SerieDocumentalObject	serieDocumental;

	public ProcedimentSerieObject() {
		super();
	}


	public ProcedimentSerieObject(ProcedimentSerie prcSerie) {
		
		ProcedimentObject prod = new ProcedimentObject(prcSerie.getAchProcediment());
		this.procediment 		= 	prod;
		
		SerieDocumentalObject sd = new SerieDocumentalObject(prcSerie.getAchSerieDocumental());
		this.serieDocumental = sd;
	}

	public TipusdocumentProcediment toDbObject() {
		TipusdocumentProcediment  db = new TipusdocumentProcediment();
		TipusdocumentProcedimentPK pk = new TipusdocumentProcedimentPK();
		if(procediment!=null) {
			pk.setProcedimentId(procediment.getId());
		}
		
		if(serieDocumental != null) {
			pk.setTipusdocumentalId(serieDocumental.getSerieId());
		}

		return db;
	}
	
	public ProcedimentObject getProcediment() {
		return procediment;
	}

	public void setProcediment(ProcedimentObject procediment) {
		this.procediment = procediment;
	}

	public SerieDocumentalObject getSerieDocumental() {
		return serieDocumental;
	}

	public void setSerieDocumental(SerieDocumentalObject serieDocumental) {
		this.serieDocumental = serieDocumental;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		ProcedimentSerieObject that = (ProcedimentSerieObject) o;
		return Objects.equals(procediment, that.procediment) && Objects.equals(serieDocumental, that.serieDocumental);
	}

	@Override
	public int hashCode() {
		return Objects.hash(procediment, serieDocumental);
	}

	@Override
	public String toString() {
		return "TipuDocumentalProcedimentObject{" +
				", procediment=" + procediment +
				", serieDocumental=" + serieDocumental +
				'}';
	}
}

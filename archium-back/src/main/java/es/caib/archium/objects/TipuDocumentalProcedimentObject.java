package es.caib.archium.objects;

import java.math.BigDecimal;
import java.util.Objects;

import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.TipusdocumentProcedimentPK;

public class TipuDocumentalProcedimentObject {

	private Integer id;
	private ProcedimentObject 		procediment;
	private TipuDocumentalObject	tipusDocumental;
	private Boolean 				obligatori;
	private Boolean 				recapitulatiu;
	
	public TipuDocumentalProcedimentObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	public TipuDocumentalProcedimentObject(ProcedimentObject procediment, TipuDocumentalObject tipusDocumentalt, Boolean obligatori,
			Boolean recapitulatiu) {
		super();
		this.procediment = procediment;
		this.tipusDocumental = tipusDocumentalt;
		this.obligatori = obligatori;
		this.recapitulatiu = recapitulatiu;
	}
	
	public TipuDocumentalProcedimentObject(TipusdocumentProcediment i) {
		
		ProcedimentObject prod = new ProcedimentObject();
		prod.setId(i.getAchProcediment().getId());
		prod.setNom(i.getAchProcediment().getNom());
		this.procediment 		= 	prod;
		
		TipuDocumentalObject td = new TipuDocumentalObject();
		td.setId(i.getAchTipusdocumental().getId());
		td.setNom(i.getAchTipusdocumental().getNom());
		this.tipusDocumental = td;
		
		this.obligatori = (i.getObligatori().intValue()==1 ? true : false);
		this.recapitulatiu = (i.getRecapitulatiu().intValue()==1 ? true : false);
	}

	public TipusdocumentProcediment toDbObject() {
		TipusdocumentProcediment  db = new TipusdocumentProcediment();
		TipusdocumentProcedimentPK pk = new TipusdocumentProcedimentPK();
		if(procediment!=null) {
			pk.setProcedimentId(procediment.getId());
		}
		
		if(tipusDocumental!=null) {
			pk.setTipusdocumentalId(tipusDocumental.getId());
		}
		
		db.setId(pk);
		db.setObligatori((obligatori==true ? new BigDecimal(1) : new BigDecimal(0)));
		db.setRecapitulatiu((recapitulatiu==true ? new BigDecimal(1) : new BigDecimal(0)));
		return db;
	}
	
	public ProcedimentObject getProcediment() {
		return procediment;
	}

	public void setProcediment(ProcedimentObject procediment) {
		this.procediment = procediment;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public TipuDocumentalObject getTipusDocumental() {
		return tipusDocumental;
	}

	public void setTipusDocumental(TipuDocumentalObject tipusDocumental) {
		this.tipusDocumental = tipusDocumental;
	}

	public Boolean getObligatori() {
		return obligatori;
	}

	public void setObligatori(Boolean obligatori) {
		this.obligatori = obligatori;
	}

	public Boolean getRecapitulatiu() {
		return recapitulatiu;
	}

	public void setRecapitulatiu(Boolean recapitulatiu) {
		this.recapitulatiu = recapitulatiu;
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
		TipuDocumentalProcedimentObject other = (TipuDocumentalProcedimentObject) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TipuDocumentalProcedimentObject [id=");
		builder.append(id);
		builder.append(", procediment=");
		builder.append(procediment);
		builder.append(", tipusDocumental=");
		builder.append(tipusDocumental);
		builder.append(", obligatori=");
		builder.append(obligatori);
		builder.append(", recapitulatiu=");
		builder.append(recapitulatiu);
		builder.append("]");
		return builder.toString();
	}

	

	

}

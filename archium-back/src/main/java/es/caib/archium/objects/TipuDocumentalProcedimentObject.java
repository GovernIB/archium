package es.caib.archium.objects;

import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.TipusdocumentProcedimentPK;

import java.math.BigDecimal;
import java.util.Objects;

public class TipuDocumentalProcedimentObject {

	private Integer id;
	private ProcedimentObject 		procediment;
	private TipuDocumentalObject	tipusDocumental;
	private Boolean 				obligatori;
	private Boolean					multiple;
	
	public TipuDocumentalProcedimentObject() {
		super();
	}

	public TipuDocumentalProcedimentObject(ProcedimentObject procediment, TipuDocumentalObject tipusDocumentalt, Boolean obligatori, Boolean multiple) {
		super();
		this.procediment = procediment;
		this.tipusDocumental = tipusDocumentalt;
		this.obligatori = obligatori;
		this.multiple = multiple;
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
		this.multiple = (i.getMultiple().intValue()==1 ? true : false);
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
		db.setMultiple((multiple==true ? new BigDecimal(1) : new BigDecimal(0)));

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

	public Boolean getMultiple() {
		return multiple;
	}

	public void setMultiple(Boolean multiple) {
		this.multiple = multiple;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TipuDocumentalProcedimentObject that = (TipuDocumentalProcedimentObject) o;
		return Objects.equals(id, that.id) && Objects.equals(procediment, that.procediment) && Objects.equals(tipusDocumental, that.tipusDocumental) && Objects.equals(obligatori, that.obligatori) && Objects.equals(multiple, that.multiple);
	}

	@Override
	public int hashCode() {
		return Objects.hash(id, procediment, tipusDocumental, obligatori, multiple);
	}

	@Override
	public String toString() {
		return "TipuDocumentalProcedimentObject{" +
				"id=" + id +
				", procediment=" + procediment +
				", tipusDocumental=" + tipusDocumental +
				", obligatori=" + obligatori +
				", multiple=" + multiple +
				'}';
	}
}

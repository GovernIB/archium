package es.caib.archium.objects;

import java.math.BigDecimal;

import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.TipusdocumentProcedimentPK;
import es.caib.archium.persistence.model.Valorprimari;
import es.caib.archium.persistence.model.ValorprimariPK;

public class ValorPrimariObject {

	private Integer id;
	private ValoracioObject achValoracio;
	private TipuValorObject	achTipusvalor;
	private Integer terminiVal;
	private String terminiType;
	private Boolean selected;
	
	public ValorPrimariObject() {
		super();
		// TODO Auto-generated constructor stub
	}

	

	public ValorPrimariObject(Integer id, ValoracioObject achValoracio, TipuValorObject achTipusvalor,
			Integer terminiVal, String terminiTytpe) {
		super();
		this.id = id;
		this.achValoracio = achValoracio;
		this.achTipusvalor = achTipusvalor;
		this.terminiVal = terminiVal;
		this.terminiType = terminiTytpe;
	}



	public ValorPrimariObject(Valorprimari db) {
		if(db!=null) {
			this.achTipusvalor = new TipuValorObject(db.getAchTipusvalor());
			
			if(db.getAchValoracio()!=null) {
				ValoracioObject vo = new ValoracioObject();
				vo.setId(db.getAchValoracio().getId());
				this.achValoracio = vo;
			}
			
			if(db.getTermini()!=null) {
				String a 	= db.getTermini().substring(db.getTermini().length() - 1, db.getTermini().length());
				if (a.equals("H"))
					this.setTerminiType("Hores");
				if (a.equals("D"))
					this.setTerminiType("Dies");
				if (a.equals("S"))
					this.setTerminiType("Setmanes");
				if (a.equals("M"))
					this.setTerminiType("Mesos");
				if (a.equals("A"))
					this.setTerminiType("Anys");
				this.terminiVal	= Integer.parseInt(db.getTermini().substring(0,db.getTermini().length() - 1));
			}
		}
	}

	public Valorprimari toDbObject() {
		Valorprimari  db = new Valorprimari();
		ValorprimariPK pk = new ValorprimariPK();
		
		if(this.achTipusvalor!=null) {
			pk.setTipusvalorId(this.achTipusvalor.getId());
		}
		
		if(this.achValoracio!=null) {
			pk.setValoracioId(this.achValoracio.getId());
		}
		
		db.setId(pk);
		
		System.out.println("TERMINI_TYPE:" + terminiType);
		
		if(terminiVal!=null && terminiType!=null) {
			db.setTermini(String.valueOf(terminiVal) + terminiType.substring(0, 1).toUpperCase());
		}
				
		return db;
	}
	
	
	

	public Integer getId() {
		return id;
	}



	public void setId(Integer id) {
		this.id = id;
	}



	public ValoracioObject getAchValoracio() {
		return achValoracio;
	}



	public void setAchValoracio(ValoracioObject achValoracio) {
		this.achValoracio = achValoracio;
	}



	public TipuValorObject getAchTipusvalor() {
		return achTipusvalor;
	}



	public void setAchTipusvalor(TipuValorObject achTipusvalor) {
		this.achTipusvalor = achTipusvalor;
	}

	
	


	public Boolean getSelected() {
		return selected;
	}



	public void setSelected(Boolean selected) {
		this.selected = selected;
	}



	public Integer getTerminiVal() {
		return terminiVal;
	}



	public void setTerminiVal(Integer terminiVal) {
		this.terminiVal = terminiVal;
	}



	public String getTerminiType() {
		return terminiType;
	}



	public void setTerminiType(String terminiType) {
		this.terminiType = terminiType;
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
		ValorPrimariObject other = (ValorPrimariObject) obj;
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
		builder.append("ValorPrimariObject [id=");
		builder.append(id);
		builder.append(", achValoracio=");
		builder.append(achValoracio);
		builder.append(", achTipusvalor=");
		builder.append(achTipusvalor);
		builder.append(", terminiVal=");
		builder.append(terminiVal);
		builder.append(", terminiType=");
		builder.append(terminiType);
		builder.append(", selected=");
		builder.append(selected);
		builder.append("]");
		return builder.toString();
	}


	

	

}

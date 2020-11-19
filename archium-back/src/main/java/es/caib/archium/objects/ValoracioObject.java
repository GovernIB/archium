package es.caib.archium.objects;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Objects;

import es.caib.archium.persistence.model.Valoracio;
import es.caib.archium.persistence.model.Valorprimari;

public class ValoracioObject {

	/**
	 * 
	 */
	private Long id;
	private Date inici;
	private Date fi;
	private List<ValorPrimariObject> achValorprimaris = new ArrayList<ValorPrimariObject>();
	private SerieDocumentalObject achSeriedocumental;
	private ValorSecundariObject achValorsecundari;
	
	
	public ValoracioObject() {
		super();
	}
	
	public ValoracioObject(Long id, Date inici, Date fi, List<ValorPrimariObject> achValorprimaris,
			SerieDocumentalObject achSeriedocumental, ValorSecundariObject achValorsecundari) {
		super();
		this.id = id;
		this.inici = inici;
		this.fi = fi;
		this.achValorprimaris = achValorprimaris;
		this.achSeriedocumental = achSeriedocumental;
		this.achValorsecundari = achValorsecundari;
	}

	public ValoracioObject(ValoracioObject ob) {
		super();
		this.id = ob.getId();
		this.inici = ob.getInici();
		this.fi = ob.getFi();
		this.achValorprimaris = new ArrayList<ValorPrimariObject>();
		for(ValorPrimariObject vp: ob.getAchValorprimaris()) {
			this.achValorprimaris.add(vp);
		}
		this.achSeriedocumental = ob.getAchSeriedocumental();
		this.achValorsecundari = ob.getAchValorsecundari();
	}

	public ValoracioObject(Valoracio db) {
		
		if(db!=null) {
			if(db.getAchSeriedocumental()!=null) {
				SerieDocumentalObject s = new SerieDocumentalObject();
				s.setSerieId(db.getAchSeriedocumental().getId());
				s.setCodi(db.getAchSeriedocumental().getCodi());
				s.setNom(db.getAchSeriedocumental().getNom());
				this.setAchSeriedocumental(s);
			}
			
			if(db.getAchValorsecundari()!=null) {
				ValorSecundariObject vs = new ValorSecundariObject();
				vs.setId(db.getAchValorsecundari().getId());
				vs.setNom(db.getAchValorsecundari().getNom());
				this.setAchValorsecundari(vs);
			}
			
			if(db.getAchValorprimaris()!=null) {
				for (Valorprimari vp : db.getAchValorprimaris()) {
					this.addValorprimari(new ValorPrimariObject(vp));
				}
			}
			
			this.id = db.getId();
			this.inici = db.getInici();
			this.fi = db.getFi();
		}
	}

	public Valoracio toDbObject() {
		Valoracio  db = new Valoracio();
		if(achSeriedocumental!=null) 
			db.setAchSeriedocumental(achSeriedocumental.toDbObject(null, null, null));
		if(achValorsecundari!=null)
			db.setAchValorsecundari(achValorsecundari.toDbObject());
		db.setId(id);
		db.setInici(inici);
		db.setFi(fi);
		return db;
	}
	
	

	public Long getId() {
		return id;
	}



	public void setId(Long id) {
		this.id = id;
	}



	public Date getInici() {
		return inici;
	}



	public void setInici(Date inici) {
		this.inici = inici;
	}



	public Date getFi() {
		return fi;
	}



	public void setFi(Date fi) {
		this.fi = fi;
	}



	public List<ValorPrimariObject> getAchValorprimaris() {
		return achValorprimaris;
	}

	public void addValorprimari(ValorPrimariObject vp) {
		this.achValorprimaris.add(vp);
	}

	public void setAchValorprimaris(List<ValorPrimariObject> achValorprimaris) {
		this.achValorprimaris = achValorprimaris;
	}



	public SerieDocumentalObject getAchSeriedocumental() {
		return achSeriedocumental;
	}



	public void setAchSeriedocumental(SerieDocumentalObject achSeriedocumental) {
		this.achSeriedocumental = achSeriedocumental;
	}



	public ValorSecundariObject getAchValorsecundari() {
		return achValorsecundari;
	}



	public void setAchValorsecundari(ValorSecundariObject achValorsecundari) {
		this.achValorsecundari = achValorsecundari;
	}

	public Boolean hashValorPrimariSelected() {
		
		Boolean hashPrimariSelected = false;
		
		if(this.getAchValorprimaris()!=null) {
			Iterator<ValorPrimariObject> it = this.getAchValorprimaris().iterator();
			while(it.hasNext() && hashPrimariSelected==false) {
				ValorPrimariObject item = it.next();
				if(item.getSelected()==true && item.getTerminiVal()!=null) {
					hashPrimariSelected = true;
				}
			}
		}

		return hashPrimariSelected;
		
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
		ValoracioObject other = (ValoracioObject) obj;
		return Objects.equals(id, other.id);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("ValoracioObject [id=");
		builder.append(id);
		builder.append(", inici=");
		builder.append(inici);
		builder.append(", fi=");
		builder.append(fi);
		builder.append(", achValorprimaris=");
		builder.append(achValorprimaris);
		builder.append(", achSeriedocumental=");
		builder.append(achSeriedocumental);
		builder.append(", achValorsecundari=");
		builder.append(achValorsecundari);
		builder.append("]");
		return builder.toString();
	}



	

	

}

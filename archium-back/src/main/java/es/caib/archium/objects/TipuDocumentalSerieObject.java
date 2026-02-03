package es.caib.archium.objects;

import es.caib.archium.persistence.model.TipusdocumentSerie;
import es.caib.archium.persistence.model.TipusdocumentSeriePK;

import java.math.BigDecimal;
import java.util.Objects;

public class TipuDocumentalSerieObject {
	private SerieDocumentalObject 		serie;
	private TipuDocumentalObject	tipusDocumental;
	private Boolean 				obligatori;
	private Boolean					multiple;

	public TipuDocumentalSerieObject() {
		super();
	}

	public TipuDocumentalSerieObject(TipusdocumentSerie i) {
		
		SerieDocumentalObject ser = new SerieDocumentalObject();
		ser.setSerieId(i.getAchSeriedocumental().getId());
		ser.setNom(i.getAchSeriedocumental().getNom());
		this.serie 		= 	ser;
		
		TipuDocumentalObject td = new TipuDocumentalObject();
		td.setId(i.getAchTipusdocumental().getId());
		td.setNom(i.getAchTipusdocumental().getNom());
		this.tipusDocumental = td;
		
		this.obligatori = (i.getObligatori().intValue() == 1);
		this.multiple = (i.getMultiple().intValue() == 1);
	}

	public TipusdocumentSerie toDbObject() {
		TipusdocumentSerie  db = new TipusdocumentSerie();
		TipusdocumentSeriePK pk = new TipusdocumentSeriePK();
		if(serie!=null) {
			pk.setSerieDocumentalId(serie.getSerieId());
		}
		
		if(tipusDocumental!=null) {
			pk.setTipusdocumentalId(tipusDocumental.getId());
		}
		
		db.setId(pk);
		db.setObligatori((obligatori ? new BigDecimal(1) : new BigDecimal(0)));
		db.setMultiple((multiple ? new BigDecimal(1) : new BigDecimal(0)));

		return db;
	}
	
	public SerieDocumentalObject getSerie() {
		return serie;
	}

	public void setSerie(SerieDocumentalObject serie) {
		this.serie = serie;
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
		TipuDocumentalSerieObject that = (TipuDocumentalSerieObject) o;
		return Objects.equals(serie, that.serie) && Objects.equals(tipusDocumental, that.tipusDocumental);
	}

	@Override
	public int hashCode() {
		return Objects.hash(serie, tipusDocumental);
	}

	@Override
	public String toString() {
		return "TipuDocumentalSerieObject{" +
				", serie=" + serie +
				", tipusDocumental=" + tipusDocumental +
				", obligatori=" + obligatori +
				", multiple=" + multiple +
				'}';
	}
}

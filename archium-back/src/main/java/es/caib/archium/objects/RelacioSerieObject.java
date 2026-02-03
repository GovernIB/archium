package es.caib.archium.objects;

import es.caib.archium.ejb.service.TipusRelacioSerieService;
import es.caib.archium.persistence.model.LimitacioNormativaSerie;
import es.caib.archium.persistence.model.LimitacioNormativaSeriePK;
import es.caib.archium.persistence.model.Serierelacionada;
import org.primefaces.model.DualListModel;

import java.util.*;

public class RelacioSerieObject {

	private SerieDocumentalObject seriedocumental;

	private Set<SerieDocumentalObject> seriesRelacionades;
	private TipusRelacioSerieObject tipusRelacio;

	private DualListModel<SerieDocumentalObject> dualListSeries;

	public RelacioSerieObject() {
		dualListSeries = new DualListModel<>();
	}

	public RelacioSerieObject(Serierelacionada rel) {
		this.seriedocumental = new SerieDocumentalObject(rel.getAchSeriedocumental1());
		this.tipusRelacio = new TipusRelacioSerieObject(rel.getTipusRelacioSerie());
	}

	@Override
	public int hashCode() {
		return Objects.hash(seriedocumental, tipusRelacio);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		RelacioSerieObject other = (RelacioSerieObject) obj;
		return Objects.equals(seriedocumental, other.seriedocumental) && Objects.equals(tipusRelacio, other.tipusRelacio);
	}

	@Override
	public String toString() {
		return "RelacioSerieObject{" +
				"seriedocumental=" + seriedocumental +
				", seriesRelacionades=" + seriesRelacionades +
				", tipusRelacio=" + tipusRelacio +
				'}';
	}

	public SerieDocumentalObject getSeriedocumental() {
		return seriedocumental;
	}

	public void setSeriedocumental(SerieDocumentalObject seriedocumental) {
		this.seriedocumental = seriedocumental;
	}

	public Set<SerieDocumentalObject> getSeriesRelacionades() {
		return seriesRelacionades;
	}

	public void setSeriesRelacionades(Set<SerieDocumentalObject> seriesRelacionades) {
		this.seriesRelacionades = seriesRelacionades;
	}

	public TipusRelacioSerieObject getTipusRelacio() {
		return tipusRelacio;
	}

	public void setTipusRelacio(TipusRelacioSerieObject tipusRelacio) {
		this.tipusRelacio = tipusRelacio;
	}

	public DualListModel<SerieDocumentalObject> getDualListSeries() {
		return dualListSeries;
	}

	public void setDualListSeries(DualListModel<SerieDocumentalObject> dualListSeries) {
		this.dualListSeries = dualListSeries;
	}

	public void addSerieDocumental(SerieDocumentalObject serie) {
		if (this.seriesRelacionades == null) {
			this.seriesRelacionades = new HashSet<>();
		}
		this.seriesRelacionades.add(serie);
	}
}
package es.caib.archium.persistence.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;


/**
 * The persistent class for the ACH_PROCEDIMENT_SERIE database table (procediments comuns que poden anar a múltiples sèries)
 * 
 */
@Entity
@Table(name="ACH_PROCEDIMENT_SERIE")
@NamedQuery(name="ProcedimentSerie.findAll", query="SELECT ps FROM ProcedimentSerie ps")
public class ProcedimentSerie implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ProcedimentSeriePK id;

	//bi-directional many-to-one association to Procediment
	@ManyToOne
	@MapsId("procedimentId")
	@JoinColumn(name="PROCEDIMENT_ID"  ,insertable=false,updatable=false)
	private Procediment achProcediment;

	//bi-directional many-to-one association to Tipusdocumental
	@ManyToOne
	@MapsId("seriedocumentalId")
	@JoinColumn(name="SERIEDOCUMENTAL_ID"  ,insertable=false,updatable=false)
	private Seriedocumental achSerieDocumental;

	public ProcedimentSerie() {
	}

	public ProcedimentSeriePK getId() {
		return this.id;
	}

	public void setId(ProcedimentSeriePK id) {
		this.id = id;
	}

	public Procediment getAchProcediment() {
		return this.achProcediment;
	}

	public void setAchProcediment(Procediment achProcediment) {
		this.achProcediment = achProcediment;
	}

	public Seriedocumental getAchSerieDocumental() {
		return achSerieDocumental;
	}

	public void setAchSerieDocumental(Seriedocumental achSerieDocumental) {
		this.achSerieDocumental = achSerieDocumental;
	}
}
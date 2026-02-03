package es.caib.archium.persistence.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;


/**
 * The persistent class for the ACH_TIPUSDOCUMENT_PROCEDIMENT database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSDOCUMENT_PROCEDIMENT")
@NamedQuery(name="TipusdocumentProcediment.findAll", query="SELECT t FROM TipusdocumentProcediment t")
public class TipusdocumentProcediment implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TipusdocumentProcedimentPK id;

	private BigDecimal obligatori;

	private BigDecimal multiple;

	//bi-directional many-to-one association to Procediment
	@ManyToOne
	@MapsId("procedimentId")
	@JoinColumn(name="PROCEDIMENT_ID"  ,insertable=false,updatable=false)
	private Procediment achProcediment;

	//bi-directional many-to-one association to Tipusdocumental
	@ManyToOne
	@MapsId("tipusdocumentalId")
	@JoinColumn(name="TIPUSDOCUMENTAL_ID"  ,insertable=false,updatable=false)
	private TipusDocumental achTipusdocumental;

	public TipusdocumentProcediment() {
	}

	public TipusdocumentProcedimentPK getId() {
		return this.id;
	}

	public void setId(TipusdocumentProcedimentPK id) {
		this.id = id;
	}

	public BigDecimal getObligatori() {
		return this.obligatori;
	}

	public void setObligatori(BigDecimal obligatori) {
		this.obligatori = obligatori;
	}

	public Procediment getAchProcediment() {
		return this.achProcediment;
	}

	public void setAchProcediment(Procediment achProcediment) {
		this.achProcediment = achProcediment;
	}

	public TipusDocumental getAchTipusdocumental() {
		return this.achTipusdocumental;
	}

	public void setAchTipusdocumental(TipusDocumental achTipusdocumental) {
		this.achTipusdocumental = achTipusdocumental;
	}

	public BigDecimal getMultiple() {
		return multiple;
	}

	public void setMultiple(BigDecimal multiple) {
		this.multiple = multiple;
	}
}
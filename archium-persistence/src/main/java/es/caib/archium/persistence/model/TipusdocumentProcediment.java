package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
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

	private BigDecimal recapitulatiu;

	//bi-directional many-to-one association to Procediment
	@ManyToOne
	@JoinColumn(name="PROCEDIMENT_ID"  ,insertable=false,updatable=false)
	private Procediment achProcediment;

	//bi-directional many-to-one association to Tipusdocumental
	@ManyToOne
	@JoinColumn(name="TIPUSDOCUMENTAL_ID"  ,insertable=false,updatable=false)
	private Tipusdocumental achTipusdocumental;

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

	public BigDecimal getRecapitulatiu() {
		return this.recapitulatiu;
	}

	public void setRecapitulatiu(BigDecimal recapitulatiu) {
		this.recapitulatiu = recapitulatiu;
	}

	public Procediment getAchProcediment() {
		return this.achProcediment;
	}

	public void setAchProcediment(Procediment achProcediment) {
		this.achProcediment = achProcediment;
	}

	public Tipusdocumental getAchTipusdocumental() {
		return this.achTipusdocumental;
	}

	public void setAchTipusdocumental(Tipusdocumental achTipusdocumental) {
		this.achTipusdocumental = achTipusdocumental;
	}

}
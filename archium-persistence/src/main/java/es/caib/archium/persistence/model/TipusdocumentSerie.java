package es.caib.archium.persistence.model;

import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Objects;


/**
 * The persistent class for the ACH_TIPUSDOCUMENT_SERIE database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSDOCUMENT_SERIE")
@NamedQuery(name="TipusdocumentSerie.findAll", query="SELECT t FROM TipusdocumentSerie t")
public class TipusdocumentSerie implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private TipusdocumentSeriePK id;

	private BigDecimal obligatori;

	private BigDecimal multiple;

	//bi-directional many-to-one association to Procediment
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("serieDocumentalId")
	@JoinColumn(name="SERIEDOCUMENTAL_ID"  ,insertable=false,updatable=false)
	private Seriedocumental achSeriedocumental;

	//bi-directional many-to-one association to Tipusdocumental
	@ManyToOne(fetch = FetchType.LAZY)
	@MapsId("tipusdocumentalId")
	@JoinColumn(name="TIPUSDOCUMENTAL_ID"  ,insertable=false,updatable=false)
	private TipusDocumental achTipusdocumental;

	public TipusdocumentSerie() {
	}

	public TipusdocumentSeriePK getId() {
		return id;
	}

	public void setId(TipusdocumentSeriePK id) {
		this.id = id;
	}

	public BigDecimal getObligatori() {
		return obligatori;
	}

	public void setObligatori(BigDecimal obligatori) {
		this.obligatori = obligatori;
	}

	public BigDecimal getMultiple() {
		return multiple;
	}

	public void setMultiple(BigDecimal multiple) {
		this.multiple = multiple;
	}

	public Seriedocumental getAchSeriedocumental() {
		return achSeriedocumental;
	}

	public void setAchSeriedocumental(Seriedocumental achSeriedocumental) {
		this.achSeriedocumental = achSeriedocumental;
	}

	public TipusDocumental getAchTipusdocumental() {
		return achTipusdocumental;
	}

	public void setAchTipusdocumental(TipusDocumental achTipusdocumental) {
		this.achTipusdocumental = achTipusdocumental;
	}

	@Override
	public String toString() {
		return "TipusdocumentSerie [id=" +
				id +
				", obligatori=" +
				obligatori +
				", multiple=" +
				multiple +
				", achSerieDocumental=" +
				achSeriedocumental +
				", achTipusdocumental=" +
				achTipusdocumental +
				"]";
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		TipusdocumentSerie that = (TipusdocumentSerie) o;
		return Objects.equals(id.getSerieDocumentalId(), that.getId().getSerieDocumentalId()) && Objects.equals(id.getTipusdocumentalId(), that.getId().getTipusdocumentalId());
	}

	@Override
	public int hashCode() {
		return Objects.hash(id.getSerieDocumentalId(), id.getTipusdocumentalId());
	}
}
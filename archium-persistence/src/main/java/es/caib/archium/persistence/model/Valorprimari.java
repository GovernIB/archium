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
 * The persistent class for the ACH_VALORPRIMARI database table.
 * 
 */
@Entity
@Table(name="ACH_VALORPRIMARI")
@NamedQuery(name="Valorprimari.findAll", query="SELECT v FROM Valorprimari v")
public class Valorprimari implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ValorprimariPK id;

	private String termini;

	//bi-directional many-to-one association to Tipusvalor
	@ManyToOne
	@MapsId("tipusvalorId")
	@JoinColumn(name="TIPUSVALOR_ID"  ,insertable=false,updatable=false)
	private TipusValor achTipusvalor;

	//bi-directional many-to-one association to Valoracio
	@ManyToOne
	@MapsId("valoracioId")
	@JoinColumn(name="VALORACIO_ID"  ,insertable=false,updatable=false)
	private Valoracio achValoracio;

	public Valorprimari() {
	}

	public ValorprimariPK getId() {
		return this.id;
	}

	public void setId(ValorprimariPK id) {
		this.id = id;
	}

	public String getTermini() {
		return this.termini;
	}

	public void setTermini(String termini) {
		this.termini = termini;
	}

	public TipusValor getAchTipusvalor() {
		return this.achTipusvalor;
	}

	public void setAchTipusvalor(TipusValor achTipusvalor) {
		this.achTipusvalor = achTipusvalor;
	}

	public Valoracio getAchValoracio() {
		return this.achValoracio;
	}

	public void setAchValoracio(Valoracio achValoracio) {
		this.achValoracio = achValoracio;
	}

}
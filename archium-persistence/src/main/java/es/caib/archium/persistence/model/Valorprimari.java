package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;


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
	@JoinColumn(name="TIPUSVALOR_ID"  ,insertable=false,updatable=false)
	private Tipusvalor achTipusvalor;

	//bi-directional many-to-one association to Valoracio
	@ManyToOne
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

	public Tipusvalor getAchTipusvalor() {
		return this.achTipusvalor;
	}

	public void setAchTipusvalor(Tipusvalor achTipusvalor) {
		this.achTipusvalor = achTipusvalor;
	}

	public Valoracio getAchValoracio() {
		return this.achValoracio;
	}

	public void setAchValoracio(Valoracio achValoracio) {
		this.achValoracio = achValoracio;
	}

}
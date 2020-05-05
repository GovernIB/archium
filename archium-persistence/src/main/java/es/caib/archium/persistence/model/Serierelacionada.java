package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ACH_SERIERELACIONADA database table.
 * 
 */
@Entity
@Table(name="ACH_SERIERELACIONADA")
@NamedQuery(name="Serierelacionada.findAll", query="SELECT s FROM Serierelacionada s")
public class Serierelacionada implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_SERIERELACIONADA_ID_GENERATOR", sequenceName="ACH_SERIERELACIONADA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_SERIERELACIONADA_ID_GENERATOR")
	private Long id;

	//bi-directional many-to-one association to Serieargen
	@ManyToOne
	@JoinColumn(name="SERIEARGEN_RELACIONADA")
	private Serieargen achSerieargen;

	//bi-directional many-to-one association to Seriedocumental
	@ManyToOne
	@JoinColumn(name="SERIEDOCUMENTAL_ID")
	private Seriedocumental achSeriedocumental1;

	//bi-directional many-to-one association to Seriedocumental
	@ManyToOne
	@JoinColumn(name="SERIE_RELACIONADA")
	private Seriedocumental achSeriedocumental2;

	public Serierelacionada() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Serieargen getAchSerieargen() {
		return this.achSerieargen;
	}

	public void setAchSerieargen(Serieargen achSerieargen) {
		this.achSerieargen = achSerieargen;
	}

	public Seriedocumental getAchSeriedocumental1() {
		return this.achSeriedocumental1;
	}

	public void setAchSeriedocumental1(Seriedocumental achSeriedocumental1) {
		this.achSeriedocumental1 = achSeriedocumental1;
	}

	public Seriedocumental getAchSeriedocumental2() {
		return this.achSeriedocumental2;
	}

	public void setAchSeriedocumental2(Seriedocumental achSeriedocumental2) {
		this.achSeriedocumental2 = achSeriedocumental2;
	}
	
}
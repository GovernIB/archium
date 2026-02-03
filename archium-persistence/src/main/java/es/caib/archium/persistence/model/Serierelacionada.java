package es.caib.archium.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Objects;


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
	private SerieArgen achSerieargen;

	//bi-directional many-to-one association to Seriedocumental
	@ManyToOne
	@JoinColumn(name="SERIEDOCUMENTAL_ID")
	private Seriedocumental achSeriedocumental1;

	//bi-directional many-to-one association to Seriedocumental
	@ManyToOne
	@JoinColumn(name="SERIE_RELACIONADA")
	private Seriedocumental achSeriedocumental2;

	@ManyToOne
	@JoinColumn(name="TIPUSRELACIO_ID")
	private TipusRelacioSerie tipusRelacioSerie;

	public Serierelacionada() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public SerieArgen getAchSerieargen() {
		return this.achSerieargen;
	}

	public void setAchSerieargen(SerieArgen achSerieargen) {
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

	public TipusRelacioSerie getTipusRelacioSerie() {
		return tipusRelacioSerie;
	}

	public void setTipusRelacioSerie(TipusRelacioSerie tipusRelacioSerie) {
		this.tipusRelacioSerie = tipusRelacioSerie;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		Serierelacionada that = (Serierelacionada) o;
		return Objects.equals(achSerieargen, that.achSerieargen) && achSeriedocumental1.equals(that.achSeriedocumental1) && Objects.equals(achSeriedocumental2, that.achSeriedocumental2) && tipusRelacioSerie.equals(that.tipusRelacioSerie);
	}

	@Override
	public int hashCode() {
		return Objects.hash(achSerieargen, achSeriedocumental1, achSeriedocumental2, tipusRelacioSerie);
	}
}
package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACH_DICTAMEN database table.
 * 
 */
@Entity
@Table(name="ACH_DICTAMEN")
@NamedQuery(name="Dictamen.findAll", query="SELECT d FROM Dictamen d")
public class Dictamen implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_DICTAMEN_ID_GENERATOR", sequenceName="ACH_DICTAMENT_SEQ" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_DICTAMEN_ID_GENERATOR")
	private Long id;

	private String acciodictaminada;

	@Temporal(TemporalType.DATE)
	private Date aprovacio;

	private String condicioreutilitzacio;
	
	private String codi;
	
	private String estat;

	public String getCodi() {
		return codi;
	}

	public void setCodi(String codi) {
		this.codi = codi;
	}

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	private String destinatarisrestringits;

	@Temporal(TemporalType.DATE)
	private Date fi;

	@Temporal(TemporalType.DATE)
	private Date inici;

	private BigDecimal serieessencial;

	private String termini;

	//bi-directional many-to-one association to Ens
	@ManyToOne
	@JoinColumn(name="ENS_ID")
	private Ens achEn;

	//bi-directional many-to-one association to Lopd
	@ManyToOne
	@JoinColumn(name="LOPD_ID")
	private Lopd achLopd;

	//bi-directional many-to-one association to Normativa
	@ManyToOne
	@JoinColumn(name="NORMATIVA_APROVACIO_ID")
	private Normativa achNormativa;

	//bi-directional many-to-one association to Seriedocumental
	@ManyToOne
	@JoinColumn(name="SERIEDOCUMENTAL_ID")
	private Seriedocumental achSeriedocumental;

	//bi-directional many-to-one association to Tipusacce
	@ManyToOne
	@JoinColumn(name="TIPUSACCES_ID")
	private Tipusacce achTipusacce;

	//bi-directional many-to-one association to Tipusdictamen
	@ManyToOne
	@JoinColumn(name="TIPUSDICTAMEN_ID")
	private Tipusdictamen achTipusdictamen;

	//bi-directional many-to-one association to DictamenTipusdocumental
	@OneToMany(mappedBy="achDictamen")
	private List<DictamenTipusdocumental> achDictamenTipusdocumentals;

	//bi-directional many-to-many association to Normativa
	@ManyToMany(mappedBy="achDictamens2")
	private List<Normativa> achNormativas;

	public Dictamen() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAcciodictaminada() {
		return this.acciodictaminada;
	}

	public void setAcciodictaminada(String acciodictaminada) {
		this.acciodictaminada = acciodictaminada;
	}

	public Date getAprovacio() {
		return this.aprovacio;
	}

	public void setAprovacio(Date aprovacio) {
		this.aprovacio = aprovacio;
	}

	public String getCondicioreutilitzacio() {
		return this.condicioreutilitzacio;
	}

	public void setCondicioreutilitzacio(String condicioreutilitzacio) {
		this.condicioreutilitzacio = condicioreutilitzacio;
	}

	public String getDestinatarisrestringits() {
		return this.destinatarisrestringits;
	}

	public void setDestinatarisrestringits(String destinatarisrestringits) {
		this.destinatarisrestringits = destinatarisrestringits;
	}

	public Date getFi() {
		return this.fi;
	}

	public void setFi(Date fi) {
		this.fi = fi;
	}

	public Date getInici() {
		return this.inici;
	}

	public void setInici(Date inici) {
		this.inici = inici;
	}

	public BigDecimal getSerieessencial() {
		return this.serieessencial;
	}

	public void setSerieessencial(BigDecimal serieessencial) {
		this.serieessencial = serieessencial;
	}

	public String getTermini() {
		return this.termini;
	}

	public void setTermini(String termini) {
		this.termini = termini;
	}

	public Ens getAchEn() {
		return this.achEn;
	}

	public void setAchEn(Ens achEn) {
		this.achEn = achEn;
	}

	public Lopd getAchLopd() {
		return this.achLopd;
	}

	public void setAchLopd(Lopd achLopd) {
		this.achLopd = achLopd;
	}

	public Normativa getAchNormativa() {
		return this.achNormativa;
	}

	public void setAchNormativa(Normativa achNormativa) {
		this.achNormativa = achNormativa;
	}

	public Seriedocumental getAchSeriedocumental() {
		return this.achSeriedocumental;
	}

	public void setAchSeriedocumental(Seriedocumental achSeriedocumental) {
		this.achSeriedocumental = achSeriedocumental;
	}

	public Tipusacce getAchTipusacce() {
		return this.achTipusacce;
	}

	public void setAchTipusacce(Tipusacce achTipusacce) {
		this.achTipusacce = achTipusacce;
	}

	public Tipusdictamen getAchTipusdictamen() {
		return this.achTipusdictamen;
	}

	public void setAchTipusdictamen(Tipusdictamen achTipusdictamen) {
		this.achTipusdictamen = achTipusdictamen;
	}

	public List<DictamenTipusdocumental> getAchDictamenTipusdocumentals() {
		return this.achDictamenTipusdocumentals;
	}

	public void setAchDictamenTipusdocumentals(List<DictamenTipusdocumental> achDictamenTipusdocumentals) {
		this.achDictamenTipusdocumentals = achDictamenTipusdocumentals;
	}

	public DictamenTipusdocumental addAchDictamenTipusdocumental(DictamenTipusdocumental achDictamenTipusdocumental) {
		getAchDictamenTipusdocumentals().add(achDictamenTipusdocumental);
		achDictamenTipusdocumental.setAchDictamen(this);

		return achDictamenTipusdocumental;
	}

	public DictamenTipusdocumental removeAchDictamenTipusdocumental(DictamenTipusdocumental achDictamenTipusdocumental) {
		getAchDictamenTipusdocumentals().remove(achDictamenTipusdocumental);
		achDictamenTipusdocumental.setAchDictamen(null);

		return achDictamenTipusdocumental;
	}

	public List<Normativa> getAchNormativas() {
		return this.achNormativas;
	}

	public void setAchNormativas(List<Normativa> achNormativas) {
		this.achNormativas = achNormativas;
	}

}
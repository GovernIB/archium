package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACH_NORMATIVA database table.
 * 
 */
@Entity
@Table(name="ACH_NORMATIVA")
@NamedQuery(name="Normativa.findAll", query="SELECT n FROM Normativa n")
public class Normativa implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_NORMATIVA_ID_GENERATOR", sequenceName="ACH_NORMATIVA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_NORMATIVA_ID_GENERATOR")
	private Long id;

	private String codi;

	@Temporal(TemporalType.DATE)
	private Date derogacio;

	private String estat;

	private String nom;

	private String nomcas;

	private String uri;

	private String urieli;

	private String urieliconsolidada;

	@Temporal(TemporalType.DATE)
	private Date vigor;

	//bi-directional many-to-one association to Dictamen
	@OneToMany(mappedBy="achNormativa")
	private List<Dictamen> achDictamens1;

	//bi-directional many-to-one association to LimitacioNormativaSerie
	@OneToMany(mappedBy="achNormativa")
	private List<LimitacioNormativaSerie> achLimitacioNormativaSeries;

	//bi-directional many-to-many association to Dictamen
	@ManyToMany
	@JoinTable(
		name="ACH_NORMATIVA_DICTAMEN"
		, joinColumns={
			@JoinColumn(name="NORMATIVA_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="DICTAMEN_ID")
			}
		)
	private List<Dictamen> achDictamens2;

	//bi-directional many-to-one association to Normativa
	@ManyToOne
	@JoinColumn(name="NORMATIVA_VIGENT")
	private Normativa achNormativa;

	//bi-directional many-to-one association to Normativa
	@OneToMany(mappedBy="achNormativa")
	private List<Normativa> achNormativas;

	//bi-directional many-to-one association to NormativaSeriedocumental
	@OneToMany(mappedBy="achNormativa")
	private List<NormativaSeriedocumental> achNormativaSeriedocumentals;

	//bi-directional many-to-many association to Procediment
	@ManyToMany(mappedBy="achNormativas")
	private List<Procediment> achProcediments;

	//bi-directional many-to-many association to Tipusdocumental
	@ManyToMany(mappedBy="achNormativas")
	private List<Tipusdocumental> achTipusdocumentals;

	public Normativa() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodi() {
		return this.codi;
	}

	public void setCodi(String codi) {
		this.codi = codi;
	}

	public Date getDerogacio() {
		return this.derogacio;
	}

	public void setDerogacio(Date derogacio) {
		this.derogacio = derogacio;
	}

	public String getEstat() {
		return this.estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getNomcas() {
		return this.nomcas;
	}

	public void setNomcas(String nomcas) {
		this.nomcas = nomcas;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getUrieli() {
		return this.urieli;
	}

	public void setUrieli(String urieli) {
		this.urieli = urieli;
	}

	public String getUrieliconsolidada() {
		return this.urieliconsolidada;
	}

	public void setUrieliconsolidada(String urieliconsolidada) {
		this.urieliconsolidada = urieliconsolidada;
	}

	public Date getVigor() {
		return this.vigor;
	}

	public void setVigor(Date vigor) {
		this.vigor = vigor;
	}

	public List<Dictamen> getAchDictamens1() {
		return this.achDictamens1;
	}

	public void setAchDictamens1(List<Dictamen> achDictamens1) {
		this.achDictamens1 = achDictamens1;
	}

	public Dictamen addAchDictamens1(Dictamen achDictamens1) {
		getAchDictamens1().add(achDictamens1);
		achDictamens1.setAchNormativa(this);

		return achDictamens1;
	}

	public Dictamen removeAchDictamens1(Dictamen achDictamens1) {
		getAchDictamens1().remove(achDictamens1);
		achDictamens1.setAchNormativa(null);

		return achDictamens1;
	}

	public List<LimitacioNormativaSerie> getAchLimitacioNormativaSeries() {
		return this.achLimitacioNormativaSeries;
	}

	public void setAchLimitacioNormativaSeries(List<LimitacioNormativaSerie> achLimitacioNormativaSeries) {
		this.achLimitacioNormativaSeries = achLimitacioNormativaSeries;
	}

	public LimitacioNormativaSerie addAchLimitacioNormativaSery(LimitacioNormativaSerie achLimitacioNormativaSery) {
		getAchLimitacioNormativaSeries().add(achLimitacioNormativaSery);
		achLimitacioNormativaSery.setAchNormativa(this);

		return achLimitacioNormativaSery;
	}

	public LimitacioNormativaSerie removeAchLimitacioNormativaSery(LimitacioNormativaSerie achLimitacioNormativaSery) {
		getAchLimitacioNormativaSeries().remove(achLimitacioNormativaSery);
		achLimitacioNormativaSery.setAchNormativa(null);

		return achLimitacioNormativaSery;
	}

	public List<Dictamen> getAchDictamens2() {
		return this.achDictamens2;
	}

	public void setAchDictamens2(List<Dictamen> achDictamens2) {
		this.achDictamens2 = achDictamens2;
	}

	public Normativa getAchNormativa() {
		return this.achNormativa;
	}

	public void setAchNormativa(Normativa achNormativa) {
		this.achNormativa = achNormativa;
	}

	public List<Normativa> getAchNormativas() {
		return this.achNormativas;
	}

	public void setAchNormativas(List<Normativa> achNormativas) {
		this.achNormativas = achNormativas;
	}

	public Normativa addAchNormativa(Normativa achNormativa) {
		getAchNormativas().add(achNormativa);
		achNormativa.setAchNormativa(this);

		return achNormativa;
	}

	public Normativa removeAchNormativa(Normativa achNormativa) {
		getAchNormativas().remove(achNormativa);
		achNormativa.setAchNormativa(null);

		return achNormativa;
	}

	public List<NormativaSeriedocumental> getAchNormativaSeriedocumentals() {
		return this.achNormativaSeriedocumentals;
	}

	public void setAchNormativaSeriedocumentals(List<NormativaSeriedocumental> achNormativaSeriedocumentals) {
		this.achNormativaSeriedocumentals = achNormativaSeriedocumentals;
	}

	public NormativaSeriedocumental addAchNormativaSeriedocumental(NormativaSeriedocumental achNormativaSeriedocumental) {
		getAchNormativaSeriedocumentals().add(achNormativaSeriedocumental);
		achNormativaSeriedocumental.setAchNormativa(this);

		return achNormativaSeriedocumental;
	}

	public NormativaSeriedocumental removeAchNormativaSeriedocumental(NormativaSeriedocumental achNormativaSeriedocumental) {
		getAchNormativaSeriedocumentals().remove(achNormativaSeriedocumental);
		achNormativaSeriedocumental.setAchNormativa(null);

		return achNormativaSeriedocumental;
	}

	public List<Procediment> getAchProcediments() {
		return this.achProcediments;
	}

	public void setAchProcediments(List<Procediment> achProcediments) {
		this.achProcediments = achProcediments;
	}

	public List<Tipusdocumental> getAchTipusdocumentals() {
		return this.achTipusdocumentals;
	}

	public void setAchTipusdocumentals(List<Tipusdocumental> achTipusdocumentals) {
		this.achTipusdocumentals = achTipusdocumentals;
	}

}
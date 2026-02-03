package es.caib.archium.persistence.model;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.PreRemove;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


/**
 * The persistent class for the ACH_SERIEDOCUMENTAL database table.
 * 
 */
@Entity
@Table(name="ACH_SERIEDOCUMENTAL")
@NamedQuery(name="Seriedocumental.findAll", query="SELECT s FROM Seriedocumental s")
public class Seriedocumental implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_SERIEDOCUMENTAL_ID_GENERATOR", sequenceName="ACH_SERIEDOCUMENTAL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_SERIEDOCUMENTAL_ID_GENERATOR")
	private Long id;

	private String codi;

	private String codiiecisa;

	private String descripcio;

	private String descripciocas;

	@Column(name="DIR3_PROMOTOR")
	private String dir3Promotor;

	private String nom;

	private String nomcas;

	private String resummigracio;
	
	private String estat;

	// Indica si la sèrie documental s'ha transferit al Sistema de Apoyo a la Tramitació
	@Column(name="ENVIATSAT")
	private Boolean enviatSAT;

	//bi-directional many-to-one association to AplicacioSerie
	@OneToMany(mappedBy="achSeriedocumental",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<AplicacioSerie> achAplicacioSeries;

	//bi-directional many-to-one association to Dictamen
	@OneToMany(mappedBy="achSeriedocumental",
			cascade = CascadeType.REMOVE)
	private List<Dictamen> achDictamens;

	//bi-directional many-to-one association to LimitacioNormativaSerie
	@OneToMany(mappedBy="achSeriedocumental",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<LimitacioNormativaSerie> achLimitacioNormativaSeries;

	//bi-directional many-to-one association to NormativaSeriedocumental
	@OneToMany(mappedBy="achSeriedocumental",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<NormativaSeriedocumental> achNormativaSeriedocumentals;

	//bi-directional many-to-one association to Procediment
	@OneToMany(mappedBy="achSeriedocumental")
	private List<Procediment> achProcediments;

	//bi-directional many-to-one association to ProcedimentSerie
	@OneToMany(mappedBy="achSerieDocumental",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private List<ProcedimentSerie> achProcedimentsComuns;

	//bi-directional many-to-one association to Catalegsery
	@ManyToOne
	@JoinColumn(name="CATALEGSERIES_ID")
	private Catalegsery achCatalegsery;

	//bi-directional many-to-one association to Funcio
	@ManyToOne
	@JoinColumn(name="FUNCIO_ID")
	private Funcio achFuncio;

	//bi-directional many-to-many association to Serieargen
	@ManyToMany
	@JoinTable(
		name="ACH_SERIEDOCUMENTAL_MIGRACIO"
		, joinColumns={
			@JoinColumn(name="SERIEDOCUMENTAL_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="SERIEARGEN_ID")
			}
		)
	private List<SerieArgen> achSerieargens;

	//bi-directional many-to-one association to Tipusserie
	@ManyToOne
	@JoinColumn(name="TIPUSSERIE_ID")
	private TipusSerie achTipusserie;

	//bi-directional many-to-one association to Serierelacionada
	@OneToMany(mappedBy="achSeriedocumental1",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private Set<Serierelacionada> achSerierelacionadas1;

	//bi-directional many-to-one association to Serierelacionada
	@OneToMany(mappedBy="achSeriedocumental2",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private Set<Serierelacionada> achSerierelacionadas2;

	//bi-directional many-to-one association to Transferencia
	@OneToMany(mappedBy="achSeriedocumental")
	private List<Transferencia> achTransferencias;

	//bi-directional many-to-one association to Valoracio
	@OneToMany(mappedBy="achSeriedocumental",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<Valoracio> achValoracios;

	//bi-directional many-to-one association to TipusdocumentSerie
	@OneToMany(mappedBy="achSeriedocumental",
			cascade = CascadeType.ALL,
			orphanRemoval = true)
	private Set<TipusdocumentSerie> achTipusdocumentSeries;

	@ManyToOne
	@JoinColumn(name="ORGANCOLLEGIAT_ID")
	private OrganCollegiat achOrganCollegiat;

	public Seriedocumental() {
	}
	
	@PreRemove
	private void preRemove() {
	    for (Procediment p : achProcediments) {
	        p.setAchSeriedocumental(null);
	    }
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

	public String getCodiiecisa() {
		return this.codiiecisa;
	}

	public void setCodiiecisa(String codiiecisa) {
		this.codiiecisa = codiiecisa;
	}

	public String getDescripcio() {
		return this.descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}

	public String getDescripciocas() {
		return this.descripciocas;
	}

	public void setDescripciocas(String descripciocas) {
		this.descripciocas = descripciocas;
	}

	public String getDir3Promotor() {
		return this.dir3Promotor;
	}

	public void setDir3Promotor(String dir3Promotor) {
		this.dir3Promotor = dir3Promotor;
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

	public String getResummigracio() {
		return this.resummigracio;
	}

	public void setResummigracio(String resummigracio) {
		this.resummigracio = resummigracio;
	}

	public List<AplicacioSerie> getAchAplicacioSeries() {
		return this.achAplicacioSeries;
	}

	public void setAchAplicacioSeries(List<AplicacioSerie> achAplicacioSeries) {
		this.achAplicacioSeries = achAplicacioSeries;
	}

	public AplicacioSerie addAchAplicacioSery(AplicacioSerie achAplicacioSery) {
		getAchAplicacioSeries().add(achAplicacioSery);
		achAplicacioSery.setAchSeriedocumental(this);

		return achAplicacioSery;
	}

	public AplicacioSerie removeAchAplicacioSery(AplicacioSerie achAplicacioSery) {
		getAchAplicacioSeries().remove(achAplicacioSery);
		achAplicacioSery.setAchSeriedocumental(null);

		return achAplicacioSery;
	}

	public List<Dictamen> getAchDictamens() {
		return this.achDictamens;
	}

	public void setAchDictamens(List<Dictamen> achDictamens) {
		this.achDictamens = achDictamens;
	}

	public Dictamen addAchDictamen(Dictamen achDictamen) {
		getAchDictamens().add(achDictamen);
		achDictamen.setAchSeriedocumental(this);

		return achDictamen;
	}

	public Dictamen removeAchDictamen(Dictamen achDictamen) {
		getAchDictamens().remove(achDictamen);
		achDictamen.setAchSeriedocumental(null);

		return achDictamen;
	}

	public List<LimitacioNormativaSerie> getAchLimitacioNormativaSeries() {
		return this.achLimitacioNormativaSeries;
	}

	public void setAchLimitacioNormativaSeries(List<LimitacioNormativaSerie> achLimitacioNormativaSeries) {
		this.achLimitacioNormativaSeries = achLimitacioNormativaSeries;
	}

	public LimitacioNormativaSerie addAchLimitacioNormativaSery(LimitacioNormativaSerie achLimitacioNormativaSery) {
		getAchLimitacioNormativaSeries().add(achLimitacioNormativaSery);
		achLimitacioNormativaSery.setAchSeriedocumental(this);

		return achLimitacioNormativaSery;
	}

	public LimitacioNormativaSerie removeAchLimitacioNormativaSery(LimitacioNormativaSerie achLimitacioNormativaSery) {
		getAchLimitacioNormativaSeries().remove(achLimitacioNormativaSery);
		achLimitacioNormativaSery.setAchSeriedocumental(null);

		return achLimitacioNormativaSery;
	}

	public List<NormativaSeriedocumental> getAchNormativaSeriedocumentals() {
		return this.achNormativaSeriedocumentals;
	}

	public void setAchNormativaSeriedocumentals(List<NormativaSeriedocumental> achNormativaSeriedocumentals) {
		this.achNormativaSeriedocumentals = achNormativaSeriedocumentals;
	}

	public NormativaSeriedocumental addAchNormativaSeriedocumental(NormativaSeriedocumental achNormativaSeriedocumental) {
		getAchNormativaSeriedocumentals().add(achNormativaSeriedocumental);
		achNormativaSeriedocumental.setAchSeriedocumental(this);

		return achNormativaSeriedocumental;
	}

	public NormativaSeriedocumental removeAchNormativaSeriedocumental(NormativaSeriedocumental achNormativaSeriedocumental) {
		getAchNormativaSeriedocumentals().remove(achNormativaSeriedocumental);
		achNormativaSeriedocumental.setAchSeriedocumental(null);

		return achNormativaSeriedocumental;
	}

	public ProcedimentSerie addAchProcedimentComu(ProcedimentSerie achProcedimentSerie) {
		getAchProcedimentsComuns().add(achProcedimentSerie);
		achProcedimentSerie.setAchSerieDocumental(this);

		return achProcedimentSerie;
	}

	public ProcedimentSerie removeAchProcedimentComu(ProcedimentSerie achProcedimentSerie) {
		getAchProcedimentsComuns().remove(achProcedimentSerie);
		achProcedimentSerie.setAchSerieDocumental(null);

		return achProcedimentSerie;
	}

	public List<Procediment> getAchProcediments() {
		return this.achProcediments;
	}

	public void setAchProcediments(List<Procediment> achProcediments) {
		this.achProcediments = achProcediments;
	}

	public Procediment addAchProcediment(Procediment achProcediment) {
		getAchProcediments().add(achProcediment);
		achProcediment.setAchSeriedocumental(this);

		return achProcediment;
	}

	public Procediment removeAchProcediment(Procediment achProcediment) {
		getAchProcediments().remove(achProcediment);
		achProcediment.setAchSeriedocumental(null);

		return achProcediment;
	}

	public Catalegsery getAchCatalegsery() {
		return this.achCatalegsery;
	}

	public void setAchCatalegsery(Catalegsery achCatalegsery) {
		this.achCatalegsery = achCatalegsery;
	}

	public Funcio getAchFuncio() {
		return this.achFuncio;
	}

	public void setAchFuncio(Funcio achFuncio) {
		this.achFuncio = achFuncio;
	}

	public List<SerieArgen> getAchSerieargens() {
		return this.achSerieargens;
	}

	public void setAchSerieargens(List<SerieArgen> achSerieargens) {
		this.achSerieargens = achSerieargens;
	}

	public List<ProcedimentSerie> getAchProcedimentsComuns() {
		return achProcedimentsComuns;
	}

	public void setAchProcedimentsComuns(List<ProcedimentSerie> achProcedimentsComuns) {
		this.achProcedimentsComuns = achProcedimentsComuns;
	}

	public TipusSerie getAchTipusserie() {
		return this.achTipusserie;
	}

	public void setAchTipusserie(TipusSerie achTipusserie) {
		this.achTipusserie = achTipusserie;
	}

	public Set<Serierelacionada> getAchSerierelacionadas1() {
		return this.achSerierelacionadas1;
	}

	public void setAchSerierelacionadas1(Set<Serierelacionada> achSerierelacionadas1) {
		this.achSerierelacionadas1 = achSerierelacionadas1;
	}

	public Serierelacionada addAchSerierelacionadas1(Serierelacionada achSerierelacionadas1) {
		getAchSerierelacionadas1().add(achSerierelacionadas1);
		achSerierelacionadas1.setAchSeriedocumental1(this);

		return achSerierelacionadas1;
	}

	public Serierelacionada removeAchSerierelacionadas1(Serierelacionada achSerierelacionadas1) {
		getAchSerierelacionadas1().remove(achSerierelacionadas1);
		achSerierelacionadas1.setAchSeriedocumental1(null);

		return achSerierelacionadas1;
	}

	public Set<Serierelacionada> getAchSerierelacionadas2() {
		return this.achSerierelacionadas2;
	}

	public void setAchSerierelacionadas2(Set<Serierelacionada> achSerierelacionadas2) {
		this.achSerierelacionadas2 = achSerierelacionadas2;
	}

	public Serierelacionada addAchSerierelacionadas2(Serierelacionada achSerierelacionadas2) {
		getAchSerierelacionadas2().add(achSerierelacionadas2);
		achSerierelacionadas2.setAchSeriedocumental2(this);

		return achSerierelacionadas2;
	}

	public Serierelacionada removeAchSerierelacionadas2(Serierelacionada achSerierelacionadas2) {
		getAchSerierelacionadas2().remove(achSerierelacionadas2);
		achSerierelacionadas2.setAchSeriedocumental2(null);

		return achSerierelacionadas2;
	}

	public List<Transferencia> getAchTransferencias() {
		return this.achTransferencias;
	}

	public void setAchTransferencias(List<Transferencia> achTransferencias) {
		this.achTransferencias = achTransferencias;
	}

	public Transferencia addAchTransferencia(Transferencia achTransferencia) {
		getAchTransferencias().add(achTransferencia);
		achTransferencia.setAchSeriedocumental(this);

		return achTransferencia;
	}

	public Transferencia removeAchTransferencia(Transferencia achTransferencia) {
		getAchTransferencias().remove(achTransferencia);
		achTransferencia.setAchSeriedocumental(null);

		return achTransferencia;
	}

	public List<Valoracio> getAchValoracios() {
		return this.achValoracios;
	}

	public void setAchValoracios(List<Valoracio> achValoracios) {
		this.achValoracios = achValoracios;
	}

	public Valoracio addAchValoracio(Valoracio achValoracio) {
		getAchValoracios().add(achValoracio);
		achValoracio.setAchSeriedocumental(this);

		return achValoracio;
	}

	public Valoracio removeAchValoracio(Valoracio achValoracio) {
		getAchValoracios().remove(achValoracio);
		achValoracio.setAchSeriedocumental(null);

		return achValoracio;
	}

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public Boolean getEnviatSAT() {
		return enviatSAT;
	}

	public void setEnviatSAT(Boolean enviatSAT) {
		this.enviatSAT = enviatSAT;
	}

	public Set<TipusdocumentSerie> getAchTipusdocumentSeries() {
		return achTipusdocumentSeries;
	}

	public void setAchTipusdocumentSeries(Set<TipusdocumentSerie> achTipusdocumentSeries) {
		this.achTipusdocumentSeries = achTipusdocumentSeries;
	}

	public TipusdocumentSerie addAchTipusdocumentSerie(TipusdocumentSerie achTipusdocumentSerie) {
		getAchTipusdocumentSeries().add(achTipusdocumentSerie);
		achTipusdocumentSerie.setAchSeriedocumental(this);

		return achTipusdocumentSerie;
	}

	public TipusdocumentSerie removeAchTipusdocumentSerie(TipusdocumentSerie achTipusdocumentSerie) {
		getAchTipusdocumentSeries().remove(achTipusdocumentSerie);
		achTipusdocumentSerie.setAchSeriedocumental(null);

		return achTipusdocumentSerie;
	}

	public OrganCollegiat getAchOrganCollegiat() {
		return achOrganCollegiat;
	}

	public void setAchOrganCollegiat(OrganCollegiat achOrganCollegiat) {
		this.achOrganCollegiat = achOrganCollegiat;
	}

	@Override
	public String toString() {
		return "Seriedocumental [id=" +
				id +
				", codi=" +
				codi +
				", nom=" +
				nom +
				"]";
	}

}
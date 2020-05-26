package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


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
	private long id;

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

	//bi-directional many-to-one association to AplicacioSerie
	@OneToMany(mappedBy="achSeriedocumental",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<AplicacioSerie> achAplicacioSeries;

	//bi-directional many-to-one association to Dictamen
	@OneToMany(mappedBy="achSeriedocumental")
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
	private List<Serieargen> achSerieargens;

	//bi-directional many-to-one association to Tipusserie
	@ManyToOne
	@JoinColumn(name="TIPUSSERIE_ID")
	private Tipusserie achTipusserie;

	//bi-directional many-to-one association to Serierelacionada
	@OneToMany(mappedBy="achSeriedocumental1",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<Serierelacionada> achSerierelacionadas1;

	//bi-directional many-to-one association to Serierelacionada
	@OneToMany(mappedBy="achSeriedocumental2",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<Serierelacionada> achSerierelacionadas2;

	//bi-directional many-to-one association to Transferencia
	@OneToMany(mappedBy="achSeriedocumental")
	private List<Transferencia> achTransferencias;

	//bi-directional many-to-one association to Valoracio
	@OneToMany(mappedBy="achSeriedocumental",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<Valoracio> achValoracios;

	public Seriedocumental() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
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

	public List<Serieargen> getAchSerieargens() {
		return this.achSerieargens;
	}

	public void setAchSerieargens(List<Serieargen> achSerieargens) {
		this.achSerieargens = achSerieargens;
	}

	public Tipusserie getAchTipusserie() {
		return this.achTipusserie;
	}

	public void setAchTipusserie(Tipusserie achTipusserie) {
		this.achTipusserie = achTipusserie;
	}

	public List<Serierelacionada> getAchSerierelacionadas1() {
		return this.achSerierelacionadas1;
	}

	public void setAchSerierelacionadas1(List<Serierelacionada> achSerierelacionadas1) {
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

	public List<Serierelacionada> getAchSerierelacionadas2() {
		return this.achSerierelacionadas2;
	}

	public void setAchSerierelacionadas2(List<Serierelacionada> achSerierelacionadas2) {
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
	
	

}
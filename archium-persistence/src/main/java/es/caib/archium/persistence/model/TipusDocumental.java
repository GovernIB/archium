package es.caib.archium.persistence.model;

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
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Size;

import es.caib.archium.persistence.model.validation.ValidacioBasica;
import es.caib.archium.persistence.model.validation.ValidacioCompleta;

import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACH_TIPUSDOCUMENTAL database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSDOCUMENTAL")
@TaulaMestra(excludedFields = {"id", "achDictamenTipusdocumentals", "achNormativas", "achTipusdocumentProcediments"})
@NamedQuery(name="Tipusdocumental.findAll", query="SELECT t FROM TipusDocumental t")
public class TipusDocumental implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSDOCUMENTAL_ID_GENERATOR", sequenceName="ACH_TIPUSDOCUMENTAL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSDOCUMENTAL_ID_GENERATOR")
	private Long id;
	
	@NotNull(groups=ValidacioCompleta.class, message = "{tipusdocumental.entitat.error.codi.nul}")
	@Pattern(
		    regexp = "^TD[\\p{Alnum}]{2}-\\d{3}$",
		    groups = ValidacioCompleta.class,
		    message = "{tipusdocumental.entitat.error.codi.patro}"
	)
	@Column(unique=true)
	private String codi;

	@NotBlank(message = "{tipusdocumental.entitat.error.definicio.buit}")
	@Size(max=4000,groups=ValidacioBasica.class, message = "{tipusdocumental.entitat.error.definicio.mida}")
	private String definicio;
	
	@Size(max=4000,groups=ValidacioBasica.class, message = "{tipusdocumental.entitat.error.definiciocas.mida}")
	private String definiciocas;

	@NotNull(groups=ValidacioCompleta.class, message = "{tipusdocumental.entitat.error.estat.nul}")
	private String estat;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fi;

	@NotNull(message = "{tipusdocumental.entitat.error.inici.nul}")
	@Temporal(TemporalType.TIMESTAMP)
	private Date inici;

	@NotNull(message = "{tipusdocumental.entitat.error.modificacio.nul}")
	@Temporal(TemporalType.TIMESTAMP)
	private Date modificacio;
	
	// Fecha en la que re realizó el último cambio de estado
	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "DATA_CANVI_ESTAT")
	private Date canviEstat;
	
	// Usuario que dio de alta el tipo documental
	@Column(name = "USUARI_ALTA")
	private String usuariInici;
	
	// Usuario que modificó el tipo documental por última vez
	@Column(name = "USUARI_MODIFICACIO")
	private String usuariModificacio;
	
	// Usuario que realizó el último cambio de estado
	@Column(name = "USUARI_CANVI_ESTAT")
	private String usuariCanviEstat;

	@NotBlank(groups=ValidacioBasica.class, message = "{tipusdocumental.entitat.error.nom.buit}")
	@Size(max=1000,groups=ValidacioBasica.class, message = "{tipusdocumental.entitat.error.nom.mida}")
	private String nom;
	
	@Size(max=1000,groups=ValidacioBasica.class, message = "{tipusdocumental.entitat.error.nomcas.mida}")
	private String nomcas;
	
	@Size(max=4000,groups=ValidacioBasica.class, message = "{tipusdocumental.entitat.error.observacions.mida}")
	private String observacions;

	//bi-directional many-to-one association to DictamenTipusdocumental
	@OneToMany(mappedBy="achTipusdocumental")
	private List<DictamenTipusdocumental> achDictamenTipusdocumentals;

	//bi-directional many-to-many association to Normativa
	@ManyToMany
	@JoinTable(
		name="ACH_NORMATIVA_TIPUSDOCUMENTAL"
		, joinColumns={
			@JoinColumn(name="TIPUSDOCUMENTAL_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="NORMATIVA_ID")
			}
		)
	private List<Normativa> achNormativas;

	//bi-directional many-to-one association to Quadretipusdocumental
	@ManyToOne
	@JoinColumn(name="QUADRETIPUSDOCUMENTAL_ID")
	private QuadreTipusDocumental quadreTipusDocumental;

	//bi-directional many-to-one association to Tipusnti
	@ManyToOne
	@JoinColumn(name="TIPUSNTI_ID")
	private TipusNti tipusNti;

	//bi-directional many-to-one association to TipusdocumentProcediment
	@OneToMany(mappedBy="achTipusdocumental")
	private List<TipusdocumentProcediment> achTipusdocumentProcediments;

	public TipusDocumental() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OrdreVisual(ordre = 100)
	public String getCodi() {
		return this.codi;
	}

	public void setCodi(String codi) {
		this.codi = codi;
	}

	@OrdreVisual(ordre = 300)
	public String getDefinicio() {
		return this.definicio;
	}

	public void setDefinicio(String definicio) {
		this.definicio = definicio;
	}

	@OrdreVisual(ordre = 400)
	public String getDefiniciocas() {
		return this.definiciocas;
	}

	public void setDefiniciocas(String definiciocas) {
		this.definiciocas = definiciocas;
	}

	@OrdreVisual(ordre = 600)
	public String getEstat() {
		return this.estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	@OrdreVisual(ordre = 900)
	public Date getFi() {
		return this.fi;
	}

	public void setFi(Date fi) {
		this.fi = fi;
	}

	@OrdreVisual(ordre = 700)
	public Date getInici() {
		return this.inici;
	}

	public void setInici(Date inici) {
		this.inici = inici;
	}

	@OrdreVisual(ordre = 800)
	public Date getModificacio() {
		return this.modificacio;
	}

	public void setModificacio(Date modificacio) {
		this.modificacio = modificacio;
	}

	@OrdreVisual(ordre = 200)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OrdreVisual(ordre = 250)
	public String getNomcas() {
		return this.nomcas;
	}

	public void setNomcas(String nomcas) {
		this.nomcas = nomcas;
	}
	
	@OrdreVisual(ordre = 450)
	public String getObservacions() {
		return this.observacions;
	}

	public void setObservacions(String observacions) {
		this.observacions = observacions;
	}

	public List<DictamenTipusdocumental> getAchDictamenTipusdocumentals() {
		return this.achDictamenTipusdocumentals;
	}

	public void setAchDictamenTipusdocumentals(List<DictamenTipusdocumental> achDictamenTipusdocumentals) {
		this.achDictamenTipusdocumentals = achDictamenTipusdocumentals;
	}

	public DictamenTipusdocumental addAchDictamenTipusdocumental(DictamenTipusdocumental achDictamenTipusdocumental) {
		getAchDictamenTipusdocumentals().add(achDictamenTipusdocumental);
		achDictamenTipusdocumental.setAchTipusdocumental(this);

		return achDictamenTipusdocumental;
	}

	public DictamenTipusdocumental removeAchDictamenTipusdocumental(DictamenTipusdocumental achDictamenTipusdocumental) {
		getAchDictamenTipusdocumentals().remove(achDictamenTipusdocumental);
		achDictamenTipusdocumental.setAchTipusdocumental(null);

		return achDictamenTipusdocumental;
	}

	public List<Normativa> getAchNormativas() {
		return this.achNormativas;
	}

	public void setAchNormativas(List<Normativa> achNormativas) {
		this.achNormativas = achNormativas;
	}

	public QuadreTipusDocumental getQuadreTipusDocumental() {
		return this.quadreTipusDocumental;
	}

	public void setQuadreTipusDocumental(QuadreTipusDocumental achQuadretipusdocumental) {
		this.quadreTipusDocumental = achQuadretipusdocumental;
	}

	@OrdreVisual(ordre = 500)
	public TipusNti getTipusNti() {
		return this.tipusNti;
	}

	public void setTipusNti(TipusNti achTipusnti) {
		this.tipusNti = achTipusnti;
	}

	public List<TipusdocumentProcediment> getAchTipusdocumentProcediments() {
		return this.achTipusdocumentProcediments;
	}

	public void setAchTipusdocumentProcediments(List<TipusdocumentProcediment> achTipusdocumentProcediments) {
		this.achTipusdocumentProcediments = achTipusdocumentProcediments;
	}

	public TipusdocumentProcediment addAchTipusdocumentProcediment(TipusdocumentProcediment achTipusdocumentProcediment) {
		getAchTipusdocumentProcediments().add(achTipusdocumentProcediment);
		achTipusdocumentProcediment.setAchTipusdocumental(this);

		return achTipusdocumentProcediment;
	}

	public TipusdocumentProcediment removeAchTipusdocumentProcediment(TipusdocumentProcediment achTipusdocumentProcediment) {
		getAchTipusdocumentProcediments().remove(achTipusdocumentProcediment);
		achTipusdocumentProcediment.setAchTipusdocumental(null);

		return achTipusdocumentProcediment;
	}
	
	@Override public String toString() { 
		return "TipusDocumental [id=" + id + ", codi=" + codi + ", nom=" + nom + "]"; }

	public Date getCanviEstat() {
		return canviEstat;
	}

	public void setCanviEstat(Date canviEstat) {
		this.canviEstat = canviEstat;
	}

	public String getUsuariInici() {
		return usuariInici;
	}

	public void setUsuariInici(String usuariInici) {
		this.usuariInici = usuariInici;
	}

	public String getUsuariModificacio() {
		return usuariModificacio;
	}

	public void setUsuariModificacio(String usuariModificacio) {
		this.usuariModificacio = usuariModificacio;
	}

	public String getUsuariCanviEstat() {
		return usuariCanviEstat;
	}

	public void setUsuariCanviEstat(String usuariCanviEstat) {
		this.usuariCanviEstat = usuariCanviEstat;
	}

}
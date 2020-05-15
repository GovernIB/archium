package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACH_PROCEDIMENT database table.
 * 
 */
@Entity
@Table(name="ACH_PROCEDIMENT")
@NamedQuery(name="Procediment.findAll", query="SELECT p FROM Procediment p")
public class Procediment implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_PROCEDIMENT_ID_GENERATOR", sequenceName="ACH_PROCEDIMENT_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_PROCEDIMENT_ID_GENERATOR")
	private long id;

	@Temporal(TemporalType.DATE)
	private Date caducitat;

	private String codirolsac;

	private String codisia;

	private String destinataris;

	@Column(name="DIR3_INSTRUCTOR")
	private String dir3Instructor;

	@Column(name="DIR3_RESOLVENT")
	private String dir3Resolvent;

	private String estat;

	private BigDecimal fiviaadministrativa;

	private String gestor;

	@Temporal(TemporalType.DATE)
	private Date modificacio;

	private String nom;

	private String objecte;

	private String observacions;

	@Temporal(TemporalType.DATE)
	private Date publicacio;

	private BigDecimal taxa;

	private String termini;

	private String termininotif;

	private String uri;

	//bi-directional many-to-one association to ContacteProcediment
	@OneToMany(mappedBy="achProcediment")
	private List<ContacteProcediment> achContacteProcediments;

	//bi-directional many-to-one association to Aplicacio
	@ManyToOne
	@JoinColumn(name="APLICACIO_ID")
	private Aplicacio achAplicacio;

	//bi-directional many-to-one association to Familiaprocediment
	@ManyToOne
	@JoinColumn(name="FAMILIAPROCEDIMENT_ID")
	private Familiaprocediment achFamiliaprocediment;

	//bi-directional many-to-one association to Formainici
	@ManyToOne
	@JoinColumn(name="FORMAINICI_ID")
	private Formainici achFormainici;

	//bi-directional many-to-many association to Materia
	@ManyToMany
	@JoinTable(
		name="ACH_MATERIA_PROCEDIMENT"
		, joinColumns={
			@JoinColumn(name="PROCEDIMENT_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="MATERIA_ID")
			}
		)
	private List<Materia> achMaterias;

	//bi-directional many-to-one association to Nivellelectronic
	@ManyToOne
	@JoinColumn(name="NIVELLELECTRONIC_ID")
	private Nivellelectronic achNivellelectronic;

	//bi-directional many-to-many association to Normativa
	@ManyToMany
	@JoinTable(
		name="ACH_NORMATIVA_PROCEDIMENT"
		, joinColumns={
			@JoinColumn(name="PROCEDIMENT_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="NORMATIVA_ID")
			}
		)
	private List<Normativa> achNormativas;

	//bi-directional many-to-one association to Seriedocumental
	@ManyToOne
	@JoinColumn(name="SERIEDOCUMENTAL_ID")
	private Seriedocumental achSeriedocumental;

	//bi-directional many-to-one association to Silenci
	@ManyToOne
	@JoinColumn(name="SILENCI_ID")
	private Silenci achSilenci;

	//bi-directional many-to-one association to TipusdocumentProcediment
	@OneToMany(mappedBy="achProcediment",
			cascade = CascadeType.ALL,
	        orphanRemoval = true)
	private List<TipusdocumentProcediment> achTipusdocumentProcediments;

	//bi-directional many-to-many association to Tipuspublic
	@ManyToMany
	@JoinTable(
		name="ACH_TIPUSPUBLIC_PROCEDIMENT"
		, joinColumns={
			@JoinColumn(name="PROCEDIMENT_ID")
			}
		, inverseJoinColumns={
			@JoinColumn(name="TIPUSPUBLIC_ID")
			}
	)
	private List<Tipuspublic> achTipuspublics;
	
	
	public Procediment() {
	}

	public long getId() {
		return this.id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public Date getCaducitat() {
		return this.caducitat;
	}

	public void setCaducitat(Date caducitat) {
		this.caducitat = caducitat;
	}

	public String getCodirolsac() {
		return this.codirolsac;
	}

	public void setCodirolsac(String codirolsac) {
		this.codirolsac = codirolsac;
	}

	public String getCodisia() {
		return this.codisia;
	}

	public void setCodisia(String codisia) {
		this.codisia = codisia;
	}

	public String getDestinataris() {
		return this.destinataris;
	}

	public void setDestinataris(String destinataris) {
		this.destinataris = destinataris;
	}

	public String getDir3Instructor() {
		return this.dir3Instructor;
	}

	public void setDir3Instructor(String dir3Instructor) {
		this.dir3Instructor = dir3Instructor;
	}

	public String getDir3Resolvent() {
		return this.dir3Resolvent;
	}

	public void setDir3Resolvent(String dir3Resolvent) {
		this.dir3Resolvent = dir3Resolvent;
	}

	public String getEstat() {
		return this.estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public BigDecimal getFiviaadministrativa() {
		return this.fiviaadministrativa;
	}

	public void setFiviaadministrativa(BigDecimal fiviaadministrativa) {
		this.fiviaadministrativa = fiviaadministrativa;
	}

	public String getGestor() {
		return this.gestor;
	}

	public void setGestor(String gestor) {
		this.gestor = gestor;
	}

	public Date getModificacio() {
		return this.modificacio;
	}

	public void setModificacio(Date modificacio) {
		this.modificacio = modificacio;
	}

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getObjecte() {
		return this.objecte;
	}

	public void setObjecte(String objecte) {
		this.objecte = objecte;
	}

	public String getObservacions() {
		return this.observacions;
	}

	public void setObservacions(String observacions) {
		this.observacions = observacions;
	}

	public Date getPublicacio() {
		return this.publicacio;
	}

	public void setPublicacio(Date publicacio) {
		this.publicacio = publicacio;
	}

	public BigDecimal getTaxa() {
		return this.taxa;
	}

	public void setTaxa(BigDecimal taxa) {
		this.taxa = taxa;
	}

	public String getTermini() {
		return this.termini;
	}

	public void setTermini(String termini) {
		this.termini = termini;
	}

	public String getTermininotif() {
		return this.termininotif;
	}

	public void setTermininotif(String termininotif) {
		this.termininotif = termininotif;
	}

	public String getUri() {
		return this.uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public List<ContacteProcediment> getAchContacteProcediments() {
		return this.achContacteProcediments;
	}

	public void setAchContacteProcediments(List<ContacteProcediment> achContacteProcediments) {
		this.achContacteProcediments = achContacteProcediments;
	}

	public ContacteProcediment addAchContacteProcediment(ContacteProcediment achContacteProcediment) {
		getAchContacteProcediments().add(achContacteProcediment);
		achContacteProcediment.setAchProcediment(this);

		return achContacteProcediment;
	}

	public ContacteProcediment removeAchContacteProcediment(ContacteProcediment achContacteProcediment) {
		getAchContacteProcediments().remove(achContacteProcediment);
		achContacteProcediment.setAchProcediment(null);

		return achContacteProcediment;
	}

	public Aplicacio getAchAplicacio() {
		return this.achAplicacio;
	}

	public void setAchAplicacio(Aplicacio achAplicacio) {
		this.achAplicacio = achAplicacio;
	}

	public Familiaprocediment getAchFamiliaprocediment() {
		return this.achFamiliaprocediment;
	}

	public void setAchFamiliaprocediment(Familiaprocediment achFamiliaprocediment) {
		this.achFamiliaprocediment = achFamiliaprocediment;
	}

	public Formainici getAchFormainici() {
		return this.achFormainici;
	}

	public void setAchFormainici(Formainici achFormainici) {
		this.achFormainici = achFormainici;
	}

	public List<Materia> getAchMaterias() {
		return this.achMaterias;
	}

	public void setAchMaterias(List<Materia> achMaterias) {
		this.achMaterias = achMaterias;
	}

	public Nivellelectronic getAchNivellelectronic() {
		return this.achNivellelectronic;
	}

	public void setAchNivellelectronic(Nivellelectronic achNivellelectronic) {
		this.achNivellelectronic = achNivellelectronic;
	}

	public List<Normativa> getAchNormativas() {
		return this.achNormativas;
	}

	public void setAchNormativas(List<Normativa> achNormativas) {
		this.achNormativas = achNormativas;
	}

	public Seriedocumental getAchSeriedocumental() {
		return this.achSeriedocumental;
	}

	public void setAchSeriedocumental(Seriedocumental achSeriedocumental) {
		this.achSeriedocumental = achSeriedocumental;
	}

	public Silenci getAchSilenci() {
		return this.achSilenci;
	}

	public void setAchSilenci(Silenci achSilenci) {
		this.achSilenci = achSilenci;
	}

	public List<TipusdocumentProcediment> getAchTipusdocumentProcediments() {
		return this.achTipusdocumentProcediments;
	}

	public void setAchTipusdocumentProcediments(List<TipusdocumentProcediment> achTipusdocumentProcediments) {
		this.achTipusdocumentProcediments = achTipusdocumentProcediments;
	}

	public TipusdocumentProcediment addAchTipusdocumentProcediment(TipusdocumentProcediment achTipusdocumentProcediment) {
		getAchTipusdocumentProcediments().add(achTipusdocumentProcediment);
		achTipusdocumentProcediment.setAchProcediment(this);

		return achTipusdocumentProcediment;
	}

	public TipusdocumentProcediment removeAchTipusdocumentProcediment(TipusdocumentProcediment achTipusdocumentProcediment) {
		getAchTipusdocumentProcediments().remove(achTipusdocumentProcediment);
		achTipusdocumentProcediment.setAchProcediment(null);

		return achTipusdocumentProcediment;
	}

	public List<Tipuspublic> getAchTipuspublics() {
		return this.achTipuspublics;
	}

	public void setAchTipuspublics(List<Tipuspublic> achTipuspublics) {
		this.achTipuspublics = achTipuspublics;
	}

}
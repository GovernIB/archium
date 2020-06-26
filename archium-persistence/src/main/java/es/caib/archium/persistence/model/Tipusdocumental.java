package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACH_TIPUSDOCUMENTAL database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSDOCUMENTAL")
@NamedQuery(name="Tipusdocumental.findAll", query="SELECT t FROM Tipusdocumental t")
public class Tipusdocumental implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSDOCUMENTAL_ID_GENERATOR", sequenceName="ACH_TIPUSDOCUMENTAL_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSDOCUMENTAL_ID_GENERATOR")
	private Long id;

	private String codi;

	private String definicio;

	private String definiciocas;

	private String estat;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date inici;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modificacio;

	private String nom;

	private String nomcas;

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
	private Quadretipusdocumental achQuadretipusdocumental;

	//bi-directional many-to-one association to Tipusnti
	@ManyToOne
	@JoinColumn(name="TIPUSNTI_ID")
	private Tipusnti achTipusnti;

	//bi-directional many-to-one association to TipusdocumentProcediment
	@OneToMany(mappedBy="achTipusdocumental")
	private List<TipusdocumentProcediment> achTipusdocumentProcediments;

	public Tipusdocumental() {
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

	public String getDefinicio() {
		return this.definicio;
	}

	public void setDefinicio(String definicio) {
		this.definicio = definicio;
	}

	public String getDefiniciocas() {
		return this.definiciocas;
	}

	public void setDefiniciocas(String definiciocas) {
		this.definiciocas = definiciocas;
	}

	public String getEstat() {
		return this.estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
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

	public String getNomcas() {
		return this.nomcas;
	}

	public void setNomcas(String nomcas) {
		this.nomcas = nomcas;
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

	public Quadretipusdocumental getAchQuadretipusdocumental() {
		return this.achQuadretipusdocumental;
	}

	public void setAchQuadretipusdocumental(Quadretipusdocumental achQuadretipusdocumental) {
		this.achQuadretipusdocumental = achQuadretipusdocumental;
	}

	public Tipusnti getAchTipusnti() {
		return this.achTipusnti;
	}

	public void setAchTipusnti(Tipusnti achTipusnti) {
		this.achTipusnti = achTipusnti;
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

}
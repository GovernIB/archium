package es.caib.archium.persistence.model;

import es.caib.archium.persistence.funcional.ObsolescenteAbstract;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACH_APLICACIO database table.
 * 
 */
@Entity
@Table(name="ACH_APLICACIO")
@NamedQuery(name="Aplicacio.findAll", query="SELECT a FROM Aplicacio a")
public class Aplicacio extends ObsolescenteAbstract {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_APLICACIO_ID_GENERATOR", sequenceName="ACH_APLICACIO_SEQ" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_APLICACIO_ID_GENERATOR")
	private Long id;

	private String estat;

	@Temporal(TemporalType.DATE)
	private Date fi;

	@Temporal(TemporalType.DATE)
	private Date inici;

	private String nom;

	private String perfil;

	private String uridev;

	private String uripro;

	//bi-directional many-to-one association to AplicacioSerie
	@OneToMany(mappedBy="achAplicacio")
	private List<AplicacioSerie> achAplicacioSeries;

	//bi-directional many-to-one association to Procediment
	@OneToMany(mappedBy="achAplicacio")
	private List<Procediment> achProcediments;

	public Aplicacio() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getEstat() {
		return this.estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	@Override
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

	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getPerfil() {
		return this.perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	public String getUridev() {
		return this.uridev;
	}

	public void setUridev(String uridev) {
		this.uridev = uridev;
	}

	public String getUripro() {
		return this.uripro;
	}

	public void setUripro(String uripro) {
		this.uripro = uripro;
	}

	public List<AplicacioSerie> getAchAplicacioSeries() {
		return this.achAplicacioSeries;
	}

	public void setAchAplicacioSeries(List<AplicacioSerie> achAplicacioSeries) {
		this.achAplicacioSeries = achAplicacioSeries;
	}

	public AplicacioSerie addAchAplicacioSery(AplicacioSerie achAplicacioSery) {
		getAchAplicacioSeries().add(achAplicacioSery);
		achAplicacioSery.setAchAplicacio(this);

		return achAplicacioSery;
	}

	public AplicacioSerie removeAchAplicacioSery(AplicacioSerie achAplicacioSery) {
		getAchAplicacioSeries().remove(achAplicacioSery);
		achAplicacioSery.setAchAplicacio(null);

		return achAplicacioSery;
	}

	public List<Procediment> getAchProcediments() {
		return this.achProcediments;
	}

	public void setAchProcediments(List<Procediment> achProcediments) {
		this.achProcediments = achProcediments;
	}

	public Procediment addAchProcediment(Procediment achProcediment) {
		getAchProcediments().add(achProcediment);
		achProcediment.setAchAplicacio(this);

		return achProcediment;
	}

	public Procediment removeAchProcediment(Procediment achProcediment) {
		getAchProcediments().remove(achProcediment);
		achProcediment.setAchAplicacio(null);

		return achProcediment;
	}

}
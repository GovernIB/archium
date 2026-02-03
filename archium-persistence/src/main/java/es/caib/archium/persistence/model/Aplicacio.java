package es.caib.archium.persistence.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import java.io.Serializable;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACH_APLICACIO database table.
 * 
 */
@Entity
@Table(name="ACH_APLICACIO")
@TaulaMestra(excludedFields = {"id", "achAplicacioSeries","achProcediments"})
@NamedQuery(name="Aplicacio.findAll", query="SELECT a FROM Aplicacio a")
public class Aplicacio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_APLICACIO_ID_GENERATOR", sequenceName="ACH_APLICACIO_SEQ" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_APLICACIO_ID_GENERATOR")
	private Long id;

	@NotNull
	private String nom;

	@NotNull
	private String estat;

	@Temporal(TemporalType.DATE)
	@NotNull
	private Date inici;

	@Temporal(TemporalType.DATE)
	private Date fi;

	@NotNull
	private String perfil;

	private String uridev;
	private String uripro;

	//bi-directional many-to-one association to AplicacioSerie
	@OneToMany(mappedBy="achAplicacio")
	private List<AplicacioSerie> achAplicacioSeries;

	public Aplicacio() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	@OrdreVisual(ordre = 0)
	public String getNom() {
		return this.nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	@OrdreVisual(ordre = 1)
	public String getEstat() {
		return this.estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	@OrdreVisual(ordre = 3)
	public Date getInici() {
		return this.inici;
	}

	public void setInici(Date inici) {
		this.inici = inici;
	}

	public Date getFi() {
		return this.fi;
	}

	@OrdreVisual(ordre = 4)
	public void setFi(Date fi) {
		this.fi = fi;
	}

	@OrdreVisual(ordre = 2)
	public String getPerfil() {
		return this.perfil;
	}

	public void setPerfil(String perfil) {
		this.perfil = perfil;
	}

	@OrdreVisual(ordre = 5)
	public String getUridev() {
		return this.uridev;
	}

	public void setUridev(String uridev) {
		this.uridev = uridev;
	}

	@OrdreVisual(ordre = 6)
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

	// TODO OJO! Ninguna entidad contiene equal y hascode...puede suponer un problema a la hora de insertar registros que tengan referencias. También a la hora de búsquedas e inserción en colecciones...
}
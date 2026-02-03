package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


/**
 * The persistent class for the ACH_QUADRECLASSIFICACIO database table.
 * 
 */
@Entity
@Table(name="ACH_QUADRECLASSIFICACIO")
@NamedQuery(name="Quadreclassificacio.findAll", query="SELECT q FROM Quadreclassificacio q")
public class Quadreclassificacio implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_QUADRECLASSIFICACIO_ID_GENERATOR", allocationSize = 1, sequenceName="ACH_QUADRECLASSIFICACIO_SEQ")
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_QUADRECLASSIFICACIO_ID_GENERATOR")
	private Long id;

	private String estat;

	@Temporal(TemporalType.TIMESTAMP)
	private Date fi;

	@Temporal(TemporalType.TIMESTAMP)
	private Date inici;

	@Temporal(TemporalType.TIMESTAMP)
	private Date modificacio;

	private String nom;

	private String nomcas;

	private String versio;

	//bi-directional many-to-one association to Funcio
	@OneToMany(mappedBy="achQuadreclassificacio")
	private List<Funcio> achFuncios;

	public Quadreclassificacio() {
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

	public String getVersio() {
		return this.versio;
	}

	public void setVersio(String versio) {
		this.versio = versio;
	}

	public List<Funcio> getAchFuncios() {
		return this.achFuncios;
	}

	public void setAchFuncios(List<Funcio> achFuncios) {
		this.achFuncios = achFuncios;
	}

	public Funcio addAchFuncio(Funcio achFuncio) {
		getAchFuncios().add(achFuncio);
		achFuncio.setAchQuadreclassificacio(this);

		return achFuncio;
	}

	public Funcio removeAchFuncio(Funcio achFuncio) {
		getAchFuncios().remove(achFuncio);
		achFuncio.setAchQuadreclassificacio(null);

		return achFuncio;
	}

}
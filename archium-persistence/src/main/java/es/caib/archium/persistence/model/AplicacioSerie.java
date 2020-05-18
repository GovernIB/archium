package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ACH_APLICACIO_SERIE database table.
 * 
 */
@Entity
@Table(name="ACH_APLICACIO_SERIE")
@NamedQuery(name="AplicacioSerie.findAll", query="SELECT a FROM AplicacioSerie a")
public class AplicacioSerie implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private AplicacioSeriePK id;

	private String descripcio;

	@Temporal(TemporalType.DATE)
	private Date fi;

	@Temporal(TemporalType.DATE)
	private Date inici;

	//bi-directional many-to-one association to Aplicacio
	@ManyToOne
	@MapsId("aplicacioId")
	@JoinColumn(name="APLICACIO_ID" ,insertable=false,updatable=false)
	private Aplicacio achAplicacio;

	//bi-directional many-to-one association to Seriedocumental
	@ManyToOne
	@MapsId("seriedocumentalId")
	@JoinColumn(name="SERIEDOCUMENTAL_ID" ,insertable=false,updatable=false)
	private Seriedocumental achSeriedocumental;

	public AplicacioSerie() {
	}

	public AplicacioSeriePK getId() {
		return this.id;
	}

	public void setId(AplicacioSeriePK id) {
		this.id = id;
	}

	public String getDescripcio() {
		return this.descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
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

	public Aplicacio getAchAplicacio() {
		return this.achAplicacio;
	}

	public void setAchAplicacio(Aplicacio achAplicacio) {
		this.achAplicacio = achAplicacio;
	}

	public Seriedocumental getAchSeriedocumental() {
		return this.achSeriedocumental;
	}

	public void setAchSeriedocumental(Seriedocumental achSeriedocumental) {
		this.achSeriedocumental = achSeriedocumental;
	}

}
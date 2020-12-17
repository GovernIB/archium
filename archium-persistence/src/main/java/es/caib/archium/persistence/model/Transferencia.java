package es.caib.archium.persistence.model;

import es.caib.archium.persistence.funcional.ObsolescenteAbstract;

import javax.persistence.*;
import java.util.Date;


/**
 * The persistent class for the ACH_TRANSFERENCIA database table.
 * 
 */
@Entity
@Table(name="ACH_TRANSFERENCIA")
@NamedQuery(name="Transferencia.findAll", query="SELECT t FROM Transferencia t")
public class Transferencia extends ObsolescenteAbstract {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TRANSFERENCIA_ID_GENERATOR", sequenceName="ACH_TRANSFERENCIA_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TRANSFERENCIA_ID_GENERATOR")
	private Long id;

	@Temporal(TemporalType.DATE)
	private Date fi;

	@Temporal(TemporalType.DATE)
	private Date inici;

	private String termini;

	//bi-directional many-to-one association to Fasearxiu
	@ManyToOne
	@JoinColumn(name="FASEARXIU_ID")
	private Fasearxiu achFasearxiu;

	//bi-directional many-to-one association to Seriedocumental
	@ManyToOne
	@JoinColumn(name="SERIEDOCUMENTAL_ID")
	private Seriedocumental achSeriedocumental;

	public Transferencia() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public String getTermini() {
		return this.termini;
	}

	public void setTermini(String termini) {
		this.termini = termini;
	}

	public Fasearxiu getAchFasearxiu() {
		return this.achFasearxiu;
	}

	public void setAchFasearxiu(Fasearxiu achFasearxiu) {
		this.achFasearxiu = achFasearxiu;
	}

	public Seriedocumental getAchSeriedocumental() {
		return this.achSeriedocumental;
	}

	public void setAchSeriedocumental(Seriedocumental achSeriedocumental) {
		this.achSeriedocumental = achSeriedocumental;
	}

}
package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import java.util.List;


/**
 * The persistent class for the ACH_FASEARXIU database table.
 * 
 */
@Entity
@Table(name="ACH_FASEARXIU")
@NamedQuery(name="Fasearxiu.findAll", query="SELECT f FROM Fasearxiu f")
public class Fasearxiu implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_FASEARXIU_ID_GENERATOR", sequenceName="ACH_FASEARXIU_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_FASEARXIU_ID_GENERATOR")
	private Long id;

	private String nom;

	private String nomcas;

	//bi-directional many-to-one association to Transferencia
	@OneToMany(mappedBy="achFasearxiu")
	private List<Transferencia> achTransferencias;

	public Fasearxiu() {
	}

	public Long getId() {
		return this.id;
	}

	public void setId(Long id) {
		this.id = id;
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

	public List<Transferencia> getAchTransferencias() {
		return this.achTransferencias;
	}

	public void setAchTransferencias(List<Transferencia> achTransferencias) {
		this.achTransferencias = achTransferencias;
	}

	public Transferencia addAchTransferencia(Transferencia achTransferencia) {
		getAchTransferencias().add(achTransferencia);
		achTransferencia.setAchFasearxiu(this);

		return achTransferencia;
	}

	public Transferencia removeAchTransferencia(Transferencia achTransferencia) {
		getAchTransferencias().remove(achTransferencia);
		achTransferencia.setAchFasearxiu(null);

		return achTransferencia;
	}

}
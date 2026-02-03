package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;
import java.util.List;


/**
 * The persistent class for the ACH_CATEGORIANTI database table.
 * 
 */
@Entity
@Table(name="ACH_CATEGORIANTI")
@TaulaMestra(excludedFields = {"id", "achTipusntis"})
@NamedQuery(name="Categorianti.findAll", query="SELECT c FROM CategoriaNti c")
public class CategoriaNti implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_CATEGORIANTI_ID_GENERATOR", sequenceName="ACH_CATEGORIANTI_SEQ" , allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_CATEGORIANTI_ID_GENERATOR")
	private Long id;

	@NotNull
	private String nom;

	@NotNull
	private String nomcas;

	//bi-directional many-to-one association to Tipusnti
	@OneToMany(mappedBy="achCategorianti")
	private List<TipusNti> achTipusntis;

	public CategoriaNti() {
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

	public List<TipusNti> getAchTipusntis() {
		return this.achTipusntis;
	}

	public void setAchTipusntis(List<TipusNti> achTipusntis) {
		this.achTipusntis = achTipusntis;
	}

	public TipusNti addAchTipusnti(TipusNti achTipusnti) {
		getAchTipusntis().add(achTipusnti);
		achTipusnti.setAchCategorianti(this);

		return achTipusnti;
	}

	public TipusNti removeAchTipusnti(TipusNti achTipusnti) {
		getAchTipusntis().remove(achTipusnti);
		achTipusnti.setAchCategorianti(null);

		return achTipusnti;
	}

}
package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;
import javax.validation.constraints.NotNull;


/**
 * The persistent class for the ACH_TIPUSSERIE database table.
 * 
 */
@Entity
@Table(name="ACH_TIPUSSERIE")
@TaulaMestra
@NamedQuery(name="Tipusserie.findAll", query="SELECT t FROM TipusSerie t")
public class TipusSerie implements Serializable {
	private static final long serialVersionUID = 1L;

	@Id
	@SequenceGenerator(name="ACH_TIPUSSERIE_ID_GENERATOR", sequenceName="ACH_TIPUSSERIE_SEQ", allocationSize = 1)
	@GeneratedValue(strategy=GenerationType.SEQUENCE, generator="ACH_TIPUSSERIE_ID_GENERATOR")
	private Long id;

	@NotNull
	private String nom;

	@NotNull
	private String nomcas;

	//bi-directional many-to-one association to Funcio
	/*@OneToMany(mappedBy="achTipusserie")
	private List<Funcio> achFuncios;*/

	//bi-directional many-to-one association to Seriedocumental
	/*@OneToMany(mappedBy="achTipusserie")
	private List<Seriedocumental> achSeriedocumentals;*/

	public TipusSerie() {
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

	/*public List<Funcio> getAchFuncios() {
		return this.achFuncios;
	}

	public void setAchFuncios(List<Funcio> achFuncios) {
		this.achFuncios = achFuncios;
	}

	public Funcio addAchFuncio(Funcio achFuncio) {
		getAchFuncios().add(achFuncio);
		achFuncio.setAchTipusserie(this);

		return achFuncio;
	}

	public Funcio removeAchFuncio(Funcio achFuncio) {
		getAchFuncios().remove(achFuncio);
		achFuncio.setAchTipusserie(null);

		return achFuncio;
	}*/

	/*public List<Seriedocumental> getAchSeriedocumentals() {
		return this.achSeriedocumentals;
	}

	public void setAchSeriedocumentals(List<Seriedocumental> achSeriedocumentals) {
		this.achSeriedocumentals = achSeriedocumentals;
	}*/

	/*public Seriedocumental addAchSeriedocumental(Seriedocumental achSeriedocumental) {
		getAchSeriedocumentals().add(achSeriedocumental);
		achSeriedocumental.setAchTipusserie(this);

		return achSeriedocumental;
	}

	public Seriedocumental removeAchSeriedocumental(Seriedocumental achSeriedocumental) {
		getAchSeriedocumentals().remove(achSeriedocumental);
		achSeriedocumental.setAchTipusserie(null);

		return achSeriedocumental;
	}*/

}
package es.caib.archium.persistence.model;

import java.io.Serializable;
import javax.persistence.*;


/**
 * The persistent class for the ACH_CONTACTE_PROCEDIMENT database table.
 * 
 */
@Entity
@Table(name="ACH_CONTACTE_PROCEDIMENT")
@NamedQuery(name="ContacteProcediment.findAll", query="SELECT c FROM ContacteProcediment c")
public class ContacteProcediment implements Serializable {
	private static final long serialVersionUID = 1L;

	@EmbeddedId
	private ContacteProcedimentPK id;

	private String contacte;

	//bi-directional many-to-one association to Procediment
	@ManyToOne
	@JoinColumn(name="PROCEDIMENT_ID"  ,insertable=false,updatable=false)
	private Procediment achProcediment;

	//bi-directional many-to-one association to Tipuscontacte
	@ManyToOne
	@JoinColumn(name="TIPUSCONTACTE_ID"  ,insertable=false,updatable=false)
	private Tipuscontacte achTipuscontacte;

	public ContacteProcediment() {
	}

	public ContacteProcedimentPK getId() {
		return this.id;
	}

	public void setId(ContacteProcedimentPK id) {
		this.id = id;
	}

	public String getContacte() {
		return this.contacte;
	}

	public void setContacte(String contacte) {
		this.contacte = contacte;
	}

	public Procediment getAchProcediment() {
		return this.achProcediment;
	}

	public void setAchProcediment(Procediment achProcediment) {
		this.achProcediment = achProcediment;
	}

	public Tipuscontacte getAchTipuscontacte() {
		return this.achTipuscontacte;
	}

	public void setAchTipuscontacte(Tipuscontacte achTipuscontacte) {
		this.achTipuscontacte = achTipuscontacte;
	}

}
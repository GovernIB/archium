package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Tipuscontacte;

@Local
public interface TipuscontacteService extends DAO<Tipuscontacte, Long> {

}
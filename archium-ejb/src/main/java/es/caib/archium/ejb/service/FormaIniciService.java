package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Formainici;
import es.caib.archium.persistence.model.Silenci;

@Local
public interface FormaIniciService extends DAO<Formainici, Long> {

}
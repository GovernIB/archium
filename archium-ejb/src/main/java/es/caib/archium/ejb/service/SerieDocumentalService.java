package es.caib.archium.ejb.service;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Seriedocumental;

import javax.ejb.Local;

@Local
public interface SerieDocumentalService extends DAO<Seriedocumental, Long> {

}

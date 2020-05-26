package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Seriedocumental;

@Local
public interface SerieDocumentalService extends DAO<Seriedocumental, Long> {


}

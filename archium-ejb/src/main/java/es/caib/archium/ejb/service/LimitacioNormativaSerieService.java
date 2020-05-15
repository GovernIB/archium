package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.LimitacioNormativaSerie;
import es.caib.archium.persistence.model.LimitacioNormativaSeriePK;

@Local
public interface LimitacioNormativaSerieService extends DAO<LimitacioNormativaSerie, LimitacioNormativaSeriePK> {
	
	public List<LimitacioNormativaSerie> getBySerie(Long serieId) throws I18NException;
	
}
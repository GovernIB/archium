package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.LimitacioNormativaSerie;
import es.caib.archium.persistence.model.LimitacioNormativaSeriePK;;

@Local
public interface LimitacioNormativaSerieService extends DAO<LimitacioNormativaSerie, LimitacioNormativaSeriePK> {
	
}
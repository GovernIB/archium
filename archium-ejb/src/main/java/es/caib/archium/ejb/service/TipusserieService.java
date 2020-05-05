package es.caib.archium.ejb.service;

import javax.ejb.Local;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.Tipusserie;

@Local
public interface TipusserieService extends DAO<Tipusserie, Long> {
	
}

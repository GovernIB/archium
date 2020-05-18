package es.caib.archium.ejb.service;

import javax.ejb.Local;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Butlleti;
@Local
public interface ButlletiService extends DAO<Butlleti, Long> {
	
}


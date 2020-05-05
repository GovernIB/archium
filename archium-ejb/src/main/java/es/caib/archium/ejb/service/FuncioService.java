package es.caib.archium.ejb.service;

import javax.ejb.Local;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Funcio;

@Local
public interface FuncioService extends DAO<Funcio, Long> {
	
}

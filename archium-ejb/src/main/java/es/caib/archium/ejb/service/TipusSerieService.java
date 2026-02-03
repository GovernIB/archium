package es.caib.archium.ejb.service;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusSerie;

import javax.ejb.Local;

@Local
public interface TipusSerieService extends DAO<TipusSerie, Long> {
	
}

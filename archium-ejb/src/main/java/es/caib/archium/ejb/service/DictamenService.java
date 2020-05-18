package es.caib.archium.ejb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Dictamen;

@Local
public interface DictamenService extends DAO<Dictamen, Long> {

	public List<Dictamen> getBySerieId(Long serieId) throws I18NException;
	
}


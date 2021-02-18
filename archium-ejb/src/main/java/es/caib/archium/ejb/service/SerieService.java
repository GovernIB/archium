package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Seriedocumental;

@Local
public interface SerieService extends DAO<Seriedocumental, Long> {

	public List<Seriedocumental> getByFuncio(Long funcioId) throws I18NException;
	
	public List<Dictamen> getDictamenVigent(Long serieId) throws I18NException;

	
}


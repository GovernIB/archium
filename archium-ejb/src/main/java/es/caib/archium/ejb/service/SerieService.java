package es.caib.archium.ejb.service;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Seriedocumental;

import javax.ejb.Local;
import java.util.List;

@Local
public interface SerieService extends DAO<Seriedocumental, Long> {

	public List<Seriedocumental> getByFuncio(Long funcioId) throws I18NException;
	
	public List<Dictamen> getDictamenVigent(Long serieId) throws I18NException;

	public Seriedocumental findByCodi(String codiSerie) throws  I18NException;
	
}


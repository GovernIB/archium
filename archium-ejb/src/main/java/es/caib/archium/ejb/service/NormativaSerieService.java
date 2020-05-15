package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.NormativaSeriedocumental;
import es.caib.archium.persistence.model.NormativaSeriedocumentalPK;


@Local
public interface NormativaSerieService extends DAO<NormativaSeriedocumental, NormativaSeriedocumentalPK> {

	public List<NormativaSeriedocumental> getBySerie(Long serieId) throws I18NException;
	
}

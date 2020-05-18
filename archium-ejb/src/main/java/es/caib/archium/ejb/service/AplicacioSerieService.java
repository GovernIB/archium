package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.AplicacioSerie;
import es.caib.archium.persistence.model.AplicacioSeriePK;

@Local
public interface AplicacioSerieService extends DAO<AplicacioSerie, AplicacioSeriePK>{

	public List<AplicacioSerie> getBySerie(Long serieId) throws I18NException;
	
}

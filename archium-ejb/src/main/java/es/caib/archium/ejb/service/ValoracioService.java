package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Tipusvalor;
import es.caib.archium.persistence.model.Valoracio;

@Local
public interface ValoracioService extends DAO<Valoracio, Long> {

	public Valoracio getBySerie(Long serieId) throws I18NException;
	
}

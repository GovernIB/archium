package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Funcio;

@Local
public interface FuncioService extends DAO<Funcio, Long> {
	
	public List<Funcio> getByParentAndQuadre(Long funcioId, Long quadreId) throws I18NException;
	
	public List<Funcio> getByQuadre(Long quadreId) throws I18NException;
	
}

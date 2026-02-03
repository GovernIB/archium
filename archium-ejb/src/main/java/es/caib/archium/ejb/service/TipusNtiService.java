package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusNti;

@Local
public interface TipusNtiService extends DAO<TipusNti, Long> {
	
	public List<String> trobarPrefixos() throws I18NException;
	
	public TipusNti trobarByPrefix(String prefix) throws I18NException;
}
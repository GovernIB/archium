package es.caib.archium.ejb.service;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Serierelacionada;

import javax.ejb.Local;
import java.util.List;
@Local
public interface SerieRelacionadaService extends DAO<Serierelacionada,Long> {

	public Serierelacionada findByRel(Long serie1Id, Long serie2Id) throws I18NException;
	public Serierelacionada findByRelArgen(Long serie1Id, Long serieArgenId) throws I18NException;
	public List<Serierelacionada> findSeriesBySerie(Long serieId) throws I18NException;
	public List<Serierelacionada> findArgenBySerie(Long serieId) throws I18NException;
}

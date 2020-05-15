package es.caib.archium.ejb.service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Serieargen;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.Serierelacionada;
@Local
public interface SerieRelacionadaService extends DAO<Serierelacionada,Long> {

	public Serierelacionada findByRel(Long serie1Id, Long serie2Id) throws I18NException;
	public Serierelacionada findByRelArgen(Long serie1Id, Long serieArgenId) throws I18NException;
	public List<Serierelacionada> findSeriesBySerie(Long serieId) throws I18NException;
	public List<Serierelacionada> findArgenBySerie(Long serieId) throws I18NException;
}

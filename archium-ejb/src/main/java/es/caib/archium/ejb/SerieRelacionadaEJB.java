package es.caib.archium.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.SerieRelacionadaService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Serieargen;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.Serierelacionada;

@Local
public class SerieRelacionadaEJB extends AbstractDAO<Serierelacionada, Long> implements SerieRelacionadaService{

	public Serierelacionada findByRel(Seriedocumental serie1,Seriedocumental serie2) throws I18NException {
		Map<String,Object> filters = new HashMap<>();
		filters.put("achSeriedocumental1", serie1);
		filters.put("achSeriedocumental2", serie2);
		List<Serierelacionada> res = findFiltered(filters);
		if(res.size()!=0)
			return res.get(0);
		
		return null;
		
	}
	public Serierelacionada findByRelArgen(Seriedocumental serie1,Serieargen serie2) throws I18NException {
		Map<String,Object> filters = new HashMap<>();
		filters.put("achSeriedocumental1", serie1);
		filters.put("achSerieargen", serie2);
		List<Serierelacionada> res = findFiltered(filters);
		if(res.size()!=0)
			return res.get(0);
		
		return null;
		
	}
}

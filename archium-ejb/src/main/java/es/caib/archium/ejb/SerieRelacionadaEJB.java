package es.caib.archium.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.ejb.service.SerieRelacionadaService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Serierelacionada;

@Stateless
@RolesAllowed({Constants.ACH_GESTOR,Constants.ACH_CSGD})
public class SerieRelacionadaEJB extends AbstractDAO<Serierelacionada, Long> implements SerieRelacionadaService{

	@Inject 
	private SerieDocumentalService serieService;
	
	@Override
	public Serierelacionada findByRel(Long serie1Id, Long serie2Id) throws I18NException {
		Map<String,Object> filters = new HashMap<>();
		filters.put("achSeriedocumental1", serieService.getReference(serie1Id));
		filters.put("achSeriedocumental2", serieService.getReference(serie2Id));
		List<Serierelacionada> res = findFiltered(filters);
		if(res.size()!=0)
			return res.get(0);
		
		return null;
		
	}
	
	@Override
	public Serierelacionada findByRelArgen(Long serie1Id, Long serieArgenId) throws I18NException {
		Map<String,Object> filters = new HashMap<>();
		filters.put("achSeriedocumental1", serieService.getReference(serie1Id));
		filters.put("achSerieargen", serieService.getReference(serieArgenId));
		List<Serierelacionada> res = findFiltered(filters);
		if(res.size()!=0)
			return res.get(0);
		
		return null;
		
	}
	
	@Override
	public List<Serierelacionada> findSeriesBySerie(Long serieId) throws I18NException {
		
		if(serieId!=null) {
			Map<String,Object> filters = new HashMap<>();
			filters.put("achSeriedocumental1", serieService.getReference(serieId) );
			//filters.put("achSeriedocumental2",null);
			filters.put("achSerieargen",null);
			return this.findFiltered(filters);
		} else {
			throw new I18NException("serierelacionadas.findSeriesBySerie.id.null", this.getClass().getSimpleName(), "findSeriesBySerie");
		}
		
		
	}
	
	@Override
	public List<Serierelacionada> findArgenBySerie(Long serieId) throws I18NException {
		
		if(serieId!=null) {
			Map<String,Object> filters = new HashMap<>();
			filters.put("achSeriedocumental1", serieService.getReference(serieId) );
			filters.put("achSeriedocumental2",null);
			//filters.put("achSerieargen",null);
			return this.findFiltered(filters);
		} else {
			throw new I18NException("serierelacionadas.findArgenBySerie.id.null", this.getClass().getSimpleName(), "findArgenBySerie");
		}
		
		
	}
}

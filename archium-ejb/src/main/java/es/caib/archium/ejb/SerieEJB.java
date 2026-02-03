package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.ejb.service.DictamenService;
import es.caib.archium.ejb.service.FuncioService;
import es.caib.archium.ejb.service.SerieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Seriedocumental;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class SerieEJB extends AbstractDAO<Seriedocumental, Long> implements SerieService  {

	@Inject
	FuncioService funcionesEJB;
	
	@Inject
	DictamenService dictamenEJB;
	
	@Override
	public List<Seriedocumental> getByFuncio(Long funcioId) throws I18NException {
		if(funcioId!=null) {
			Map<String, Object> filtersS = new HashMap<String, Object>();		
			filtersS.put("achFuncio", funcionesEJB.getReference(funcioId));
			OrderBy orderS = new OrderBy("id", OrderType.DESC);	
			return this.findFiltered(filtersS, orderS);
		} else {
			throw new I18NException("serie.getByFuncio.id.null", this.getClass().getSimpleName(), "getByFuncio");
		}
	}

	@Override
	public List<Dictamen> getDictamenVigent(Long serieId) throws I18NException {
		if(serieId!=null) {
			Map<String, Object> filtersS = new HashMap<String, Object>();		
			filtersS.put("achSeriedocumental", this.getReference(serieId));
			filtersS.put("estat", "Vigent");
			OrderBy orderS = new OrderBy("id", OrderType.DESC);	
			List<Dictamen> listDictamenes = dictamenEJB.findFiltered(filtersS, orderS);
			if(listDictamenes!=null) {
				if(listDictamenes.size()>0) {
					return listDictamenes;
				} else {
					return null;
				}
			} else {
				return null;
			}
		} else {
			throw new I18NException("serie.getDictamenVigent.id.null", this.getClass().getSimpleName(), "getDictamenVigent");
		}
	}

	@Override
	@PermitAll
	public Seriedocumental findByCodi(String codiSerie) throws I18NException {
		Seriedocumental  serie = null;
		if(codiSerie!=null) {
			Map<String, Object> filtersS = new HashMap<String, Object>();
			filtersS.put("codi", codiSerie);
			List<Seriedocumental> resultats = this.findFiltered(filtersS);
			if (resultats != null && resultats.size() == 1) {
				serie = resultats.get(0);
			}
		} else {
			throw new I18NException("serie.findByCodi.id.null", this.getClass().getSimpleName(), "findByCodi");
		}
		return serie;
	}
	
	@Override
    public boolean tieneRelaciones(Long id) {
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(s) FROM Serierelacionada s WHERE s.achSerieargen.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		Long comptarB = entityManager.createQuery("SELECT COUNT(s) FROM Seriedocumental s JOIN s.achSerieargens p WHERE p.id = :id",Long.class)
				.setParameter("id",id)
				.getSingleResult();
		
		return (comptarA + comptarB) > 0;
	}

}

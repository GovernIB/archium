package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.LimitacioNormativaSerieService;
import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.LimitacioNormativaSerie;
import es.caib.archium.persistence.model.LimitacioNormativaSeriePK;


@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class LimitacioNormativaSerieEJB extends AbstractDAO<LimitacioNormativaSerie,LimitacioNormativaSeriePK> implements LimitacioNormativaSerieService{

	@Inject 
	private SerieDocumentalService serieService;
	
	@Override
	public List<LimitacioNormativaSerie> getBySerie(Long serieId) throws I18NException {
		if(serieId!=null) {
			Map<String,Object> filters  = new HashMap<>();
			filters.put("achSeriedocumental", serieService.getReference(serieId));
			return this.findFiltered(filters);
		}
		
		return new ArrayList<LimitacioNormativaSerie>();
	}
	
}

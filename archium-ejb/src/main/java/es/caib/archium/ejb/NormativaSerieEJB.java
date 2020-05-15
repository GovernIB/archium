package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.NormativaSerieService;
import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.NormativaSeriedocumental;
import es.caib.archium.persistence.model.NormativaSeriedocumentalPK;


@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class NormativaSerieEJB extends AbstractDAO<NormativaSeriedocumental, NormativaSeriedocumentalPK> implements NormativaSerieService{

	@Inject 
	private SerieDocumentalService serieService;
	
	@Override
	public List<NormativaSeriedocumental> getBySerie(Long serieId) throws I18NException {
		if(serieId!=null) {
			Map<String,Object> filters  = new HashMap<>();
			filters.put("achSeriedocumental", serieService.getReference(serieId));
			return this.findFiltered(filters);
		}
		
		return new ArrayList<NormativaSeriedocumental>();
	}

}

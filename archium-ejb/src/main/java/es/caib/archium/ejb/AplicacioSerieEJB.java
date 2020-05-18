package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.AplicacioSerieService;
import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.AplicacioSerie;
import es.caib.archium.persistence.model.AplicacioSeriePK;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class AplicacioSerieEJB  extends AbstractDAO<AplicacioSerie,AplicacioSeriePK> implements AplicacioSerieService{

	@Inject 
	private SerieDocumentalService serieService;
	
	@Override
	public List<AplicacioSerie> getBySerie(Long serieId) throws I18NException {
		if(serieId!=null) {
			Map<String,Object> filters  = new HashMap<>();
			filters.put("achSeriedocumental", serieService.getReference(serieId));
			return this.findFiltered(filters);
		}
		
		return new ArrayList<AplicacioSerie>();
	}

}

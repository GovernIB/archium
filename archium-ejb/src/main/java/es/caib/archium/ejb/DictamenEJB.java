package es.caib.archium.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.ejb.service.DictamenService;
import es.caib.archium.ejb.service.SerieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Dictamen;

@Stateless
@RolesAllowed({Constants.ACH_GESTOR,Constants.ACH_CSGD})
public class DictamenEJB extends AbstractDAO<Dictamen, Long> implements DictamenService  {

	@Inject
	SerieService serieEJB;
	
	@Override
	public List<Dictamen> getBySerieId(Long serieId) throws I18NException {
		Map<String, Object> filters= new HashMap<>();
		if(serieId!= null) {
			filters.put("achSeriedocumental", serieEJB.getReference(serieId));
			return this.findFiltered(filters);
		} else {
			throw new I18NException("dictamen.getBySerieId.id.null", this.getClass().getSimpleName(), "getBySerieId");
		}
	}

	
}

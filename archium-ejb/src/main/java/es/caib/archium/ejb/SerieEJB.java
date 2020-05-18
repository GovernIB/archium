package es.caib.archium.ejb;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.ejb.service.FuncioService;
import es.caib.archium.ejb.service.SerieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Seriedocumental;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class SerieEJB extends AbstractDAO<Seriedocumental, Long> implements SerieService  {

	@Inject
	FuncioService funcionesEJB;
	
	@Override
	public List<Seriedocumental> getByFuncio(Long funcioId) throws I18NException {
		if(funcioId!=null) {
			Map<String, Object> filtersS = new HashMap<String, Object>();		
			filtersS.put("achFuncio", funcionesEJB.getReference(funcioId));
			OrderBy orderS = new OrderBy("id", OrderType.DESC);	
			return this.findFiltered(filtersS, orderS);
		}
		return null;
	}

	
}

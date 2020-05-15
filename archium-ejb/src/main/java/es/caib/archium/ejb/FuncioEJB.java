package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.ejb.service.FuncioService;
import es.caib.archium.ejb.service.QuadreclassificacioService;
import es.caib.archium.ejb.service.TipusSerieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.Quadreclassificacio;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class FuncioEJB extends AbstractDAO<Funcio, Long> implements FuncioService  {

	@Inject
	QuadreclassificacioService quadreclassificacioEJB;
	@Inject
	TipusSerieService tipuSerieEJB;
	
	@Override
	public List<Funcio> getByParentAndQuadre(Long funcioId, Long quadreId) throws I18NException {
		if(quadreId!=null) {
			Map<String, Object> filtersF = new HashMap<String, Object>();
			if(funcioId!=null) {
				filtersF.put("achFuncio", this.getReference(funcioId));
			} else {
				filtersF.put("achFuncio", null);
			}
			filtersF.put("achQuadreclassificacio", quadreclassificacioEJB.getReference(quadreId));
			OrderBy orderF = new OrderBy("ordre", OrderType.ASC);	
			System.out.println("funcioId" + funcioId);
			System.out.println("quadreId" + quadreId);
			return this.findFiltered(filtersF, orderF);
		}
		return null;
	}

	@Override
	public List<Funcio> getByQuadre(Long quadreId) throws I18NException {
		if(quadreId!=null) {
			Map<String, Object> filtersF = new HashMap<String, Object>();		
			filtersF.put("achQuadreclassificacio", quadreclassificacioEJB.getReference(quadreId));
			OrderBy orderF = new OrderBy("ordre", OrderType.ASC);	
			System.out.println("quadreId" + quadreId);
			return this.findFiltered(filtersF, orderF);
		}
		return null;
	}

	
	
}

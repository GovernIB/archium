package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.ejb.service.FuncioService;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.ejb.service.TipusSerieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Funcio;

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
	QuadreClassificacioService quadreclassificacioEJB;
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
			return this.findFiltered(filtersF, orderF);
		} else {
			throw new I18NException("funcio.getByParentAndQuadre.id.null", this.getClass().getSimpleName(), "getByParentAndQuadre");
		}
	}

	@Override
	public List<Funcio> getByQuadre(Long quadreId) throws I18NException {
		if(quadreId!=null) {
			Map<String, Object> filtersF = new HashMap<String, Object>();		
			filtersF.put("achQuadreclassificacio", quadreclassificacioEJB.getReference(quadreId));
			OrderBy orderF = new OrderBy("ordre", OrderType.ASC);	
			return this.findFiltered(filtersF, orderF);
		} else {
			throw new I18NException("funcio.getByQuadre.id.null", this.getClass().getSimpleName(), "getByQuadre");
		}
	}

	@Override
	public List<Funcio> loadTree(Long quadreId, Long fromFuncioId) throws I18NException {
				
		List<Funcio> list = this.getByParentAndQuadre(fromFuncioId, quadreId);
		this.recursiveTree(quadreId, list);
		return list;
		
	}
	
	private void recursiveTree(Long quadreId, List<Funcio> list) throws I18NException {
		for(Funcio f:list) {
			f.setAchFuncios(this.getByParentAndQuadre(f.getId(), quadreId));
			this.recursiveTree(quadreId, f.getAchFuncios());
		}
	}

	
	
}

package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.ejb.service.FuncioService;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.ejb.service.TipusSerieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Funcio;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

	@PermitAll
	public List<String> llistaAmbitsFuncionals(Idioma idioma) throws I18NException {
		OrderBy order = new OrderBy("nom" + (Idioma.CASTELLA.equals(idioma) ? "cas" : ""), OrderType.ASC);
		Map<String, Object> filtres = new HashMap<>();
		filtres.put("achFuncio", null);

		List<Funcio> funcions = this.findFiltered(filtres, order);
		List<String> possiblesAmbitsFuncionals = new ArrayList<>();
		if (funcions != null) {
			for (Funcio fun : funcions) {
				possiblesAmbitsFuncionals.add(Idioma.CASTELLA.equals(idioma) ? fun.getNomcas() : fun.getNom());
			}
		}
		return possiblesAmbitsFuncionals;
	}
	
	
}

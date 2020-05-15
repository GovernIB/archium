package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.ProcedimientoService;
import es.caib.archium.ejb.service.TipusDocumentProcedimentService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.TipusdocumentProcedimentPK;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusDocumentProcedimentEJB extends AbstractDAO<TipusdocumentProcediment, TipusdocumentProcedimentPK> implements TipusDocumentProcedimentService  {

	@Inject
	ProcedimientoService procedimentEJB;
	
	@Override
	public List<TipusdocumentProcediment> getTipusDocument(Long procedimentId) throws I18NException {
		
		if(procedimentId!=null) {
			Map<String,Object> filters = new HashMap<>();
			filters.put("achProcediment", procedimentEJB.getReference(procedimentId));
			return this.findFiltered(filters);
		}
		
		
		return null;
	}

		
}

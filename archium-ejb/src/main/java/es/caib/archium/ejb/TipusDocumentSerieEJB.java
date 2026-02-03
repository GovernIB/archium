package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.ejb.service.TipusDocumentSerieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipusdocumentSerie;
import es.caib.archium.persistence.model.TipusdocumentSeriePK;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusDocumentSerieEJB extends AbstractDAO<TipusdocumentSerie, TipusdocumentSeriePK> implements TipusDocumentSerieService {

	@Inject
	SerieDocumentalService serieDocumentalEJB;
	
	@Override
	public List<TipusdocumentSerie> getTipusDocument(Long serieId) throws I18NException {
		
		if(serieId != null) {
			Map<String,Object> filters = new HashMap<>();
			filters.put("achSeriedocumental", serieDocumentalEJB.getReference(serieId));
			return this.findFiltered(filters);
		} else {
			throw new I18NException("tipusdocumentserie.getTipusDocument.id.null", this.getClass().getSimpleName(), "getTipusDocument");
		}
		
	}

		
}

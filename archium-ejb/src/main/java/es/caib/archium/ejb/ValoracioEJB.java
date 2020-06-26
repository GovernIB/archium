package es.caib.archium.ejb;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.ejb.service.NuevaSerieService;
import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.ejb.service.TipusValorService;
import es.caib.archium.ejb.service.ValoracioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.Tipusvalor;
import es.caib.archium.persistence.model.Valoracio;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.ejb.Stateless;
import javax.inject.Inject;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class ValoracioEJB extends AbstractDAO<Valoracio, Long> implements ValoracioService{

	@Inject
	SerieDocumentalService serieEJB;
	
	@Override
	public Valoracio getBySerie(Long serieId) throws I18NException {
		if(serieId!=null) {
			Map<String, Object> filtersF = new HashMap<String, Object>();
			filtersF.put("achSeriedocumental", serieEJB.getReference(serieId));
			List<Valoracio> db = this.findFiltered(filtersF);
			if(db.size()>0) return db.get(0);
			else return null;
		} else {
			throw new I18NException("valoracio.getBySerie.id.null", this.getClass().getSimpleName(), "getBySerie");
		}
	}
	
}

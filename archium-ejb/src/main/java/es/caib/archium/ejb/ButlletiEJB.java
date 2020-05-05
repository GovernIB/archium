package es.caib.archium.ejb;

import es.caib.archium.ejb.service.ButlletiService;
import es.caib.archium.ejb.service.TipusDocumentService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Butlleti;
import es.caib.archium.persistence.model.Tipusdocumental;

import javax.ejb.Local;

@Local
public class ButlletiEJB extends AbstractDAO<Butlleti, Long> implements ButlletiService  {

		
}

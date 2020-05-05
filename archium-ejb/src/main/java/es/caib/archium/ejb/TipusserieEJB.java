package es.caib.archium.ejb;

import es.caib.archium.ejb.service.TipusserieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusserie;

import javax.ejb.Local;

@Local
public class TipusserieEJB extends AbstractDAO<Tipusserie, Long> implements TipusserieService  {

	
}

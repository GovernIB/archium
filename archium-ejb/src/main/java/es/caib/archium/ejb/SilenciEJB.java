package es.caib.archium.ejb;

import javax.ejb.Local;

import es.caib.archium.ejb.service.ProcedimientoService;
import es.caib.archium.ejb.service.SilenciService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Procediment;
import es.caib.archium.persistence.model.Silenci;

@Local
public class SilenciEJB extends AbstractDAO<Silenci, Long> implements SilenciService  {
		
}

package es.caib.archium.ejb;

import javax.ejb.Local;

import es.caib.archium.ejb.service.ProcedimientoService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Procediment;

@Local
public class ProcedimientoEJB extends AbstractDAO<Procediment, Long> implements ProcedimientoService  {
		
}

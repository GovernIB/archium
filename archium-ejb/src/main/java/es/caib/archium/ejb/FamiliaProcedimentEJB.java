package es.caib.archium.ejb;

import javax.ejb.Local;

import es.caib.archium.ejb.service.FamiliaProcedimientService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Familiaprocediment;

@Local
public class FamiliaProcedimentEJB extends AbstractDAO<Familiaprocediment, Long> implements FamiliaProcedimientService  {
		
}

package es.caib.archium.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.FamiliaProcedimentService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Familiaprocediment;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class FamiliaProcedimentEJB extends AbstractDAO<Familiaprocediment, Long> implements FamiliaProcedimentService  {
	
}

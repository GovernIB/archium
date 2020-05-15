package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.ProcedimientoService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Procediment;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class ProcedimientoEJB extends AbstractDAO<Procediment, Long> implements ProcedimientoService  {
		
}

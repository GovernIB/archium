package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.SilenciService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Silenci;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class SilenciEJB extends AbstractDAO<Silenci, Long> implements SilenciService  {
		
}

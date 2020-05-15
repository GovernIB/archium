package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.ButlletiService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Butlleti;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class ButlletiEJB extends AbstractDAO<Butlleti, Long> implements ButlletiService  {

		
}

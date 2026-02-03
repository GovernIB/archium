package es.caib.archium.ejb;

import es.caib.archium.ejb.service.OrganCollegiatService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.OrganCollegiat;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class OrganCollegiatEJB extends AbstractDAO<OrganCollegiat, Long> implements OrganCollegiatService {
	
}

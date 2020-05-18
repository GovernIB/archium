package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.LopdService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Lopd;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class LopdEJB extends AbstractDAO<Lopd, Long> implements LopdService  {

	
}

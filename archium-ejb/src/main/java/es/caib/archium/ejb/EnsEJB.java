package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.EnsService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Ens;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class EnsEJB extends AbstractDAO<Ens, Long> implements EnsService  {

	
}

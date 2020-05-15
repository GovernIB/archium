package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.AplicacioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Aplicacio;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class AplicacioEJB extends AbstractDAO<Aplicacio, Long> implements AplicacioService  {
		
}

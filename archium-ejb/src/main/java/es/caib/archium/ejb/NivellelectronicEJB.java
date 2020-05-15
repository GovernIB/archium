package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.NivellelectronicService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Nivellelectronic;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class NivellelectronicEJB extends AbstractDAO<Nivellelectronic, Long> implements NivellelectronicService  {
		
}

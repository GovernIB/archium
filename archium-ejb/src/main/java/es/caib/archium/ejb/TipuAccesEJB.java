package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipuAccesService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusacce;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipuAccesEJB extends AbstractDAO<Tipusacce, Long> implements TipuAccesService  {

	
}

package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipusSerieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusserie;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusSerieEJB extends AbstractDAO<Tipusserie, Long> implements TipusSerieService  {

	
}

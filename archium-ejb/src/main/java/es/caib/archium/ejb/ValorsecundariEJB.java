package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.ValorsecundariService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Valorsecundari;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class ValorsecundariEJB extends AbstractDAO<Valorsecundari, Long> implements ValorsecundariService {

}
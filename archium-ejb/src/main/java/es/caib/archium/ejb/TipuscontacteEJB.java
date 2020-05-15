package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipuscontacteService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipuscontacte;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipuscontacteEJB extends AbstractDAO<Tipuscontacte, Long> implements TipuscontacteService {

}
package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipusnormativaService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusnormativa;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusnormativaEJB extends AbstractDAO<Tipusnormativa, Long> implements TipusnormativaService {

	
}

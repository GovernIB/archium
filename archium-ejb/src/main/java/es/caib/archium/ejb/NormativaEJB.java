package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.NormativaService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Normativa;


@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class NormativaEJB extends AbstractDAO<Normativa, Long> implements NormativaService  {

}
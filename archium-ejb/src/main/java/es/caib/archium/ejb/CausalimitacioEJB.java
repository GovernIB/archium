package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.CausalimitacioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Causalimitacio;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class CausalimitacioEJB extends AbstractDAO<Causalimitacio, Long> implements CausalimitacioService{

}
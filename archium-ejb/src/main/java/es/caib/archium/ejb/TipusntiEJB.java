package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipusntiService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusnti;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusntiEJB extends AbstractDAO<Tipusnti, Long> implements TipusntiService {

}
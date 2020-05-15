package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipuDictamenService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusdictamen;


@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipuDictamenEJB extends AbstractDAO<Tipusdictamen, Long> implements TipuDictamenService  {

	
}

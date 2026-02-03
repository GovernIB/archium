package es.caib.archium.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.FaseArxiuService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Fasearxiu;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class FaseArxiuEJB extends AbstractDAO<Fasearxiu, Long> implements FaseArxiuService{
	
	
}

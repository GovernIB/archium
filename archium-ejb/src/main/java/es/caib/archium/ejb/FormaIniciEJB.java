package es.caib.archium.ejb;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.FormaIniciService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Formainici;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class FormaIniciEJB extends AbstractDAO<Formainici, Long> implements FormaIniciService  {
	
}

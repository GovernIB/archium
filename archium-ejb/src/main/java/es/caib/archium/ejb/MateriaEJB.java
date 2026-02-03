package es.caib.archium.ejb;

import es.caib.archium.ejb.service.MateriaService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Materia;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class MateriaEJB extends AbstractDAO<Materia, Long> implements MateriaService  {
	
}

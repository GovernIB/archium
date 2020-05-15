package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.CatalegserieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Catalegsery;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class CatalegserieEJB extends AbstractDAO<Catalegsery, Long> implements CatalegserieService{

}

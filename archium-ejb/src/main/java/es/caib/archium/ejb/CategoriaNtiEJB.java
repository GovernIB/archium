package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.CategoriaNtiService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Categorianti;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class CategoriaNtiEJB extends AbstractDAO<Categorianti, Long> implements CategoriaNtiService{

}

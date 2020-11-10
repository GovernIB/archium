package es.caib.archium.ejb;

import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Quadreclassificacio;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class QuadreClassificacioEJB extends AbstractDAO<Quadreclassificacio, Long> implements QuadreClassificacioService  {

}

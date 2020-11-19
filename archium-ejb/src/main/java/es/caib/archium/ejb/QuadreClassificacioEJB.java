package es.caib.archium.ejb;

import es.caib.archium.commons.utils.Constants;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Quadreclassificacio;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
@RolesAllowed({Constants.ACH_GESTOR,Constants.ACH_CSGD})
public class QuadreClassificacioEJB extends AbstractDAO<Quadreclassificacio, Long> implements QuadreClassificacioService  {

}

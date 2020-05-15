package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.QuadreclassificacioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Quadreclassificacio;

import java.util.Date;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class QuadreclassificacioEJB extends AbstractDAO<Quadreclassificacio, Long> implements QuadreclassificacioService  {

}

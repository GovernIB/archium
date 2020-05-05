package es.caib.archium.ejb;

import es.caib.archium.ejb.service.QuadreclassificacioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Quadreclassificacio;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
public class QuadreclassificacioEJB extends AbstractDAO<Quadreclassificacio, Long> implements QuadreclassificacioService  {

}

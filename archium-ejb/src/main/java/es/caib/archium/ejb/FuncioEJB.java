package es.caib.archium.ejb;

import es.caib.archium.ejb.service.FuncioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Funcio;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
public class FuncioEJB extends AbstractDAO<Funcio, Long> implements FuncioService  {

	
}

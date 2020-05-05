package es.caib.archium.ejb;

import es.caib.archium.ejb.service.DictamenService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Funcio;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
public class DictamenEJB extends AbstractDAO<Dictamen, Long> implements DictamenService  {

	
}

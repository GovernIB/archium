package es.caib.archium.ejb;

import es.caib.archium.ejb.service.EnsService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Ens;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
public class EnsEJB extends AbstractDAO<Ens, Long> implements EnsService  {

	
}

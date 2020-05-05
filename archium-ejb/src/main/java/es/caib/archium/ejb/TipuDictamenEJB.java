package es.caib.archium.ejb;

import es.caib.archium.ejb.service.TipuDictamenService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusdictamen;

import javax.ejb.Local;

@Local
public class TipuDictamenEJB extends AbstractDAO<Tipusdictamen, Long> implements TipuDictamenService  {

	
}

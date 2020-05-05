package es.caib.archium.ejb;

import es.caib.archium.ejb.service.DictamenService;
import es.caib.archium.ejb.service.LopdService;
import es.caib.archium.ejb.service.TipusnormativaService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.Lopd;
import es.caib.archium.persistence.model.Tipusnormativa;

import javax.ejb.Local;
import javax.ejb.Stateless;

@Local
public class TipusnormativaEJB extends AbstractDAO<Tipusnormativa, Long> implements TipusnormativaService {

	
}

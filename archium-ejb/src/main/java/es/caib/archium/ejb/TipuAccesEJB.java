package es.caib.archium.ejb;

import es.caib.archium.ejb.service.TipuAccesService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusacce;

import javax.ejb.Local;

@Local
public class TipuAccesEJB extends AbstractDAO<Tipusacce, Long> implements TipuAccesService  {

	
}

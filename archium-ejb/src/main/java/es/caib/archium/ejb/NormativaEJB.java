package es.caib.archium.ejb;

import javax.ejb.Local;

import es.caib.archium.ejb.service.NormativaService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Normativa;


@Local
public class NormativaEJB extends AbstractDAO<Normativa, Long> implements NormativaService  {

}
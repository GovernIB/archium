package es.caib.archium.ejb;

import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Seriedocumental;

import javax.ejb.Local;

@Local
public class SerieDocumentalEJB extends AbstractDAO<Seriedocumental, Long> implements SerieDocumentalService  {

	
}

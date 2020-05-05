package es.caib.archium.ejb;

import javax.ejb.Local;

import es.caib.archium.ejb.service.FormaIniciService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Formainici;

@Local
public class FormaIniciEJB extends AbstractDAO<Formainici, Long> implements FormaIniciService  {
		
}

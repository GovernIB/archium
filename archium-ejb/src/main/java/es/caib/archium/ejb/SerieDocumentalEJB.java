package es.caib.archium.ejb;

import es.caib.archium.commons.utils.Constants;
import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Seriedocumental;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

@Stateless
@RolesAllowed({Constants.ACH_GESTOR,Constants.ACH_CSGD})
public class SerieDocumentalEJB extends AbstractDAO<Seriedocumental, Long> implements SerieDocumentalService  {

	
}

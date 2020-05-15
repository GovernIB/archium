package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipusDocumentService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusdocumental;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusDocumentEJB extends AbstractDAO<Tipusdocumental, Long> implements TipusDocumentService  {

		
}

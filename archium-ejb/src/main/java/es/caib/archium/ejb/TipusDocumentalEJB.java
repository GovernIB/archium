package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipusDocumentalService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusdocumental;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusDocumentalEJB extends AbstractDAO<Tipusdocumental, Long> implements TipusDocumentalService {

}
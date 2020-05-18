package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.QuadreTipusDocumentalService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Quadretipusdocumental;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class QuadreTipusDocumentalEJB extends AbstractDAO<Quadretipusdocumental, Long> implements QuadreTipusDocumentalService {

}
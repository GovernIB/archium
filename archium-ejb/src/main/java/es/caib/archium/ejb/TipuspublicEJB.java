package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipuspublicService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipuspublic;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipuspublicEJB extends AbstractDAO<Tipuspublic, Long> implements TipuspublicService{

}
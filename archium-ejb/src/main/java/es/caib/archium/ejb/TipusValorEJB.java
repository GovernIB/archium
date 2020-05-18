package es.caib.archium.ejb;

import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipusValorService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusvalor;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusValorEJB extends AbstractDAO<Tipusvalor, Long> implements TipusValorService{

}
package es.caib.archium.ejb;


import javax.annotation.security.DeclareRoles;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.SerieArgenService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Serieargen;


@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class SerieArgenEJB extends AbstractDAO<Serieargen, Long> implements SerieArgenService{

}

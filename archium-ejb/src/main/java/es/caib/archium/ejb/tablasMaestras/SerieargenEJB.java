package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.SerieargenService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Serieargen;

@Local
public class SerieargenEJB extends AbstractDAO<Serieargen, Long> implements SerieargenService{

}

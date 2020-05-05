package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.SilenciService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Silenci;

@Local
public class SilenciEJB extends AbstractDAO<Silenci, Long> implements SilenciService {

}
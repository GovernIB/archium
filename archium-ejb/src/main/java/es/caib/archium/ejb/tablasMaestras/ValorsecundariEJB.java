package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.ValorsecundariService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Valorsecundari;

@Local
public class ValorsecundariEJB extends AbstractDAO<Valorsecundari, Long> implements ValorsecundariService {

}
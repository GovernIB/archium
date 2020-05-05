package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.TipusserieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusserie;

@Local
public class TipusserieEJB extends AbstractDAO<Tipusserie, Long> implements TipusserieService {

}

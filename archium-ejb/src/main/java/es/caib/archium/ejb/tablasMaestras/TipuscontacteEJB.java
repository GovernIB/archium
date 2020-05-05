package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.TipuscontacteService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipuscontacte;

@Local
public class TipuscontacteEJB extends AbstractDAO<Tipuscontacte, Long> implements TipuscontacteService {

}
package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.LopdService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Lopd;

@Local
public class LopdEJB extends AbstractDAO<Lopd, Long> implements LopdService {

}
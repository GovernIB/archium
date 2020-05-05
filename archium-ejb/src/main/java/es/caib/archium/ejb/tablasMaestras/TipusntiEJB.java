package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.TipusntiService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusnti;

@Local
public class TipusntiEJB extends AbstractDAO<Tipusnti, Long> implements TipusntiService {

}
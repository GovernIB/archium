package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.TipusdictamentService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusdictamen;

@Local
public class TipusdictamentEJB extends AbstractDAO<Tipusdictamen, Long> implements TipusdictamentService{

}

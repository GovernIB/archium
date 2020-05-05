package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.TipusdocumentalService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Tipusdocumental;

@Local
public class TipusdocumentalEJB extends AbstractDAO<Tipusdocumental, Long> implements TipusdocumentalService {

}
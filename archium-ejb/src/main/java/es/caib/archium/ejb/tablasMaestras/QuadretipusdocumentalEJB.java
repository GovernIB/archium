package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.QuadretipusdocumentalService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Quadretipusdocumental;

@Local
public class QuadretipusdocumentalEJB extends AbstractDAO<Quadretipusdocumental, Long> implements QuadretipusdocumentalService {

}
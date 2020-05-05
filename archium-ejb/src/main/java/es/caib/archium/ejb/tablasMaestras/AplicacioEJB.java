package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.DictamenService;
import es.caib.archium.ejb.service.tablasMaestras.AplicacioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Aplicacio;
import es.caib.archium.persistence.model.Dictamen;

@Local
public class AplicacioEJB extends AbstractDAO<Aplicacio, Long> implements AplicacioService{

}


package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.FasearxiuService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Fasearxiu;

@Local
public class FasearxiuEJB extends AbstractDAO<Fasearxiu, Long> implements FasearxiuService{

}

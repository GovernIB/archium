package es.caib.archium.ejb.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.ejb.service.tablasMaestras.EnsService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Ens;

@Local
public class EnsEJB extends AbstractDAO<Ens, Long> implements EnsService{

}

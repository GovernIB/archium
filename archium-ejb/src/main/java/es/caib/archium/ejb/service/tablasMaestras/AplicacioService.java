package es.caib.archium.ejb.service.tablasMaestras;


import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Aplicacio;

@Local
public interface AplicacioService extends DAO<Aplicacio, Long> {

}


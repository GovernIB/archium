package es.caib.archium.ejb.service.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Lopd;

@Local
public interface LopdService extends DAO<Lopd, Long> {

}
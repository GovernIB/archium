package es.caib.archium.ejb.service.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Serieargen;

@Local
public interface SerieargenService extends DAO<Serieargen, Long> {

}
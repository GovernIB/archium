package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Aplicacio;

@Local
public interface AplicacionService extends DAO<Aplicacio, Long>{

}

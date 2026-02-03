package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Catalegsery;

@Local
public interface CatalegSerieService extends DAO<Catalegsery, Long>{

}

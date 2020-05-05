package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.AplicacioSerie;
import es.caib.archium.persistence.model.AplicacioSeriePK;

@Local
public interface AplicacioSerieService extends DAO<AplicacioSerie, AplicacioSeriePK>{

}

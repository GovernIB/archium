package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Tipusacce;

@Local
public interface TipuAccesService extends DAO<Tipusacce, Long> {

}


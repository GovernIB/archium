package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Fasearxiu;

@Local
public interface FasearxiuService extends DAO<Fasearxiu, Long> {

}
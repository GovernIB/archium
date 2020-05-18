package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Causalimitacio;


@Local
public interface CausaLimitacioService extends DAO<Causalimitacio, Long> {

}

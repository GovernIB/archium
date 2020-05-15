package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Tipuspublic;

@Local
public interface TipuspublicService extends DAO<Tipuspublic, Long> {

}
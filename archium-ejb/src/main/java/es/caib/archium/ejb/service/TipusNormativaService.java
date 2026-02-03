package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusNormativa;

@Local
public interface TipusNormativaService extends DAO<TipusNormativa, Long> {

}


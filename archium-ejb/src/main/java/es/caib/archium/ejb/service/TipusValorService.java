package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusValor;

@Local
public interface TipusValorService extends DAO<TipusValor, Long> {

}
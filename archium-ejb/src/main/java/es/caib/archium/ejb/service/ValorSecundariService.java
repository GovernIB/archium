package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Valorsecundari;

@Local
public interface ValorSecundariService extends DAO<Valorsecundari, Long> {

}
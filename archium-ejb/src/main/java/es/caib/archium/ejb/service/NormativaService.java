package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Normativa;


@Local
public interface NormativaService extends DAO<Normativa, Long> {

}
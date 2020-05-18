package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Lopd;
import es.caib.archium.persistence.model.Tipusnormativa;

@Local
public interface TipusNormativaService extends DAO<Tipusnormativa, Long> {

}


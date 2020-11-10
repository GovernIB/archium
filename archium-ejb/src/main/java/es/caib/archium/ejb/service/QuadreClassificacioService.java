package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Quadreclassificacio;

@Local
public interface QuadreClassificacioService extends DAO<Quadreclassificacio, Long> {

}

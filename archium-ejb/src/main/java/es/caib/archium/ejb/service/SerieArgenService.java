package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Serieargen;

@Local
public interface SerieArgenService extends DAO<Serieargen, Long> {

}

package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Serierelacionada;
@Local
public interface SerieRelacionadaService extends DAO<Serierelacionada,Long> {

}

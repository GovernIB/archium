package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.SerieArgen;

@Local
public interface SerieArgenService extends DAO<SerieArgen, Long> {
	
	List<SerieArgen> buscarPorTexto(String texto);

}

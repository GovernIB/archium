package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Normativa;


@Local
public interface NormativaService extends DAO<Normativa, Long> {
	
	public List<Normativa> buscarPorTexto(String texto);

}
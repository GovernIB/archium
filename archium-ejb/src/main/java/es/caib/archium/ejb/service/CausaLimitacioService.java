package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.CausaLimitacio;


@Local
public interface CausaLimitacioService extends DAO<CausaLimitacio, Long> {

	List<CausaLimitacio> buscarPorTexto(String texto);

}

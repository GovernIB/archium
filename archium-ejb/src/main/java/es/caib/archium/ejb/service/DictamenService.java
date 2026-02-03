package es.caib.archium.ejb.service;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Dictamen;

import javax.ejb.Local;
import java.util.List;

@Local
public interface DictamenService extends DAO<Dictamen, Long> {

	public List<Dictamen> getBySerieId(Long serieId) throws I18NException;

	/**
	 * Permet obtenir les dades necessàries per tal de generar una taula de valoració (PDF)
	 * @param dictamenId Identificador del dictamen del qual es vol generar la taula de valoració
	 * @return Conjunt de dades que representen la taula de valoració
	 */
	public List<Object[]> getDadesTaulaValoracio(Long dictamenId);

	/**
	 * Aquesta funcionalitat fa els controls i la lògica per posar un dictamen com a Vigent.
	 * Implica que generarà la taula de valoració en format PDF, la signarà mitjançant portafib i la pujarà al gestor documental
	 * És necessari que el dictamen es trobi en estat 'publicable'
	 * @param dictamen
	 */
	public void publicarDictamen(Dictamen dictamen);

	/**
	 * Determina si un dictamen es pot passar a estat Vigent (es pot publicar al gestor documental)
	 * @param dictamen
	 * @return
	 */
	public boolean isPublicable(Dictamen dictamen);
}


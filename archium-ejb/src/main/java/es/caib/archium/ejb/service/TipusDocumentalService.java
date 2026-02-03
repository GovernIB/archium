package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusDocumental;

@Local
public interface TipusDocumentalService extends DAO<TipusDocumental, Long> {

	/**
	 * Crea o actualiza un TipusDocumental con las validaciones correspondientes
	 * @param tipusDocumental Entidad a guardar
	 * @param esNou true si es alta, false si es edición
	 * @param prefijo necesario si el usuario no introduce código en el alta del documento
	 * @param usuariActual nombre del usuario para trazabilidad
	 * @return Entidad guardada
	 * @throws I18NException si hay errores de validación o persistencia
	 */
	TipusDocumental guardarAmbValidacio(TipusDocumental tipusDocumental, String prefix, String usuariActual,boolean esNou) throws I18NException;
	
	/**
	 * Busca un TipusDocumental por código
	 * @param código del tipo documental que se quiere buscar
	 * @return Entidad encontradas
	 * @throws I18NException si hay errores
	 */
	TipusDocumental trobarPerCodi(String codi) throws I18NException;
	
	/**
	 * Elimina un tipo documental de la base de datos
	 * @param código del tipo documental que se quiere buscar
	 * @param usuariActual nombre del usuario para trazabilidad
	 * @throws I18NException si hay errores
	 */
	void eliminar(String codi, String usuariActual, boolean logic) throws I18NException;
	
	/**
	 * Helper: Obtiene solo los tipos documentales activos (disponibles para uso)
	 * @return Lista de tipos documentales en estado ACTIU
	 */
	List<TipusDocumental> trobarActius() throws I18NException;
	
	public boolean entitatRelacionades(Long id);

}
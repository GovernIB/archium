package es.caib.archium.ejb.service;

import java.io.InputStream;
import java.util.List;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusDocumental;

@Local
public interface TipusDocumentalExportService extends DAO<TipusDocumental, Long> {
	
	/**
	 * Método que construye un mapa con varios filtros para cribar tipos documentales
	 * @param código del tipo documental
	 * @param nom del tipo documental
	 * @param estat del tipo documental
	 */
	List<TipusDocumental> findByFilters(String codi, String nom, String estat) throws I18NException;
	
	/**
	 * Método que genera un csv a partir de un listado de tipos documentales filtrados (o no)
	 */
	InputStream generaCsvTipusDocumentalsFiltrats(List<TipusDocumental> tipusDocumentals);
	
	/**
	 * Método que genera un pdf a partir de un listado de tipos documentales filtrados (o no)
	 */
	InputStream generaPdfTipusDocumentals(List<TipusDocumental> lista, Idioma idioma);

}
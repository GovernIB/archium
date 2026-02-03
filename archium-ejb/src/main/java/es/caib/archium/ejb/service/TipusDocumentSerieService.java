package es.caib.archium.ejb.service;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusdocumentSerie;
import es.caib.archium.persistence.model.TipusdocumentSeriePK;

import javax.ejb.Local;
import java.util.List;

@Local
public interface TipusDocumentSerieService extends DAO<TipusdocumentSerie, TipusdocumentSeriePK> {

	public List<TipusdocumentSerie> getTipusDocument(Long serieId) throws I18NException;
	
}


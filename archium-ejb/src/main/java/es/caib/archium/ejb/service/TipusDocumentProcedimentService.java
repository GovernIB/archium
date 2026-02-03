package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.TipusdocumentProcedimentPK;

@Local
public interface TipusDocumentProcedimentService extends DAO<TipusdocumentProcediment, TipusdocumentProcedimentPK> {

	public List<TipusdocumentProcediment> getTipusDocument(Long procedimentId) throws I18NException;
	
}


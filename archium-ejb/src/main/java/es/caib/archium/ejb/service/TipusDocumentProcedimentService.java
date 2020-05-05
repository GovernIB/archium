package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.TipusdocumentProcedimentPK;

@Local
public interface TipusDocumentProcedimentService extends DAO<TipusdocumentProcediment, TipusdocumentProcedimentPK> {

}


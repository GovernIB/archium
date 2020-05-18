package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.Tipusdocumental;

@Local
public interface TipusDocumentService extends DAO<Tipusdocumental, Long> {

}


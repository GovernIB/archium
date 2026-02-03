package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusDocumental;

@Local
public interface TipusDocumentService extends DAO<TipusDocumental, Long> {

}


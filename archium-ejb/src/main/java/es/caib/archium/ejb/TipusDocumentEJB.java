package es.caib.archium.ejb;

import es.caib.archium.ejb.service.TipusDocumentProcedimentService;
import es.caib.archium.ejb.service.TipusDocumentService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.Tipusdocumental;

import javax.ejb.Local;

@Local
public class TipusDocumentEJB extends AbstractDAO<Tipusdocumental, Long> implements TipusDocumentService  {

		
}

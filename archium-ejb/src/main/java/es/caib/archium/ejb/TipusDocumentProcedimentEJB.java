package es.caib.archium.ejb;

import es.caib.archium.ejb.service.TipusDocumentProcedimentService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.TipusdocumentProcedimentPK;

import javax.ejb.Local;

@Local
public class TipusDocumentProcedimentEJB extends AbstractDAO<TipusdocumentProcediment, TipusdocumentProcedimentPK> implements TipusDocumentProcedimentService  {

		
}

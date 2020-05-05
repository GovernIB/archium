package es.caib.archium.ejb;

import javax.ejb.Local;

import es.caib.archium.ejb.service.ProcedimientoService;
import es.caib.archium.ejb.service.tablasMaestras.AplicacioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Aplicacio;
import es.caib.archium.persistence.model.Nivellelectronic;
import es.caib.archium.persistence.model.Procediment;
import es.caib.archium.persistence.model.Silenci;

@Local
public class AplicacioEJB extends AbstractDAO<Aplicacio, Long> implements AplicacioService  {
		
}

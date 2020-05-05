package es.caib.archium.ejb;

import javax.ejb.Local;

import es.caib.archium.ejb.service.AplicacionService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Aplicacio;

@Local
public class AplicacionEJB extends AbstractDAO<Aplicacio, Long> implements AplicacionService{

}

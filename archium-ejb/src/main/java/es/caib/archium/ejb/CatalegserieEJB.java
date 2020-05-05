package es.caib.archium.ejb;

import javax.ejb.Local;

import es.caib.archium.ejb.service.CatalegserieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Catalegsery;

@Local
public class CatalegserieEJB extends AbstractDAO<Catalegsery, Long> implements CatalegserieService{

}

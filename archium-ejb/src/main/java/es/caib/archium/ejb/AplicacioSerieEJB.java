package es.caib.archium.ejb;

import javax.ejb.Local;

import es.caib.archium.ejb.service.AplicacioSerieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.AplicacioSerie;
import es.caib.archium.persistence.model.AplicacioSeriePK;

@Local
public class AplicacioSerieEJB  extends AbstractDAO<AplicacioSerie,AplicacioSeriePK> implements AplicacioSerieService{

}

package es.caib.archium.ejb.service.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Tipusserie;

@Local
public interface TipusserieService extends DAO<Tipusserie, Long> {

}
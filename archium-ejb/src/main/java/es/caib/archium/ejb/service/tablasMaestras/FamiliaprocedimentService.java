package es.caib.archium.ejb.service.tablasMaestras;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Familiaprocediment;

@Local
public interface FamiliaprocedimentService extends DAO<Familiaprocediment, Long> {

}
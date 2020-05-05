package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Familiaprocediment;
import es.caib.archium.persistence.model.Procediment;

@Local
public interface FamiliaProcedimientService extends DAO<Familiaprocediment, Long> {

}
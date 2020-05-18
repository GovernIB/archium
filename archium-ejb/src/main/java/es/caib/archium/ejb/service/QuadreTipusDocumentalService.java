package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Quadretipusdocumental;

@Local
public interface QuadreTipusDocumentalService extends DAO<Quadretipusdocumental, Long> {

}
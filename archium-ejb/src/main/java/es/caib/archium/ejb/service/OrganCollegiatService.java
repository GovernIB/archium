package es.caib.archium.ejb.service;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.OrganCollegiat;

import javax.ejb.Local;

@Local
public interface OrganCollegiatService extends DAO<OrganCollegiat, Long> {

}
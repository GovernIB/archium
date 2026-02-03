package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.CategoriaNti;


@Local
public interface CategoriaNtiService extends DAO<CategoriaNti, Long> {

}
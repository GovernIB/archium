package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Categorianti;


@Local
public interface CategoriaNtiService extends DAO<Categorianti, Long> {

}
package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Tipusvalor;

@Local
public interface TipusvalorService extends DAO<Tipusvalor, Long> {

}
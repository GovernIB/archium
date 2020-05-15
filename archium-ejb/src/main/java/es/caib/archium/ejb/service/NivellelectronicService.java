package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Nivellelectronic;

@Local
public interface NivellelectronicService extends DAO<Nivellelectronic, Long> {

}
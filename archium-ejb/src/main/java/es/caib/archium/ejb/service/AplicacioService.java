package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Aplicacio;

import java.util.List;

@Local
public interface AplicacioService extends DAO<Aplicacio, Long> {
    public List<String> llistaAplicacions() throws I18NException;
}

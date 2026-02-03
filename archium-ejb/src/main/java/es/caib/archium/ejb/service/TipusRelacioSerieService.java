package es.caib.archium.ejb.service;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusRelacioSerie;

import javax.ejb.Local;

@Local
public interface TipusRelacioSerieService extends DAO<TipusRelacioSerie, Long> {

    public TipusRelacioSerie findByCodi(String codi) throws I18NException;
}


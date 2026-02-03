package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipuAcces;

import java.util.List;

@Local
public interface TipuAccesService extends DAO<TipuAcces, Long> {

    public List<String> llistaAccessos(Idioma idioma) throws I18NException;
}


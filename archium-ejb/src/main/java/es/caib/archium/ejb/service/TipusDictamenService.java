package es.caib.archium.ejb.service;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.TipusDictamen;

import java.util.List;

@Local
public interface TipusDictamenService extends DAO<TipusDictamen, Long> {

    public List<String> llistaTipusDictamen(Idioma idioma) throws I18NException;

}


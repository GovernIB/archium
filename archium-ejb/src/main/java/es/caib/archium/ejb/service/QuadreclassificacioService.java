package es.caib.archium.ejb.service;

import java.util.List;

import javax.ejb.Local;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Quadreclassificacio;
import es.caib.archium.persistence.model.Serierelacionada;

@Local
public interface QuadreclassificacioService extends DAO<Quadreclassificacio, Long> {
	
}

package es.caib.archium.ejb.service;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Procediment;

import javax.ejb.Local;
import java.util.List;

@Local
public interface ProcedimientoService extends DAO<Procediment, Long> {

    public List<Procediment> findProcedimentsBySerie(Long serieId) throws I18NException;

    public List<Procediment> findProcedimentsSenseSerie() throws I18NException;

    public Procediment findProcedimentByCodiSia(String codiSia);
}
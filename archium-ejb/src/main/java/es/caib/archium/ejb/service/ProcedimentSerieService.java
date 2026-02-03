package es.caib.archium.ejb.service;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Procediment;
import es.caib.archium.persistence.model.ProcedimentSerie;
import es.caib.archium.persistence.model.ProcedimentSeriePK;

import javax.ejb.Local;
import java.util.List;
import java.util.Set;

@Local
public interface ProcedimentSerieService extends DAO<ProcedimentSerie, ProcedimentSeriePK> {

    public List<ProcedimentSerie> getProcedimentComuns(Long serieId) throws I18NException;

    public Set<Procediment> getTotProcedimentComuns() throws I18NException;
}


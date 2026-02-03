package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.ProcedimentSerieService;
import es.caib.archium.ejb.service.SerieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Procediment;
import es.caib.archium.persistence.model.ProcedimentSerie;
import es.caib.archium.persistence.model.ProcedimentSeriePK;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class ProcedimentSerieEJB extends AbstractDAO<ProcedimentSerie, ProcedimentSeriePK> implements ProcedimentSerieService  {

    @Inject
    SerieService serieEJB;

    public List<ProcedimentSerie> getProcedimentComuns(Long serieId) throws I18NException {
        if (serieId != null) {
            Map<String,Object> filters  = new HashMap<>();
            filters.put("achSerieDocumental", serieEJB.getReference(serieId));
            return this.findFiltered(filters);
        } else {
            throw new I18NException("procedimentserie.getProcedimentComuns.id.null", this.getClass().getSimpleName(), "getProcedimentComuns");
        }
    }

    public Set<Procediment> getTotProcedimentComuns() throws I18NException {
            Set<Procediment> procedimentsComuns = new HashSet<>();

            List<ProcedimentSerie> procedimentSeries = this.findAll();
            if (procedimentSeries != null) {
                for (ProcedimentSerie ps : procedimentSeries) {
                    procedimentsComuns.add(ps.getAchProcediment());
                }
            }
            return procedimentsComuns;
    }
}

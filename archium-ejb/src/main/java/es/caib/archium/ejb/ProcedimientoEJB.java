package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.ProcedimientoService;
import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Procediment;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.NoResultException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class ProcedimientoEJB extends AbstractDAO<Procediment, Long> implements ProcedimientoService {

    @Inject
    private SerieDocumentalService serieService;

    @Override
    public List<Procediment> findProcedimentsBySerie(Long serieId) throws I18NException {

        if (serieId != null) {
            Map<String, Object> filters = new HashMap<>();
            filters.put("achSeriedocumental", serieService.getReference(serieId));
            return this.findFiltered(filters);
        } else {
            throw new I18NException("procediments.findProcedimentsBySerie.id.null", this.getClass().getSimpleName(), "findProcedimentsBySerie");
        }
    }

    @Override
    public List<Procediment> findProcedimentsSenseSerie() throws I18NException {

        Map<String, Object> filters = new HashMap<>();
        filters.put("achSeriedocumental", null);
        return this.findFiltered(filters);
    }

    @Override
    @PermitAll
    public Procediment findProcedimentByCodiSia(String codiSia) {
        Procediment prc = null;
        if (codiSia != null) {
            try {
                prc = entityManager.createQuery("SELECT p FROM Procediment p WHERE p.codisia = :codiSia", Procediment.class)
                        .setParameter("codiSia", codiSia).getSingleResult();
            } catch (NoResultException e) {
                log.debug("No s'ha trobat procediment per al codi SIA " + codiSia);
            }
        } else {
            List<Procediment> procediments = entityManager.createQuery("SELECT p FROM Procediment p WHERE p.codisia IS NULL", Procediment.class).getResultList();
            if (procediments != null && procediments.size() > 0) {
                prc = procediments.get(0);
            }
        }
        return prc;
    }
}

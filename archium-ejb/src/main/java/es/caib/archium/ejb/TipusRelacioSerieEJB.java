package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.TipusRelacioSerieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipusRelacioSerie;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusRelacioSerieEJB extends AbstractDAO<TipusRelacioSerie, Long> implements TipusRelacioSerieService {

    public TipusRelacioSerie findByCodi(String codi) throws I18NException {
        Map<String, Object> filters = new HashMap<>();
        filters.put("codi", codi);
        List<TipusRelacioSerie> resultats = this.findFiltered(filters);

        TipusRelacioSerie tipusRelacio = null;
        if (resultats != null && resultats.size() > 0) {
            tipusRelacio = resultats.get(0);
        }
        return tipusRelacio;
    }
    
    @Override
    public boolean tieneRelaciones(Long id) {
    	
    	log.info("Verificando relaciones para TipusRelacioSerie con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(s) FROM Serierelacionada s WHERE s.tipusRelacioSerie.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("TipusRelacioSerie id={} tiene {} relaciones con Serierelacionada. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Serierelacionada");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("TipusRelacionesSerie id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("TipusRelacionesSerie id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA) > 0;
	}
}

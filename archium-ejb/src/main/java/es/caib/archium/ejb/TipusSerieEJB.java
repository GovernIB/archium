package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipusSerieService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipusSerie;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusSerieEJB extends AbstractDAO<TipusSerie, Long> implements TipusSerieService  {
	
	@Override
    public boolean tieneRelaciones(Long id) {
		
		log.info("Verificando relaciones para TipusSerie con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(s) FROM Funcio s WHERE s.achTipusserie.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("TipusSerie id={} tiene {} relaciones con Funcio. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Funcio");
	    }
		
		Long comptarB = entityManager.createQuery("SELECT COUNT(s) FROM Seriedocumental s WHERE s.achTipusserie.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarB > 0) {
	        log.info("TipusSerie id={} tiene {} relaciones con Seriedocumental. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Seriedocumental");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("TipusSerie id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("TipusSerie id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA + comptarB) > 0;
	}
	
}

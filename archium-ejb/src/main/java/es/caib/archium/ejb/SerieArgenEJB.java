package es.caib.archium.ejb;


import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.SerieArgenService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.SerieArgen;


@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class SerieArgenEJB extends AbstractDAO<SerieArgen, Long> implements SerieArgenService{
	
	@Override
    public boolean tieneRelaciones(Long id) {
		
		log.info("Verificando relaciones para SerieArgen con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(s) FROM Serierelacionada s WHERE s.achSerieargen.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("SerieArgen id={} tiene {} relaciones con Serierelacionada. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Serierelacionada");
	    }
		
		Long comptarB = entityManager.createQuery("SELECT COUNT(s) FROM Seriedocumental s JOIN s.achSerieargens p WHERE p.id = :id",Long.class)
				.setParameter("id",id)
				.getSingleResult();
		
		if (comptarB > 0) {
	        log.info("SerieArgen id={} tiene {} relaciones con Seriedocumental. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Seriedocumental");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("SerieArgen id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("SerieArgen id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA + comptarB) > 0;
	}
	
}

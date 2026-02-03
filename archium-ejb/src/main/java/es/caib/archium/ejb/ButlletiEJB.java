package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.ButlletiService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Butlleti;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class ButlletiEJB extends AbstractDAO<Butlleti, Long> implements ButlletiService  {
	
	@Override
    public boolean tieneRelaciones(Long id) {
		
		log.info("Verificando relaciones para Butlleti con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(n) FROM Normativa n WHERE n.butlleti.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("Butlleti id={} tiene {} relaciones con Normativa. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Normativa");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("Butlleti id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("Butlleti id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA) > 0;
	}
	
}

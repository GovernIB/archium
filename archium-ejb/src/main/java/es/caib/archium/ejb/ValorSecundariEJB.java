package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.ValorSecundariService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.ValorSecundari;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class ValorSecundariEJB extends AbstractDAO<ValorSecundari, Long> implements ValorSecundariService {
	
	@Override
    public boolean tieneRelaciones(Long id) {
		
		log.info("Verificando relaciones para ValorSecundari con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(s) FROM Valoracio s WHERE s.achValorsecundari.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("ValorSecundari id={} tiene {} relaciones con Valoracio. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Valoracio");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("ValorSecundari id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("ValorSecundari id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA) > 0;
	}
	
}
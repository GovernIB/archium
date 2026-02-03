package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.LopdService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Lopd;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class LopdEJB extends AbstractDAO<Lopd, Long> implements LopdService  {

	@Override
    public boolean tieneRelaciones(Long id) {
		
		log.info("Verificando relaciones para Lopd con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(d) FROM Dictamen d WHERE d.achLopd.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("Lopd id={} tiene {} relaciones con Dictamen. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Dictamen");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("Lopd id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("Lopd id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA) > 0;
	}
	
}

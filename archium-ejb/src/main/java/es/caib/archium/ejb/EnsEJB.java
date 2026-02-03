package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.EnsService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Ens;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class EnsEJB extends AbstractDAO<Ens, Long> implements EnsService  {

	@Override
    public boolean tieneRelaciones(Long id) {
		
		log.info("Verificando relaciones para Ens con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(d) FROM Dictamen d WHERE d.achEn.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("Ens id={} tiene {} relaciones con Dictamen. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Dictamen");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("Ens id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("Ens id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA) > 0;
	}
	
}

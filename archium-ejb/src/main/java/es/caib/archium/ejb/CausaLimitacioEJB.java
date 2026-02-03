package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.CausaLimitacioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.CausaLimitacio;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class CausaLimitacioEJB extends AbstractDAO<CausaLimitacio, Long> implements CausaLimitacioService{
	
	@Override
    public boolean tieneRelaciones(Long id) {
		
		log.info("Verificando relaciones para CausaLimitacio con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(l) FROM LimitacioNormativaSerie l WHERE l.achCausalimitacio.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("CausaLimitacio id={} tiene {} relaciones con LimitacioNormativaSerie. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " LimitacioNormativaSerie");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if (tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("CausaLimitacio id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("CausaLimitacio id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA) > 0;
	}

}
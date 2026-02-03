package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipusNormativaService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipusNormativa;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusNormativaEJB extends AbstractDAO<TipusNormativa, Long> implements TipusNormativaService {

	@Override
    public boolean tieneRelaciones(Long id) {
		
		log.info("Verificando relaciones para TipusNormativa con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(n) FROM Normativa n WHERE n.tipusNormativa.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("TipusNormativa id={} tiene {} relaciones con Normativa. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Normativa");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("TipusNormativa id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("TipusNormativa id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA) > 0;
	}
	
}

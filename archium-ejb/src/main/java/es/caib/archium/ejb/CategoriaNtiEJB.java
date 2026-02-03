package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.CategoriaNtiService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.CategoriaNti;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class CategoriaNtiEJB extends AbstractDAO<CategoriaNti, Long> implements CategoriaNtiService{
	
	@Override
    public boolean tieneRelaciones(Long id) {
		
		log.info("Verificando relaciones para CategoriaNti con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(n) FROM TipusNti n WHERE n.achCategorianti.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("CategoriaNti id={} tiene {} relaciones con TipusNti. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Tipus Nti");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("Categoria Nti id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("Categoria Nti id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA) > 0;
	}
	
}

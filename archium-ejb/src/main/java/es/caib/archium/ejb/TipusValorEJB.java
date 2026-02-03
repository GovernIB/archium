package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.TipusValorService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipusValor;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusValorEJB extends AbstractDAO<TipusValor, Long> implements TipusValorService{

	@Override
    public boolean tieneRelaciones(Long id) {
		
		log.info("Verificando relaciones para TipusValor con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(v) FROM Valorprimari v WHERE v.achTipusvalor.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("TipusValor id={} tiene {} relaciones con Valorprimari. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Valor Primari");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if (tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("TipusValor id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("TipusValor id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA) > 0;
	}
	
	
}
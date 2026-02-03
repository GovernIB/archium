package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.ejb.service.TipusNtiService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipusNti;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusNtiEJB extends AbstractDAO<TipusNti, Long> implements TipusNtiService {
	
	public List<String> trobarPrefixos() throws I18NException {

		List<String> prefixos = new ArrayList<>();
			
		prefixos.addAll(findAll(new OrderBy("codi")).stream().map(n -> n.getCodi().substring(2)).collect(Collectors.toList()));
			
		return prefixos;
		
	}
	
	public TipusNti trobarByPrefix(String prefix) throws I18NException {
	    Map<String, Object> filters = new HashMap<>();
	    filters.put("codi", prefix);
	    
	    List<TipusNti> resultats = findFiltered(filters);
	    
	    log.info("Primer resultado obtenido: {}", resultats.isEmpty() ? null : resultats.get(0));
		
		return resultats.isEmpty() ? null : resultats.get(0);
	}
	
	@Override
    public boolean tieneRelaciones(Long id) {
		
		log.info("Verificando relaciones para TipusNti con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(s) FROM TipusDocumental s WHERE s.tipusNti.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("TipusNti id={} tiene {} relaciones con TipusDocumental. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " TipusDocumental");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("TipusNti id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("TipusNti id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA) > 0;
	}

}
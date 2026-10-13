package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import es.caib.archium.ejb.service.CausaLimitacioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.CausaLimitacio;
import es.caib.archium.persistence.model.Normativa;

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
	
	@Override
	public List<CausaLimitacio> buscarPorTexto(String texto) {
	    String filtro = "%" + texto.toLowerCase() + "%";

	    String conAcentos = "áéíóúÁÉÍÓÚüÜàèìòùÀÈÌÒÙ";
	    String sinAcentos = "aeiouAEIOUuUaeiouAEIOU";

	    String jpql = "SELECT c FROM CausaLimitacio c " +
	            "WHERE LOWER(FUNCTION('TRANSLATE', c.codi, '" + conAcentos + "', '" + sinAcentos + "')) LIKE :filtro " +
	            "OR LOWER(FUNCTION('TRANSLATE', c.nom, '" + conAcentos + "', '" + sinAcentos + "')) LIKE :filtro " +
	            "ORDER BY c.nom ASC";

	    TypedQuery<CausaLimitacio> query = entityManager.createQuery(jpql, CausaLimitacio.class);
	    query.setParameter("filtro", filtro);

	    return query.getResultList();
	}

}

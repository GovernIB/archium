package es.caib.archium.ejb;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.persistence.TypedQuery;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.ejb.service.AplicacioService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Aplicacio;
import es.caib.archium.persistence.model.Normativa;

import java.util.ArrayList;
import java.util.List;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class AplicacioEJB extends AbstractDAO<Aplicacio, Long> implements AplicacioService  {

    @PermitAll
    public List<String> llistaAplicacions() throws I18NException {
        OrderBy order = new OrderBy("nom", OrderType.ASC);
        List<Aplicacio> aplicacions = this.findAll(order);
        List<String> possiblesAplicacions = new ArrayList<>();
        if (aplicacions != null) {
            for (Aplicacio apl : aplicacions) {
                possiblesAplicacions.add(apl.getNom());
            }
        }
        return possiblesAplicacions;
    }
    
    @Override
    public boolean tieneRelaciones(Long id) {
    	
		log.info("Verificando relaciones para Aplicacio con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(a) FROM AplicacioSerie a WHERE a.achAplicacio.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("Aplicacio id={} tiene {} relaciones con AplicacioSerie. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " AplicacioSerie");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if (tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("Aplicacio id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("Aplicacio id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA) > 0;
	}
    
    @Override
	public List<Aplicacio> buscarPorTexto(String texto) {
	    String filtro = "%" + texto.toLowerCase() + "%";

	    String conAcentos = "áéíóúÁÉÍÓÚüÜàèìòùÀÈÌÒÙ";
	    String sinAcentos = "aeiouAEIOUuUaeiouAEIOU";

	    String jpql = "SELECT a FROM Aplicacio a " +
	            "WHERE LOWER(FUNCTION('TRANSLATE', a.nom, '" + conAcentos + "', '" + sinAcentos + "')) LIKE :filtro " +
	            "ORDER BY a.nom ASC";

	    TypedQuery<Aplicacio> query = entityManager.createQuery(jpql, Aplicacio.class);
	    query.setParameter("filtro", filtro);

	    return query.getResultList();
	}
}

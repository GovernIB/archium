package es.caib.archium.ejb;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.ejb.service.NormativaService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Normativa;


@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class NormativaEJB extends AbstractDAO<Normativa, Long> implements NormativaService  {
	
	@Override
    public boolean tieneRelaciones(Long id) {
		
		log.info("Verificando relaciones para Normativa con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(d) FROM Dictamen d WHERE d.achNormativa.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("Normativa id={} tiene {} relaciones con Dictamen. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Dictamen");
	    }
		
		Long comptarB = entityManager.createQuery("SELECT COUNT(d) FROM Dictamen d JOIN d.achNormativas n WHERE n.id = :id",Long.class)
				.setParameter("id",id)
				.getSingleResult();
		
		if (comptarB > 0) {
	        log.info("Normativa id={} tiene {} relaciones con Dictamen. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Dictamen");
	    }
		
		Long comptarC = entityManager.createQuery("SELECT COUNT(l) FROM LimitacioNormativaSerie l WHERE l.achNormativa.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarC > 0) {
	        log.info("Normativa id={} tiene {} relaciones con LimitacioNormativaSerie. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " LimitacioNormativaSerie");
	    }
		
		Long comptarD = entityManager.createQuery("SELECT COUNT(n) FROM NormativaSeriedocumental n WHERE n.achNormativa.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();
		
		if (comptarD > 0) {
	        log.info("Normativa id={} tiene {} relaciones con NormativaSeriedocumental. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " NormativaSeriedocumental");
	    }
		
		Long comptarE = entityManager.createQuery("SELECT COUNT(p) FROM Procediment p JOIN p.achNormativas n WHERE n.id = :id",Long.class)
				.setParameter("id",id)
				.getSingleResult();
		
		if (comptarE > 0) {
	        log.info("Normativa id={} tiene {} relaciones con Procediment. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Procediment");
	    }
		
		Long comptarF = entityManager.createQuery("SELECT COUNT(d) FROM TipusDocumental d JOIN d.achNormativas n WHERE n.id = :id",Long.class)
				.setParameter("id",id)
				.getSingleResult();
		
		if (comptarF > 0) {
	        log.info("Normativa id={} tiene {} relaciones con TipusDocumental. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " TipusDocumental");
	    }
		
		Long comptarG = entityManager.createQuery("SELECT COUNT(n) FROM Normativa n WHERE n.achNormativa.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();
		
		if (comptarG > 0) {
	        log.info("Normativa id={} tiene {} relaciones con Normativa. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Normativa");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("Normativa id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("Normativa id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA + comptarB + comptarC + comptarD + comptarE + comptarF + comptarG) > 0;
	}
	
}
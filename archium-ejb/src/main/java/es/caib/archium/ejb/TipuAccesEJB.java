package es.caib.archium.ejb;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.ejb.service.TipuAccesService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipuAcces;

import java.util.ArrayList;
import java.util.List;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipuAccesEJB extends AbstractDAO<TipuAcces, Long> implements TipuAccesService  {

    @PermitAll
	public List<String> llistaAccessos(Idioma idioma) throws I18NException {

        OrderBy order = new OrderBy("nom" + (Idioma.CASTELLA.equals(idioma) ? "cas" : ""), OrderType.ASC);
        List<TipuAcces> accessos = this.findAll(order);
        List<String> possiblesAccessos = new ArrayList<>();
        if (accessos != null) {
            for (TipuAcces tipuAcces : accessos) {
                possiblesAccessos.add(Idioma.CASTELLA.equals(idioma) ? tipuAcces.getNomcas() : tipuAcces.getNom());
            }
        }
        return possiblesAccessos;
    }
    
    @Override
    public boolean tieneRelaciones(Long id) {
    	
    	log.info("Verificando relaciones para TipuAcces con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(s) FROM Dictamen s WHERE s.achTipusacce.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("TipuAcces id={} tiene {} relaciones con Dictamen. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " Dictamen");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("TipusAcces id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("TipusAcces id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA) > 0;
	}
    
}

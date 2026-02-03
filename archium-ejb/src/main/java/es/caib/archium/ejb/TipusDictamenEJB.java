package es.caib.archium.ejb;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.i18n.Idioma;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.ejb.service.TipusDictamenService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipusDictamen;

import java.util.ArrayList;
import java.util.List;


@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusDictamenEJB extends AbstractDAO<TipusDictamen, Long> implements TipusDictamenService {

    @PermitAll
    public List<String> llistaTipusDictamen(Idioma idioma) throws I18NException {
        OrderBy order = new OrderBy("nom" + (Idioma.CASTELLA.equals(idioma) ? "cas" : ""), OrderType.ASC);
        List<TipusDictamen> tipusDictamens = this.findAll(order);
        List<String> possiblesTipusDictamen = new ArrayList<>();
        if (tipusDictamens != null) {
            for (TipusDictamen td : tipusDictamens) {
                possiblesTipusDictamen.add(Idioma.CASTELLA.equals(idioma) ? td.getNomcas() : td.getNom());
            }
        }
        return possiblesTipusDictamen;
    }
    
    @Override
    public boolean tieneRelaciones(Long id) {
		
    	log.info("Verificando relaciones para TipusDictamen con id: {}", id);
		
	    List<String> entidadesRelacionadas = new ArrayList<>();
    	
		Long comptarA = entityManager.createQuery("SELECT COUNT(s) FROM Dictamen s WHERE s.achTipusdictamen.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarA > 0) {
	        log.info("TipusDictamen id={} tiene {} relaciones con Dictamen. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + "Dictamen");
	    }
		
		Long comptarB = entityManager.createQuery("SELECT COUNT(s) FROM DictamenTipusdocumental s WHERE s.achTipusdictamen.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		if (comptarB > 0) {
	        log.info("TipusDictamen id={} tiene {} relaciones con DictamenTipusdocumental. No se puede eliminar.", id, comptarA);
	        entidadesRelacionadas.add(comptarA + " DictamenTipusdocumental");
	    }
		
		boolean tieneRelaciones = !entidadesRelacionadas.isEmpty();
		
		if(tieneRelaciones) {
			
			this.detalleRelaciones = String.join(", ", entidadesRelacionadas);
		    log.warn("TipusDictamen id={} tiene relaciones: {}", id, this.detalleRelaciones);
			
		} else {			
			log.info("TipusDictamen id={} no tiene relaciones, se puede eliminar", id);
		}
		
		return (comptarA + comptarB) > 0;
	}
}

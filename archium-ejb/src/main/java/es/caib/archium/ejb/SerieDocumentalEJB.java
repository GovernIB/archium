package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Normativa;
import es.caib.archium.persistence.model.Seriedocumental;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import java.util.List;

@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class SerieDocumentalEJB extends AbstractDAO<Seriedocumental, Long> implements SerieDocumentalService  {

    // Error ~redmine:#6298 Al eliminar un dictamen també s'ha de desvincular la seva normativa associada
    @Override
    public void delete(Long id) throws I18NException {

        Seriedocumental s = this.entityManager.find(Seriedocumental.class, id);
        log.debug("Obtenint els dictàmens de la sèrie...");
        for (Dictamen d : s.getAchDictamens()) {
            List<Normativa> normativesDictamen = d.getAchNormativas();
            if (normativesDictamen != null && normativesDictamen.size() > 0) {
                log.debug("Del dictamen amb id = " + d.getId() + " llevarem la normativa associada...");
                for (Normativa n : d.getAchNormativas()) {
                    log.debug("Llevam el dictamen associat a la normativa (relació dictamen - normativa)... id Normativa = " + n.getId());
                    n.getAchDictamens2().remove(d);
                }
            }
        }
        super.delete(id);
    }
    
    @Override
    public boolean tieneRelaciones(Long id) {
		
		Long comptarA = entityManager.createQuery("SELECT COUNT(a) FROM AplicacioSerie a WHERE a.achSeriedocumental.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		Long comptarB = entityManager.createQuery("SELECT COUNT(d) FROM Dictamen d WHERE d.achSeriedocumental.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		Long comptarC = entityManager.createQuery("SELECT COUNT(d) FROM LimitacioNormativaSerie d WHERE d.achSeriedocumental.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		Long comptarD = entityManager.createQuery("SELECT COUNT(d) FROM NormativaSeriedocumental d WHERE d.achSeriedocumental.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		Long comptarE = entityManager.createQuery("SELECT COUNT(d) FROM Procediment d WHERE d.achSeriedocumental.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		Long comptarF = entityManager.createQuery("SELECT COUNT(d) FROM ProcedimentSerie d WHERE d.achSeriedocumental.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		Long comptarG = entityManager.createQuery("SELECT COUNT(s) FROM SerieArgen s JOIN s.achSeriedocumentals p WHERE p.id = :id",Long.class)
				.setParameter("id",id)
				.getSingleResult();
		
		Long comptarH = entityManager.createQuery("SELECT COUNT(d) FROM Serierelacionada d WHERE d.achSeriedocumental1.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();
		
		Long comptarI = entityManager.createQuery("SELECT COUNT(d) FROM Serierelacionada d WHERE d.achSeriedocumental2.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		Long comptarJ = entityManager.createQuery("SELECT COUNT(d) FROM TipusdocumentSerie d WHERE d.achSeriedocumental.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();
		
		Long comptarK = entityManager.createQuery("SELECT COUNT(d) FROM Transferencia d WHERE d.achSeriedocumental.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();
		
		return (comptarA + comptarB + comptarC + comptarD + comptarE + comptarF + comptarG + comptarH + comptarI + comptarJ + comptarK) > 0;
	}
}

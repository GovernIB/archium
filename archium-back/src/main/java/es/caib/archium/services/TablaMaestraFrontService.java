package es.caib.archium.services;


import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.persistence.dao.DAO;
import es.caib.archium.persistence.model.Aplicacio;
import es.caib.archium.persistence.model.Butlleti;
import es.caib.archium.persistence.model.CategoriaNti;
import es.caib.archium.persistence.model.CausaLimitacio;
import es.caib.archium.persistence.model.Ens;
import es.caib.archium.persistence.model.Lopd;
import es.caib.archium.persistence.model.Normativa;
import es.caib.archium.persistence.model.SerieArgen;
import es.caib.archium.persistence.model.TaulaMestra;
import es.caib.archium.persistence.model.TipuAcces;
import es.caib.archium.persistence.model.TipusDictamen;
import es.caib.archium.persistence.model.TipusNormativa;
import es.caib.archium.persistence.model.TipusNti;
import es.caib.archium.persistence.model.TipusRelacioSerie;
import es.caib.archium.persistence.model.TipusSerie;
import es.caib.archium.persistence.model.TipusValor;
import es.caib.archium.persistence.model.ValorSecundari;

import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Named;
import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Named("TablaMaestraServices")
@ApplicationScoped
public class TablaMaestraFrontService {

	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	/**
	 * Cerca totes les entitats anotades amb "TaulaMestra"
	 * Només aquestes sortiran al selector
	 * @return
	 */
	public Map<String, Class<?>> findTaules() {
		log.debug("Cercant les taules mestres del model d'Archium...");
		Reflections reflections = new Reflections("es.caib.archium.persistence.model");
		//Set<Class<?>> taulesMestres = reflections.getTypesAnnotatedWith(TaulaMestra.class);

		// Problema a producció: No és possible fer ús de Reflections
		Set<Class<?>> taulesMestres = new HashSet<>(15);
		taulesMestres.add(TipusValor.class);
		taulesMestres.add(Aplicacio.class);
		taulesMestres.add(Butlleti.class);
		taulesMestres.add(CategoriaNti.class);
		taulesMestres.add(CausaLimitacio.class);
		taulesMestres.add(Ens.class);
		taulesMestres.add(Lopd.class);
		taulesMestres.add(Normativa.class);
		taulesMestres.add(SerieArgen.class);
		taulesMestres.add(TipuAcces.class);
		taulesMestres.add(TipusDictamen.class);
		taulesMestres.add(TipusNormativa.class);
		taulesMestres.add(TipusNti.class);
		taulesMestres.add(TipusRelacioSerie.class);
		taulesMestres.add(TipusSerie.class);
		taulesMestres.add(ValorSecundari.class);

		log.debug("Taules mestres trobades: " + (taulesMestres != null ? taulesMestres.size() : 0));
		return taulesMestres.stream().collect(Collectors.toMap(Class::getSimpleName, x -> x));
	}

	public List<Object> findAll(Class<?> entity) {
		List<Object> registres = new ArrayList<>();
		try {
			DAO dao = this.getDaoEJB(entity);
			registres = dao.findAll();
		} catch (ClassNotFoundException | I18NException e) {
			e.printStackTrace();
		}
		return registres;
	}

	public void update(Serializable object, Class<?> entityClass) throws I18NException {
		DAO dao = null;
		try {
			dao = this.getDaoEJB(entityClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		dao.update(object);
	}

	public void save(Class<?> entity, Serializable model) {
		try {
			DAO dao = this.getDaoEJB(entity);
			dao.create(model);
		} catch (ClassNotFoundException | I18NException e) {
			e.printStackTrace();
		}
	}	
	
	public void delete(Class<?> entityClass, Long id) throws I18NException {
		DAO dao = null;
		try {
			dao = this.getDaoEJB(entityClass);
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
		// Verificar antes de intentar eliminar
	    if (dao.tieneRelaciones(id)) {
	    	log.info("La entidad {} con id={} está relacionada, no se puede eliminar", entityClass.getSimpleName(), id);
	    	
	    	String detalle = dao.getDetalleRelaciones();

	    	log.info("Detalles de relaciones: {}", detalle);
	    	
	        throw new I18NException(
	            "delete.error.relacionada.detalle",
	            this.getClass().getSimpleName(),
	            "delete",
	            entityClass.getSimpleName(),
	            detalle
	        );
	    }
	    
	    dao.delete(id);
	}

	private DAO getDaoEJB(Class<?> entityClass) throws ClassNotFoundException {
		log.debug("Obtenint informació de la classe " + "es.caib.archium.ejb.service." + entityClass.getSimpleName() + "Service");

		Class<?> type = Class.forName("es.caib.archium.ejb.service." + entityClass.getSimpleName() + "Service");
		return (DAO) CDI.current().select(type).get();
	}
}


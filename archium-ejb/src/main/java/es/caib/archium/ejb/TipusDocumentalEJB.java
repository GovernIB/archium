package es.caib.archium.ejb;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.ejb.service.TipusDocumentalService;
import es.caib.archium.ejb.service.TipusNtiService;
import es.caib.archium.persistence.dao.AbstractDAO;
import es.caib.archium.persistence.model.TipusDocumental;
import es.caib.archium.persistence.model.TipusNti;
import es.caib.archium.persistence.model.validation.ValidacioBasica;
import es.caib.archium.persistence.model.validation.ValidacioCompleta;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.persistence.LockModeType;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;
import javax.transaction.Transactional;
import javax.validation.ConstraintViolation;
import javax.validation.Validator;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;


@Stateless
@RolesAllowed({"ACH_GESTOR"})
public class TipusDocumentalEJB extends AbstractDAO<TipusDocumental, Long> implements TipusDocumentalService {
	
	private static final String ESTAT_ESBORRANY = "ESBORRANY";
	private static final String ESTAT_ACTIU = "ACTIU";
	private static final String ESTAT_OBSOLET = "OBSOLET";
	
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Inject
	private Validator validador;
	
	@Inject
	private TipusNtiService tipusNtiService;

	/**
	 * Crea o actualiza un TipusDocumental con las validaciones correspondientes
	 * @param tipusDocumental Entidad a guardar
	 * @param esNou true si es alta, false si es edición
	 * @param prefix necesario si el usuario no introduce código en el alta del documento
	 * @param usuariActual nombre del usuario para trazabilidad
	 * @return Entidad guardada
	 * @throws I18NException si hay errores de validación o persistencia
	 */
	@Override
	@Transactional
	public TipusDocumental guardarAmbValidacio(TipusDocumental tipusDocumental, String prefix, String usuariActual, boolean esNou) throws I18NException {
		
		try {
			if (esNou) {
				log.info("El tipo documental es nuevo, entrando en el servicio de creación");
				return crearNouTipusDocumental(tipusDocumental,prefix, usuariActual);
			} else {
				return actualitzarTipusDocumental(tipusDocumental, usuariActual);
			}
		} catch (I18NException e) {
			throw e;
		} catch (Exception e) {
			throw new I18NException("tipusdocumental.error.guardar", this.getClass().getSimpleName(),"saveWithValidation",e.getMessage());
		}
		
	}
	
	/**
	 * Crea un TipusDocumental con las validaciones correspondientes
	 * @param tipusDocumental Entidad a guardar
	 * @param prefix necesario si el usuario no introduce código completo en el alta del documento
	 * @param usuariActual nombre del usuario para trazabilidad
	 * @return Entidad guardada
	 * @throws I18NException si hay errores de validación o persistencia
	 */
	private TipusDocumental crearNouTipusDocumental(TipusDocumental tipusDocumental,String prefix, String usuariActual) throws I18NException {
		log.info("Código del tipo que se va a crear: {}. Prefijo proporcionado: {}", tipusDocumental.getCodi(), prefix);
		// Setear el TipusNTI seleccionado a partir del prefijo dado por el usuario
		cercarValitatTipusNti(tipusDocumental,prefix);
		// Validar campos básicos (sin contar el código, pues puede venir vacío para genear)
		validarGrup(tipusDocumental, ValidacioBasica.class);
		// Generar el código
		assignarCodi(tipusDocumental, prefix);
		// Validar formato del código (comprobando de nuevo las básicas)
		tipusDocumental.setEstat(ESTAT_ESBORRANY);
		validarGrup(tipusDocumental, ValidacioCompleta.class);
		// Validar la unicidad del código
		validarCodiUnic(tipusDocumental.getCodi());
		// Queda comprobar si se da estado por defecto al crear
		log.info("El tipo documental ha pasado todas las validaciones. Se va a proceder a su guardado");
		// Establecer valores iniciales
	    Date ara = new Date();
	    
	    tipusDocumental.setInici(ara);
	    tipusDocumental.setModificacio(ara);
	    tipusDocumental.setUsuariInici(usuariActual);
		// Guardar
		return this.create(tipusDocumental);
	}
	
	/**
	 * Actualiza un TipusDocumental con las validaciones correspondientes
	 * @param tipusDocumental Entidad a actualizar
	 * @param usuariActual nombre del usuario para trazabilidad
	 * @return Entidad actualizada
	 * @throws I18NException si hay errores de validación o persistencia
	 */
	private TipusDocumental actualitzarTipusDocumental(TipusDocumental tipusDocumental, String usuariActual) throws I18NException {
		
		log.info("Tipo documental que se está actualizando: {}", tipusDocumental.getCodi());
		
		// Comprobamos que el tipo existe en la BD
		
		TipusDocumental existent = trobarPerCodi(tipusDocumental.getCodi());
		
		if (existent == null) {
			
			log.info("No se ha encontrado el tipo documental a editar en la BD");
			throw new I18NException("tipusdocumental.error.notrobat",this.getClass().getSimpleName(),"actualitzarTipusDocumental",tipusDocumental.getCodi());
		}
		
		// Comprobamos todas las validaciones de entidad
		validarGrup(tipusDocumental,ValidacioCompleta.class);
		
		// Comprobamos la restricción de cambio de estado
		if (!esStatValid(existent.getEstat(), tipusDocumental.getEstat())) {
			log.info("Se ha intentado hacer un cambio de estado no válido. Inicial: {} -> Final: {}", existent.getEstat(),tipusDocumental.getEstat());
			throw new I18NException("tipusdocumental.error.state.transition.invalid",this.getClass().getSimpleName(),"actualitzarTipusDocumental",existent.getEstat(),
					tipusDocumental.getEstat());
		}
		
		// Copiamos los valores que vienen del formulario en la entidad a actualizar
		 existent.setNom(tipusDocumental.getNom());
		 existent.setNomcas(tipusDocumental.getNomcas());
		 existent.setDefinicio(tipusDocumental.getDefinicio());
		 existent.setDefiniciocas(tipusDocumental.getDefiniciocas());
		 existent.setObservacions(tipusDocumental.getObservacions());
		 
		 // Actualizamos la trazabilidad del estado antes de actualizar el valor base
		 
		 if (!existent.getEstat().equals(tipusDocumental.getEstat())) {
			 
			 // Si se ha actualizado el estado se registra
			 
			 existent.setUsuariCanviEstat(usuariActual);
			 existent.setCanviEstat(new Date());
			 
		 }
		 
		 existent.setEstat(tipusDocumental.getEstat());
		 existent.setFi(tipusDocumental.getFi());
		 
		 // Actualizamos el resto de la trazabilidad
		 existent.setModificacio(new Date());
		 existent.setUsuariModificacio(usuariActual);
		 
		 log.info("Campos actualizados en la entidad base a almacenar");

	    
	    // Actualizar el tipo documental
		return this.update(existent);
		
	}
	
	/*
	 * Método que comprueba que el prefijo seleccionado en el formulario coincide con un TipusNti y se establece la relación
	 */
	public void cercarValitatTipusNti(TipusDocumental tipusDocumental,String prefix) throws I18NException {
		
		// No comprobamos que se mande prefijo porque ya se hace en el controlador
		
		log.info("Buscando TipusNti con código: TD{}", prefix);
		
		Map<String,Object> filtres = new HashMap<>();
		
		filtres.put("codi","TD"+prefix);
		
		List<TipusNti> resultats = tipusNtiService.findFiltered(filtres);
		
		if (resultats.isEmpty()) {
			log.error("No se ha encontrado ningún TipusNti con el código: TD{}", prefix);
	        throw new I18NException("tipusdocumental.error.tipusnti.noexisteix",
	            this.getClass().getSimpleName(), "cercarValidarTipusNti", prefix);
	    }
		
		TipusNti tipusNti = resultats.get(0);
			
		tipusDocumental.setTipusNti(tipusNti);
			
		log.info("TipusNti encontrado - Nom: {}, Codi: {}", tipusNti.getNom(), tipusNti.getCodi());
			
		// Validación de seguridad: prefijo coincide con el código del tipus nti
			
		if (!tipusNti.getCodi().equals("TD"+prefix)) {
				
			log.error("Inconsistencia: el código buscado 'TD{}' no coincide con el código del TipusNti encontrado '{}'", 
		            prefix, tipusNti.getCodi());
		        throw new I18NException("tipusdocumental.error.tipusnti.prefix.inconsistent",
		            this.getClass().getSimpleName(), "cercarValidarTipusNti", 
		            prefix, tipusNti.getCodi());
				
		}
				
	}
	
	/**
	 * Comprueba que se cumplan las restricciones a nivel de entidad según una clase validadora
	 * @param tipusDocumental Entidad a validar
	 * @param grup Clase validadora
	 * @throws I18NException si hay errores de validación
	 */
	private void validarGrup(TipusDocumental tipusDocumental, Class<?> grup) throws I18NException {
		
		log.info("Verificando si el tipo documental cumple las validaciones de tipo: {}", grup.getName());
		
	    Set<ConstraintViolation<TipusDocumental>> violacions = validador.validate(tipusDocumental, grup);
	    
	    if (!violacions.isEmpty()) {
	        ConstraintViolation<TipusDocumental> primeraViolacio = violacions.iterator().next();
	        
	        log.info("El tipo documental incumple una validación: {}", primeraViolacio.getMessage());
	        
	        throw new I18NException("tipusdocumental.error.validacio",this.getClass().getSimpleName(),"validarGrup",primeraViolacio.getMessage());
	    }
	}
	
	/**
	 * Comprueba si un tipo documental necesita obtener un código, generándolo para aquellos que lo necesiten
	 * @param tipusDocumental Entidad que necesita código
	 * @param prefix Prefijo necesario para la generación
	 * @throws I18NException si hay errores de validación
	 */
	private void assignarCodi(TipusDocumental tipusDocumental, String prefix) throws I18NException {
		String codi = tipusDocumental.getCodi();
		
		log.info("Comprobando el formato del código introducido: {}", codi);
		
		if (codi == null || codi.trim().isEmpty()) {
			
			log.info("No se ha enviado código. Verificando si el usuario ha introducio un prefijo: {}", prefix);
			
			// Si no se pasa el código, se genera con el prefijo, siempre que se haya mandado
			
			if (prefix == null || prefix.trim().isEmpty()) {
				log.info("El usuario no ha enviado un prefijo. No se puede generar el código");
				throw new I18NException("tipusdocumental.error.prefix.requerit",this.getClass().getSimpleName(), "generateNextCode");
			}
			
			log.info("El usuario ha especificado un prefijo. Entrando en la generación");
			String codiGenerat = generarSeguentCodi(prefix);
			
			tipusDocumental.setCodi(codiGenerat);
			
		}
		
		log.info("El usuario ha proporcionado el código completo. No es necesario autogenerar");
	}
	
	/**
	 * Método que genera el código para un tipo documental a partir de un prefijo
	 * @param prefix necesario para la generación
	 * @return String con el código generado
	 * @throws I18NException si hay errores de validación
	 */
	private String generarSeguentCodi(String prefix) throws I18NException {
		if (prefix.length() != 2) {
			log.info("El prefijo introducido por el usuario no cumple con el formato esperado: {}", prefix);
			throw new I18NException("tipusdocumental.error.prefix.invalid",
					this.getClass().getSimpleName(), "generateNextCode");
		}

		// Realizamos la consulta con bloqueo pesimista para asegurar la generación atómica.
		String jpql = "SELECT t FROM TipusDocumental t " +
                "WHERE t.codi LIKE :prefix " +
                "ORDER BY t.codi DESC";
  
	    TypedQuery<TipusDocumental> query = entityManager.createQuery(jpql, TipusDocumental.class);
	    query.setParameter("prefix", "TD" + prefix + "-%");
	    query.setMaxResults(1);
	    query.setLockMode(LockModeType.PESSIMISTIC_WRITE);  
		
		log.info("El secuencial máximo encontrado para el prefjio {} es: {}", prefix, query.getSingleResult());

		try {
			
			TipusDocumental lastTipus = query.getSingleResult();
	        String lastCode = lastTipus.getCodi();
	        
			// Extraer el secuencial YYY del código (últimos 3 caracteres)
			String sequentialPart = lastCode.substring(lastCode.length() - 3);
			int nextSequential = Integer.parseInt(sequentialPart) + 1;
			
			log.info("El siguiente secuencial disponible es: {}", nextSequential);

			if (nextSequential > 999) {
				log.info("El prefijo ya tiene todos los secuenciales generados. Se debe emplear otro prefijo");
				throw new I18NException("tipusdocumental.error.sequencia.excedit", this.getClass().getSimpleName(), "generarSeguentCodi", "TD" + prefix);
			}
			
			log.info("Éxito en la generación del código: {}", String.format("TD%s-%03d", prefix, nextSequential));
			return String.format("TD%s-%03d", prefix, nextSequential);
		} catch (NoResultException e) {
			// No existe ningún código con ese prefijo, empezar desde 001
			return String.format("TD%s-001", prefix);
		} catch (NumberFormatException e) {
			throw new I18NException("tipusdocumental.error.code.parseig", this.getClass().getSimpleName(), "generarSeguentCodi");
		}
	}
	
	/**
	 * Valida que el código generado no pertenezca a una tipo documental ya existente en la BD
	 * @param codi Código generado a validar
	 * @throws I18NException si hay errores de validación o persistencia
	 */
	private void validarCodiUnic(String codi) throws I18NException {
		
		log.info("Comprobando que el código proporcionado no pertenezca a un tipo documental existente:{}", codi);
		
		// Buscamos si el tipo existe en la bd por código
		
		TipusDocumental existent = trobarPerCodi(codi);
		
		if (existent != null) {
				
				log.info("Ya existe un tipo documental con el código especificado en la BD");
				throw new I18NException("tipusdocumental.error.codi.duplicat",this.getClass().getSimpleName(),"validarCodiUnic",codi);
			
		}
		
		log.info("No se ha encontrado ningún tipo documental con el código especificado");
		
	}
	
	/**
	 * Busca un TipusDocumental por código
	 * @param codi del tipo documental que se quiere buscar
	 * @return Entidad encontrada
	 * @throws I18NException si hay errores
	 */
	@Override
	public TipusDocumental trobarPerCodi(String codi) throws I18NException {
		
		log.info("Buscando el tipo documental por código: {}", codi);
		
		Map<String,Object> filtres = new HashMap<>();
		
		filtres.put("codi",codi);
		
		List<TipusDocumental> resultats = findFiltered(filtres);
		
		log.info("Primer resultado obtenido: {}", resultats.isEmpty() ? null : resultats.get(0));
		
		return resultats.isEmpty() ? null : resultats.get(0);
		
	}
	
	/**
	 * Comprueba que el cambio de estado al actualizar un tipo documental es válido
	 * @param codi del tipo documental que se quiere buscar
	 * @return true si el cambio es BORRADOR a ACTIVO
	 * @return false si se intenta realizar cualquier otro cambio de estado
	 */
	private boolean esStatValid(String actualStat, String nouStat) {
		// Solo se permite cambiar de BORRADOR a ACTIVO
		if (ESTAT_ESBORRANY.equals(actualStat) && ESTAT_ACTIU.equals(nouStat)) {
			return true;
		}
		// Si el estado no cambia, es válido
		if (actualStat != null && actualStat.equals(nouStat)) {
			return true;
		}
		return false;
	}
	
	/*
	 * Método que aplica la lógica para realizar la eliminación de un tipo documental
	 * @param usuariActual usuario que realiza la acción para trazabilidad
	 */
	@Override
	public void eliminar(String codi, String usuariActual,boolean logic) throws I18NException {
		
		TipusDocumental existent = trobarPerCodi(codi);
			
		if(logic) {
				
			// Eliminación lógica: cambiar estado a OBSOLET
	        log.info("Realizando eliminación lógica del tipo documental: {}", codi);
	        
	        existent.setFi(new Date());
	        existent.setEstat(ESTAT_OBSOLET);
	        existent.setModificacio(new Date());
	        existent.setCanviEstat(new Date());
	        existent.setUsuariCanviEstat(usuariActual);
	        existent.setUsuariModificacio(usuariActual);
	        
	        update(existent);
	        
	        log.info("Eliminación lógica completada");
	        
		} else {
			
			// Eliminación física: verificar que no tenga relaciones
			
			log.info("Realizando eliminación física del tipo documental: {}", codi);
			
			 if (entitatRelacionades(existent.getId())) {
				 log.error("El tipo documental '{}' tiene entidades relacionadas", codi);
				 throw new I18NException("tipusdocumental.error.eliminar.invalid", this.getClass().getSimpleName(), "eliminar", existent.getCodi());
		     }
		        
		     delete(existent.getId());
		        
		     log.info("Eliminación física completada");
			
		}
		
	}
	
	/**
	 * Helper: Obtiene solo los tipos documentales activos (disponibles para uso)
	 * @return Lista de tipos documentales en estado ACTIU
	 */
	@Override
	public List<TipusDocumental> trobarActius() throws I18NException {
		
		Map<String, Object> filtres = new HashMap<>();
	    filtres.put("estat", ESTAT_ACTIU);
	    return findFiltered(filtres, new OrderBy("codi"));
		
	}
	
	/**
	 * Método que comprueba si un tipo documental se encuentra relacionado con otras entidades
	 * @param código del tipo documental que se quiere buscar
	 * @return true si el cambio es BORRADOR a ACTIVO
	 * @return false si se intenta realizar cualquier otro cambio de estado
	 */
	public boolean entitatRelacionades(Long id) {
		
		Long comptarDictamens = entityManager.createQuery("SELECT COUNT(d) FROM DictamenTipusdocumental d WHERE d.achTipusdocumental.id = :id", Long.class)
				.setParameter("id",id)
				.getSingleResult();	
		
		Long comptarNormativa = entityManager.createQuery("SELECT COUNT(n) FROM Normativa n JOIN n.achTipusdocumentals t WHERE t.id = :id",Long.class)
				.setParameter("id",id)
				.getSingleResult();
		
		Long comptarDocumentserie = entityManager.createQuery("SELECT COUNT(ds) FROM TipusdocumentSerie ds WHERE ds.achTipusdocumental.id = :id",Long.class)
				.setParameter("id", id)
				.getSingleResult();
		
		Long comptarProcediment = entityManager.createQuery("SELECT COUNT(p) FROM TipusdocumentProcediment p WHERE p.achTipusdocumental.id = :id",Long.class)
				.setParameter("id", id)
				.getSingleResult();
		
		return (comptarDictamens + comptarNormativa + comptarDocumentserie + comptarProcediment) > 0;
	}
	
}
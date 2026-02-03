	package es.caib.archium.controllers;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.ejb.service.TipusDocumentalService;
import es.caib.archium.ejb.service.TipusNtiService;
import es.caib.archium.persistence.model.TipusDocumental;
import es.caib.archium.persistence.model.TipusNti;
import es.caib.archium.utils.FrontExceptionTranslate;

@Named("tipusDocumentalController")
@ViewScoped
public class TipusDocumentalController implements Serializable {

    private static final long serialVersionUID = 1L;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    private TipusDocumentalService tipusDocumentalService;
    
    @Inject
    private TipusNtiService tipusNtiService;

    private List<TipusDocumental> llistaTipus = new ArrayList<>();
    // Cargamos los prefijos disponibles en la BD
    private List<String> prefixos = new ArrayList<>();
    private Boolean error = false;

    // Variables del formulario para crear/editar
    private String prefix;
    private String sequencial;
    private String codi;
    private String nom;
    private String nomCas;
    private String definicio;
    private String definicioCas;
    private String estat;
    private String observacions;
    // Necesitamos guardar el estado original para comparar con el que se ponga en la edición
	private String estatOriginal;
	// Usuario actual para trazabilidad;
	private String usuariActual;
	
	// Para la eliminación
	private String codiAEliminar;
	private boolean eliminacioLogica;
	private boolean teRelacions;
	
	// Para el filtrado en la vista
	private List<TipusDocumental> filtratTipus;
	
	// Para Easy Search de prefijos en caché
	private List<String> prefixosFormatats;

	Locale locale;
    ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
    
    /**
	 * Constructor por defecto que carga la lista de tipos documentales encontrados en la BD y los prefijos de TipusNTI
	 */
    @PostConstruct
    public void init() {

        FacesContext context = FacesContext.getCurrentInstance();
        locale = context.getViewRoot().getLocale();

        try {
            // Cargar todos todos los tipos documentales de la BD
        	llistaTipus = tipusDocumentalService.findAll(new OrderBy("codi"));
            log.info("Cargados {} Tipus Documentals desde BD", llistaTipus.size());
            // Cargamos todos los prefijos disponibles
            prefixos = tipusNtiService.trobarPrefixos();
            log.info("Cargados {} prefijos desde BD", prefixos.size());
            // Pre-formatear y cachear los prefijos
            prefixosFormatats = prefixos.stream()
                    .map(this::formatPrefixAmbNom)
                    .collect(Collectors.toList());
                log.info("Prefijos formateados y cacheados: {}", prefixosFormatats.size());
            
            // Guardamos el usuario actual
            if (context != null && context.getExternalContext() != null) {
            	Principal principal = context.getExternalContext().getUserPrincipal();
            	if (principal != null) {
            		usuariActual = principal.getName();
            	} else {
            		usuariActual = "SYSTEM";
            		log.warn("No se pudo obtener el usuario actual, usando SYSTEM");
            	}
            } else {
            	usuariActual = "SYSTEM";
            	log.warn("No hay contexto disponible, usando SYSTEM");
            }
        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, locale));
            error = true;
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "S'ha produït un error carregant les dades"));
        }
    }

    /**
     * Método para limpiar los campos antes de añadir o después de editar un tipo documental
     */
    public void netejarCamps() {
    	
    	log.info("Limpiando los campos del formulario");
        // Limpiar campos del formulario
        this.prefix = null;
        this.codi = null;
        this.sequencial = null;
        this.nom = null;
        this.nomCas = null;
        this.definicio = null;
        this.definicioCas = null;
        this.estat = null;
        this.observacions = null;
        
    }
    
    /*
     * Método para limpiar las variables de eliminación
     */
    private void netejarVariablesEliminacio() {
        this.codiAEliminar = null;
        this.teRelacions = false;
        this.estat = null;
        log.debug("Variables de eliminación limpiadas");
    }
    
    /**
	 * Prepara el formulario de edición rellenando los datos encontrados de la entidad a editar
	 * @param codi Código de la entidad que se queire editar
	 */
    public void prepararEdicio(String codi) {
    	
    	log.info("Preparando edición del tipo documental con el código: {}", codi);
    	
    	try {
    		
    		TipusDocumental existent = tipusDocumentalService.trobarPerCodi(codi);
    		
    		if (existent == null) {
    			
    			log.info("No se encuentra el tipo documental seleccionado en la BD para su edición");
    			throw new I18NException("tipusdocumental.error.notrobat",this.getClass().getSimpleName(),"prepararEdicio", this.getCodi());
    		
    		}
			
    		log.info("Se ha encontrado el tipo documental en la BD: {}", existent.getCodi());
    		
			this.codi = existent.getCodi();
    		this.nom = existent.getNom();
    		this.nomCas = existent.getNomcas();
    		this.definicio = existent.getDefinicio();
    		this.definicioCas = existent.getDefiniciocas();
    		this.estat = existent.getEstat();
    		this.estatOriginal = existent.getEstat();
    		this.observacions = existent.getObservacions();
    		
    		log.info("Campos del tipo documental introducidos en el formulario de edición");
    		
    	} catch (I18NException e) {
    		log.error(FrontExceptionTranslate.translate(e, locale));
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No s'ha pogut carregar el tipus documental"));
        }
    
    		
    }
    
    /**
     * Prepara la eliminación
     */
    public void prepararEliminacio(String codi) {
    	
    	try {
    		
    		TipusDocumental existent = tipusDocumentalService.trobarPerCodi(codi);
    		
    		if (existent == null) {
    			
    			log.info("No se encuentra el tipo documental seleccionado en la BD para su borrado");
    			throw new I18NException("tipusdocumental.error.notrobat",this.getClass().getSimpleName(),"prepararEliminacio", this.getCodi());
    			  		
    		}
    		
            this.codiAEliminar = codi;
            this.estat = existent.getEstat(); // para desactivar eliminación lógica en tipos documentales que son obsoletos 
            this.teRelacions = tipusDocumentalService.entitatRelacionades(existent.getId()); // para mostrar solo eliminación lógica o ambas
            
            boolean esObsolet = "OBSOLET".equals(estat);
            
            log.info("Preparando eliminación: código={}, relaciones={}, estado={}", 
                    codi, teRelacions, estat);
                
            PrimeFaces.current().ajax().addCallbackParam("teRelacions", teRelacions);
            PrimeFaces.current().ajax().addCallbackParam("esObsolet", esObsolet);
    		
    		
    		
    	} catch(I18NException e) {
    		
    		log.error(FrontExceptionTranslate.translate(e, locale));
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No s'ha pogut carregar el tipus documental"));
    			
    	}
    	
    }

    /**
     * Guarda un nuevo Tipus Documental
     */
    public void guardar() {
        log.info("Guardando nuevo Tipus Documental");

        try {
            TipusDocumental tipusDocumental = new TipusDocumental();
            
            // Si el usuario ha pasado secuencial y prefijo, debemos contruir el código para que se valide en el servicio
            
            if (sequencial != null && !sequencial.trim().isEmpty()) {
            	
            	if (prefix == null || prefix.trim().isEmpty()) {
            		
            		FacesContext.getCurrentInstance().addMessage(null,
                            new FacesMessage(FacesMessage.SEVERITY_ERROR,
                                "Error",
                                "Has d'seleccionar un prefix"));
                        return;
            		
            	}
            	
            	// Formateamos el secuencial
            	
            	int seq = Integer.parseInt(sequencial);
            	String codi = String.format("TD%s-%03d", prefix, seq);
            	tipusDocumental.setCodi(codi);
            	
            	log.info("Código especificado manualmente: {}", codi);
            	
            } else {
                // Si no se genera automáticamente en el servicio
                tipusDocumental.setCodi(null);
                log.info("Código se generará automáticamente con prefix: {}", prefix);
            }

            tipusDocumental.setNom(this.nom);
            tipusDocumental.setNomcas(this.nomCas);
            tipusDocumental.setDefinicio(this.definicio);
            tipusDocumental.setDefiniciocas(this.definicioCas);
            tipusDocumental.setEstat(this.estat);
            tipusDocumental.setObservacions(observacions);

            // Guardar
            log.info("Entrando en el servicio de guardado");
            tipusDocumentalService.guardarAmbValidacio(tipusDocumental, this.getPrefix(), usuariActual, true);

            // Recargar lista
            llistaTipus = tipusDocumentalService.findAll(new OrderBy("codi"));

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_INFO,
                    "Tipus Documental creat",
                    "S'ha creat correctament el tipus documental: " + tipusDocumental.getCodi()));

        } catch (I18NException e) {
        	log.error(FrontExceptionTranslate.translate(e, locale));
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No s'ha pogut crear el tipus documental: " + FrontExceptionTranslate.translate(e, locale)));
        } 
    }
    
    /**
     * Método para editar un Tipo Documental
     */
    public void editar() {
        log.info("Editando Tipus Documental: {}", this.getCodi());

        try {
        	
        	TipusDocumental tipusDocumental = new TipusDocumental();
        	
        	tipusDocumental.setCodi(this.codi);
            tipusDocumental.setNom(this.nom);
            tipusDocumental.setNomcas(this.nomCas);
            tipusDocumental.setDefinicio(this.definicio);
            tipusDocumental.setDefiniciocas(this.definicioCas);
            tipusDocumental.setEstat(this.estat);
            tipusDocumental.setObservacions(this.observacions);
            
   		 	// Guardar
            log.info("Entrando al servicio de actualización");
            tipusDocumentalService.guardarAmbValidacio(tipusDocumental, null, usuariActual,false);
            
            // Recargar lista
            llistaTipus = tipusDocumentalService.findAll(new OrderBy("codi"));
            
            // Usamos el método abrir modal del guardado para limpiar los campos después de editar
            
            netejarCamps();
            
            FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(FacesMessage.SEVERITY_INFO,
                        "Tipus Documental actualitzat",
                        "S'ha actualitzat correctament el tipus documental: " + tipusDocumental.getCodi()));
        	
        	
        } catch (I18NException e) {
        	log.error(FrontExceptionTranslate.translate(e, locale));
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No s'ha pogut actualitzar el tipus documental: " + FrontExceptionTranslate.translate(e, locale)));
        }
    }
    
    /*
     * Helper para la vista para saber cuándo un tipo documental es obsoleto
     */
    public boolean isObsolet() {
    	
    	return "OBSOLET".equals(estat);
    	
    }
    
    /*
     * Método que permite llama a la eliminación lógica de un tipo documental
     */
    public void eliminatLogic() {
    	log.info("Ejecutando eliminación lógica");
    	eliminar(true);
    	
    }
    
    /*
     * Método que permite llama a la eliminación física de un tipo documental
     */
    public void eliminatFisic() {
    	log.info("Ejecutando eliminación lógica");
    	eliminar(false);
    	
    }
     
    /**
     * Método para eliminar un tipo documental
     */
    public void eliminar(boolean logic) {
    	
        log.info("Eliminando Tipus Documental con código: {} (lógica: {})", codiAEliminar, logic);

        try {
        	
            tipusDocumentalService.eliminar(codiAEliminar, usuariActual, logic);
            
            // Recargar lista
            llistaTipus = tipusDocumentalService.findAll(new OrderBy("codi"));

            String mensaje = logic ? 
                    "El tipus documental s'ha marcat com a OBSOLET" : 
                    "S'ha eliminat permanentment";

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Tipus Documental eliminat", mensaje + ": " + codiAEliminar));
            
        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, locale));
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "No s'ha pogut eliminar el tipus documental: " + FrontExceptionTranslate.translate(e, locale)));
        } finally {
			netejarVariablesEliminacio();
		}
    }
    
    public String getIdiomaActual() {
        FacesContext context = FacesContext.getCurrentInstance();
        Locale locale = context.getViewRoot().getLocale();
        return locale.getLanguage();
    }
    
    /**
     * Helper para verificar si un tipo documental es obsoleto y tiene relaciones (para deshabilitar el botón de eliminar)
     */
    public boolean esObsoletAmbRelacions(Long id, String estat) {
        if (!"OBSOLET".equals(estat)) {
            return false; // Si no es obsoleto no necesitamos verificar
        }
        
        return tipusDocumentalService.entitatRelacionades(id);
       
    }
    
    /**
     * Autocompletar las queries filtrando los prefijos en caché
     */
    public List<String> autocompletarPrefix(String query) {
        // Si no hay query, devolver todos los prefijos formateados (desde caché) (para dropdown o queries vacías)
        if (query == null || query.trim().isEmpty()) {
            return prefixosFormatats;
        }

        // Filtrar desde caché según la query
        String queryLower = query.toLowerCase().trim();
        return prefixosFormatats.stream()
            .filter(p -> p.toLowerCase().contains(queryLower))
            .collect(Collectors.toList());
    }
    
    /**
     * Formatea el prefijo con su nombre: "01" -> "01 - Resolució" en la caché
     */
    private String formatPrefixAmbNom(String prefix) {
        try {
        	TipusNti nti = tipusNtiService.trobarByPrefix(prefix);
            return nti != null ? prefix + " - " + nti.getNom() : prefix;
        } catch (I18NException e) {
            log.error("Error formateando prefix: {}", prefix, e);
            return prefix; // Fallback: devolver solo el código (evitamos problemas de FaceContext durante la inicialización)
        }
    }
    
    /*
     * Método que indica a la vista que la edición está deshabilitada si el tipo documental es obsoleto
     */
    public void intentarEditar(String codi) throws I18NException {
    	
    	TipusDocumental td = tipusDocumentalService.trobarPerCodi(codi);
    	
    	if("OBSOLET".equals(td.getEstat())) {
    		PrimeFaces.current().ajax().addCallbackParam("esObsolet", true);
    		return;
    	}
    	
    	prepararEdicio(codi);
    	PrimeFaces.current().ajax().addCallbackParam("esObsolet", false);
    	
    }
    
    /*
     * Método que indica a la vista que el borrado está deshabilitado si el tipo documental es obsoleto y tiene relaciones
     */
    public void intentarEliminar(String codi) throws I18NException {
        TipusDocumental td = tipusDocumentalService.trobarPerCodi(codi);
        
        if ("OBSOLET".equals(td.getEstat()) && tipusDocumentalService.entitatRelacionades(td.getId())) {
            PrimeFaces.current().ajax().addCallbackParam("obsoletAmbRelacions", true);
            return;
        }
        
        prepararEliminacio(codi);
        PrimeFaces.current().ajax().addCallbackParam("obsoletAmbRelacions", false);
    }

    // Getters y Setters
    public List<TipusDocumental> getLlistaTipus() {
        return llistaTipus;
    }
    
    public List<String> getPrefixos() {
    	return prefixos;
    }

    public void setListaTipus(List<TipusDocumental> llistaTipus) {
        this.llistaTipus = llistaTipus;
    }
    
    public void setPrefixos(List<String> prefixos) {
    	this.prefixos = prefixos;
    }

    public Boolean getError() {
        return error;
    }

    public void setError(Boolean error) {
        this.error = error;
    }

    public Locale getLocale() {
        return locale;
    }

    public void setLocale(Locale locale) {
        this.locale = locale;
    }

    public String getPrefix() {
        return prefix;
    }

    public void setPrefix(String prefix) {
        this.prefix = prefix;
    }

    public String getCodi() {
        return codi;
    }
    
    public String getSequencial() {
    	return sequencial;
    }
    
    public void setSequencial(String sequencial) {
    	this.sequencial = sequencial;
    }
    

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getNomCas() {
        return nomCas;
    }

    public void setNomCas(String nomCas) {
        this.nomCas = nomCas;
    }

    public String getDefinicio() {
        return definicio;
    }

    public void setDefinicio(String definicio) {
        this.definicio = definicio;
    }

    public String getDefinicioCas() {
        return definicioCas;
    }

    public void setDefinicioCas(String definicioCas) {
        this.definicioCas = definicioCas;
    }
    
    public String getEstat() {
        return estat;
    }
    
    public String getEstatOriginal() { 
        return estatOriginal; 
    }
    
    public void setEstatOriginal(String estatOriginal) { 
        this.estatOriginal = estatOriginal; 
    }

    public void setEstat(String estat) {
        this.estat = estat;
    }

    public String getObservacions() {
        return observacions;
    }

    public void setObservacions(String observacions) {
        this.observacions = observacions;
    }
    
    public List<TipusDocumental> getFiltratTipus() {
		return filtratTipus;
	}

	public void setFiltratTipus(List<TipusDocumental> filtratTipus) {
		this.filtratTipus = filtratTipus;
	}

	public String getCodiAEliminar() {
		return codiAEliminar;
	}

	public void setCodiAEliminar(String codiAEliminar) {
		this.codiAEliminar = codiAEliminar;
	}

	public boolean isEliminacioLogica() {
		return eliminacioLogica;
	}

	public void setEliminacioLogica(boolean eliminacioLogica) {
		this.eliminacioLogica = eliminacioLogica;
	}

	public boolean isTeRelacions() {
		return teRelacions;
	}

	public void setTeRelacions(boolean teRelacions) {
		this.teRelacions = teRelacions;
	}
}

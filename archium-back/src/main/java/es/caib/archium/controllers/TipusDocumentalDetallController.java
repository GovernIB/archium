package es.caib.archium.controllers;

import java.io.Serializable;
import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

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
import es.caib.archium.ejb.service.TipusDocumentalService;
import es.caib.archium.persistence.model.TipusDocumental;
import es.caib.archium.utils.FrontExceptionTranslate;

@Named("tipusDocumentalDetallController")
@ViewScoped
public class TipusDocumentalDetallController implements Serializable {

    private static final long serialVersionUID = 1L;
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    @Inject
    private TipusDocumentalService tipusDocumentalService;

    private List<TipusDocumental> tipusDocumentalLlista;
    
    private String codi;
    private Boolean error = false;
    
 // Variables del formulario para crear/editar
    private String prefix;
    private String sequencial;
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

    Locale locale;
    ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
    
    @PostConstruct
    public void init() {

        FacesContext context = FacesContext.getCurrentInstance();
        locale = context.getViewRoot().getLocale();

        	 // Guardamos el usuario actual
            if (context != null && context.getExternalContext() != null) {
            	Principal principal = context.getExternalContext().getUserPrincipal();
            	if (principal != null) {
            		usuariActual = principal.getName();
            	} else {
            		usuariActual = "SYSTEM";
            		log.warn("c");
            	}
            } else {
            	usuariActual = "SYSTEM";
            	log.warn("No hay contexto disponible, usando SYSTEM");
            }
 }
    
    /**
	 * Introduce en la lista los detalles del tipo documental que se quiere mostrar
	 */
    public void carregarDetalls() {
    	
    	log.info("Cargando los detalles del tipo documental con código: {}",codi);
    	
    	try {
    		
    		
    		TipusDocumental existent = tipusDocumentalService.trobarPerCodi(codi);
    		
    		// Si se encuentra, lo metemos en la lista para seleccione al crear la tabla
    		
    		if (existent != null) {
    			
    			log.info("Tipo documental encontrado : {}. Añadiéndolo a la lista para mostrarlo por pantalla", existent.getCodi());
    			tipusDocumentalLlista = new ArrayList<>();
    			tipusDocumentalLlista.add(existent);
    			
    		}
    		
    		
    		
    	} catch (I18NException e) {
    		log.error(FrontExceptionTranslate.translate(e, locale));
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",
                    "Error carregant el tipus documental"));
    		
    	}
    	
    	
    }
    
    // Getters y Setters
    
    public String getCodi() {
        return codi;
    }

    public void setCodi(String codi) {
        this.codi = codi;
    }

    public List<TipusDocumental> getTipusDocumentalLlista() {
        return tipusDocumentalLlista;
    }

    public void setTipusDocumentalLlista(List<TipusDocumental> tipusDocumentalLlista) {
        this.tipusDocumentalLlista = tipusDocumentalLlista;
    }
}

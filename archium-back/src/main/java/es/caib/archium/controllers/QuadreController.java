package es.caib.archium.controllers;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.services.QuadreFrontService;
import es.caib.archium.utils.FrontExceptionTranslate;
import org.primefaces.PrimeFaces;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import javax.transaction.Transactional;
import java.io.Serializable;
import java.util.*;


@Named("quadreController")
@ViewScoped
public class QuadreController implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8853876271578469058L;
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	Locale locale;
	
	private Long  	 nouId;
    private String 	 nombreNuevo;
    private String 	 nombreNuevoCast;
    private String 	 nouEstat;


	private Date 	 nouFi;
	private String 	 nouVersio;


    private String	 nombreQuadre;
    private String	 estat;
    private Long 	 quadreId;

    private String nouNodeId;
    
    @Inject
    private QuadreFrontService services;
	@Inject
	private ExternalContext externalContext;
        
    private List<QuadreObject> listaCuadros = new ArrayList<>();
    private List<QuadreObject> listaQuadreFilter = new ArrayList<>();
    
    private List<QuadreObject> selectedCuadros;
    
    private  List<String> nomsQuadre;
    private  List<String> listaEstats;
    private  List<String> selectedEstats;
    
    ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
    
    private Boolean error = false;
    
	@PostConstruct
    public void init() {
	
		FacesContext context = FacesContext.getCurrentInstance();	
	    locale = context.getViewRoot().getLocale();
		
    	try{
    		listaCuadros = services.findAll();
    		listaQuadreFilter = listaCuadros;
			listaEstats = new ArrayList<>();
			listaEstats.add(messageBundle.getString("general.estats.esborrany")); 
			listaEstats.add(messageBundle.getString("general.estats.revisat"));
			listaEstats.add(messageBundle.getString("general.estats.publicable"));
			listaEstats.add(messageBundle.getString("general.estats.vigent"));
			listaEstats.add(messageBundle.getString("general.estats.obsolet"));
			selectedCuadros = new ArrayList<>();
			selectedEstats = new ArrayList<>();
			FacesContext ctxt = FacesContext.getCurrentInstance(); //get your hands on the current request context
	        ctxt.getPartialViewContext().getRenderIds().add("panel");
    	}
    	catch(I18NException e) 
    	{	
    		log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
    		error = true; 
    	}
    	
    }

	public void synchronize(QuadreObject cuadro) {
		log.debug("Se sincroniza el cuadro: " + cuadro.toString());

		try {
			this.services.synchronize(cuadro.getId());
			log.debug("Proceso de sincronizacion finalizado con exito");
			listaCuadros = services.findAll();
			listaQuadreFilter = listaCuadros;

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("nuevocuadro.sinc.ok")));
		} catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
			FacesMessage message = new FacesMessage();
			message.setSeverity(FacesMessage.SEVERITY_ERROR);
			if("excepcion.general.Exception".equals(e.getMessage())){
				message.setSummary(messageBundle.getString("nuevocuadro.sincro.error"));
				message.setDetail(messageBundle.getString("nuevocuadro.sincro.error"));
			}else {
				message.setSummary(messageBundle.getString(e.getMessage()));
				message.setDetail(messageBundle.getString(e.getMessage()));
			}
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
	}

	@Transactional
	public void deleteClassificationTable(QuadreObject cuadro) throws I18NException {
		log.debug("Se llama al proceso de eliminar cuadro");

		try {
			this.services.deleteClassificationTable(cuadro.getId());
			log.info("Proceso de eliminacion finalizado con exito");
			listaCuadros = services.findAll();
			listaQuadreFilter = listaCuadros;

			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("nuevocuadro.delete.ok")));
		} catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
			if("excepcion.general.Exception".equals(e.getMessage()) || "excepcion.general.NullPointerException".equals(e.getMessage())){
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevocuadro.delete.error"), null));
			}else {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString(e.getMessage()), null));
			}
		}

	}

	/**
	 * Devuelve true si el usuario logueado tiene permisos para llamar al CSGD
	 *
	 * @return
	 */
	public boolean checkPermissionsToCallCSGD() {
		HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
		List<String> roles = Constants.permissionsToCallCSGD();
		for(String rol : roles){
			if(request.isUserInRole(rol)){
				return true;
			}
		}

		return false;
	}
    
	public void save() {
		
		
		try {
			QuadreObject object = new QuadreObject();
			object.setNom(this.getNombreNuevo());
			object.setNomCas(this.getNombreNuevoCast());		
			object.setEstat(this.getNouEstat());
			
	    	if(services.checkNameUnique(null, object.getNom())) {
	    		if (services.save(object)!=null) {
					listaCuadros = services.findAll();
					listaQuadreFilter = listaCuadros;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("nuevocuadro.ok")));
					
		        } else {
		        	FacesMessage message = new FacesMessage();
		    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		    		message.setSummary(messageBundle.getString("nuevocuadro.error"));
		    		message.setDetail(messageBundle.getString("nuevocuadro.error"));
		    		FacesContext.getCurrentInstance().addMessage(null, message);
		        }
	    	} else {
	    		FacesContext.getCurrentInstance().validationFailed();
	    		FacesMessage message = new FacesMessage();
	    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
	    		message.setSummary(messageBundle.getString("nuevocuadro.nom.repetido"));
	    		message.setDetail(messageBundle.getString("nuevocuadro.nom.repetido"));
	    		FacesContext.getCurrentInstance().addMessage(null, message);
	    	}
	    	
	       
	        this.setNombreNuevo("");    	
	    	this.setNombreNuevoCast("");
		} catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
			FacesMessage message = new FacesMessage();
    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
    		message.setSummary(messageBundle.getString("nuevocuadro.error"));
    		message.setDetail(messageBundle.getString("nuevocuadro.error"));
    		FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
    }
	
	public void update() {
		
		try {
			QuadreObject obj = new QuadreObject() ;
			obj.setId(this.nouId);
			obj.setEstat(this.nouEstat);
			obj.setNom(this.getNombreNuevo().trim());
			obj.setNomCas(this.getNombreNuevoCast().trim());
			obj.setFi(this.getNouFi());
			obj.setModificacio(new Date());
			obj.setVersio(this.getNouVersio());
			obj.setNodeId(this.getNouNodeId());
			// Al modificar ponemos siempre  el synchronized a false
			obj.setSynchronized(false);

	    	if(services.checkNameUnique(obj.getId(), obj.getNom())) {
			
				QuadreObject up = services.update(obj);
				if(up==null) {
					FacesContext.getCurrentInstance().addMessage(null, 
						new FacesMessage(FacesMessage.SEVERITY_ERROR, 
								messageBundle.getString("updatecuadro.error"), 
								messageBundle.getString("updatecuadro.error")
						)
					);
				} else {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("updatecuadro.ok")));
				}
				
				listaCuadros = services.findAll();
				listaQuadreFilter = listaCuadros;
				
	    	} else {
	    		FacesContext.getCurrentInstance().validationFailed();
	    		FacesMessage message = new FacesMessage();
	    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
	    		message.setSummary(messageBundle.getString("updatecuadro.nom.repetido"));
	    		message.setDetail(messageBundle.getString("updatecuadro.nom.repetido"));
	    		FacesContext.getCurrentInstance().addMessage(null, message);
	    	}
		} catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
			FacesMessage message = new FacesMessage();
    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
    		message.setSummary(messageBundle.getString("updatecuadro.error"));
    		message.setDetail(messageBundle.getString("updatecuadro.error"));
    		FacesContext.getCurrentInstance().addMessage(null, message);
		}
		
		
    }	
	
    public void abrirModal() {
    	this.setNouId(null);
    	this.setNombreNuevo("");
    	this.setNombreNuevoCast("");    	
    	this.setNouFi(null);
    	this.setNouVersio(null);
        
    }
    
    public void abrirModalUpdate(QuadreObject object) {
    	
    	
    	try {
			QuadreObject quadre = this.services.getQuadreById(object.getId());
			
			if(quadre!=null) {
				this.setNouId(object.getId());
				this.setNombreNuevo(quadre.getNom());
		    	this.setNombreNuevoCast(quadre.getNomCas());
		    	this.setNouEstat(quadre.getEstat());
		    	this.setNouFi(quadre.getFi());
		    	this.setNouVersio(quadre.getVersio());
		    	this.setNouNodeId(quadre.getNodeId());

		    	PrimeFaces.current().executeScript("PF('quadreModalUpdate').show()");
			} else {
	 			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("cuadro.get.error"), null);
	 			FacesContext.getCurrentInstance().addMessage(null, msg);	
			}
	    	
		} catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
 			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("cuadro.get.error"), null);
 			FacesContext.getCurrentInstance().addMessage(null, msg);	
		}
    	
    	
    	
    }


	public List<QuadreObject> getListaQuadreFilter() {
		return listaQuadreFilter;
	}


	public void setListaQuadreFilter(List<QuadreObject> listaQuadreFilter) {
		this.listaQuadreFilter = listaQuadreFilter;
	}
	
	public List<QuadreObject> getListaCuadros() {
		return listaCuadros;
	}


	public Long getNouId() {
		return nouId;
	}


	public void setNouId(Long nouId) {
		this.nouId = nouId;
	}


	public void setListaCuadros(List<QuadreObject> listaCuadros) {
		this.listaCuadros = listaCuadros;
	}

	public String getNouEstat() {
			return nouEstat;
		}


	public void setNouEstat(String nouEstat) {
			this.nouEstat = nouEstat;
		}




	public Date getNouFi() {
		return nouFi;
	}


	public void setNouFi(Date nouFi) {
		this.nouFi = nouFi;
	}


	public String getNouVersio() {
		return nouVersio;
	}


	public void setNouVersio(String nouVersio) {
		this.nouVersio = nouVersio;
	}

	public String getNouNodeId() {
		return nouNodeId;
	}

	public void setNouNodeId(String nouNodeId) {
		this.nouNodeId = nouNodeId;
	}

	public List<QuadreObject> getSelectedCuadros() {
		return selectedCuadros;
	}


	public void setSelectedCuadros(List<QuadreObject> selectedCuadros) {
		this.selectedCuadros = selectedCuadros;
	}

	public List<String> getNomsQuadre() {
		return nomsQuadre;
	}

	public void setNomsQuadre(List<String> nomsQuadre) {
		this.nomsQuadre = nomsQuadre;
	}

	public String getNombreNuevoCast() {
		return nombreNuevoCast;
	}
	
	public void setNombreNuevoCast( String nombreNuevoCast) {
		this.nombreNuevoCast = nombreNuevoCast;
	}

	public String getNombreNuevo() {
		return nombreNuevo;
	}

	public String getNombreQuadre() {
		return nombreQuadre;
	}

	public void setNombreQuadre(String nombreQuadre) {
		this.nombreQuadre = nombreQuadre;
	}

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public List<String> getListaEstats() {
		return listaEstats;
	}

	public void setListaEstats(List<String> listaEstats) {
		this.listaEstats = listaEstats;
	}

	public List<String> getSelectedEstats() {
		return selectedEstats;
	}

	public void setSelectedEstats(List<String> selectedEstats) {
		this.selectedEstats = selectedEstats;
	}

	public void setNombreNuevo( String nombreNuevo) {
		this.nombreNuevo = nombreNuevo;
	}
  
    public Long getQuadreId() {
		return quadreId;
	}

	public void setQuadreId(Long quadreId) {
		this.quadreId = quadreId;
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

}
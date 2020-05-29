package es.caib.archium.controllers;
import java.util.Date;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.validation.constraints.NotNull;

import org.primefaces.event.UnselectEvent;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.services.QuadreFrontService;


@Named("quadreController")
@ViewScoped
public class QuadreController implements Serializable{
 
	/**
	 * 
	 */
	private static final long serialVersionUID = -8853876271578469058L;
	
	private Long  	 nouId;
    private String 	 nombreNuevo;
    private String 	 nombreNuevoCast;
    private String 	 nouEstat;
   

    private Date 	 nouFi;
    private String 	 nouVersio;
    

    
    private String	 nombreQuadre;
    private String	 estat;
    private Long 	 quadreId;
    
    @Inject
    private QuadreFrontService services;
        
    private List<QuadreObject> listaCuadros = new ArrayList<>();
    private List<QuadreObject> listaQuadreFilter = new ArrayList<>();
    
    private List<QuadreObject> selectedCuadros;
    
    private  List<String> nomsQuadre;
    private  List<String> listaEstats;
    private  List<String> selectedEstats;
    
    ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
    
	@PostConstruct
    public void init() {
	
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
    		
    	}
    	catch(Exception e) 
    	{	
    	}
    	FacesContext ctxt = FacesContext.getCurrentInstance(); //get your hands on the current request context
        ctxt.getPartialViewContext().getRenderIds().add("panel");
    }

	/*public void onItemUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        FacesMessage msg = new FacesMessage();
        msg.setSummary("Item unselected: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
         
        context.addMessage(null, msg);
    }*/
    
	public void save() {
		QuadreObject object = new QuadreObject();
		object.setNom(this.getNombreNuevo());
		object.setNomCas(this.getNombreNuevoCast());		
		object.setEstat(this.getNouEstat());
		
		boolean b = true;
    	for(QuadreObject i:  listaCuadros)
		{
    		if (i.getNom().equalsIgnoreCase(object.getNom().trim())) {
    			b = false;
    		}    		
		}
		
    	if(b) {
    		if (services.save(object)) {
	        	try {
					listaCuadros = services.findAll();
					listaQuadreFilter = listaCuadros;
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("nuevocuadro.ok")));
				} catch (I18NException e) {
					e.printStackTrace();
				}
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
    }
	
	public void update() {
		QuadreObject obj = new QuadreObject() ;
		obj.setId(this.nouId);
		obj.setEstat(this.nouEstat);
		obj.setNom(this.getNombreNuevo().trim());
		obj.setNomCas(this.getNombreNuevoCast().trim());
		obj.setFi(this.getNouFi());
		obj.setModificacio(new Date());
		obj.setVersio(this.getNouVersio());
		
		boolean b = true;
    	for(QuadreObject i:  listaCuadros)
		{
    		if (i.getNom().equalsIgnoreCase(obj.getNom().trim()) && i.getId()!=obj.getId()) {
    			b = false;
    		}    		
		}
		
    	if(b) {
		
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
	        try {
				listaCuadros = services.findAll();
				listaQuadreFilter = listaCuadros;
			} catch (I18NException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
    	} else {
    		FacesContext.getCurrentInstance().validationFailed();
    		FacesMessage message = new FacesMessage();
    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
    		message.setSummary(messageBundle.getString("updatecuadro.nom.repetido"));
    		message.setDetail(messageBundle.getString("updatecuadro.nom.repetido"));
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
    	this.setNouId(object.getId());
    	this.setNombreNuevo(object.getNom());    	
    	this.setNombreNuevoCast(object.getNomCas());
    	this.setNouEstat(object.getEstat());
    	this.setNouFi(object.getFi());
    	this.setNouVersio(object.getVersio());
    	
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

}
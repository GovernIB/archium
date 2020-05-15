package es.caib.archium.controllers;
import java.util.Date;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

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
	@NotNull(message = "El Nom no pot ser nul")
    private String 	 nombreNuevo;
    private String 	 nombreNuevoCast;
    private String 	 nouEstat;
   

	private Date 	 nouInici;
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
    
    
    
	@PostConstruct
    public void init() {
	
    	try{
    		listaCuadros = services.findAll();
    		listaQuadreFilter = listaCuadros;
			listaEstats = new ArrayList<>();
			listaEstats.add("Esborrany"); 
			listaEstats.add("Revisat");
			listaEstats.add("Publicable");
			listaEstats.add("Vigent");
			listaEstats.add("Obsolet");
			selectedCuadros = new ArrayList<>();
			selectedEstats = new ArrayList<>();
    		
    	}
    	catch(Exception e) 
    	{	
    		System.out.println("fallo al  Init controler quadre");
    		System.err.println(e);
    	}
    	FacesContext ctxt = FacesContext.getCurrentInstance(); //get your hands on the current request context
        ctxt.getPartialViewContext().getRenderIds().add("panel");
    }

	public void onItemUnselect(UnselectEvent event) {
        FacesContext context = FacesContext.getCurrentInstance(); 
        FacesMessage msg = new FacesMessage();
        msg.setSummary("Item unselected: " + event.getObject().toString());
        msg.setSeverity(FacesMessage.SEVERITY_INFO);
         
        context.addMessage(null, msg);
    }
    
	public void save() {
		QuadreObject object = new QuadreObject();
		object.setNom(this.getNombreNuevo());
		object.setNomCas(this.getNombreNuevoCast());		
		object.setEstat(this.getNouEstat());
        if (services.save(this.getListaCuadros(), object)) {
        	try {
				listaCuadros = services.findAll();
				listaQuadreFilter = listaCuadros;
			} catch (I18NException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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
		obj.setInici(this.getNouInici());
		obj.setModificacio(new Date());
		obj.setVersio(this.getNouVersio());
        services.update(this.getListaCuadros(),obj);
        try {
			listaCuadros = services.findAll();
			listaQuadreFilter = listaCuadros;
		} catch (I18NException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }	
	
    public void abrirModal() {
    	//System.out.println("Abrir modal");
    	this.setNouId(null);
    	this.setNombreNuevo("");    	
    	this.setNombreNuevoCast("");    	
    	this.setNouFi(null);
    	this.setNouInici(null);
    	this.setNouVersio(null);
        
    }
    
    public void abrirModalUpdate(QuadreObject object) {
    	//System.out.println("Abrir modal Update");
    	this.setNouId(object.getId());
    	this.setNombreNuevo(object.getNom());    	
    	this.setNombreNuevoCast(object.getNomCas());
    	this.setNouEstat(object.getEstat());
    	this.setNouFi(object.getFi());
    	this.setNouInici(object.getInici());
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


	public Date getNouInici() {
		return nouInici;
	}


	public void setNouInici(Date nouInici) {
		this.nouInici = nouInici;
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
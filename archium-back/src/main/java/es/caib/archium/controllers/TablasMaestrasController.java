package es.caib.archium.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.TauleMestreObject;
import es.caib.archium.services.TablaMaestraFrontService;
import es.caib.archium.utils.FrontExceptionTranslate;

import java.io.Serializable;

@Named("tablasMaestras")
@ViewScoped
public class TablasMaestrasController implements Serializable {

	private static final long serialVersionUID = -3157090024972868135L;
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	Locale locale;	
	
	@Inject
	TablaMaestraFrontService tablaMaestraServices;
	
	List<TauleMestreObject>	tablaMaestra = new ArrayList<>();
	List<TauleMestreObject>	tablaMaestrafilter= new ArrayList<>();
	List<String> columns = new ArrayList<>();
	List<String> listaTabla	= new ArrayList<>();
	String tabla = new String();	
	Boolean error = false;
	
	ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
	
	@PostConstruct
    public void init() {   
		
		FacesContext context = FacesContext.getCurrentInstance();	
	    locale = context.getViewRoot().getLocale();
		
    	try {
    		this.listaTabla = iniTablas();
    	} catch(I18NException e) {	
    		log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
    		error = true;
    	}        
    }
	public void ChangeEvent(ValueChangeEvent event) { 
		try {
	    	this.setTabla(event.getNewValue().toString());
	    	listarTabla();
		}catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
			FacesContext.getCurrentInstance().addMessage(null, 
				new FacesMessage(FacesMessage.SEVERITY_ERROR, 
						messageBundle.getString("tablasmaestras.cargadatos.error"), 
						messageBundle.getString("tablasmaestras.cargadatos.error")
				)
			);
		}
		    	
    }
	
	private void listarTabla () throws I18NException{
		
		String option = this.getTabla();
		this.setTablaMaestra(tablaMaestraServices.findAll(option));

	}
	
	private List<String> iniTablas() throws I18NException{
		List<String> listatabla = new ArrayList<>();
		listatabla.add("Serieargen");
		listatabla.add("Categorianti");
		listatabla.add("Tipusnti");
		listatabla.add("Quadretipusdocumental");
		listatabla.add("Tipusdocumental");
		listatabla.add("Tipusserie");
		listatabla.add("Ens");
		listatabla.add("Lopd");
		listatabla.add("Aplicacio");
		listatabla.add("Tipusacces");
		listatabla.add("Causalimitacio");
		listatabla.add("Tipusvalor");
		listatabla.add("Valorsecundari");
		listatabla.add("Fasearxiu");
		listatabla.add("Tipusdictament");
		listatabla.add("Formainici");
		listatabla.add("Materia");
		listatabla.add("Silenci");
		listatabla.add("Nivellelectronic");
		listatabla.add("Familiaprocediment");
		listatabla.add("Tipuscontacte");
		listatabla.add("Tipuspublic");
		listatabla.add("Normativa");
		listatabla.add("Tipusnormativa");
		listatabla.add("Butlleti");
		return listatabla;
	}
	
	public List<TauleMestreObject> getTablaMaestra() {
		return tablaMaestra;
	}

	public void setTablaMaestra(List<TauleMestreObject> tablaMaestra) {
		this.tablaMaestra = tablaMaestra;
	}

	public String getTabla() {
		return tabla;
	}

	public void setTabla(String tabla) {
		this.tabla = tabla;
	}

	public List<String> getColumns() {
		return columns;
	}

	public void setColumns(List<String> columns) {
		this.columns = columns;
	}

	public List<String> getListaTabla() {
		return listaTabla;
	}

	public void setListaTabla(List<String> listatabla) {
		this.listaTabla = listatabla;
	}
	public TablasMaestrasController() {
		super();
	}
	public List<TauleMestreObject> getTablaMaestrafilter() {
		return tablaMaestrafilter;
	}
	public void setTablaMaestrafilter(List<TauleMestreObject> tablaMaestrafilter) {
		this.tablaMaestrafilter = tablaMaestrafilter;
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

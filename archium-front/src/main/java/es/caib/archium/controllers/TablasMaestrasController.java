package es.caib.archium.controllers;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.PostConstruct;
import javax.faces.event.ValueChangeEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.TauleMestreObject;
import es.caib.archium.services.TablaMaestraServices;
import java.io.Serializable;

@Named("tablasMaestras")
@ViewScoped
public class TablasMaestrasController implements Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -3157090024972868135L;
	List<TauleMestreObject>	tablaMaestra 		= new ArrayList<>();
	List<TauleMestreObject>	tablaMaestrafilter	= new ArrayList<>();
	List<String> 	columns  		= new ArrayList<>();
	List<String> 	listaTabla		= new ArrayList<>();
	@Inject
	TablaMaestraServices service ;

	String tabla 					= new String();
	
	@PostConstruct
    public void init() throws I18NException {   
    	//System.out.println("init del Taules mestres");
    	try 
    	{
    		this.listaTabla = iniTablas();
    	}
    	catch(Exception e) 
    	{	
    		System.err.println("fallo al  Init controler Taules Mestres");
    		System.err.println(e);
    		e.printStackTrace();
    	}        
    }
	public void ChangeEvent(ValueChangeEvent event) { 
		try {
	    	this.setTabla(event.getNewValue().toString());
	    	listarTabla ();
		}
		catch (Exception e) {
			System.err.println("Error en el evento de cambiar");
			e.printStackTrace();
			// TODO: handle exception
		}
		    	
    }
	private void clonar() {
        for(TauleMestreObject i : this.tablaMaestra)
        {                       
        	TauleMestreObject aux = new TauleMestreObject();
			aux.setNom(i.getNom());
			//this.setTablaMaestrafilter(aux);
			this.tablaMaestrafilter.add(aux);
        }
		
	} 
	
	private void listarTabla (){
		
		String option = this.getTabla();
		//this.columns.add("Nom");
		try {
			this.setTablaMaestra(service.findAll(option));
			//System.out.println("tabla maestra "+this.getTablaMaestra());
			//clonar();
			//System.out.println("tabla maestra filter"+ this.getTablaMaestrafilter());
		} catch (I18NException e) {
			// TODO Auto-generated catch block
			System.err.println("Error construyendo la tabla a mostrar.");
			e.printStackTrace();
		}
	}
	
	private List<String> iniTablas(){
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
		// TODO Auto-generated constructor stub
	}
	public List<TauleMestreObject> getTablaMaestrafilter() {
		return tablaMaestrafilter;
	}
	public void setTablaMaestrafilter(List<TauleMestreObject> tablaMaestrafilter) {
		this.tablaMaestrafilter = tablaMaestrafilter;
	}
}

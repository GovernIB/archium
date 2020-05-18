package es.caib.archium.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.ResourceBundle;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import javax.el.ELContext;
import javax.enterprise.context.RequestScoped;
import javax.faces.annotation.ManagedProperty;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.validator.ValidatorException;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.SerieService;
import es.caib.archium.objects.AplicacionObject;
import es.caib.archium.objects.CatalegSeriesObject;
import es.caib.archium.objects.CausaLimitacioObject;
import es.caib.archium.objects.Document;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.LimitacioNormativaSerieObject;
import es.caib.archium.objects.NormativaAprobacioObject;
import es.caib.archium.objects.SerieArgenObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.objects.TipusSerieObject;
import es.caib.archium.services.SerieFrontService;
import java.io.Serializable;

@Named
@ViewScoped
public class NuevaSerieController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8940713018192370062L;
	private Long serieId;
	private String codi;
	private String nom;
	private String nomCas;
	private Long catalegSeriId;
	private Long funcioId;
	private String descripcio;
	private String descripcioCas;
	private String resumMigracio;
	private String dir3Promotor;
	private Long tipusSerieId;
	private String codiIecisa;
	private String  funcionNom;
	private FuncioObject serieFuncio;
	private String serieEstat;
	
	private SerieDocumentalObject modify;
	
	@Inject
	private SerieFrontService service;
	
	/*@ManagedProperty("#{messages}")
	private ResourceBundle messages;*/
	
	private List<CatalegSeriesObject> listaCatalegSerie;
	private List<FuncioObject> listaFunciones;
	private List<TipusSerieObject> listaTipusSerie;
	
	private List<SerieDocumentalObject> listaSeries;
	private List<AplicacionObject> listaAplicaciones;
	
	private List<SerieDocumentalObject> listaSeriesSelected;
	private List<AplicacionObject> listaAplicacionesSelected;
    private DualListModel<AplicacionObject> aplicaciones;
    private DualListModel<SerieDocumentalObject> seriesRelacionadas;
    
    private List<NormativaAprobacioObject> listaNormativaAprobacio;
    private List<CausaLimitacioObject> listaCausaLimitacio;
    
    private List<LimitacioNormativaSerieObject> listaRelacionLNS;
    
    
    private NormativaAprobacioObject selectedNormativa;
    //SerieArgen
    private DualListModel<SerieArgenObject> seriesArgenRelacionadas;
    private List<SerieArgenObject> seriesArgenSelected;
    private List<SerieArgenObject> listaSeriesArgen;
    //NormativasSerioe
    private DualListModel<NormativaAprobacioObject> normativasSerie;
    private List<NormativaAprobacioObject> normativasSerieSelected;
    

    
    
    ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
    
	@PostConstruct
	public void init() throws I18NException {

		
		
		listaCatalegSerie = service.getListaCatalegSerie();
		listaFunciones = service.getListaFunciones();
		listaTipusSerie = service.getListaTipusSerie();
		listaSeries = service.getListaSeries();
		listaAplicaciones = service.getListaAplicaciones();
		listaNormativaAprobacio = service.getListaNormativas();
		listaCausaLimitacio = service.getListaCausaLimitacio();
		listaSeriesArgen = service.getListaSeriesArgen();
		
		
		seriesArgenSelected = new ArrayList<>();
		listaSeriesSelected = new ArrayList<>();
		listaAplicacionesSelected= new ArrayList<>();
		normativasSerieSelected = new ArrayList<>();
		listaRelacionLNS = new ArrayList<LimitacioNormativaSerieObject>();

		aplicaciones = new DualListModel<AplicacionObject>(listaAplicaciones, listaAplicacionesSelected);
		seriesRelacionadas = new DualListModel<SerieDocumentalObject>(listaSeries,listaSeriesSelected);
		seriesArgenRelacionadas = new DualListModel<SerieArgenObject>(listaSeriesArgen,seriesArgenSelected);
		normativasSerie = new DualListModel<>(listaNormativaAprobacio ,normativasSerieSelected);
	}
	
	public void loadFromFunction(Document<FuncioObject> d) {
		clearForm();
		if (d==null) {
    		serieFuncio = null;
    	} else {
    		serieFuncio = d.getObject();
    		if(serieFuncio.getTipoSerie()!=null) {
    			tipusSerieId = serieFuncio.getTipoSerie().getId();
    		}
    	} 
	}
	
	
	public void updateSerie(Document<SerieDocumentalObject> obj) {
		clearForm();
		
		if (obj==null) {
			modify = null;
    	} else {
    		modify = obj.getObject();
    	} 
		serieId= obj.getId();
		codi = modify.getCodi();
		nom = modify.getNom();
		System.out.println(modify.toString());
		nomCas = modify.getNomCas();
		catalegSeriId = modify.getCatalegSeriId();
		//funcioId =  modify.getFuncioId();
		serieFuncio = modify.getFuncio();
		descripcio =modify.getDescripcio();
		descripcioCas= modify.getDescripcioCas();
		resumMigracio = modify.getResumMigracio();
		dir3Promotor = modify.getDir3Promotor();
		
		tipusSerieId = modify.getTipusSerieId();		
		
		codiIecisa = modify.getCodiIecisa();
		serieFuncio = modify.getFuncio();
		serieEstat = (modify.getEstat()!=null ? modify.getEstat() : "ESBORRANY");
		
		List<AplicacionObject> targetAppsList = new ArrayList<>();
		List<SerieDocumentalObject> relatedSeriesList = new ArrayList<>();
		List<SerieArgenObject> relatedArgensList = new ArrayList<>();
		List<NormativaAprobacioObject> normativasList = new ArrayList<>();
		try {
			targetAppsList = service.getListaAplicacionesBySerie(serieId);
			List<AplicacionObject> filteredApps = new ArrayList<>(aplicaciones.getSource());
			filteredApps.removeAll(targetAppsList);
			aplicaciones.setSource(filteredApps);
			aplicaciones.setTarget(targetAppsList);
			
			relatedSeriesList = service.getListaSeriesBySerie(serieId);
			List<SerieDocumentalObject> filtered =  new ArrayList<>(seriesRelacionadas.getSource());
			filtered.removeAll(relatedSeriesList);
			seriesRelacionadas.setSource(filtered);
			seriesRelacionadas.setTarget(relatedSeriesList);
			
			relatedArgensList = service.getListaSeriesArgenBySerie(serieId);
			List<SerieArgenObject> filteredArgen = new ArrayList<>(seriesArgenRelacionadas.getSource());
			filteredArgen.removeAll(relatedArgensList);
			seriesArgenRelacionadas.setSource(filteredArgen);
			seriesArgenRelacionadas.setTarget(relatedArgensList);
			listaRelacionLNS = service.getListaLNS(serieId);
			//normativasList= 
			normativasList = service.getListaNormativasBySerie(serieId);
			List <NormativaAprobacioObject> filteredNormativas = new ArrayList<NormativaAprobacioObject>(normativasSerie.getSource());
			filteredNormativas.removeAll(normativasList);
			normativasSerie.setTarget(normativasList);			
			normativasSerie.setSource(filteredNormativas);
		} catch (I18NException e) {
			e.printStackTrace();
		}	
	}
	public void save() throws Exception {
		System.out.println("SERIE ID" + serieId);
		

		if(this.validateNormativas()) {
			Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
			FuncionesController funcBean = (FuncionesController) viewMap.get("funciones");
			
			if(serieId == null) {
				SerieDocumentalObject newS = service.createNuevaSerie(codi,nom,nomCas,catalegSeriId,serieFuncio.getId()
						,descripcio,descripcioCas,resumMigracio,dir3Promotor, serieEstat,
						tipusSerieId,codiIecisa,aplicaciones.getTarget(),seriesRelacionadas.getTarget(),seriesArgenRelacionadas.getTarget(), listaRelacionLNS,normativasSerie.getTarget());
			
				
				
				TreeNode node = new DefaultTreeNode(new Document<SerieDocumentalObject>(newS.getSerieId(), newS.getCodi(), newS.getNom(), "Serie", newS), 
						funcBean.getNodeFromFunctionId(serieFuncio.getId(), "Funcio", "insert", null));
				
			} else {
				SerieDocumentalObject upS = service.updateSerieDocumental(serieId, codi, nom, nomCas, 
						catalegSeriId, serieFuncio.getId(), descripcio, descripcioCas, resumMigracio, dir3Promotor, serieEstat, tipusSerieId, codiIecisa, aplicaciones.getTarget(),seriesRelacionadas.getTarget(),seriesArgenRelacionadas.getTarget(), listaRelacionLNS,
						normativasSerie.getTarget());
				funcBean.getNodeFromFunctionId(serieId, "Serie", "update", upS);
			}
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dades Salvats"));
		} else {
			FacesContext.getCurrentInstance().validationFailed();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.duplicateNLS"), null);
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
        clearForm();
	}
	
	private Boolean validateNormativas() {
		
		boolean duplicado = false;
		int i=0, j = 0;
		int count = listaRelacionLNS.size();
		
		while(i<count && duplicado==false) {
			LimitacioNormativaSerieObject item = listaRelacionLNS.get(i);
			j=0;
			while(j<count && duplicado==false) {
				LimitacioNormativaSerieObject item2 = listaRelacionLNS.get(j);
				if(item.getNormativa().getId()==item2.getNormativa().getId()
						&& item.getCausaLimitacio().getId()==item2.getCausaLimitacio().getId()
						&& i!=j) {
					duplicado=true;
				}
				j++;
			}
			i++;
		}
		
		return !duplicado;
	}
	
	private void clearForm(){
		
		seriesRelacionadas.setSource(listaSeries);
		seriesRelacionadas.setTarget(new ArrayList<>());
		aplicaciones.setSource(listaAplicaciones);
		aplicaciones.setTarget(new ArrayList<>());
		seriesArgenRelacionadas.setSource(listaSeriesArgen);
		seriesArgenRelacionadas.setTarget(new ArrayList<>());
		normativasSerie.setSource(listaNormativaAprobacio);
		normativasSerie.setTarget(new ArrayList<>());
		
		listaRelacionLNS.clear();
		
		serieId= null;
		codi = null;
		nom = null;
		nomCas = null;
		catalegSeriId = null;
		funcioId =  null;
		descripcio =null;
		descripcioCas= null;
		resumMigracio = null;
		dir3Promotor = null;
		tipusSerieId = null;
		codiIecisa = null;
		serieEstat = "ESBORRANY";
		//modify
	}
	
	public void onLimitacioSelectedListener(AjaxBehaviorEvent event){
    	
    		LimitacioNormativaSerieObject lns = new LimitacioNormativaSerieObject();
    		lns.setNormativa(selectedNormativa);
    		if(listaRelacionLNS.size()==0) {
    			lns.setId(0);
    		} else {
    			int lastid = listaRelacionLNS.get(listaRelacionLNS.size()-1).getId();
    			lns.setId(lastid+1);
    		}
    		
    		listaRelacionLNS.add(lns);
    		
    		selectedNormativa = null;
    }
	
	public void deleteLNS(LimitacioNormativaSerieObject lnsitem) {
		int index = listaRelacionLNS.indexOf(lnsitem);
		if(index>=0) {
			listaRelacionLNS.remove(index);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Eliminado", null);
			FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Error al eliminar la relación", null);
			FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
		}
	}
	
	public void saveLNS(LimitacioNormativaSerieObject lnsitem) {

		
		if(this.validateNormativas()) {
			int index = listaRelacionLNS.indexOf(lnsitem);
			if(serieId!=null) lnsitem.setSeriedocumental(modify);
			if(index>=0) {
				listaRelacionLNS.set(index, lnsitem);
			} else {
				listaRelacionLNS.add(lnsitem);
			}		
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Guardado", null);
			FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
		} else {
			FacesContext.getCurrentInstance().validationFailed();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.duplicateNLS"), null);
			FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
		}
		
	}
	
	
	public NormativaAprobacioObject getSelectedNormativa() {
		return selectedNormativa;
	}

	public void setSelectedNormativa(NormativaAprobacioObject selectedNormativa) {
		this.selectedNormativa = selectedNormativa;
	}

	public String getCodi() {
		return codi;
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


	public Long getCatalegSeriId() {
		return catalegSeriId;
	}


	public void setCatalegSeriId(Long catalegSeriId) {
		
		this.catalegSeriId = catalegSeriId;
	}


	public Long getFuncioId() {
		return funcioId;
	}


	public void setFuncioId(Long funcioId) {
		this.funcioId = funcioId;
	}


	public String getDescripcio() {
		return descripcio;
	}


	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
	}


	public String getDescripcioCas() {
		return descripcioCas;
	}


	public void setDescripcioCas(String descripcioCas) {
		this.descripcioCas = descripcioCas;
	}


	public String getResumMigracio() {
		return resumMigracio;
	}


	public void setResumMigracio(String resumMigracio) {
		this.resumMigracio = resumMigracio;
	}


	public String getDir3Promotor() {
		return dir3Promotor;
	}


	public void setDir3Promotor(String dir3Promotor) {
		this.dir3Promotor = dir3Promotor;
	}


	public Long getTipusSerieId() {
		return tipusSerieId;
	}


	public void setTipusSerieId(Long tipusSerieId) {
		this.tipusSerieId = tipusSerieId;
	}


	public String getCodiIecisa() {
		return codiIecisa;
	}


	public void setCodiIecisa(String codiIecisa) {
		this.codiIecisa = codiIecisa;
	}
	

	public List<CatalegSeriesObject> getListaCatalegSerie() {
		return listaCatalegSerie;
	}


	public void setListaCatalegSerie(List<CatalegSeriesObject> listaCatalegSerie) {
		this.listaCatalegSerie = listaCatalegSerie;
	}


	public List<FuncioObject> getListaFunciones() {
		return listaFunciones;
	}


	public void setListaFunciones(List<FuncioObject> listaFunciones) {
		this.listaFunciones = listaFunciones;
	}


	public List<TipusSerieObject> getListaTipusSerie() {
		return listaTipusSerie;
	}


	public void setListaTipusSerie(List<TipusSerieObject> listaTipusSerie) {
		this.listaTipusSerie = listaTipusSerie;
	}


	public List<SerieDocumentalObject> getListaSeries() {
		return listaSeries;
	}
	public void setListaSeries(List<SerieDocumentalObject> listaSeries) {
		this.listaSeries = listaSeries;
	}
	public List<AplicacionObject> getListaAplicaciones() {
		return listaAplicaciones;
	}
	public void setListaAplicaciones(List<AplicacionObject> listaAplicaciones) {
		this.listaAplicaciones = listaAplicaciones;
	}
	public List<SerieDocumentalObject> getListaSeriesSelected() {
		return listaSeriesSelected;
	}
	public void setListaSeriesSelected(List<SerieDocumentalObject> listaSeriesSelected) {
		this.listaSeriesSelected = listaSeriesSelected;
	}
	public List<AplicacionObject> getListaAplicacionesSelected() {
		return listaAplicacionesSelected;
	}
	public void setListaAplicacionesSelected(List<AplicacionObject> listaAplicacionesSelected) {
		this.listaAplicacionesSelected = listaAplicacionesSelected;
	}
	public DualListModel<AplicacionObject> getAplicaciones() {
		return aplicaciones;
	}
	public void setAplicaciones(DualListModel<AplicacionObject> aplicaciones) {
		this.aplicaciones = aplicaciones;
	}
	public DualListModel<SerieDocumentalObject> getSeriesRelacionadas() {
		return seriesRelacionadas;
	}
	public void setSeriesRelacionadas(DualListModel<SerieDocumentalObject> seriesRelacionadas) {
		this.seriesRelacionadas = seriesRelacionadas;
	}
	public String getFuncionNom() {
		return funcionNom;
	}
	public void setFuncionNom(String funcionNom) {
		this.funcionNom = funcionNom;
	}

	public SerieDocumentalObject getModify() {
		return modify;
	}

	public void setModify(SerieDocumentalObject modify) {
		this.modify = modify;
	}

	public Long getSerieId() {
		return serieId;
	}

	public void setSerieId(Long serieId) {
		this.serieId = serieId;
	}

	public SerieFrontService getService() {
		return service;
	}

	public void setService(SerieFrontService service) {
		this.service = service;
	}

	public FuncioObject getSerieFuncio() {
		return serieFuncio;
	}

	public void setSerieFuncio(FuncioObject serieFuncio) {
		this.serieFuncio = serieFuncio;
	}

	public String getSerieEstat() {
		return serieEstat;
	}

	public void setSerieEstat(String serieEstat) {
		this.serieEstat = serieEstat;
	}

	public List<NormativaAprobacioObject> getListaNormativaAprobacio() {
		return listaNormativaAprobacio;
	}

	public void setListaNormativaAprobacio(List<NormativaAprobacioObject> listaNormativaAprobacio) {
		this.listaNormativaAprobacio = listaNormativaAprobacio;
	}

	public List<CausaLimitacioObject> getListaCausaLimitacio() {
		return listaCausaLimitacio;
	}

	public void setListaCausaLimitacio(List<CausaLimitacioObject> listaCausaLimitacio) {
		this.listaCausaLimitacio = listaCausaLimitacio;
	}

	public List<LimitacioNormativaSerieObject> getListaRelacionLNS() {
		return listaRelacionLNS;
	}

	public void setListaRelacionLNS(List<LimitacioNormativaSerieObject> listaRelacionLNS) {
		this.listaRelacionLNS = listaRelacionLNS;
	}

	public DualListModel<SerieArgenObject> getSeriesArgenRelacionadas() {
		return seriesArgenRelacionadas;
	}

	public void setSeriesArgenRelacionadas(DualListModel<SerieArgenObject> seriesArgenRelacionadas) {
		this.seriesArgenRelacionadas = seriesArgenRelacionadas;
	}

	public List<SerieArgenObject> getSeriesArgenSelected() {
		return seriesArgenSelected;
	}

	public void setSeriesArgenSelected(List<SerieArgenObject> seriesArgenSelected) {
		this.seriesArgenSelected = seriesArgenSelected;
	}

	public List<SerieArgenObject> getListaSeriesArgen() {
		return listaSeriesArgen;
	}

	public void setListaSeriesArgen(List<SerieArgenObject> listaSeriesArgen) {
		this.listaSeriesArgen = listaSeriesArgen;
	}

	public DualListModel<NormativaAprobacioObject> getNormativasSerie() {
		return normativasSerie;
	}

	public void setNormativasSerie(DualListModel<NormativaAprobacioObject> normativasSerie) {
		this.normativasSerie = normativasSerie;
	}

	public List<NormativaAprobacioObject> getNormativasSerieSelected() {
		return normativasSerieSelected;
	}

	public void setNormativasSerieSelected(List<NormativaAprobacioObject> normativasSerieSelected) {
		this.normativasSerieSelected = normativasSerieSelected;
	}
	
	
	
	
}

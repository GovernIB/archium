package es.caib.archium.controllers;

import java.util.ArrayList;
import java.util.Iterator;
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

import es.caib.archium.commons.utils.Constants;
import org.primefaces.PrimeFaces;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.SerieService;
import es.caib.archium.objects.AplicacionObject;
import es.caib.archium.objects.CatalegSeriesObject;
import es.caib.archium.objects.CausaLimitacioObject;
import es.caib.archium.objects.Dir3Object;
import es.caib.archium.objects.Document;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.LimitacioNormativaSerieObject;
import es.caib.archium.objects.NormativaAprobacioObject;
import es.caib.archium.objects.SerieArgenObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.objects.TipuDocumentalProcedimentObject;
import es.caib.archium.objects.TipuValorObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.objects.TipusSerieObject;
import es.caib.archium.objects.ValorPrimariObject;
import es.caib.archium.objects.ValorSecundariObject;
import es.caib.archium.objects.ValoracioObject;
import es.caib.archium.persistence.model.Valoracio;
import es.caib.archium.services.SerieFrontService;
import es.caib.archium.utils.FrontExceptionTranslate;

import java.io.Serializable;

@Named
@ViewScoped
public class NuevaSerieController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -8940713018192370062L;
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
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
	private ValoracioObject valoracio;
	
	private SerieDocumentalObject modify;
	
	@Inject
	private SerieFrontService service;
	
	private List<Dir3Object> listaDir3;
	
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
    private DualListModel<SerieArgenObject> seriesArgenRelacionadas;
    private List<SerieArgenObject> seriesArgenSelected;
    private List<SerieArgenObject> listaSeriesArgen;
    private DualListModel<NormativaAprobacioObject> normativasSerie;
    private List<NormativaAprobacioObject> normativasSerieSelected;
    
    private List<TipuValorObject> listaTiposValor;
    private List<ValorSecundariObject> listaValoresSecundarios;
    
    
    ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
	FuncionesController funcBean = null;

	private static String UNIDAD_PLAZO_SERIE_DEFAULT_MESSAGE = null;

	@PostConstruct
	public void init(){

		UNIDAD_PLAZO_SERIE_DEFAULT_MESSAGE = messageBundle.getString("general.plazos.anys");
		
		Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
		this.funcBean = (FuncionesController) viewMap.get("funciones");
		
		seriesArgenSelected = new ArrayList<SerieArgenObject>();
		listaSeriesSelected = new ArrayList<SerieDocumentalObject>();
		listaAplicacionesSelected= new ArrayList<AplicacionObject>();
		normativasSerieSelected = new ArrayList<NormativaAprobacioObject>();
		listaRelacionLNS = new ArrayList<LimitacioNormativaSerieObject>();
		valoracio = new ValoracioObject();
		
		try {
			listaDir3 = service.getListaDir3();
			
			listaTiposValor = service.getListaTipusValor();
			listaValoresSecundarios = service.getListaValorsSecundari();
			
			listaCatalegSerie = service.getListaCatalegSerie();
			listaFunciones = service.getListaFunciones();
			listaTipusSerie = service.getListaTipusSerie();
			listaSeries = service.getListaSeries();
			listaAplicaciones = service.getListaAplicaciones();
			listaNormativaAprobacio = service.getListaNormativas();
			listaCausaLimitacio = service.getListaCausaLimitacio();
			listaSeriesArgen = service.getListaSeriesArgen();
					
			for(TipuValorObject tv: listaTiposValor) {
				ValorPrimariObject vp = new ValorPrimariObject();
				vp.setAchTipusvalor(tv);
				vp.setTerminiType(UNIDAD_PLAZO_SERIE_DEFAULT_MESSAGE);
				valoracio.addValorprimari(vp);
			}
					
		} catch (I18NException e) {
			listaAplicaciones = new ArrayList<AplicacionObject>();
			listaNormativaAprobacio = new ArrayList<NormativaAprobacioObject>();
			listaSeries = new ArrayList<SerieDocumentalObject>();
			listaSeriesArgen = new ArrayList<SerieArgenObject>();
			funcBean.setError(true);
			log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
		} finally {
			aplicaciones = new DualListModel<AplicacionObject>(listaAplicaciones, listaAplicacionesSelected);
			seriesRelacionadas = new DualListModel<SerieDocumentalObject>(listaSeries,listaSeriesSelected);
			seriesArgenRelacionadas = new DualListModel<SerieArgenObject>(listaSeriesArgen,seriesArgenSelected);
			normativasSerie = new DualListModel<NormativaAprobacioObject>(listaNormativaAprobacio ,normativasSerieSelected);
		}
		
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
	
	public void deleteSerie(Document<SerieDocumentalObject> s) {

    	try {
			this.service.deleteSerie(s.getId());
			TreeNode node = funcBean.getNodeFromFunctionId(s.getId(), "Serie", "update", s);
			node.getParent().getChildren().remove(node);
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("nuevaserie.delete.ok")));
		} catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.delete.error"), null));
		}
    }
	
	
	public void updateSerie(Document<SerieDocumentalObject> obj) {
		
		clearForm();
		
		
		try {
			
			SerieDocumentalObject modify = this.service.findById(obj.getId());
			
			if(modify!=null) {
				serieId= obj.getId();
				codi = modify.getCodi();
				nom = modify.getNom();
				nomCas = modify.getNomCas();
				catalegSeriId = modify.getCatalegSeriId();
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
				
				for(LimitacioNormativaSerieObject lns: listaRelacionLNS) {
					List<CausaLimitacioObject> filteredCL = new ArrayList<CausaLimitacioObject>(listaCausaLimitacio);
					filteredCL.removeAll(lns.getListCausaLimitacio());
					lns.setDualListCausas(new DualListModel<CausaLimitacioObject>());
		    		lns.getDualListCausas().setSource(filteredCL);
		    		lns.getDualListCausas().setTarget(lns.getListCausaLimitacio());
				}
				
				normativasList = service.getListaNormativasBySerie(serieId);
				List <NormativaAprobacioObject> filteredNormativas = new ArrayList<NormativaAprobacioObject>(normativasSerie.getSource());
				filteredNormativas.removeAll(normativasList);
				normativasSerie.setSource(filteredNormativas);
				normativasSerie.setTarget(normativasList);			
				
				
				ValoracioObject dbValoracio = service.getValoracioSerie(serieId);
				valoracio = new ValoracioObject(dbValoracio);
				valoracio.getAchValorprimaris().clear();
				for(TipuValorObject tv: listaTiposValor) {
					
					boolean existe = false;
					Iterator<ValorPrimariObject> it = dbValoracio.getAchValorprimaris().iterator();
					while(it.hasNext() && existe==false) {
						ValorPrimariObject item = it.next();
						if(tv.getId()==item.getAchTipusvalor().getId()) {
							item.setSelected(true);
							valoracio.addValorprimari(item);
							existe=true;
						}
					}
									
					if(existe==false) {
						
						ValorPrimariObject vp = new ValorPrimariObject();
						vp.setTerminiType(UNIDAD_PLAZO_SERIE_DEFAULT_MESSAGE);
						vp.setAchTipusvalor(tv);
						ValoracioObject val = new ValoracioObject();
						val.setId(dbValoracio.getId());
						vp.setAchValoracio(val);
						valoracio.addValorprimari(vp);
					}
					
				}
				
				PrimeFaces.current().executeScript("PF('serieModalDialog').show()");
			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.update.abrirUpdate.error"), messageBundle.getString("procediment.update.abrirUpdate.error"));
			 	FacesContext.getCurrentInstance().addMessage(null, msg);		
			}

		} catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.update.abrirUpdate.error"), messageBundle.getString("procediment.update.abrirUpdate.error"));
		 	FacesContext.getCurrentInstance().addMessage(null, msg);		
		} 
	}
	public void save(){	
		
		try {
			if(this.validateValoracion()) {
					
				if(serieId == null) {
					SerieDocumentalObject newS = service.createNuevaSerie(codi,nom,nomCas,catalegSeriId,serieFuncio.getId()
							,descripcio,descripcioCas,resumMigracio,dir3Promotor, serieEstat,
							tipusSerieId,codiIecisa,aplicaciones.getTarget(),seriesRelacionadas.getTarget(),seriesArgenRelacionadas.getTarget(), listaRelacionLNS,normativasSerie.getTarget(), valoracio);
				
					
					
					TreeNode node = new DefaultTreeNode(new Document<SerieDocumentalObject>(newS.getSerieId(), newS.getCodi(), newS.getNom(), "Serie", newS), 
							funcBean.getNodeFromFunctionId(serieFuncio.getId(), "Funcio", "insert", null));
					
				} else {
					SerieDocumentalObject upS = service.updateSerieDocumental(serieId, codi, nom, nomCas, 
							catalegSeriId, serieFuncio.getId(), descripcio, descripcioCas, resumMigracio, dir3Promotor, serieEstat, tipusSerieId, codiIecisa, aplicaciones.getTarget(),seriesRelacionadas.getTarget(),seriesArgenRelacionadas.getTarget(), listaRelacionLNS,
							normativasSerie.getTarget(), valoracio);
					funcBean.getNodeFromFunctionId(serieId, "Serie", "update", upS);
				}
				listaSeries = service.getListaSeries();
				clearForm();
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("nuevaserie.ok")));

			} else {
				FacesContext.getCurrentInstance().validationFailed();
				FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.valoracio"), null);
				FacesContext.getCurrentInstance().addMessage(null, message);
			}
		}catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
			FacesMessage message = null;
			if(serieId == null) {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.insert.error"), null);
			} else {
				message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.update.error"), null);
			}
			FacesContext.getCurrentInstance().addMessage(null, message);
		}
       
	}
	
	private Boolean validateValoracion() {
				
		if(valoracio.getAchValorsecundari()==null 
			&& (valoracio.getAchValorprimaris()==null || valoracio.hashValorPrimariSelected()==false)){
			return true;
		} else if(valoracio.getAchValorsecundari()!=null 
			&& valoracio.getAchValorprimaris()!=null
			&& valoracio.hashValorPrimariSelected()==true) {
			return true;
		}
		return false;
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
		
		valoracio = new ValoracioObject();
		for(TipuValorObject tv: listaTiposValor) {
			ValorPrimariObject vp = new ValorPrimariObject();
			vp.setAchTipusvalor(tv);
			vp.setTerminiType(UNIDAD_PLAZO_SERIE_DEFAULT_MESSAGE);
			valoracio.addValorprimari(vp);
		}
	}
	
	private Boolean validNormativa() {
		Boolean valid = true;
		
		Iterator<LimitacioNormativaSerieObject> i = listaRelacionLNS.iterator();
		
		while(i.hasNext() && valid==true) {
			if(i.next().getNormativa().equals(selectedNormativa)){
				valid=false;
			}
		}
		
		return valid;
	}
	
	public void onLimitacioSelectedListener(AjaxBehaviorEvent event){
		
		
		if(this.validNormativa()) {
			LimitacioNormativaSerieObject lns = new LimitacioNormativaSerieObject();
    		lns.setListCausaLimitacio(new ArrayList<CausaLimitacioObject>());
    		lns.setDualListCausas(new DualListModel<CausaLimitacioObject>());
    		lns.getDualListCausas().setSource(listaCausaLimitacio);
    		lns.getDualListCausas().setTarget(new ArrayList<CausaLimitacioObject>());
    		lns.setNormativa(selectedNormativa);
    		listaRelacionLNS.add(lns);
    		selectedNormativa = null;
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.dupnorm"), null);
			FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
		}
		
			
    }
	
	public void deleteLNS(LimitacioNormativaSerieObject lnsitem) {
		int index = listaRelacionLNS.indexOf(lnsitem);
		if(index>=0) {
			listaRelacionLNS.remove(index);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageBundle.getString("datos.eliminados"), null);
			FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.delcausanorm"), null);
			FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
		}
	}
	
	public void saveLNS(LimitacioNormativaSerieObject lnsitem) {
			
		if(lnsitem.getDualListCausas().getTarget().size()>0) {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageBundle.getString("datos.salvados"), null);
			FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
		} else {
			FacesContext.getCurrentInstance().validationFailed();
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.causanorm"), null);
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

	public List<TipuValorObject> getListaTiposValor() {
		return listaTiposValor;
	}

	public void setListaTiposValor(List<TipuValorObject> listaTiposValor) {
		this.listaTiposValor = listaTiposValor;
	}

	public List<ValorSecundariObject> getListaValoresSecundarios() {
		return listaValoresSecundarios;
	}

	public void setListaValoresSecundarios(List<ValorSecundariObject> listaValoresSecundarios) {
		this.listaValoresSecundarios = listaValoresSecundarios;
	}

	public ValoracioObject getValoracio() {
		return valoracio;
	}

	public void setValoracio(ValoracioObject valoracio) {
		this.valoracio = valoracio;
	}

	public List<Dir3Object> getListaDir3() {
		return listaDir3;
	}

	public void setListaDir3(List<Dir3Object> listaDir3) {
		this.listaDir3 = listaDir3;
	}

	

	
	
	
	
}

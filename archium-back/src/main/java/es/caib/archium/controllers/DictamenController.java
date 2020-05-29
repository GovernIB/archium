package es.caib.archium.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.DictamenObject;
import es.caib.archium.objects.Document;
import es.caib.archium.objects.EnsObject;
import es.caib.archium.objects.LopdObject;
import es.caib.archium.objects.NormativaAprobacioObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.objects.TipuAccesObject;
import es.caib.archium.objects.TipuDictamenObject;
import es.caib.archium.services.DictamenFrontService;

@Named("dictamenController")
@ViewScoped
public class DictamenController implements Serializable {
	
   	    // nullable
	    private String 	 destinatariRestringits;
	    private String	 condicioReutilizacio;
	    private String	 accioDictaminada;
	    
	    //private List<DictamenObject> tipusDictamenList 		= new ArrayList<>();
	    //private List<DictamenObject> lopdList		 		= new ArrayList<>();
	    //private List<DictamenObject> ensList		 		= new ArrayList<>();
	    //private List<DictamenObject> tiposAccessList 		= new ArrayList<>();
	    
	    private Date inici;
	    private Date 	aprovacio;
	    private Date 	fin;
	    private String 	termini;
	    private String 	estat;
	    private String 	codi;
	    private Boolean serieEsencial;
	

		// not nullable
		private Long 		id;
		private Long 		dictamenId;
		private Long		documentalId;
		private Long 	 	tipuDictamenId;
		private Long		tipuAccesId;
		private Long		ensId;
		private Long		lopdId;
		private Long		normativaAprovacioId;
	
		private DictamenObject 				dictamen = new DictamenObject();
		private SerieDocumentalObject		serieDocumental;
		private TipuDictamenObject 		 	tipuDictamen;
		private TipuAccesObject				tipuAcces;
		private EnsObject					ens;
		private LopdObject					lopd;
		private NormativaAprobacioObject	normativaAprovacio;
	
		private List<DictamenObject> 		listaDictamen;
		private List<SerieDocumentalObject> 			listaDocumental;
		private List<TipuDictamenObject> 	listatipuDictamen;
		private List<TipuAccesObject> 		listaTipuAcces;
		private List<EnsObject>				listaEns;
		private List<LopdObject>			listaLopd;
		private List<NormativaAprobacioObject>	listaNormativaAprovacio;
		private List<String>				estados	= new ArrayList<>();
		
		private Document<DictamenObject> miObjeto  = new Document<DictamenObject>();
		
		private List<String>						plazos1		= 	new ArrayList<>();
		private String 		plazoTermini;
		private Integer 	plazoTerminiVal;
		
	    @Inject
	    private DictamenFrontService 			serviceDictamen;	    
    
	    ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
	    
	    @PostConstruct
	    public void init() throws I18NException {   
	    	try 
	    	{
	    		this.listaDictamen 		= this.serviceDictamen.findAll();	    		
	    		
	    		this.listaDocumental 	=	this.serviceDictamen.findAllDocumental();
	    		this.listatipuDictamen 	=	this.serviceDictamen.findAllTipuDictamen();
	    		this.listaTipuAcces 	=	this.serviceDictamen.findAllTipuAcces();
	    		this.listaEns 			=	this.serviceDictamen.findAllEns();
	    		this.listaLopd 			=	this.serviceDictamen.findAllLopd();
	    		this.listaNormativaAprovacio 	=	this.serviceDictamen.findAllNormativaAprovacio();
	    		//this.listaCuadrosClasificacion = this.servicesCuadroClasificacion.findAll();
	    		//this.listaTipusserie = this.servicesTipusseries.findAll();
	    		this.estados.add(messageBundle.getString("general.estats.esborrany")); 
	    		this.estados.add(messageBundle.getString("general.estats.revisat"));
	    		this.estados.add(messageBundle.getString("general.estats.publicable"));
	    		this.estados.add(messageBundle.getString("general.estats.vigent"));
	    		this.estados.add(messageBundle.getString("general.estats.obsolet"));
	    		
	    		this.plazos1.add(messageBundle.getString("general.plazos.hores"));
	    		this.plazos1.add(messageBundle.getString("general.plazos.dies"));
	    		this.plazos1.add(messageBundle.getString("general.plazos.setmanes"));
	    		this.plazos1.add(messageBundle.getString("general.plazos.mesos"));
	    		this.plazos1.add(messageBundle.getString("general.plazos.anys"));

	    	}
	    	catch(Exception e) 
	    	{	
	    		e.printStackTrace();
	    	}        
	    }
	    
	   
	    private void save() {    
	    	
	    	Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
			FuncionesController funcBean = (FuncionesController) viewMap.get("funciones");
	    	
			if (this.plazoTerminiVal != null) {
				this.setTermini(this.plazoTerminiVal.toString().concat(this.plazoTermini.substring(0,1)));
			}
			
			try {
				DictamenObject newD = this.serviceDictamen.create(this.serieDocumental,
										  	this.tipuDictamen,
										  	this.accioDictaminada,
										  	this.termini,
										  	this.destinatariRestringits,
										  	this.fin,
										  	this.tipuAcces,
										  	this.ens,
										  	this.lopd,
										  	this.condicioReutilizacio,
										  	this.serieEsencial,
										  	this.normativaAprovacio,
										  	this.aprovacio,
										  	this.codi,
										  	this.estat
											);
				TreeNode node = new DefaultTreeNode(new Document<DictamenObject>(newD.getId(), newD.getCodi(), newD.getAccioDictaminada(), "Dictamen", newD), 
						//funcBean.getNodeFromFunctionId(serieDocumental.getSerieId(), "Serie", "insert", null));
						funcBean.getNodeFromFunctionId(serieDocumental.getSerieId(), "Serie", "insert", null));
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("dictamen.insert.ok")));
			} catch (Exception eee) {
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("dictamen.insert.error")));
				eee.printStackTrace();
			}			
	    }
	    
	    private void update() {
	    	
	    	Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
			FuncionesController funcBean = (FuncionesController) viewMap.get("funciones");
	    	
			if (this.plazoTerminiVal != null) {
				this.setTermini(this.plazoTerminiVal.toString().concat(this.plazoTermini.substring(0,1)));
			}
			
	    	try {
	    		
	    		DictamenObject obj = new DictamenObject();
		    	obj.setId(this.getId());
		    	obj.setSerieDocumental(this.getSerieDocumental());
		    	obj.setTipusdictamen(this.getTipuDictamen());
		    	obj.setAccioDictaminada(this.getAccioDictaminada());
		    	obj.setTermini(this.getTermini());
		    	obj.setDestinatarisRestrigits(this.getDestinatariRestringits());
		    	obj.setInici(this.getInici());
		    	obj.setFi(this.getFin());
		    	obj.setTipusAcces(this.getTipuAcces());
		    	obj.setEns(this.getEns());
		    	obj.setLopd(this.getLopd());
		    	obj.setCondicioReutilitzacio(this.getCondicioReutilizacio());
		    	obj.setSerieEsencial(this.getSerieEsencial());
		    	obj.setNormativaAprovacio(this.getNormativaAprovacio());
		    	obj.setAprovacio(this.getAprovacio());
	    		obj.setCodi(this.getCodi());
	    		obj.setEstat(this.getEstat());
	    		
	    		DictamenObject upD = this.serviceDictamen.update(obj);
		        FacesMessage msg = new FacesMessage(messageBundle.getString("dictamen.update.ok"));
		        FacesContext.getCurrentInstance().addMessage(null, msg);		        
		        
		        funcBean.getNodeFromFunctionId(upD.getId(), "Dictamen", "update", upD);
		        
		    }
	    	catch (Exception e) {
	 			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("dictamen.update.error"), null);
	 			FacesContext.getCurrentInstance().addMessage(null, msg);	
	 			e.printStackTrace();
	 		}
	    }
	    
	    public void saveUpdate() {
	    	if (this.getId() == null) { 		
	    		save();	    		
	    	}
	    	else { 
	    		update();
	    	}	    	
	    }
	   
	    public void addDictamentfromSerie(Document<SerieDocumentalObject> obj){

	    	this.setSerieDocumental(obj.getObject());
	    	this.id = null;
	    	this.tipuDictamen = null;
	    	this.accioDictaminada = null;
	    	this.termini = null;
	    	this.destinatariRestringits = null;
	    	this.inici = null;
	    	this.fin = null;
	    	this.tipuAcces = null;
	    	this.ens = null;
	    	this.lopd = null;
	    	this.condicioReutilizacio = null;
	    	this.serieEsencial = false;
	    	this.normativaAprovacio = null;
	    	this.aprovacio = null;
	    	this.codi = null;
	    	this.estat = null;
	    	this.plazoTermini = null;
	    	this.plazoTerminiVal = null;
	    }
	    
	    public void updateDictament(Document<DictamenObject> obj)
	    {	    	
			this.setId(obj.getObject().getId());
	    	this.setSerieDocumental(obj.getObject().getSerieDocumental());
	    	this.setTipuDictamen(obj.getObject().getTipusdictamen());
	    	this.setAccioDictaminada(obj.getObject().getAccioDictaminada());
	    	
	    	
	    	if (obj.getObject().getTermini() != null) {
				String a 	= obj.getObject().getTermini().substring(obj.getObject().getTermini().length() - 1, obj.getObject().getTermini().length());
				if (a.equals("H"))
					this.setPlazoTermini(messageBundle.getString("general.plazos.hores"));
				if (a.equals("D"))
					this.setPlazoTermini(messageBundle.getString("general.plazos.dies"));
				if (a.equals("S"))
					this.setPlazoTermini(messageBundle.getString("general.plazos.setmanes"));
				if (a.equals("M"))
					this.setPlazoTermini(messageBundle.getString("general.plazos.mesos"));
				if (a.equals("A"))
					this.setPlazoTermini(messageBundle.getString("general.plazos.anys"));
				this.plazoTerminiVal 	= Integer.parseInt(obj.getObject().getTermini().substring(0,obj.getObject().getTermini().length() - 1));
			}
	    	
	    	this.setTermini(obj.getObject().getTermini());	    	
	    	this.setDestinatariRestringits(obj.getObject().getDestinatarisRestrigits());
	    	this.setInici(obj.getObject().getInici());
	    	this.setFin(obj.getObject().getFi());
	    	this.setTipuAcces(obj.getObject().getTipusAcces());
	    	this.setEns(obj.getObject().getEns());
	    	this.setLopd(obj.getObject().getLopd());
	    	this.setCondicioReutilizacio(obj.getObject().getCondicioReutilitzacio());
	    	this.setSerieEsencial(obj.getObject().getSerieEsencial());
	    	this.setNormativaAprovacio(obj.getObject().getNormativaAprovacio());
	    	this.setAprovacio(obj.getObject().getAprovacio());
	    	this.setEstat(obj.getObject().getEstat());
	    	this.setCodi(obj.getObject().getCodi());
	    }
	   
	   

		public Long getId() {
			return id;
		}



		public void setId(Long id) {
			this.id = id;
		}


		public String getDestinatariRestringits() {
			return destinatariRestringits;
		}



		public void setDestinatariRestringits(String destinatariRestringits) {
			this.destinatariRestringits = destinatariRestringits;
		}



		public String getCondicioReutilizacio() {
			return condicioReutilizacio;
		}



		public void setCondicioReutilizacio(String condicioReutilizacio) {
			this.condicioReutilizacio = condicioReutilizacio;
		}



		public String getAccioDictaminada() {
			return accioDictaminada;
		}



		public void setAccioDictaminada(String accioDictaminada) {
			this.accioDictaminada = accioDictaminada;
		}

		public Date getAprovacio() {
			return aprovacio;
		}



		public void setAprovacio(Date aprovacio) {
			this.aprovacio = aprovacio;
		}



		public Date getFin() {
			return fin;
		}



		public void setFin(Date fin) {
			this.fin = fin;
		}



		public String getTermini() {
			return termini;
		}



		public void setTermini(String termini) {
			this.termini = termini;
		}



		public Boolean getSerieEsencial() {
			return serieEsencial;
		}



		public void setSerieEsencial(Boolean serieEsencial) {
			this.serieEsencial = serieEsencial;
		}



		public Long getDictamenId() {
			return dictamenId;
		}



		public void setDictamenId(Long dictamenId) {
			this.dictamenId = dictamenId;
		}



		public Long getDocumentalId() {
			return documentalId;
		}



		public void setDocumentalId(Long documentalId) {
			this.documentalId = documentalId;
		}



		public Long getTipuDictamenId() {
			return tipuDictamenId;
		}



		public void setTipuDictamenId(Long tipuDictamenId) {
			this.tipuDictamenId = tipuDictamenId;
		}



		public Long getTipuAccesId() {
			return tipuAccesId;
		}



		public void setTipuAccesId(Long tipuAccesId) {
			this.tipuAccesId = tipuAccesId;
		}



		public Long getEnsId() {
			return ensId;
		}



		public void setEnsId(Long ensId) {
			this.ensId = ensId;
		}



		public Long getLopdId() {
			return lopdId;
		}



		public void setLopdId(Long lopdId) {
			this.lopdId = lopdId;
		}



		public Long getNormativaAprovacioId() {
			return normativaAprovacioId;
		}



		public void setNormativaAprovacioId(Long normativaAprovacioId) {
			this.normativaAprovacioId = normativaAprovacioId;
		}



		public DictamenObject getDictamen() {
			return dictamen;
		}



		public void setDictamen(DictamenObject dictamen) {
			this.dictamen = dictamen;
		}



		public SerieDocumentalObject getSerieDocumental() {
			return serieDocumental;
		}



		public void setSerieDocumental(SerieDocumentalObject serieDocumental) {
			this.serieDocumental = serieDocumental;
		}



		public TipuDictamenObject getTipuDictamen() {
			return tipuDictamen;
		}



		public void setTipuDictamen(TipuDictamenObject tipuDictamen) {
			this.tipuDictamen = tipuDictamen;
		}



		public TipuAccesObject getTipuAcces() {
			return tipuAcces;
		}



		public void setTipuAcces(TipuAccesObject tipuAcces) {
			this.tipuAcces = tipuAcces;
		}



		public EnsObject getEns() {
			return ens;
		}



		public void setEns(EnsObject ens) {
			this.ens = ens;
		}



		public LopdObject getLopd() {
			return lopd;
		}



		public void setLopd(LopdObject lopd) {
			this.lopd = lopd;
		}



		public NormativaAprobacioObject getNormativaAprovacio() {
			return normativaAprovacio;
		}



		public void setNormativaAprovacio(NormativaAprobacioObject normativaAprovacio) {
			this.normativaAprovacio = normativaAprovacio;
		}



		public List<DictamenObject> getListaDictamen() {
			return listaDictamen;
		}



		public void setListaDictamen(List<DictamenObject> listaDictamen) {
			this.listaDictamen = listaDictamen;
		}



		public List<SerieDocumentalObject> getListaDocumental() {
			return listaDocumental;
		}



		public void setListaDocumental(List<SerieDocumentalObject> listaDocumental) {
			this.listaDocumental = listaDocumental;
		}



		public List<TipuDictamenObject> getListatipuDictamen() {
			return listatipuDictamen;
		}



		public void setListatipuDictamen(List<TipuDictamenObject> listatipuDictamen) {
			this.listatipuDictamen = listatipuDictamen;
		}



		public List<TipuAccesObject> getListaTipuAcces() {
			return listaTipuAcces;
		}



		public void setListaTipuAcces(List<TipuAccesObject> listaTipuAcces) {
			this.listaTipuAcces = listaTipuAcces;
		}



		public List<EnsObject> getListaEns() {
			return listaEns;
		}



		public void setListaEns(List<EnsObject> listaEns) {
			this.listaEns = listaEns;
		}



		public List<LopdObject> getListaLopd() {
			return listaLopd;
		}



		public void setListaLopd(List<LopdObject> listaLopd) {
			this.listaLopd = listaLopd;
		}



		public List<NormativaAprobacioObject> getListaNormativaAprovacio() {
			return listaNormativaAprovacio;
		}



		public void setListaNormativaAprovacio(List<NormativaAprobacioObject> listaNormativaAprovacio) {
			this.listaNormativaAprovacio = listaNormativaAprovacio;
		}



		public DictamenFrontService getServiceDictamen() {
			return serviceDictamen;
		}



		public void setServiceDictamen(DictamenFrontService serviceDictamen) {
			this.serviceDictamen = serviceDictamen;
		}



		public Document<DictamenObject> getMiObjeto() {
			return miObjeto;
		}



		public void setMiObjeto(Document<DictamenObject> miObjeto) {
			this.miObjeto = miObjeto;
		}


		public String getEstat() {
			return estat;
		}


		public void setEstat(String estat) {
			this.estat = estat;
		}


		public String getCodi() {
			return codi;
		}


		public void setCodi(String codi) {
			this.codi = codi;
		}


		public List<String> getEstados() {
			return estados;
		}


		public void setEstados(List<String> estados) {
			this.estados = estados;
		}


		public List<String> getPlazos1() {
			return plazos1;
		}


		public void setPlazos1(List<String> plazos1) {
			this.plazos1 = plazos1;
		}


		public String getPlazoTermini() {
			return plazoTermini;
		}


		public void setPlazoTermini(String plazoTermini) {
			this.plazoTermini = plazoTermini;
		}


		public Integer getPlazoTerminiVal() {
			return plazoTerminiVal;
		}


		public void setPlazoTerminiVal(Integer plazoTerminiVal) {
			this.plazoTerminiVal = plazoTerminiVal;
		}


		public Date getInici() {
			return inici;
		}


		public void setInici(Date inici) {
			this.inici = inici;
		}

		

	}
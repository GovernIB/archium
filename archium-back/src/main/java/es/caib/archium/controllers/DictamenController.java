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

import es.caib.archium.commons.utils.Constants;
import org.primefaces.PrimeFaces;
import org.primefaces.event.CellEditEvent;
import org.primefaces.event.RowEditEvent;
import org.primefaces.event.UnselectEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import es.caib.archium.utils.FrontExceptionTranslate;

@Named("dictamenController")
@ViewScoped
public class DictamenController implements Serializable {
	

		private static final long serialVersionUID = 1L;
		protected final Logger log = LoggerFactory.getLogger(this.getClass());
		
		private String destinatariRestringits;
	    private String condicioReutilizacio;
	    private String accioDictaminada;
	    private Date inici;
	    private Date aprovacio;
	    private Date fin;
	    private String termini;
	    private String estat;
	    private String estatAnterior;
	    private String codi;
	    private Boolean serieEsencial;
		private Long id;
		private Long dictamenId;
		private Long documentalId;
		private Long tipuDictamenId;
		private Long tipuAccesId;
		private Long ensId;
		private Long lopdId;
		private Long normativaAprovacioId;
	
		private DictamenObject dictamen = new DictamenObject();
		private SerieDocumentalObject serieDocumental;
		private TipuDictamenObject tipuDictamen;
		private TipuAccesObject	tipuAcces;
		private EnsObject ens;
		private LopdObject lopd;
		private NormativaAprobacioObject normativaAprovacio;
	
		private List<DictamenObject> listaDictamen;
		private List<SerieDocumentalObject> listaDocumental;
		private List<TipuDictamenObject> listatipuDictamen;
		private List<TipuAccesObject> listaTipuAcces;
		private List<EnsObject>	listaEns;
		private List<LopdObject> listaLopd;
		private List<NormativaAprobacioObject> listaNormativaAprovacio;
		private List<String> estados = new ArrayList<>();
		
		private Document<DictamenObject> miObjeto = new Document<DictamenObject>();
		
		private List<String> plazos1 = new ArrayList<>();
		private String plazoTermini;
		private Integer	plazoTerminiVal;
		
	    @Inject
	    private DictamenFrontService serviceDictamen;	    
    
	    ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
	    
		FuncionesController funcBean = null;
		
		List<DictamenObject> listDictamenFromSerie= null;
		DictamenObject nuevoDictamenVigente = null;
		Boolean confirmacionVigent = false;
		Boolean confirmacionVigentReq = false;

		private static String UNIDAD_PLAZO_DICTAMEN_DEFAULT_MESSAGE = null;
	    
	    @PostConstruct
	    public void init(){   
	    		    
	    	UNIDAD_PLAZO_DICTAMEN_DEFAULT_MESSAGE = messageBundle.getString("general.plazos.anys");
	    	Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
			this.funcBean = (FuncionesController) viewMap.get("funciones");
	    	this.confirmacionVigent = false;
	    	this.confirmacionVigentReq = false;
	    	this.listDictamenFromSerie = null;
	    	this.nuevoDictamenVigente = null;
	    	
	    	try {
	    		this.listaDictamen = this.serviceDictamen.findAll();	    		
	    		this.listaDocumental = this.serviceDictamen.findAllDocumental();
	    		this.listatipuDictamen = this.serviceDictamen.findAllTipuDictamen();
	    		this.listaTipuAcces = this.serviceDictamen.findAllTipuAcces();
	    		this.listaEns = this.serviceDictamen.findAllEns();
	    		this.listaLopd = this.serviceDictamen.findAllLopd();
	    		this.listaNormativaAprovacio = this.serviceDictamen.findAllNormativaAprovacio();
	    		
	    		this.plazos1.add(messageBundle.getString("general.plazos.hores"));
	    		this.plazos1.add(messageBundle.getString("general.plazos.dies"));
	    		this.plazos1.add(messageBundle.getString("general.plazos.setmanes"));
	    		this.plazos1.add(messageBundle.getString("general.plazos.mesos"));
	    		this.plazos1.add(messageBundle.getString("general.plazos.anys"));

	    	} catch(I18NException e) {	
	    		log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
	    		funcBean.setError(true);
	    	}        
	    }
	    
	   
	    private void save() {    
	    	
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
							funcBean.getNodeFromFunctionId(serieDocumental.getSerieId(), "Serie", "insert", null));
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("dictamen.insert.ok")));
					
				
			} catch (I18NException eee) {
				log.error(FrontExceptionTranslate.translate(eee, funcBean.getLocale()));
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("dictamen.insert.error"), null));
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
	    	catch (I18NException e) {
	    		log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
	 			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("dictamen.update.error"), null);
	 			FacesContext.getCurrentInstance().addMessage(null, msg);	
	 		}
	    }
	    
	    public void deleteDictamen(Document<DictamenObject> d) {

	    	try {
	    		
	    		if(this.serviceDictamen.findById(d.getObject().getId()).getEstat().equals("Vigent")) {
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("dictamen.delete.vigentError"), null));
	    		} else {
	    			this.serviceDictamen.deleteDictamen(d.getId());
					TreeNode node = funcBean.getNodeFromFunctionId(d.getId(), "Dictamen", "update", d);
					node.getParent().getChildren().remove(node);
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("dictamen.delete.ok")));
	    		}
				
			} catch (I18NException e) {
				log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
				FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("dictamen.delete.error"), null));
			}
	    }
	    
	    public void cancelSaveVigentConfirm() {
	    	this.confirmacionVigent = false;	    	
	    }
	    
	    public void cancelSaveVigentConfirmReq() {
	    	this.confirmacionVigentReq = false;
	    }
	    
	    public void saveUpdate() {
	    	
	    	try {
	    		
				if(this.serviceDictamen.checkDictamenVigent(this.getId(), this.getEstat(), this.getSerieDocumental().getSerieId())==true
						&& this.confirmacionVigent==false) {
					
					this.confirmacionVigent = true;
					FacesContext.getCurrentInstance().validationFailed();
					PrimeFaces current = PrimeFaces.current();
					current.executeScript("PF('vigentConfirmDialog').show();");
					
				} else if(this.estatAnterior.equals("Vigent") 
						&& !this.estat.equals("Vigent")
						&& this.confirmacionVigentReq==false) {
					
					this.listDictamenFromSerie = this.serviceDictamen.getBySerie(serieDocumental);
					
					if(this.listDictamenFromSerie!=null && this.listDictamenFromSerie.size()==1) {
						FacesContext.getCurrentInstance().validationFailed();
						FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("dictamen.checkVigentReq.error"), null);
			 			FacesContext.getCurrentInstance().addMessage(null, msg);	
			 			this.listDictamenFromSerie = null;
					} else {
						this.confirmacionVigentReq = true;
						DictamenObject obj = new DictamenObject();
				    	obj.setId(this.getId());
						this.listDictamenFromSerie.remove(obj);
						FacesContext.getCurrentInstance().validationFailed();
						PrimeFaces current = PrimeFaces.current();
						current.executeScript("PF('vigentConfirmReqDialog').show();");
					}					
					
				} else {
					
					if(this.confirmacionVigent==true) {
						this.serviceDictamen.changeVigent2Obsolet(this.getSerieDocumental().getSerieId());
					} else if(this.confirmacionVigentReq==true) {
						this.serviceDictamen.changeEstatDictamen(this.nuevoDictamenVigente, "Vigent");
					}
				
					if (this.getId() == null) { 		
						save();	    		
					} else { 
						update();
					}	   
					
					this.confirmacionVigent = false;
					this.confirmacionVigentReq = false;
					this.listDictamenFromSerie = null;
					this.nuevoDictamenVigente = null;
				}
				
			} catch (I18NException e) {
				log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
	 			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("dictamen.checkVigent.error"), null);
	 			FacesContext.getCurrentInstance().addMessage(null, msg);	
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
	    	this.serieEsencial = null;
	    	this.normativaAprovacio = null;
	    	this.aprovacio = null;
	    	this.codi = null;
	    	this.estat = null;
	    	this.plazoTermini = UNIDAD_PLAZO_DICTAMEN_DEFAULT_MESSAGE;
	    	this.plazoTerminiVal = null;
	    	this.confirmacionVigent = false;
	    	this.confirmacionVigentReq = false;
	    	this.listDictamenFromSerie = null;
	    	this.nuevoDictamenVigente = null;
	    	this.estatAnterior = "";
	    }
	    
	    public void updateDictament(Document<DictamenObject> d) {	    	
	    	
	    	
			try {
				
				DictamenObject obj;
				obj = this.serviceDictamen.findById(d.getId());
				
				if(obj!=null) {
					this.setId(obj.getId());
			    	this.setSerieDocumental(obj.getSerieDocumental());
			    	this.setTipuDictamen(obj.getTipusdictamen());
			    	this.setAccioDictaminada(obj.getAccioDictaminada());
			    	
			    	
			    	if (obj.getTermini() != null) {
						String a 	= obj.getTermini().substring(obj.getTermini().length() - 1, obj.getTermini().length());
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
						this.plazoTerminiVal 	= Integer.parseInt(obj.getTermini().substring(0,obj.getTermini().length() - 1));
					}else{
			    		this.setPlazoTermini(UNIDAD_PLAZO_DICTAMEN_DEFAULT_MESSAGE);
					}
			    	
			    	this.setTermini(obj.getTermini());	    	
			    	this.setDestinatariRestringits(obj.getDestinatarisRestrigits());
			    	this.setInici(obj.getInici());
			    	this.setFin(obj.getFi());
			    	this.setTipuAcces(obj.getTipusAcces());
			    	this.setEns(obj.getEns());
			    	this.setLopd(obj.getLopd());
			    	this.setCondicioReutilizacio(obj.getCondicioReutilitzacio());
			    	this.setSerieEsencial(obj.getSerieEsencial());
			    	this.setNormativaAprovacio(obj.getNormativaAprovacio());
			    	this.setAprovacio(obj.getAprovacio());
			    	this.setEstat(obj.getEstat());
			    	this.setCodi(obj.getCodi());
			    	this.confirmacionVigent = false;
			    	this.confirmacionVigentReq = false;
			    	this.listDictamenFromSerie = null;
			    	this.nuevoDictamenVigente = null;
			    	this.setEstatAnterior(obj.getEstat());
			    	PrimeFaces.current().executeScript("PF('dictamenModalDialog').show()");
				} else {
		 			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("dictamen.get.error"), null);
		 			FacesContext.getCurrentInstance().addMessage(null, msg);
				}
				
			} catch (I18NException e) {
				log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
	 			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("dictamen.get.error"), null);
	 			FacesContext.getCurrentInstance().addMessage(null, msg);	
			}
	    	
			
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


		public String getEstatAnterior() {
			return estatAnterior;
		}


		public void setEstatAnterior(String estatAnterior) {
			this.estatAnterior = estatAnterior;
		}


		public FuncionesController getFuncBean() {
			return funcBean;
		}


		public void setFuncBean(FuncionesController funcBean) {
			this.funcBean = funcBean;
		}


		public List<DictamenObject> getListDictamenFromSerie() {
			return listDictamenFromSerie;
		}


		public void setListDictamenFromSerie(List<DictamenObject> listDictamenFromSerie) {
			this.listDictamenFromSerie = listDictamenFromSerie;
		}


		public DictamenObject getNuevoDictamenVigente() {
			return nuevoDictamenVigente;
		}


		public void setNuevoDictamenVigente(DictamenObject nuevoDictamenVigente) {
			this.nuevoDictamenVigente = nuevoDictamenVigente;
		}


		public Boolean getConfirmacionVigent() {
			return confirmacionVigent;
		}


		public void setConfirmacionVigent(Boolean confirmacionVigent) {
			this.confirmacionVigent = confirmacionVigent;
		}


		public Boolean getConfirmacionVigentReq() {
			return confirmacionVigentReq;
		}


		public void setConfirmacionVigentReq(Boolean confirmacionVigentReq) {
			this.confirmacionVigentReq = confirmacionVigentReq;
		}

		
		

	}
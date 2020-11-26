package es.caib.archium.controllers;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import org.primefaces.PrimeFaces;
import org.primefaces.event.SelectEvent;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.AplicacioObject;
import es.caib.archium.objects.Dir3Object;
import es.caib.archium.objects.Document;
import es.caib.archium.objects.FamiliaprocedimentObject;
import es.caib.archium.objects.FormainiciObject;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.LimitacioNormativaSerieObject;
import es.caib.archium.objects.MateriaObject;
import es.caib.archium.objects.NivellelectronicObject;
import es.caib.archium.objects.NormativaObject;
import es.caib.archium.objects.ProcedimentObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.objects.SilenciObject;
import es.caib.archium.objects.TipuDocumentalObject;
import es.caib.archium.objects.TipuDocumentalProcedimentObject;
import es.caib.archium.objects.tipusPublicObject;
import es.caib.archium.persistence.model.LimitacioNormativaSerie;
import es.caib.archium.persistence.model.Procediment;
import es.caib.archium.services.ProcedimentFrontService;
import es.caib.archium.utils.FrontExceptionTranslate;

@Named("procedimentController")
@ViewScoped
public class ProcedimentController implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7143141088336287019L;
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	Locale locale;
	private Long 			id;
	private String 			codisia;
	private String 			nom;
	private String 			objecte;
	//private EstatObject   Estat;  //En el futuroo se añadira esta tabla y tendra que transformarse
	private String 			estat;
	private String 			destinataris;
	private String 			observacions;
	private String 			uri;
	
	private FamiliaprocedimentObject 		familiaprocediment;
	private FormainiciObject 				formainici;
	private SilenciObject 					silenci;
	private NivellelectronicObject 			nivellelectronic;
	private SerieDocumentalObject 			seriedocumental;
	private AplicacioObject				 	aplicacio;
	private TipuDocumentalObject			selectedTipoDocumental;
	
	private String 			codirolsac; 			
	private String 			termine;
	private String 			termininotif;
	private Boolean			fiViaAdministrativa;
	private Boolean			taxa;
	private String 			dir3Resolvent;
	private String 			dir3Instructor;
	private Date 			publicacio;
	private Date 			caducitat;
	private Date 			modificacio;	
	private String 			gestor;
	private ProcedimentObject		modify;
	

	@Inject
	private ProcedimentFrontService 			serviceProcediment;	    
	private List<Dir3Object> listaDir3;
	private List<ProcedimentObject> 		listaProcediment 		= 	new ArrayList<>();
	private List<ProcedimentObject> 		listaFilter				= 	new ArrayList<>();
	private List<FamiliaprocedimentObject> 	listaFamiliaprocediment = 	new ArrayList<>();
	private List<FormainiciObject> 			listaFormainici 		= 	new ArrayList<>();
	private List<SilenciObject> 			listaSilenci 			= 	new ArrayList<>();
	private List<NivellelectronicObject> 	listaNivellelectronic	= 	new ArrayList<>();
	private List<SerieDocumentalObject> 	listaSerieDocumental 	= 	new ArrayList<>();
	private List<AplicacioObject> 			listaAplicacio			= 	new ArrayList<>();
	private List<SerieDocumentalObject>     seriesRelacionadas		= 	new ArrayList<>();
	
	private List<NormativaObject>			listaNormativa 			= 	new ArrayList<>();
	private List<MateriaObject>				listaMateria 			= 	new ArrayList<>();
	private List<tipusPublicObject>			listaTipusPublic 		= 	new ArrayList<>();
	private List<TipuDocumentalObject>		listatipoDocumental		= 	new ArrayList<>();
	
	private List<tipusPublicObject>				listaTipusPublicSelected		= 	new ArrayList<>();
	private List<MateriaObject>					materiaSelected			= 	new ArrayList<>();
	
	private List<NormativaObject>				normativaSelected		= 	new ArrayList<>();
	private List<TipuDocumentalProcedimentObject> listaTDP = new ArrayList<TipuDocumentalProcedimentObject>();
	private List<String>						estados		= 	new ArrayList<>();
	private List<String>						plazos1		= 	new ArrayList<>();
	private List<String>						plazos2		= 	new ArrayList<>();
	
	private DualListModel<MateriaObject>					materiaRelacionada;
	private DualListModel<NormativaObject>					normativaRelacionada;
	private DualListModel<tipusPublicObject>				tipusPublicRelacionada;
	
	private Integer 	plazo1;
	private Integer 	plazo2;
	private String 		plazo1a;
	private String 		plazo2a;
	
	private ProcedimentObject procedimientoSelected;
	
	private Boolean error = false;
	
	ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");

	@PostConstruct
	public void init(){   
		
		listaTipusPublicSelected =	new ArrayList<>();
		materiaSelected	= new ArrayList<>();
		normativaSelected = new ArrayList<>();
		
		FacesContext context = FacesContext.getCurrentInstance();	
	    locale = context.getViewRoot().getLocale();
		
		try 
		{
			listaDir3 = serviceProcediment.getListaDir3();
	    	this.listaProcediment 			= 	this.serviceProcediment.findAllProcedimiento();	    		    				    
    		this.listaFamiliaprocediment 	=	this.serviceProcediment.findAllFamiliaprocediment();
    		this.listaFormainici 			= 	this.serviceProcediment.findAllFormainici();
    		this.listaSilenci 				=	this.serviceProcediment.findAllSilenci();
    		this.listaNivellelectronic		=	this.serviceProcediment.findAllNivellelectronic();
    		this.listaSerieDocumental 		=	this.serviceProcediment.findAllSeriedocumental();
    		this.listaAplicacio				=	this.serviceProcediment.findAllAplicacio();
    		this.listaNormativa				= 	this.serviceProcediment.findAllNormativa();
    		this.listaMateria				= 	this.serviceProcediment.findAllMateria();
    		this.listaTipusPublic			=	this.serviceProcediment.findAllTipusPublic();
    		this.listatipoDocumental		= 	this.serviceProcediment.findAllTipoDocumental();
    		
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

    		this.plazos2.add(messageBundle.getString("general.plazos.hores"));
    		this.plazos2.add(messageBundle.getString("general.plazos.dies"));
    		this.plazos2.add(messageBundle.getString("general.plazos.setmanes"));
    		this.plazos2.add(messageBundle.getString("general.plazos.mesos"));
    		this.plazos2.add(messageBundle.getString("general.plazos.anys"));
    		
    		this.listaFilter = this.listaProcediment;
    	} catch(I18NException e) {	
    		log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
    		error = true;
    	} finally {
    		this.materiaRelacionada 		= 	new DualListModel<MateriaObject>(listaMateria,materiaSelected);
    		this.normativaRelacionada		= 	new DualListModel<NormativaObject>(listaNormativa,normativaSelected);
    		this.tipusPublicRelacionada		= 	new DualListModel<tipusPublicObject>(listaTipusPublic,listaTipusPublicSelected);
    	}
    }
	
	private void inicializarUpdate(ProcedimentObject obj) throws I18NException{
		
		
		ProcedimentObject p;

		p = this.serviceProcediment.findById(obj.getId());
		
		
		if(p!=null) {
			this.modify = p;
			this.id 			= p.getId();
			this.codisia 		= p.getCodisia();
			this.nom			= p.getNom();
			this.objecte 		= p.getObjecte();
			//private EstatObject   Estat;  //En el futuroo se añadira esta tabla y tendra que transformarse
			this.estat			= p.getEstat();
			this.destinataris	= p.getDestinataris();
			this.observacions	= p.getObservacions();
			this.uri			= p.getUri();
			
			this.familiaprocediment = p.getFamiliaprocediment();
			this.formainici 		= p.getFormainici();
			this.silenci			= p.getSilenci();
			this.nivellelectronic 	= p.getNivellelectronic();
			this.seriedocumental	= p.getSeriedocumental();
			this.aplicacio			= p.getAplicacio();
			this.termine  			= p.getTermine();
			this.termininotif		= p.getTermininotif();
			

			if (this.termine != null) {
				String a 	= this.termine.substring(this.termine.length() - 1, this.termine.length());
				if (a.equals("H"))
					this.setPlazo1a(messageBundle.getString("general.plazos.hores"));
				if (a.equals("D"))
					this.setPlazo1a(messageBundle.getString("general.plazos.dies"));
				if (a.equals("S"))
					this.setPlazo1a(messageBundle.getString("general.plazos.setmanes"));
				if (a.equals("M"))
					this.setPlazo1a(messageBundle.getString("general.plazos.mesos"));
				if (a.equals("A"))
					this.setPlazo1a(messageBundle.getString("general.plazos.anys"));
				this.plazo1 	= Integer.parseInt(this.termine.substring(0,this.termine.length() - 1));
			}
			else {
				this.plazo1a 	= null;
				this.plazo1 	= null;
			}
			if (this.termininotif != null) {
				this.plazo2 	= Integer.parseInt(this.termininotif.substring(0,this.termininotif.length() - 1));
				String a 	= this.termininotif.substring(this.termininotif.length() - 1, this.termininotif.length());				 	
				if (a.equals("H"))
					this.setPlazo2a(messageBundle.getString("general.plazos.hores"));
				if (a.equals("D"))
					this.setPlazo2a(messageBundle.getString("general.plazos.dies"));
				if (a.equals("S"))
					this.setPlazo2a(messageBundle.getString("general.plazos.setmanes"));
				if (a.equals("M"))
					this.setPlazo2a(messageBundle.getString("general.plazos.mesos"));
				if (a.equals("A"))
					this.setPlazo2a(messageBundle.getString("general.plazos.anys"));
			}
			else {
				this.plazo2a 	= null;
				this.plazo2 	= null;
			}
		
		
			this.codirolsac 		= p.getCodirolsac(); 	
			this.termine  			= p.getTermine();
			this.termininotif		= p.getTermininotif();
			this.fiViaAdministrativa = p.getFiViaAdministrativa();
			this.taxa				= p.getTaxa();
			this.dir3Resolvent 		= p.getDir3Resolvent();
			this.dir3Instructor 	= p.getDir3Instructor();
			this.publicacio 		= p.getPublicacio();
			this.caducitat			= p.getCaducitat();
			this.modificacio		= p.getModificacio();	
			this.gestor 			= p.getGestor();
		
		
			if (p.getListaTipuDocumental() == null){
				p.setListaTipuDocumental(this.serviceProcediment.listaTipo(p.getListaTipuDocumentalProcedimiento()));
			}	
			
			if (p.getListaMateria()!= null) { 
				this.materiaRelacionada.setTarget(new ArrayList<> (p.getListaMateria()));
				List<MateriaObject> filterList = new ArrayList<>(listaMateria);
				if (obj.getListaMateria() != null){
					filterList.removeAll(p.getListaMateria());
				}
				this.materiaRelacionada.setSource(filterList);
			} else {
				this.materiaRelacionada.setTarget(new ArrayList<> ());
				this.materiaRelacionada.setSource(new ArrayList<> (listaMateria));
			}
			
			if(p.getListaTipuPublic() != null) {
				this.tipusPublicRelacionada.setTarget(new ArrayList<> (p.getListaTipuPublic()));
				List<tipusPublicObject> filterList4 = new ArrayList<>(listaTipusPublic);
				if (p.getListaTipuPublic()!= null) {
					filterList4.removeAll(p.getListaTipuPublic());
				}
				this.tipusPublicRelacionada.setSource(filterList4);
			} else {
				this.tipusPublicRelacionada.setTarget(new ArrayList<>());
				this.tipusPublicRelacionada.setSource(new ArrayList<>(listaTipusPublic));
			}
			
			if (p.getListaNormativa() != null) {
				this.normativaRelacionada.setTarget(new ArrayList<> (p.getListaNormativa()));
				List<NormativaObject> filterList3 = new ArrayList<>(listaNormativa);
				if (p.getListaNormativa()!= null) {
					filterList3.removeAll(p.getListaNormativa());
				}
				this.normativaRelacionada.setSource(filterList3);			
			} else {
				this.normativaRelacionada.setTarget(new ArrayList<> ());
				this.normativaRelacionada.setSource(new ArrayList<> (listaNormativa));
			}
			
			listaTDP = serviceProcediment.getListaTDP(id);
			
		} else {
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("procediment.update.abrirUpdate.error"), messageBundle.getString("procediment.update.abrirUpdate.error"));
		 	FacesContext.getCurrentInstance().addMessage("message-global", msg);
		}
	}
	
	void inicializarSave(){
		
		this.id 		= null;
		this.codisia 	= null;
		this.nom		= null;
		this.objecte 	= null;
		//private EstatObject   Estat;  //En el futuroo se añadira esta tabla y tendra que transformarse
		this.estat		= null;
		this.destinataris	= null;
		this.observacions	= null;
		this.uri			= null;
		
		this.familiaprocediment = new FamiliaprocedimentObject();
		this.formainici 		= new FormainiciObject();
		this.silenci			= new SilenciObject();
		this.nivellelectronic 	= new NivellelectronicObject();
		this.seriedocumental	= new SerieDocumentalObject();
		this.aplicacio			= new AplicacioObject();
		
		this.codirolsac 	= null; 
		this.plazo1 		= null;
		this.plazo1a 		= null;
		this.plazo2 		= null;
		this.plazo2a 		= null;
		this.termine  		= null;
		this.termininotif	= null;
		this.fiViaAdministrativa = null;
		this.taxa			= null;
		this.dir3Resolvent 	= null;
		this.dir3Instructor = null;
		this.publicacio 	= null;
		this.caducitat		= null;
		this.modificacio	= null;
		this.gestor 		= null;

		this.materiaRelacionada.setSource(this.listaMateria);
		this.tipusPublicRelacionada.setSource(this.listaTipusPublic);
		this.normativaRelacionada.setSource(this.listaNormativa);
		this.materiaRelacionada.setTarget(new ArrayList<>());
		this.normativaRelacionada.setTarget(new ArrayList<>());
		this.tipusPublicRelacionada.setTarget(new ArrayList<>());	
		this.selectedTipoDocumental = null;
		this.listaTDP = new ArrayList<>();
	}
	
 	public void abrirModal(){
		inicializarSave();
	}
 	
 	public void onRowSelect(SelectEvent event) {
 		
 		try {
			inicializarUpdate(procedimientoSelected);
			PrimeFaces.current().executeScript("PF('procedimentModalDialog').show()");
		} catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("procediment.update.abrirUpdate.error"), messageBundle.getString("procediment.update.abrirUpdate.error"));
		 	FacesContext.getCurrentInstance().addMessage("message-global", msg);
		}
 		
    }
	
	
  
	private ProcedimentObject objetoPersistir() {
		if (this.plazo1 != null) {
			this.setTermine(this.plazo1.toString().concat(this.plazo1a.substring(0,1)));
		}
		if (this.plazo2 != null) {
			this.setTermininotif(this.plazo2.toString().concat(this.plazo2a.substring(0,1)));
		}
		ProcedimentObject objeto = new ProcedimentObject(this.codisia, this.nom, this.objecte, this.estat, this.destinataris, this.observacions, this.uri, 
				this.familiaprocediment, this.formainici, this.silenci, this.nivellelectronic, this.seriedocumental, this.aplicacio, this.codirolsac, this.termine, this.termininotif, 
				this.fiViaAdministrativa, this.taxa, this.dir3Resolvent, this.dir3Instructor, this.publicacio, this.caducitat, this.modificacio, this.gestor);		
		return objeto;
	}
	
	public void deleteProcediment(ProcedimentObject p) {

    	try {
			this.serviceProcediment.deleteProcediment(p.getId());
			FacesContext.getCurrentInstance().addMessage("message-global", new FacesMessage(messageBundle.getString("procediment.delete.ok")));
			this.listaProcediment.remove(p);
			this.listaFilter.remove(p);
		} catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
			FacesContext.getCurrentInstance().addMessage("message-global", new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("procediment.delete.error"), null));
		}
    }
	
	public void save() {    
		try {		
			this.materiaSelected 			= materiaRelacionada.getTarget();			
			this.normativaSelected 			= normativaRelacionada.getTarget();
			this.listaTipusPublicSelected   = tipusPublicRelacionada.getTarget();
			
			this.serviceProcediment.create(objetoPersistir(), this.materiaSelected, this.listaTipusPublicSelected, this.normativaSelected, listaTDP);
			
			FacesContext.getCurrentInstance().addMessage("message-global", new FacesMessage(messageBundle.getString("procediment.insert.ok")));
			this.listaProcediment = this.serviceProcediment.findAllProcedimiento();	    	
			this.listaFilter = this.listaProcediment;
		} catch (I18NException eee) {
			log.error(FrontExceptionTranslate.translate(eee, this.getLocale()));
			FacesMessage message = new FacesMessage();
    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
    		message.setSummary(messageBundle.getString("procediment.insert.error"));
    		message.setDetail(messageBundle.getString("procediment.insert.error"));
    		FacesContext.getCurrentInstance().addMessage("message-global", message);
		}			
	}
		    
    public void update(ProcedimentObject objeto) {
		try {
			this.materiaSelected 			= this.materiaRelacionada.getTarget();			
			this.normativaSelected 			= this.normativaRelacionada.getTarget();
			this.listaTipusPublicSelected   = this.tipusPublicRelacionada.getTarget();
		    this.serviceProcediment.update(objeto, this.materiaSelected, this.listaTipusPublicSelected, this.normativaSelected, listaTDP);

			FacesMessage msg = new FacesMessage(messageBundle.getString("procediment.update.ok"));
			this.listaProcediment = this.serviceProcediment.findAllProcedimiento();	
			this.listaFilter = this.listaProcediment;
			FacesContext.getCurrentInstance().addMessage("message-global", msg);
		}
		catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
			FacesMessage message = new FacesMessage();
    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
    		message.setSummary(messageBundle.getString("procediment.update.error"));
    		message.setDetail(messageBundle.getString("procediment.update.error"));
    		FacesContext.getCurrentInstance().addMessage("message-global", message);
		}
	}
		    
	public void saveUpdate() {
	  	if (this.id == null) { //Grabar	    		
	  		save();	    		
	   	}
	   	else { //update
	    	ProcedimentObject obj = new ProcedimentObject(objetoPersistir());
	   		obj.setId(this.getId());
	   		obj.setModificacio(new Date());
	  		update(obj);
	  	}
		inicializarSave();
	}
	
	
	public void onTipoDocumentalSelectedListener(AjaxBehaviorEvent event){
    			
		if(this.validateTDP()) {
			TipuDocumentalProcedimentObject tdp = new TipuDocumentalProcedimentObject();
			tdp.setTipusDocumental(selectedTipoDocumental);
			if(listaTDP.size()==0) {
				tdp.setId(0);
			} else {
				int lastid = listaTDP.get(listaTDP.size()-1).getId();
				tdp.setId(lastid+1);
			}
			
			listaTDP.add(tdp);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevoprocediment.validate.duplicateTDP"), null);
			FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
		}
		
		selectedTipoDocumental = null;
		
	}
	
	public void deleteTDP(TipuDocumentalProcedimentObject tdpitem) {
		int index = listaTDP.indexOf(tdpitem);
		if(index>=0) {
			listaTDP.remove(index);
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageBundle.getString("procediment.tdp.delete.ok"), null);
			FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
		} else {
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("procediment.tdp.delete.error"), null);
			FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
		}
	}
	
	public void saveTDP(TipuDocumentalProcedimentObject tdpitem) {

		int index = listaTDP.indexOf(tdpitem);
		if(id!=null) tdpitem.setProcediment(modify);
		if(index>=0) {
			listaTDP.set(index, tdpitem);
		} else {
			listaTDP.add(tdpitem);
		}	
		FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageBundle.getString("procediment.tdp.save.ok"), null);
		FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
	}
	
	private Boolean validateTDP() {
		
		if(listaTDP==null) return true;
		
		boolean duplicado = false;
		Iterator<TipuDocumentalProcedimentObject> it = listaTDP.iterator();
		
		while(it.hasNext() && duplicado==false) {
			TipuDocumentalProcedimentObject item = it.next();
			if(selectedTipoDocumental.getId()==item.getTipusDocumental().getId()) {
				duplicado=true;
			}
		}
		
		return !duplicado;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getCodisia() {
		return codisia;
	}

	public void setCodisia(String codisia) {
		this.codisia = codisia;
	}

	public String getNom() {
		return nom;
	}

	public void setNom(String nom) {
		this.nom = nom;
	}

	public String getObjecte() {
		return objecte;
	}

	public void setObjecte(String objecte) {
		this.objecte = objecte;
	}

	public String getEstat() {
		return estat;
	}

	public void setEstat(String estat) {
		this.estat = estat;
	}

	public String getDestinataris() {
		return destinataris;
	}

	public void setDestinataris(String destinataris) {
		this.destinataris = destinataris;
	}

	public String getObservacions() {
		return observacions;
	}

	public void setObservacions(String observacions) {
		this.observacions = observacions;
	}

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public FamiliaprocedimentObject getFamiliaprocediment() {
		return familiaprocediment;
	}

	public void setFamiliaprocediment(FamiliaprocedimentObject familiaprocediment) {
		this.familiaprocediment = familiaprocediment;
	}

	public FormainiciObject getFormainici() {
		return formainici;
	}

	public void setFormainici(FormainiciObject formainici) {
		this.formainici = formainici;
	}

	public SilenciObject getSilenci() {
		return silenci;
	}

	public void setSilenci(SilenciObject silenci) {
		this.silenci = silenci;
	}

	public NivellelectronicObject getNivellelectronic() {
		return nivellelectronic;
	}

	public void setNivellelectronic(NivellelectronicObject nivellelectronic) {
		this.nivellelectronic = nivellelectronic;
	}

	public SerieDocumentalObject getSeriedocumental() {
		return seriedocumental;
	}

	public void setSeriedocumental(SerieDocumentalObject seriedocumental) {
		this.seriedocumental = seriedocumental;
	}

	public AplicacioObject getAplicacio() {
		return aplicacio;
	}

	public void setAplicacio(AplicacioObject aplicacio) {
		this.aplicacio = aplicacio;
	}

	public String getCodirolsac() {
		return codirolsac;
	}

	public void setCodirolsac(String codirolsac) {
		this.codirolsac = codirolsac;
	}

	public String getTermine() {
		return termine;
	}

	public void setTermine(String termine) {
		this.termine = termine;
	}

	public String getTermininotif() {
		return termininotif;
	}

	public void setTermininotif(String termininotif) {
		this.termininotif = termininotif;
	}

	public Boolean getFiViaAdministrativa() {
		return fiViaAdministrativa;
	}

	public void setFiViaAdministrativa(Boolean fiViaAdministrativa) {
		this.fiViaAdministrativa = fiViaAdministrativa;
	}

	public Boolean getTaxa() {
		return taxa;
	}

	public void setTaxa(Boolean taxa) {
		this.taxa = taxa;
	}

	public String getDir3Resolvent() {
		return dir3Resolvent;
	}

	public void setDir3Resolvent(String dir3Resolvent) {
		this.dir3Resolvent = dir3Resolvent;
	}

	public String getDir3Instructor() {
		return dir3Instructor;
	}

	public void setDir3Instructor(String dir3Instructor) {
		this.dir3Instructor = dir3Instructor;
	}

	public Date getPublicacio() {
		return publicacio;
	}

	public void setPublicacio(Date publicacio) {
		this.publicacio = publicacio;
	}

	public Date getCaducitat() {
		return caducitat;
	}

	public void setCaducitat(Date caducitat) {
		this.caducitat = caducitat;
	}

	public Date getModificacio() {
		return modificacio;
	}
	public void setModificacio(Date modificacio) {
		this.modificacio = modificacio;
	}
	public String getGestor() {
		return gestor;
	}

	public void setGestor(String gestor) {
		this.gestor = gestor;
	}

	public ProcedimentFrontService getServiceProcediment() {
		return serviceProcediment;
	}

	public void setServiceProcediment(ProcedimentFrontService serviceProcediment) {
		this.serviceProcediment = serviceProcediment;
	}

	public List<ProcedimentObject> getListaProcediment() {
		return listaProcediment;
	}

	public void setListaProcediment(List<ProcedimentObject> listaProcediment) {
		this.listaProcediment = listaProcediment;
	}

	public List<FamiliaprocedimentObject> getListaFamiliaprocediment() {
		return listaFamiliaprocediment;
	}

	public void setListaFamiliaprocediment(List<FamiliaprocedimentObject> listaFamiliaprocediment) {
		this.listaFamiliaprocediment = listaFamiliaprocediment;
	}

	public List<FormainiciObject> getListaFormainici() {
		return listaFormainici;
	}

	public void setListaFormainici(List<FormainiciObject> listaFormainici) {
		this.listaFormainici = listaFormainici;
	}

	public List<SilenciObject> getListaSilenci() {
		return listaSilenci;
	}

	public void setListaSilenci(List<SilenciObject> listaSilenci) {
		this.listaSilenci = listaSilenci;
	}

	public List<NivellelectronicObject> getListaNivellelectronic() {
		return listaNivellelectronic;
	}

	public void setListaNivellelectronic(List<NivellelectronicObject> listaNivellelectronic) {
		this.listaNivellelectronic = listaNivellelectronic;
	}

	public List<SerieDocumentalObject> getListaSerieDocumental() {
		return listaSerieDocumental;
	}

	public void setListaSerieDocumental(List<SerieDocumentalObject> listaSerieDocumental) {
		this.listaSerieDocumental = listaSerieDocumental;
	}

	public List<AplicacioObject> getListaAplicacio() {
		return listaAplicacio;
	}

	public void setListaAplicacio(List<AplicacioObject> listaAplicacio) {
		this.listaAplicacio = listaAplicacio;
	}
	public List<SerieDocumentalObject> getSeriesRelacionadas() {
		return seriesRelacionadas;
	}
	public void setSeriesRelacionadas(List<SerieDocumentalObject> seriesRelacionadas) {
		this.seriesRelacionadas = seriesRelacionadas;
	}
	public List<NormativaObject> getListaNormativa() {
		return listaNormativa;
	}
	public void setListaNormativa(List<NormativaObject> listaNormativa) {
		this.listaNormativa = listaNormativa;
	}
	public List<MateriaObject> getListaMateria() {
		return listaMateria;
	}
	public void setListaMateria(List<MateriaObject> listaMateria) {
		this.listaMateria = listaMateria;
	}
	
	public List<MateriaObject> getMateriaSelected() {
		return materiaSelected;
	}
	public void setMateriaSelected(List<MateriaObject> materiaSelected) {
		this.materiaSelected = materiaSelected;
	}
	
	public List<NormativaObject> getNormativaSelected() {
		return normativaSelected;
	}
	public void setNormativaSelected(List<NormativaObject> normativaSelected) {
		this.normativaSelected = normativaSelected;
	}
	public DualListModel<MateriaObject> getMateriaRelacionada() {
		return materiaRelacionada;
	}
	public void setMateriaRelacionada(DualListModel<MateriaObject> materiaRelacionada) {
		this.materiaRelacionada = materiaRelacionada;
	}
	
	public DualListModel<NormativaObject> getNormativaRelacionada() {
		return normativaRelacionada;
	}
	public void setNormativaRelacionada(DualListModel<NormativaObject> normativaRelacionada) {
		this.normativaRelacionada = normativaRelacionada;
	}

	public List<TipuDocumentalObject> getListatipoDocumental() {
		return listatipoDocumental;
	}

	public void setListatipoDocumental(List<TipuDocumentalObject> listatipoDocumental) {
		this.listatipoDocumental = listatipoDocumental;
	}

	

	public List<String> getEstados() {
		return estados;
	}

	public void setEstados(List<String> estados) {
		this.estados = estados;
	}

	public List<tipusPublicObject> getListaTipusPublic() {
		return listaTipusPublic;
	}

	public void setListaTipusPublic(List<tipusPublicObject> listaTipusPublic) {
		this.listaTipusPublic = listaTipusPublic;
	}

	public List<tipusPublicObject> getListaTipusPublicSelected() {
		return listaTipusPublicSelected;
	}

	public void setListaTipusPublicSelected(List<tipusPublicObject> listaTipusPublicSelected) {
		this.listaTipusPublicSelected = listaTipusPublicSelected;
	}

	public DualListModel<tipusPublicObject> getTipusPublicRelacionada() {
		return tipusPublicRelacionada;
	}

	public void setTipusPublicRelacionada(DualListModel<tipusPublicObject> tipusPublicRelacionada) {
		this.tipusPublicRelacionada = tipusPublicRelacionada;
	}

	public List<String> getPlazos1() {
		return plazos1;
	}

	public void setPlazos1(List<String> plazos) {
		this.plazos1 = plazos;
	}

	public List<String> getPlazos2() {
		return plazos2;
	}

	public void setPlazos2(List<String> plazos) {
		this.plazos2 = plazos;
	}
	
	public Integer getPlazo1() {
		return plazo1;
	}

	public void setPlazo1(Integer plazo1) {
		this.plazo1 = plazo1;
	}

	public Integer getPlazo2() {
		return plazo2;
	}

	public void setPlazo2(Integer plazo2) {
		this.plazo2 = plazo2;
	}

	public String getPlazo1a() {
		return plazo1a;
	}

	public void setPlazo1a(String plazo1a) {
		this.plazo1a = plazo1a;
	}

	public String getPlazo2a() {
		return plazo2a;
	}

	public void setPlazo2a(String plazo2a) {
		this.plazo2a = plazo2a;
	}

	public TipuDocumentalObject getSelectedTipoDocumental() {
		return selectedTipoDocumental;
	}

	public void setSelectedTipoDocumental(TipuDocumentalObject selectedTipoDocumental) {
		this.selectedTipoDocumental = selectedTipoDocumental;
	}

	public List<TipuDocumentalProcedimentObject> getListaTDP() {
		return listaTDP;
	}

	public void setListaTDP(List<TipuDocumentalProcedimentObject> listaTDP) {
		this.listaTDP = listaTDP;
	}

	public ProcedimentObject getModify() {
		return modify;
	}

	public void setModify(ProcedimentObject modify) {
		this.modify = modify;
	}

	public List<ProcedimentObject> getListaFilter() {
		return listaFilter;
	}

	public void setListaFilter(List<ProcedimentObject> listaFilter) {
		this.listaFilter = listaFilter;
	}

	public ProcedimentObject getProcedimientoSelected() {
		return procedimientoSelected;
	}

	public void setProcedimientoSelected(ProcedimentObject procedimientoSelected) {
		this.procedimientoSelected = procedimientoSelected;
	}

	public List<Dir3Object> getListaDir3() {
		return listaDir3;
	}

	public void setListaDir3(List<Dir3Object> listaDir3) {
		this.listaDir3 = listaDir3;
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

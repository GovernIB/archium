package es.caib.archium.controllers;

/* https://www.primefaces.org/showcase/ui/overlay/dialog/loginDemo.xhtml 
 * https://www.journaldev.com/4056/primefaces-utilities-requestcontext-el-functions-dialog-framework-search-expression-framework#primefaces-dialog-framework-8211-data-communication*/

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.PrimeFaces;
import org.primefaces.event.NodeCollapseEvent;
import org.primefaces.event.NodeExpandEvent;
import org.primefaces.event.NodeSelectEvent;
import org.primefaces.event.NodeUnselectEvent;
import org.primefaces.event.data.PageEvent;
import org.primefaces.event.data.SortEvent;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.TreeNode;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.interceptor.Logged;
import es.caib.archium.objects.DictamenObject;
import es.caib.archium.objects.Document;
import es.caib.archium.objects.EnsObject;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.LopdObject;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.objects.TipuAccesObject;
import es.caib.archium.objects.TipuDictamenObject;
import es.caib.archium.objects.TipusSerieObject;
import es.caib.archium.services.FuncioService;
import es.caib.archium.services.QuadreServices;

@Logged
@Named("funciones")
@ViewScoped
public class FuncionesController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	@Inject
    private FuncioService servicesFunciones;
	@Inject
    private QuadreServices servicesCuadroClasificacion;
	
	
	private FuncioObject nuevaFuncion;
	private String nuevoCodi;
	private String nuevoNom;
	private String nuevoNomCas;
	private QuadreObject nuevoQuadre;
	private FuncioObject nuevoFuncioPare;
	private TipusSerieObject nuevoTipusserie;
	private String nuevoEstat;
	private BigDecimal nuevoOrdre;
	private FuncioObject funcioPare;
	
    private List<FuncioObject> listaFunciones;
    private List<QuadreObject> listaCuadrosClasificacion = new ArrayList<QuadreObject>();
    private List<TipusSerieObject> listaTipusserie;
    
    private TreeNode nodoSeleccionado = null;
    private FuncioObject funcionSeleccionada = null;
    private QuadreObject cuadroSeleccionado = null;
    private TipusSerieObject tipusserieSeleccionado = new TipusSerieObject();
    private FuncioObject funcionActualizar = null;
    
    private TreeNode root;
    
    private TreeNode selectedNode;
    private TreeNode filterNode;
    private Map<String, String> infoSelectedElement = new LinkedHashMap<String, String>();

    private  List<String> listaEstats = new ArrayList<>();
    
    private Long quadreId;
    

	@PostConstruct
    public void init() throws I18NException {   
    	try 
    	{
    		listaEstats.add("Esborrany"); 
    		listaEstats.add("Revisat");
    		listaEstats.add("Publicable");
    		listaEstats.add("Vigent");
    		listaEstats.add("Obsolet");
    		
    		this.listaCuadrosClasificacion = this.servicesCuadroClasificacion.findAll();
    		
    		FacesContext context = FacesContext.getCurrentInstance();
    	    String quadreIdString = context.getExternalContext().getRequestParameterMap().get("quadreId");
    		
    	    if(quadreIdString!=null) {
    	    	quadreId = Long.parseLong(quadreIdString);
    	    }
    	    
    		if(quadreId==null) {
    			if(listaCuadrosClasificacion.size()>=0) {
    				quadreId = listaCuadrosClasificacion.get(0).getId();
    			}
    		}
    		
    		if(quadreId==null) {
    			
    		} else {
    			this.cuadroSeleccionado = this.servicesCuadroClasificacion.getQuadreById(quadreId);
        		this.root = this.servicesFunciones.getContent(cuadroSeleccionado);
        		this.filterNode = this.root.getParent();
        		this.listaFunciones = this.servicesFunciones.findAllByQuadre(this.cuadroSeleccionado);
        		
        		this.listaTipusserie = this.servicesFunciones.findAllTipusSerie();    	
    		}
    		
    			
    	}
    	catch(Exception e) 
    	{	
    		System.out.println("fallo al  Init controler funcion");
    		System.err.println(e);
    	}        
    }
    
    public void changeQuadre(QuadreObject quadre) {
    	this.clearSelection();
    	this.cuadroSeleccionado = quadre;
    	try {
			this.root = this.servicesFunciones.getContent(cuadroSeleccionado);	
			this.filterNode = this.root.getParent();
		} catch (I18NException e) {
			e.printStackTrace();
		}
    }
    
    public void setFuncioActualizar(Document<FuncioObject> d) {
    	this.clearSelection();
    	this.clearForm();
    	if (d==null) {
    		this.funcionActualizar = null;
    	} else {
    		this.funcionActualizar = d.getObject();
        	this.nuevoCodi = d.getObject().getCodi();
        	this.nuevoEstat = d.getObject().getEstat();
        	this.nuevoNom = d.getObject().getNom();
        	this.nuevoNomCas = d.getObject().getNomcas();
        	this.nuevoOrdre = d.getObject().getOrdre();
        	this.nuevoTipusserie = d.getObject().getTipoSerie();
        	this.funcionSeleccionada = d.getObject().getFuncioPare();
    		this.funcioPare = d.getObject().getFuncioPare();

    	}    	
    }
    
    public void setFuncioSeleccionada(Document<FuncioObject> d) {
    	this.clearSelection();
    	clearForm();
    	this.funcionActualizar = null;
    	
    	if (d==null) {
    		this.funcionSeleccionada = null;
    	} else {
    		this.funcionSeleccionada = d.getObject();
    		this.funcioPare = d.getObject();
    		System.out.println("FUNCION CONTROLLER (Seleccionada): Funcio: " + funcioPare.toString());
    	}    	
    }
    
    public void update() {    
    	System.out.println("Salvar dato");
		
		try {
			//TreeNode oldParent = this.getNodeFromFunctionId(funcionActualizar.getFuncioPare() == null ? null:  funcionActualizar.getFuncioPare().getId(), "Funcio", "insert", null);
			FuncioObject f = this.funcionActualizar;
			TreeNode oldParent = null;
			if(f.getFuncioPare() != null  )
			{
				oldParent = this.getNodeFromFunctionId(funcionActualizar.getFuncioPare().getId(), "Funcio", "insert", null); 
			}
			
			f.setCodi(this.nuevoCodi);
			f.setNom(this.nuevoNom);
			f.setNomcas(this.nuevoNomCas!=null ? this.nuevoNomCas: null);
			f.setEstat(this.nuevoEstat);
			f.setOrdre(this.nuevoOrdre);
			f.setInici(new Date());
			f.setModificacio(new Date());
			f.setFi(null);
			f.setTipoSerie(nuevoTipusserie);
			f.setFuncioPare(funcioPare);
			FuncioObject upF = this.servicesFunciones.update(f);

			this.listaFunciones = this.servicesFunciones.findAllByQuadre(this.cuadroSeleccionado);
			
			
			if(oldParent != null)
			{
				TreeNode node = this.getNodeFromFunctionId(upF.getId(), "Funcio", "update", f);
				oldParent.getChildren().remove(node);
				TreeNode parentNode;
				if(funcioPare != null)
				{
					parentNode = getNodeFromFunctionId(this.funcioPare.getId(), "Funcio", "insert", null);
				}
				else
				{
					parentNode = root;
				}
				node.setParent(parentNode);
				parentNode.getChildren().add(node);
			}
			else
			{
				TreeNode node = this.getNodeFromFunctionId(upF.getId(), "Funcio", "update", f);
				if(funcioPare!= null)
				{
					root.getChildren().remove(node);
					TreeNode parentNode= getNodeFromFunctionId(this.funcioPare.getId(), "Funcio", "insert", null);
					parentNode.getChildren().add(node);
					node.setParent(parentNode);
				}
			}
			
			//oldParent.getChildren().remove(node);
	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dades Salvats"));
	        
		} catch (I18NException e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Salvants Dades 1"));
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Salvants Dades 2"));
		}
    }

    public void save() {    
    	System.out.println("Salvar dato");
		
		try {
			FuncioObject f = new FuncioObject();
			f.setCodi(this.nuevoCodi);
			f.setNom(this.nuevoNom);
			f.setNomcas(this.nuevoNomCas!=null ? this.nuevoNomCas: null);
			f.setEstat(this.nuevoEstat);
			f.setOrdre(this.nuevoOrdre);
			f.setInici(new Date());
			f.setModificacio(new Date());
			f.setFi(null);
			FuncioObject newF = this.servicesFunciones.create(f, this.cuadroSeleccionado, this.funcioPare, this.nuevoTipusserie);
			this.listaFunciones = this.servicesFunciones.findAll();
			TreeNode node = new DefaultTreeNode(new Document<FuncioObject>(newF.getId(), newF.getCodi(), newF.getNom(), "Funcio", newF), (this.funcionSeleccionada!=null) ? this.getNodeFromFunctionId(this.funcionSeleccionada.getId(), "Funcio", "insert", null) : this.root);

	        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dades Salvats"));
	        
		} catch (I18NException e) {
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Salvants Dades"));
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error Salvants Dades"));
		}
    }
    
    public <T> TreeNode getNodeFromFunctionId(long id, String type, String action, T updateObject) {
    	Optional<TreeNode> node = this.recursiveTreeNode(this.root, id, type, action, updateObject);
    	
    	if(node.isEmpty()) {
    		return this.root;
    	} else {
    		return node.get();
    	}
    }
    
   private <T> Optional<TreeNode> recursiveTreeNode(TreeNode node, long id, String type, String action, T updateObject) {
	   Document<T> d = (Document<T>) node.getData();
	   if(d.getId()==id && d.getType()==type) {
		   if(action=="update") {
			   String typeObject=updateObject.getClass().getSimpleName();
			   switch (typeObject) {
					case "FuncioObject":
						FuncioObject f = (FuncioObject) updateObject;
						Document<FuncioObject> fdoc = new Document<FuncioObject>(f.getId(), f.getCodi(), f.getNom(), "Funcio", f);
						((DefaultTreeNode) node).setData(fdoc);
						break;
					case "SerieDocumentalObject":
						SerieDocumentalObject s = (SerieDocumentalObject) updateObject;
						Document<SerieDocumentalObject> sdoc = new Document<SerieDocumentalObject>(s.getSerieId(), s.getCodi(), s.getNom(), "Serie", s);
						((DefaultTreeNode) node).setData(sdoc);
						break;
					case "DictamenObject":
						DictamenObject di = (DictamenObject) updateObject;
						Document<DictamenObject> fun = new Document<DictamenObject>(di.getId(), "ID"+di.getId(), di.getAccioDictaminada(), "Dictamen", di);
						((DefaultTreeNode) node).setData(fun);
						break;
				default:
					break;
				}			   
		   } else {
			   node.setExpanded(true);
		   }
		   return Optional.of(node);
	   } else {
		   for(TreeNode n : node.getChildren()) {
			   Optional<TreeNode> t = this.recursiveTreeNode(n, id, type, action, updateObject);
			   if(t.isPresent()) return t; 
		   }
		   return Optional.empty();
	   }
   }
    
    public void cerrarModal() {
        PrimeFaces.current().dialog().closeDynamic(null);
        FacesContext ctxt = FacesContext.getCurrentInstance(); //get your hands on the current request context
        ctxt.getPartialViewContext().getRenderIds().add("panel1");

    }

    public void onNodeSelect(NodeSelectEvent event) {
    	
    	Object dataObject = event.getTreeNode().getData();
    	
    	if(this.infoSelectedElement.isEmpty() || event.getTreeNode()!=this.nodoSeleccionado) {
    	
	    	
	    	this.infoSelectedElement = new LinkedHashMap<String, String>();
	    	
	    	String type = ((Document)dataObject).getType();
	    	
	    	if (type=="Funcio") {
	    		Document<FuncioObject> f = (Document<FuncioObject>) dataObject;
	    		this.infoSelectedElement.put("type", "FUNCIO");
	    		this.infoSelectedElement.put("id", String.valueOf(f.getObject().getId()));
	    		this.infoSelectedElement.put("Codi", String.valueOf(f.getObject().getCodi()));
	    		this.infoSelectedElement.put("Nom", String.valueOf(f.getObject().getNom()));
	    		
	    		String nomcas = f.getObject().getNomcas();
	    		if(nomcas!=null) {
	    			this.infoSelectedElement.put("Nom cas", nomcas);
	    		}
	    		
	    		this.infoSelectedElement.put("Quadre", f.getObject().getCodigoCuadro().getNom());
	    		
	    		FuncioObject fpare = f.getObject().getFuncioPare();
	    		if(fpare!=null) {
	    			this.infoSelectedElement.put("Funcio pare", fpare.getNom());
	    		}
	    		
	    		TipusSerieObject tserie = f.getObject().getTipoSerie();
	    		if(tserie!=null) {
	    			this.infoSelectedElement.put("Tipu serie", tserie.getNom());
	    		}
	    		
	    		this.infoSelectedElement.put("Estat", f.getObject().getEstat());
	    		this.infoSelectedElement.put("Ordre", String.valueOf(f.getObject().getOrdre()));
	    		this.infoSelectedElement.put("Inici", String.valueOf(f.getObject().getInici()));
	    		this.infoSelectedElement.put("Modificacio", String.valueOf(f.getObject().getModificacio()));
	    		
	    		Date fi = f.getObject().getFi();
	    		if(fi!=null) {
	    			this.infoSelectedElement.put("Fi", String.valueOf(fi));
	    		}
	    		this.nodoSeleccionado = event.getTreeNode();
	    	} else if (type=="Serie") {
	    		Document<SerieDocumentalObject> f = (Document<SerieDocumentalObject>) dataObject;
	    		this.infoSelectedElement.put("type", "SERIE DOCUMENTAL");
	    		this.infoSelectedElement.put("id", String.valueOf(f.getObject().getSerieId()));
	    		this.infoSelectedElement.put("Codi", String.valueOf(f.getObject().getCodi()));
	    		this.infoSelectedElement.put("Nom", String.valueOf(f.getObject().getNom()));
	    		//Seriedocumental data = serie
	    		String nomcas = f.getObject().getNomCas();
	    		if(nomcas!=null) {
	    			this.infoSelectedElement.put("Nom cas", nomcas);
	    		}
	    			    		
	    		Long codiFnc= f.getObject().getFuncio().getId();
	    		if(codiFnc!=null) {
	    			this.infoSelectedElement.put("Codi funcio", String.valueOf(codiFnc));
	    		}
	    		
	    		/*TipusserieObject tserie = f.getObject().getTipoSerie();
	    		if(tserie!=null) {
	    			this.infoSelectedElement.put("Tipu serie", tserie.getNom());
	    		}*/
	    		
	    		this.infoSelectedElement.put("Descripcio", f.getObject().getDescripcio());
	    		this.infoSelectedElement.put("Descripcio Castellá", String.valueOf(f.getObject().getDescripcioCas()));
	    		this.infoSelectedElement.put("Dir3 promotor", f.getObject().getDir3Promotor());
	    		this.infoSelectedElement.put("Codi IECISA", f.getObject().getCodiIecisa());
	    		
	    		this.nodoSeleccionado = event.getTreeNode();
	    	} else if (type=="Dictamen") {
	    		Document<DictamenObject> d = (Document<DictamenObject>) dataObject;
	    		this.infoSelectedElement.put("type", "DICTAMEN");
	    		this.infoSelectedElement.put("id", String.valueOf(d.getObject().getId()));
	    		
	    		this.infoSelectedElement.put("Serie Documental", String.valueOf(d.getObject().getSerieDocumental().getCodi()));
	    		
	    		TipuDictamenObject td = d.getObject().getTipusdictamen();
	    		if(td!=null) {
	    			this.infoSelectedElement.put("Tipu dictamen", td.getNom());
	    		}
	    		
	    		TipuAccesObject ta = d.getObject().getTipusAcces();
	    		if(ta!=null) {
	    			this.infoSelectedElement.put("Tipu acces", ta.getNom());
	    		}
	    		
	    		this.infoSelectedElement.put("Normativa aprobacio", d.getObject().getNormativaAprovacio().getCodi());
	    		
	    		EnsObject ens = d.getObject().getEns();
	    		if(ens!=null) {
	    			this.infoSelectedElement.put("Ens", ens.getNom());
	    		}
	    		
	    		LopdObject lopd = d.getObject().getLopd();
	    		if(lopd!=null) {
	    			this.infoSelectedElement.put("Lopd", lopd.getNom());
	    		}
	    		
	    		this.infoSelectedElement.put("Termini", d.getObject().getTermini());
	    		
	    		
	    		String accd = d.getObject().getAccioDictaminada();
	    		if(accd!=null) {
	    			this.infoSelectedElement.put("Accio Dictaminada", accd);
	    		}
	    		
	    		String cr = d.getObject().getCondicioReutilitzacio();
	    		if(cr!=null) {
	    			this.infoSelectedElement.put("Concicion de reutilitzacio", cr);
	    		}
	    		
	    		String dr = d.getObject().getDestinatarisRestrigits();
	    		if(dr!=null) {
	    			this.infoSelectedElement.put("Destinataris Restingits", dr);
	    		}
	    		
	    		Boolean se = d.getObject().getSerieEsencial();
	    		if(se!=null) {
	    			this.infoSelectedElement.put("Serie Esencial", (se ? "SI": "NO"));
	    		}
	    		
	    		this.infoSelectedElement.put("Inici", String.valueOf(d.getObject().getInici()));
	    		this.infoSelectedElement.put("Aprobacio", String.valueOf(d.getObject().getAprovacio()));
	    		
	    		Date fi = d.getObject().getFi();
	    		if(fi!=null) {
	    			this.infoSelectedElement.put("Fi", String.valueOf(fi));
	    		}
	    		this.nodoSeleccionado = event.getTreeNode();
	    	}

    	} else {
    		event.getTreeNode().setSelected(false);
    		this.nodoSeleccionado = null;
    		this.infoSelectedElement = new LinkedHashMap<String, String>();
    	}

    }
    
    private void clearForm(){
		
    	nuevaFuncion = null;
    	nuevoCodi = null;
    	nuevoNom = null;
    	nuevoNomCas = null;
    	nuevoQuadre = null;
    	nuevoFuncioPare = null;
    	nuevoTipusserie = null;
    	nuevoEstat = null;
    	nuevoOrdre = null;
		funcioPare = null;

	}
    
    public void onExpandTree(NodeExpandEvent event) {
    	this.clearSelection();
    }
    
    public void onCollapseTree(NodeCollapseEvent event) {
    	this.clearSelection();
    }
    
    public void onSortTree(SortEvent event) {
    	this.clearSelection();
    }
    
    public void onPageTree(PageEvent event) {
    	this.clearSelection();
    }
 
    public void onNodeUnselect(NodeUnselectEvent event) {
    	event.getTreeNode().setSelected(false);
    	this.nodoSeleccionado = null;
    	this.infoSelectedElement = new LinkedHashMap<String, String>();
    }
    
    private void clearSelection() {
    	if(this.selectedNode!=null) {
    		this.selectedNode.setSelected(false);
    		this.nodoSeleccionado = null;
        	this.infoSelectedElement = new LinkedHashMap<String, String>();
    	}
    }
    
    public void onQuadreSelectedListener(AjaxBehaviorEvent event){
    	
    	this.clearSelection();
    	try {
			this.root = this.servicesFunciones.getContent(cuadroSeleccionado);	
			this.filterNode = this.root.getParent();
		} catch (I18NException e) {
			e.printStackTrace();
		}
    }
    
	public FuncioObject getFuncionSeleccionada() {
		return funcionSeleccionada;
	}

	public void setFuncionSeleccionada(FuncioObject funcionSeleccionada) {
		this.funcionSeleccionada = funcionSeleccionada;
	}

	public TipusSerieObject getTipusserieSeleccionado() {
		return tipusserieSeleccionado;
	}

	public void setTipusserieSeleccionado(TipusSerieObject tipusserieSeleccionado) {
		this.tipusserieSeleccionado = tipusserieSeleccionado;
	}


	public FuncioService getServicesFunciones() {
		return servicesFunciones;
	}

	public void setServicesFunciones(FuncioService servicesFunciones) {
		this.servicesFunciones = servicesFunciones;
	}

	public String getNuevoCodi() {
		return nuevoCodi;
	}

	public void setNuevoCodi(String nuevoCodi) {
		this.nuevoCodi = nuevoCodi;
	}

	public String getNuevoNom() {
		return nuevoNom;
	}

	public void setNuevoNom(String nuevoNom) {
		this.nuevoNom = nuevoNom;
	}

	public String getNuevoNomCas() {
		return nuevoNomCas;
	}

	public void setNuevoNomCas(String nuevoNomCas) {
		this.nuevoNomCas = nuevoNomCas;
	}

	public String getNuevoEstat() {
		return nuevoEstat;
	}

	public void setNuevoEstat(String nuevoEstat) {
		this.nuevoEstat = nuevoEstat;
	}

	public BigDecimal getNuevoOrdre() {
		return nuevoOrdre;
	}

	public void setNuevoOrdre(BigDecimal nuevoOrdre) {
		this.nuevoOrdre = nuevoOrdre;
	}

	public QuadreObject getNuevoQuadre() {
		return nuevoQuadre;
	}

	public void setNuevoQuadre(QuadreObject nuevoQuadre) {
		this.nuevoQuadre = nuevoQuadre;
	}

	public List<QuadreObject> getListaCuadrosClasificacion() {
		return listaCuadrosClasificacion;
	}

	public void setListaCuadrosClasificacion(List<QuadreObject> listaCuadrosClasificacion) {
		this.listaCuadrosClasificacion = listaCuadrosClasificacion;
	}

	public QuadreObject getCuadroSeleccionado() {
		return cuadroSeleccionado;
	}

	public void setCuadroSeleccionado(QuadreObject cuadroSeleccionado) {
		this.cuadroSeleccionado = cuadroSeleccionado;
	}

	public QuadreServices getServicesCuadroClasificacion() {
		return servicesCuadroClasificacion;
	}

	public void setServicesCuadroClasificacion(QuadreServices servicesCuadroClasificacion) {
		this.servicesCuadroClasificacion = servicesCuadroClasificacion;
	}

	public FuncioObject getNuevaFuncion() {
		return nuevaFuncion;
	}

	public void setNuevaFuncion(FuncioObject nuevaFuncion) {
		this.nuevaFuncion = nuevaFuncion;
	}

	public FuncioObject getNuevoFuncioPare() {
		return nuevoFuncioPare;
	}

	public void setNuevoFuncioPare(FuncioObject nuevoFuncioPare) {
		this.nuevoFuncioPare = nuevoFuncioPare;
	}

	public TipusSerieObject getNuevoTipusserie() {
		return nuevoTipusserie;
	}

	public void setNuevoTipusserie(TipusSerieObject nuevoTipusserie) {
		this.nuevoTipusserie = nuevoTipusserie;
	}

	public List<FuncioObject> getListaFunciones() {
		return listaFunciones;
	}

	public void setListaFunciones(List<FuncioObject> listaFunciones) {
		this.listaFunciones = listaFunciones;
	}

	public List<TipusSerieObject> getListaTipusserie() {
		return listaTipusserie;
	}

	public void setListaTipusserie(List<TipusSerieObject> listaTipusserie) {
		this.listaTipusserie = listaTipusserie;
	}

	public TreeNode getRoot() {
		return root;
	}

	public void setRoot(TreeNode root) {
		this.root = root;
	}

	public TreeNode getNodoSeleccionado() {
		return nodoSeleccionado;
	}

	public void setNodoSeleccionado(TreeNode nodoSeleccionado) {
		this.nodoSeleccionado = nodoSeleccionado;
	}

	public FuncioObject getFuncionActualizar() {
		return funcionActualizar;
	}

	public void setFuncionActualizar(FuncioObject funcionActualizar) {
		this.funcionActualizar = funcionActualizar;
	}

	public TreeNode getSelectedNode() {
		return selectedNode;
	}

	public void setSelectedNode(TreeNode selectedNode) {
		this.selectedNode = selectedNode;
	}

	public Map<String, String> getInfoSelectedElement() {
		return infoSelectedElement;
	}

	public void setInfoSelectedElement(Map<String, String> infoSelectedElement) {
		this.infoSelectedElement = infoSelectedElement;
	}

	public FuncioObject getFuncioPare() {
		return funcioPare;
	}

	public void setFuncioPare(FuncioObject funcioPare) {
		this.funcioPare = funcioPare;
	}

	public TreeNode getFilterNode() {
		return filterNode;
	}

	public void setFilterNode(TreeNode filterNode) {
		this.filterNode = filterNode;
	}

	public List<String> getListaEstats() {
		return listaEstats;
	}

	public void setListaEstats(List<String> listaEstats) {
		this.listaEstats = listaEstats;
	}

	public Long getQuadreId() {
		return quadreId;
	}

	public void setQuadreId(Long quadreId) {
		this.quadreId = quadreId;
	}
	
	
	
}

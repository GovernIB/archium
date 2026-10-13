package es.caib.archium.controllers;

import java.io.IOException;
import java.io.Serializable;
import java.math.BigDecimal;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

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
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import es.caib.archium.services.FuncioFrontService;
import es.caib.archium.services.QuadreFrontService;
import es.caib.archium.utils.FrontExceptionTranslate;

@Logged
@Named("funciones")
@ViewScoped
public class FuncionesController implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	protected final Logger log = LoggerFactory.getLogger(this.getClass());
	
	Locale locale;
	
	@Inject
    private FuncioFrontService servicesFunciones;
	@Inject
    private QuadreFrontService servicesCuadroClasificacion;
	
	
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
    

    
    private TreeNode selectedNode;
    private TreeNode filterNode;
    private Map<String, String> infoSelectedElement = new LinkedHashMap<String, String>();

    private  List<String> listaEstats = new ArrayList<>();
    
    private Long quadreId;
    
    ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
    
    private Boolean error = false;
    private String searchText;
    private TreeNode root;
    
    private Long loadedQuadreId = null;
    
	private static final Pattern DIACRITICS = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
    
    
	@PostConstruct
    public void init() {
		log.debug("Dentro del método 'init de FuncionesController'");
    	try {
    		listaEstats.add(messageBundle.getString("general.estats.esborrany")); 
			listaEstats.add(messageBundle.getString("general.estats.revisat"));
			listaEstats.add(messageBundle.getString("general.estats.publicable"));
			listaEstats.add(messageBundle.getString("general.estats.vigent"));
			listaEstats.add(messageBundle.getString("general.estats.obsolet"));
    		
    		this.listaCuadrosClasificacion = this.servicesCuadroClasificacion.findAll();
    		
    		FacesContext context = FacesContext.getCurrentInstance();
    	    String quadreIdString = context.getExternalContext().getRequestParameterMap().get("quadreId");
    		log.debug("Id de cuadro recibido: " + quadreIdString);
    	    locale = context.getViewRoot().getLocale();
    	    
    	    searchText = "";

			// Intenta agafar el quadre a partir de paràmetre de la URL...
    	    if(quadreIdString!=null) {
    	    	quadreId = Long.parseLong(quadreIdString);
    	    }
    	    // Si no arriba, agafa el per defecte
    		if(quadreId==null) {
    			if(listaCuadrosClasificacion.size() > 0) {
    				quadreId = this.servicesCuadroClasificacion.getQuadreClassificacioPerDefecte(listaCuadrosClasificacion);
    			}
    		}
    		
    		if(quadreId==null) {
    			log.debug("No hay cuadro de clasificación por defecto");
    			TreeNode root = new DefaultTreeNode(new Document(0, "All", "All", "All", null), null);
    		} else {
    			this.cuadroSeleccionado = this.servicesCuadroClasificacion.getQuadreById(quadreId);
        		this.root = this.servicesFunciones.getContent(cuadroSeleccionado);
        		this.filterNode = this.root.getParent();
//        		this.listaFunciones =  	new ArrayList();
//        		this.listaTipusserie =  	new ArrayList();   	
//        		this.listaFunciones = this.servicesFunciones.loadTree(quadreId, null);
//        		this.listaTipusserie = this.servicesFunciones.findAllTipusSerie();    	
    		}
    		
    		FacesContext ctxt = FacesContext.getCurrentInstance(); //get your hands on the current request context
            ctxt.getPartialViewContext().getRenderIds().add("panelButtons");
    			
    	} catch(I18NException e) {	
    		log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
    		error = true;
    	}     
    	log.debug("Saliendo del método 'init'");
    }

    public void setFuncioActualizar(Document<FuncioObject> d) {
    	this.clearSelection();
    	this.clearForm();
    	if (d==null) {
    		this.funcionActualizar = null;
    	} else {
    		
    		try {
    			if (this.listaFunciones == null || 
    	    		    this.listaFunciones.isEmpty() || 
    	    		    !cuadroSeleccionado.getId().equals(this.loadedQuadreId)) {
    	    		    
    	    		    this.listaFunciones = this.servicesFunciones.loadTree(cuadroSeleccionado.getId(), null);
    	    		    this.loadedQuadreId = cuadroSeleccionado.getId();
    	    		}

    			if (this.listaTipusserie == null || this.listaTipusserie.isEmpty()) {
    			    this.listaTipusserie = this.servicesFunciones.findAllTipusSerie();
    			}
    			
				FuncioObject f = this.servicesFunciones.findById(d.getId());
				
				if(f!=null) {
					this.funcionActualizar = f;
		        	this.nuevoCodi = f.getCodi();
		        	this.nuevoEstat = f.getEstat();
		        	this.nuevoNom = f.getNom();
		        	this.nuevoNomCas = f.getNomcas();
		        	this.nuevoOrdre = f.getOrdre();
		        	this.nuevoTipusserie = f.getTipoSerie();
		        	this.funcionSeleccionada = f.getFuncioPare();
		    		this.funcioPare = f.getFuncioPare();
		    		PrimeFaces.current().executeScript("PF('documentDialog').show()");
				} else {
					FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.get.error"), null);
		 			FacesContext.getCurrentInstance().addMessage(null, msg);	
				}
				
				
			} catch (I18NException e) {
				log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
	 			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.get.error"), null);
	 			FacesContext.getCurrentInstance().addMessage(null, msg);	
			}
    		
    		

    	}    	
    }
    
    public String loadFromFunction(Document d) {
        clearForm();
        FuncioObject serieFuncio = null;
        log.debug("¿ES EL DOCUMENTO NULL?: {})", d);
        if (d != null) {
        	try {
				FuncioObject f = this.servicesFunciones.findById(d.getId());
				
				if (f != null) {
					funcionSeleccionada = f;
				}
				
	        } catch (I18NException e) {
				log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
	 			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.get.error"), null);
	 			FacesContext.getCurrentInstance().addMessage(null, msg);	
			}
        }

        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("serieFuncioParametro", funcionSeleccionada);
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("quadreId", cuadroSeleccionado.getId());
        
        return "/views/nuevaSerie.xhtml?faces-redirect=true";
    }
    
    public String redirect() {
		FacesContext context = FacesContext.getCurrentInstance();
		context.getExternalContext().getFlash().setKeepMessages(true);
		
        FacesContext.getCurrentInstance().getExternalContext().getFlash().put("quadreId", cuadroSeleccionado.getId());
		return "/views/nuevaSerie.xhtml?faces-redirect=true";
	}
    
    /**
	 * Carrega els valors de la sèrie al modal d'edició
	 * 
	 * @param obj
	 */
	public String updateSerie(Document<SerieDocumentalObject> obj) {
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("serieIdEditar", obj.getId());
		//clearForm();
		log.debug("¿ES EL DOCUMENTO NULL?: {})", obj);
		funcionSeleccionada = obj.getObject().getFuncio();
		FuncioObject funcioObjectParametro = (obj != null) ? funcionSeleccionada : null;
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("serieFuncioParametro",
				funcioObjectParametro);
		
		FacesContext.getCurrentInstance().getExternalContext().getFlash().put("quadreId", cuadroSeleccionado.getId());

		return "/views/editarSerie.xhtml?faces-redirect=true";
    }
    
    public void setFuncioSeleccionada(Document<FuncioObject> d) throws I18NException {
    	this.clearSelection();
    	clearForm();
    	if (this.listaFunciones == null || 
    		    this.listaFunciones.isEmpty() || 
    		    !cuadroSeleccionado.getId().equals(this.loadedQuadreId)) {
    		    
    		    this.listaFunciones = this.servicesFunciones.loadTree(cuadroSeleccionado.getId(), null);
    		    this.loadedQuadreId = cuadroSeleccionado.getId();
    		}

    	if (this.listaTipusserie == null || this.listaTipusserie.isEmpty()) {
		    this.listaTipusserie = this.servicesFunciones.findAllTipusSerie();
		}
    	this.funcionActualizar = null;
    	
    	if (d==null) {
    		this.funcionSeleccionada = null;
    	} else {
    		this.funcionSeleccionada = d.getObject();
    		this.funcioPare = d.getObject();
    	}    	
    }
    
    public void deleteFuncio(Document<FuncioObject> f) {
        try {
            this.servicesFunciones.deleteFuncio(f.getId());

            if (searchText != null && !searchText.trim().isEmpty()) {
                // Modo filtrado: reconstruir el filtro con datos actualizados
                filtrarTreeNode();
            } else {
                // Buscar con "insert" para no modificar datos del nodo
                TreeNode node = this.getNodeFromFunctionId(f.getId(), "Funcio", "insert", null);
                if (node != null && node != this.root) {
                    node.getParent().getChildren().remove(node);
                }
            }

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(messageBundle.getString("funciones.delete.ok")));
        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    messageBundle.getString("funciones.delete.error"), null));
        }
    }
    
    public void update() {
        try {
            FuncioObject f = this.funcionActualizar;

            f.setCodi(this.nuevoCodi);
            f.setNom(this.nuevoNom);
            f.setNomcas(this.nuevoNomCas != null ? this.nuevoNomCas : null);
            f.setEstat(this.nuevoEstat);
            f.setOrdre(this.nuevoOrdre);
            f.setInici(new Date());
            f.setModificacio(new Date());
            f.setFi(null);
            f.setTipoSerie(nuevoTipusserie);
            f.setFuncioPare(funcioPare);

            if (!this.checkFuncioPareValid(f, funcioPare)) {
                FacesContext.getCurrentInstance().validationFailed();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    messageBundle.getString("funciones.funciopare.conflict"), null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                FuncioObject upF = this.servicesFunciones.update(f);
                this.listaFunciones = this.servicesFunciones.loadTree(quadreId, null);

                if (searchText != null && !searchText.trim().isEmpty()) {
                    filtrarTreeNode();
                } else {
                    // Eliminar el nodo antiguo ANTES de cualquier manipulación
                    TreeNode oldFNode = this.getNodeFromFunctionId(upF.getId(), "Funcio", "update", f);
                    if (oldFNode != null && oldFNode != this.root) {
                        TreeNode oldParent = oldFNode.getParent();
                        if (oldParent != null) {
                            oldParent.getChildren().remove(oldFNode);
                        }
                    }

                    if (funcioPare != null) {
                        Map<Long, TreeNode> mapa = getMapaNodos();
                        boolean padreYaCargado = mapa.containsKey(funcioPare.getId());

                        if (padreYaCargado) {
                            TreeNode padreDestino = mapa.get(funcioPare.getId());
                            padreDestino.getChildren().clear();
                            this.servicesFunciones.expandQuadre(cuadroSeleccionado, padreDestino, funcioPare.getId());
                            padreDestino.setExpanded(true);

                            // Buscar upF entre los hijos cargados por expandQuadre
                            TreeNode nodoMovido = null;
                            for (TreeNode hijo : padreDestino.getChildren()) {
                                if (hijo.getData() instanceof Document) {
                                    Document<?> doc = (Document<?>) hijo.getData();
                                    if (doc.getId() == upF.getId()) {
                                        nodoMovido = hijo;
                                        break;
                                    }
                                }
                            }
                            // Fallback por si expandQuadre no lo devuelve aún
                            if (nodoMovido == null) {
                                nodoMovido = new DefaultTreeNode(
                                    new Document<FuncioObject>(upF.getId(), upF.getCodi(), upF.getNom(), "Funcio", upF),
                                    padreDestino
                                );
                                new DefaultTreeNode("DUMMY", null, nodoMovido);
                            }
                            expandirHastaRaiz(nodoMovido);

                        } else {
                            // Padre no cargado: abrir la ruta hasta padreDestino pero dejarlo cerrado
                            // expandQuadre lo cargará con todos sus hijos (incluido upF) cuando el usuario lo expanda
                            TreeNode padreDestino = abreNodos(funcioPare, mapa);
                            expandirHastaRaiz(padreDestino); // abre los ancestros, padreDestino queda cerrado
                        }

                    } else {
                        // Sin padre: crear directamente bajo root
                        TreeNode nodoMovido = new DefaultTreeNode(
                            new Document<FuncioObject>(upF.getId(), upF.getCodi(), upF.getNom(), "Funcio", upF),
                            root
                        );
                        new DefaultTreeNode("DUMMY", null, nodoMovido);
                        expandirHastaRaiz(nodoMovido);
                    }
                }

                FacesContext.getCurrentInstance().addMessage(null,
                    new FacesMessage(messageBundle.getString("funciones.update.ok")));
            }
        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.update.error"), null));
        }
    }


    public void save() {
        try {
            FuncioObject f = new FuncioObject();
            f.setCodi(this.nuevoCodi);
            f.setNom(this.nuevoNom);
            f.setNomcas(this.nuevoNomCas != null ? this.nuevoNomCas : null);
            f.setEstat(this.nuevoEstat);
            f.setOrdre(this.nuevoOrdre);
            f.setInici(new Date());
            f.setModificacio(new Date());
            f.setFi(null);

            FuncioObject newF = this.servicesFunciones.create(f, this.cuadroSeleccionado, this.funcioPare, this.nuevoTipusserie);
            this.listaFunciones = this.servicesFunciones.loadTree(quadreId, null);

            if (searchText != null && !searchText.trim().isEmpty()) {
                filtrarTreeNode();
            } else {
                // Determinar el padre destino
                TreeNode padreDestino;
                if (funcioPare != null) {
                    Map<Long, TreeNode> mapa = getMapaNodos();
                    boolean padreYaCargado = mapa.containsKey(funcioPare.getId());

                    if (padreYaCargado) {
                        padreDestino = mapa.get(funcioPare.getId());
                        padreDestino.getChildren().clear();
                        this.servicesFunciones.expandQuadre(cuadroSeleccionado, padreDestino, funcioPare.getId());
                        padreDestino.setExpanded(true);

                        // Buscar newF entre los hijos cargados por expandQuadre
                        TreeNode nodoCreado = null;
                        for (TreeNode hijo : padreDestino.getChildren()) {
                            if (hijo.getData() instanceof Document) {
                                Document<?> doc = (Document<?>) hijo.getData();
                                if (doc.getId() == newF.getId()) {
                                    nodoCreado = hijo;
                                    break;
                                }
                            }
                        }
                        // Fallback por si expandQuadre no lo devuelve aún
                        if (nodoCreado == null) {
                            nodoCreado = new DefaultTreeNode(
                                new Document<FuncioObject>(newF.getId(), newF.getCodi(), newF.getNom(), "Funcio", newF),
                                padreDestino
                            );
                            new DefaultTreeNode("DUMMY", null, nodoCreado);
                        }
                        expandirHastaRaiz(nodoCreado);

                    } else {
                        // Padre no cargado: abrir la ruta hasta padreDestino pero dejarlo cerrado
                        // expandQuadre lo cargará con todos sus hijos (incluido newF) cuando el usuario lo expanda
                        padreDestino = abreNodos(funcioPare, mapa);
                        expandirHastaRaiz(padreDestino); // abre los ancestros, padreDestino queda cerrado
                    }
                } else {
                    // Sin padre: crear directamente bajo root
                    TreeNode nodoCreado = new DefaultTreeNode(
                        new Document<FuncioObject>(newF.getId(), newF.getCodi(), newF.getNom(), "Funcio", newF),
                        root
                    );
                    new DefaultTreeNode("DUMMY", null, nodoCreado);
                    expandirHastaRaiz(nodoCreado);
                }
            }

            FacesContext.getCurrentInstance().addMessage(null,
                new FacesMessage(messageBundle.getString("funciones.insert.ok")));

        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.insert.error"), null));
        }
    }
    
    
    
    private void cierraNodosRecursivamente(TreeNode nodo) {
    	if (nodo != root) {
    		nodo.setExpanded(false);
    		cierraNodosRecursivamente(nodo.getParent());
    	} else {
    		nodo.setExpanded(false);
    	}
    }
    
    private TreeNode abreNodos(FuncioObject seleccionado, Map<Long, TreeNode> funcioMap) throws I18NException {

        // 1. Construir la ruta desde el nodo hasta el primer ancestro cargado
        List<FuncioObject> ruta = new ArrayList<>();
        FuncioObject actual = seleccionado;

        while (actual != null && !funcioMap.containsKey(actual.getId())) {
            ruta.add(0, actual); // orden raíz → hoja
            actual = actual.getFuncioPare();
        }

        // 2. Punto de arranque: primer ancestro cargado, o root si ninguno lo está
        TreeNode nodoActual = (actual != null && funcioMap.containsKey(actual.getId()))
            ? funcioMap.get(actual.getId())
            : root;

        // 3. Recorrer la ruta simulando expansión en cada nivel
        for (FuncioObject funcio : ruta) {

            // Simular el evento de expansión — igual que onExpandTree
            Object data = nodoActual.getData();
            Long idNodo = null;
            if (data instanceof Document) {
                Object obj = ((Document<?>) data).getObject();
                if (obj instanceof FuncioObject) {
                    idNodo = ((FuncioObject) obj).getId();
                }
            }
            this.servicesFunciones.expandQuadre(cuadroSeleccionado, nodoActual, idNodo);
            nodoActual.setExpanded(true);

            // Buscar el hijo que corresponde al siguiente paso de la ruta
            TreeNode siguiente = null;
            for (TreeNode hijo : nodoActual.getChildren()) {
                if (hijo.getData() instanceof Document) {
                    Document<?> doc = (Document<?>) hijo.getData();
                    if (doc.getId() == funcio.getId().longValue()) {
                        siguiente = hijo;
                        break;
                    }
                }
            }

            if (siguiente == null) break; // no debería ocurrir si los datos son consistentes
            nodoActual = siguiente;
        }

        return nodoActual; // devuelve el nodo destino para expandirHastaRaiz
    }

    private void expandirHastaRaiz(TreeNode nodo) {
        TreeNode actual = nodo.getParent();
        while (actual != null) {
            actual.setExpanded(true);
            actual = actual.getParent();
        }
    }
    
    private Map<Long, TreeNode> getMapaNodos() {
        Map<Long, TreeNode> mapa = new HashMap<>();
        recorrerMapa(root, mapa);
        return mapa;
    }

    private void recorrerMapa(TreeNode node, Map<Long, TreeNode> mapa) {
        if (node == null) return;

        Object data = node.getData();

        if (data instanceof Document) {
            Document<FuncioObject> doc = (Document<FuncioObject>) data;

            // evitar dummy
            if (!"DUMMY".equals(doc.getType())) {
                mapa.put(doc.getId(), node);
            }
        }

        for (TreeNode child : node.getChildren()) {
            recorrerMapa(child, mapa);
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
        Object data = node.getData();
        Document<T> d;

        if (data instanceof Document) {
            d = (Document<T>) data;
        } else {
            return Optional.empty();
        }

        if (d.getId() == id && d.getType() == type) {
            if (action == "update") {
                String typeObject = updateObject.getClass().getSimpleName();
                switch (typeObject) {
                    case "FuncioObject":
                        FuncioObject f = (FuncioObject) updateObject;
                        ((DefaultTreeNode) node).setData(new Document<FuncioObject>(f.getId(), f.getCodi(), f.getNom(), "Funcio", f));
                        break;
                    case "SerieDocumentalObject":
                        SerieDocumentalObject s = (SerieDocumentalObject) updateObject;
                        ((DefaultTreeNode) node).setData(new Document<SerieDocumentalObject>(s.getSerieId(), s.getCodi(), s.getNom(), "Serie", s.getEnviatSAT(), s));
                        break;
                    case "DictamenObject":
                        DictamenObject di = (DictamenObject) updateObject;
                        ((DefaultTreeNode) node).setData(new Document<DictamenObject>(di.getId(), di.getCodi(), di.getAccioDictaminada(), "Dictamen", di));
                        break;
                    default:
                        break;
                }
            } else {
                node.setExpanded(true);
            }
            return Optional.of(node);
        }

        // ← Quitado el "if (!n.isLeaf())" — ahora busca en todos los hijos
        for (TreeNode n : node.getChildren()) {
            Optional<TreeNode> t = this.recursiveTreeNode(n, id, type, action, updateObject);
            if (t.isPresent()) return t;
        }

        return Optional.empty();
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
	    		this.infoSelectedElement.put("type", messageBundle.getString("nuevafuncion.type"));
	    		this.infoSelectedElement.put("id", String.valueOf(f.getObject().getId()));
	    		this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.codi"), String.valueOf(f.getObject().getCodi()));
	    		this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.nom"), String.valueOf(f.getObject().getNom()));
	    		
	    		String nomcas = f.getObject().getNomcas();
	    		if(nomcas!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.nomcas"), nomcas);
	    		}
	    		
	    		this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.cuadro"), f.getObject().getCodigoCuadro().getNom());
	    		
	    		FuncioObject fpare = f.getObject().getFuncioPare();
	    		if(fpare!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.funcionpadre"), fpare.getNom());
	    		}
	    		
	    		TipusSerieObject tserie = f.getObject().getTipoSerie();
	    		if(tserie!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.tiposerie"), tserie.getNom());
	    		}
	    		
	    		this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.estado"), f.getObject().getEstat());
	    		this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.orden"), String.valueOf(f.getObject().getOrdre()));
	    		this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.inici"), String.valueOf(f.getObject().getInici()));
	    		this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.modificacio"), String.valueOf(f.getObject().getModificacio()));
	    		
	    		Date fi = f.getObject().getFi();
	    		if(fi!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.fi"), String.valueOf(fi));
	    		}
	    		this.nodoSeleccionado = event.getTreeNode();
	    	} else if (type=="Serie") {
	    		Document<SerieDocumentalObject> f = (Document<SerieDocumentalObject>) dataObject;
	    		this.infoSelectedElement.put("type", messageBundle.getString("nuevaserie.type"));
	    		this.infoSelectedElement.put("id", String.valueOf(f.getObject().getSerieId()));
	    		this.infoSelectedElement.put(messageBundle.getString("nuevaserie.codiserie"), String.valueOf(f.getObject().getCodi()));
	    		this.infoSelectedElement.put(messageBundle.getString("nuevaserie.nombreserie"), String.valueOf(f.getObject().getNom()));
	    		//Seriedocumental data = serie
	    		String nomcas = f.getObject().getNomCas();
	    		if(nomcas!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("nuevaserie.nombrecastellano"), nomcas);
	    		}
	    			    		
	    		FuncioObject fnc= f.getObject().getFuncio();
	    		if(fnc!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("nuevaserie.codifuncion"), String.valueOf(fnc.getCodi() + "-" + fnc.getNom()));
	    		}
	    		
	    		/*TipusserieObject tserie = f.getObject().getTipoSerie();
	    		if(tserie!=null) {
	    			this.infoSelectedElement.put("Tipu serie", tserie.getNom());
	    		}*/
	    		
	    		this.infoSelectedElement.put(messageBundle.getString("nuevaserie.descripcio"), f.getObject().getDescripcio());
	    		if(f.getObject().getDescripcioCas()!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("nuevaserie.descripciocas"), String.valueOf(f.getObject().getDescripcioCas()));
	    		}
	    		
	    		if(f.getObject().getDir3Promotor()!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("nuevaserie.dir3prom"), f.getObject().getDir3Promotor());
	    		}
	    		
	    		if(f.getObject().getCodiIecisa()!=null) {	
	    			this.infoSelectedElement.put(messageBundle.getString("nuevaserie.codiiecisa"), f.getObject().getCodiIecisa());
	    		}
	    		
	    		this.infoSelectedElement.put(messageBundle.getString("nuevaserie.estat"), f.getObject().getEstat());
	    		
	    		this.nodoSeleccionado = event.getTreeNode();
	    	} else if (type=="Dictamen") {
	    		Document<DictamenObject> d = (Document<DictamenObject>) dataObject;
	    		this.infoSelectedElement.put("type", messageBundle.getString("dictamen.type"));
	    		this.infoSelectedElement.put("id", String.valueOf(d.getObject().getId()));
	    		this.infoSelectedElement.put(messageBundle.getString("nuevodictamen.codi"), String.valueOf(d.getObject().getCodi()));
	    		this.infoSelectedElement.put(messageBundle.getString("dictamen.serieDoc"), String.valueOf(d.getObject().getSerieDocumental().getCodi()));
	    		
	    		TipuDictamenObject td = d.getObject().getTipusdictamen();
	    		if(td!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("dictamen.tipuDic"), td.getNom());
	    		}
	    		
	    		TipuAccesObject ta = d.getObject().getTipusAcces();
	    		if(ta!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("dictamen.tipuAcc"), ta.getNom());
	    		}
	    		
	    		this.infoSelectedElement.put(messageBundle.getString("dictamen.normApro"), d.getObject().getNormativaAprovacio().getCodi());
	    		
	    		EnsObject ens = d.getObject().getEns();
	    		if(ens!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("dictamen.ens"), ens.getNom());
	    		}
	    		
	    		LopdObject lopd = d.getObject().getLopd();
	    		if(lopd!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("dictamen.lopd"), lopd.getNom());
	    		}
	    		
	    		this.infoSelectedElement.put(messageBundle.getString("dictamen.termini"), d.getObject().getTerminiFormat());
	    		
	    		
	    		String accd = d.getObject().getAccioDictaminada();
	    		if(accd!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("dictamen.accion"), accd);
	    		}
	    		
	    		String cr = d.getObject().getCondicioReutilitzacio();
	    		if(cr!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("dictamen.condicioReu"), cr);
	    		}
	    		
	    		String dr = d.getObject().getDestinatarisRestrigits();
	    		if(dr!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("dictamen.destRest"), dr);
	    		}
	    		
	    		Boolean se = d.getObject().getSerieEsencial();
	    		if(se!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("nuevodictamen.serieEsencial"), (se ? messageBundle.getString("si"): messageBundle.getString("no")));
	    		}
	    		
	    		this.infoSelectedElement.put(messageBundle.getString("dictamen.dataIni"), String.valueOf(d.getObject().getInici()));
	    		
	    		if(d.getObject().getAprovacio()!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("dictamen.dataApro"), String.valueOf(d.getObject().getAprovacio()));
	    		}
	    		Date fi = d.getObject().getFi();
	    		if(fi!=null) {
	    			this.infoSelectedElement.put(messageBundle.getString("dictamen.dataIni"), String.valueOf(fi));
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
        try {
            TreeNode nodeInUi = event.getTreeNode(); // Este nodo puede ser del filteredNode
            Document doc = (Document) nodeInUi.getData();
            
            // 1. Cargar hijos en el nodo que disparó el evento (Vista actual)
            cargarHijosSegunTipo(nodeInUi, doc);
            nodeInUi.setExpanded(true);

            // 2. Si hay un filtro activo, buscar el "espejo" en el ROOT y actualizarlo
            if (this.filterNode != null) {
                TreeNode mirrorNode = findNodeById(this.root, doc);
                if (mirrorNode != null) {
                    cargarHijosSegunTipo(mirrorNode, doc);
                    mirrorNode.setExpanded(true);
                }
            } else if (this.root != null) {
               
            }

        } catch (Exception e) {
            log.error("Error expandiendo: " + e.getMessage(), e);
        }
    }

    private void cargarHijosSegunTipo(TreeNode node, Document document) throws Exception {
        Object docObj = document.getObject();
        if (docObj instanceof FuncioObject) {
            this.servicesFunciones.expandQuadre(cuadroSeleccionado, node, ((FuncioObject) docObj).getId());
        } else if (docObj instanceof SerieDocumentalObject) {
            this.servicesFunciones.expandSerie(cuadroSeleccionado, node, ((SerieDocumentalObject) docObj).getSerieId());
        }
    }

    
    
    public TreeNode findNodeById(TreeNode searchRoot, Document targetDoc) {
        if (searchRoot == null || targetDoc == null) return null;
        
        Document currentDoc = (Document) searchRoot.getData();
        if (currentDoc != null && currentDoc.getId() == targetDoc.getId()) {
            return searchRoot;
        }
        
        for (TreeNode child : searchRoot.getChildren()) {
            TreeNode found = findNodeById(child, targetDoc);
            if (found != null) return found;
        }
        return null;
    }
    
    private String limpiarAcentos(String texto) {
	    if (texto == null) return "";
	    
	    String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
	    return DIACRITICS.matcher(normalizado)
	                     .replaceAll("")
	                     .toLowerCase();
	}
    
    public void filtrarTreeNode() throws I18NException {
    	this.filterNode = null;
    	
    	if (cumpleRequisitosBusqueda(searchText)) {
    		
    		String s = limpiarAcentos(searchText);
    		
    		this.root = new DefaultTreeNode("root", null);
    		
    		List<DictamenObject> dictamenSeleccionados = servicesFunciones.filtrarDictamenes(s);
    		List<SerieDocumentalObject> seriesSeleccionadas = servicesFunciones.filtrarSeries(s);
    		
    		Map<Long, FuncioObject> funcioMap2 = new HashMap<>();
    		
    		for (DictamenObject dict: dictamenSeleccionados) {
    			FuncioObject padre = servicesFunciones.extraerFuncionRaizDeSerie(dict.getSerieDocumental());
				funcioMap2.put(padre.getId(), padre);
			}
    		
    		for (SerieDocumentalObject serie: seriesSeleccionadas) {
    			FuncioObject padre = servicesFunciones.extraerFuncionRaizDeSerie(serie);
				funcioMap2.put(padre.getId(), padre);
			}
    		
    		List<FuncioObject> funcioSeleccionadas = servicesFunciones.filtrarFunciones(s, funcioMap2.keySet());
    		
    		Map<Long, TreeNode> funcioMap = new HashMap<>();	

    		for (FuncioObject seleccionado: funcioSeleccionadas) {
    			if (Objects.equals(seleccionado.getCodigoCuadro().getId(), cuadroSeleccionado.getId())) {
        			servicesFunciones.crearTreeNodeFuncio(seleccionado, funcioMap, root);
    			}
    		}
    		
    		Map<Long, TreeNode> serieMap = new HashMap<>();
    		
    		for (SerieDocumentalObject serie: seriesSeleccionadas) {
    			if (Objects.equals(serie.getFuncio().getCodigoCuadro().getId(), cuadroSeleccionado.getId())) {
    				servicesFunciones.crearTreeNodeSerie(serie, funcioMap, serieMap, root);
    			}
    		}

    		dictamenSeleccionados.sort((n1, n2) -> {

                BigDecimal cod1 = n1.getSerieDocumental().getFuncio().getOrdre();
                BigDecimal cod2 = n2.getSerieDocumental().getFuncio().getOrdre();

                if (cod1 == null) return 1;
                if (cod2 == null) return -1;
                
                return cod1.compareTo(cod2);
            });
    		
    		Map<Long, TreeNode> dictamenMap = new HashMap<>();
    		
    		for (DictamenObject seleccionado: dictamenSeleccionados) {
    			if (Objects.equals(seleccionado.getSerieDocumental().getFuncio().getCodigoCuadro().getId(), cuadroSeleccionado.getId())) {
    				servicesFunciones.crearTreeNodeDictamen(seleccionado, funcioMap, serieMap, dictamenMap, root);
    			}
    		}
    		
    		for (TreeNode node: serieMap.values()) {
    			if (node.getChildren().isEmpty()) {
					new DefaultTreeNode("DUMMY", new Document<String>(-1L, "DUMMY", "DUMMY", "DUMMY", "DUMMY"), node);
    				node.setExpanded(false);
    			}
    		}
    		
    		for (TreeNode node: funcioMap.values()) {
    			if (node.getChildren().isEmpty()) {
					new DefaultTreeNode("DUMMY", new Document<String>(-1L, "DUMMY", "DUMMY", "DUMMY", "DUMMY"), node);
    				node.setExpanded(false);
    			}
    		}
    		
    		ordenarHijos(root.getChildren());    		
    		
    		this.filterNode = this.root;
    		PrimeFaces.current().executeScript("PF('treeTable').clearFilters()");
    	} else {
    		if (searchText == null || searchText.trim().isEmpty()) {
	    		try {
					reiniciarVista();
				} catch (IOException e) {
					e.printStackTrace();
				}
    		}
    	}
    }
    
    
    private List<TreeNode> ordenarHijos(List<TreeNode> children) {
    	
    	if (children == null || children.isEmpty()) {
    		return children;
    	}
    	
    	if (children.get(0).getData() instanceof Document) {
    		Object obj = ((Document<?>) children.get(0).getData()).getObject();
            if (!(obj instanceof FuncioObject)) {
            	return children;
            }
    	}
        
    	children.sort((n1, n2) -> {
            FuncioObject f1 = getFuncioObject(n1);
            FuncioObject f2 = getFuncioObject(n2);

            if (f1 == null && f2 == null) return 0;
            if (f1 == null) return 1;
            if (f2 == null) return -1;

            Long id1 = (f1.getTipoSerie() != null) ? f1.getTipoSerie().getId() : null;
            Long id2 = (f2.getTipoSerie() != null) ? f2.getTipoSerie().getId() : null;

            int compTipo = compareNullsLast(id1, id2);
            
            if (compTipo != 0) {
                return compTipo;
            }

            return compareNullsLast(f1.getOrdre(), f2.getOrdre());
        });
        
        for (TreeNode child : children) {
            ordenarHijos(child.getChildren());
        }
        
        return children;
    }

    private FuncioObject getFuncioObject(TreeNode node) {
        if (node != null && node.getData() instanceof Document) {
            Object obj = ((Document<?>) node.getData()).getObject();
            if (obj instanceof FuncioObject) {
                return (FuncioObject) obj;
            }
        }
        return null;
    }

    private <T extends Comparable<T>> int compareNullsLast(T v1, T v2) {
        if (v1 == v2) return 0;
        if (v1 == null) return 1;
        if (v2 == null) return -1;
        return v1.compareTo(v2);
    }

	private boolean cumpleRequisitosBusqueda(String text) {
        if (text == null || text.trim().isEmpty()) {
            return false;
        }
        
        String cleanText = text.trim().toLowerCase();
        int longitud = cleanText.length();

        if (cleanText.startsWith("sd") || cleanText.startsWith("pv")) {
            return longitud >= 3; 
        }
        
        return longitud >= 2;
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
//    	this.clearSelection();
//    	this.searchText = null;
//        this.filterNode = null;
//    	try {
//			this.root = this.servicesFunciones.getContent(cuadroSeleccionado);	
//			this.filterNode = this.root.getParent();
//			this.listaFunciones =  	new ArrayList();
//			 PrimeFaces.current().executeScript("PF('treeTable').clearFilters()");
////			this.listaFunciones = this.servicesFunciones.loadTree(cuadroSeleccionado.getId(), null);
//		} catch (I18NException e) {
//			FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.quadre.load"), null));
//			log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
//		}
    	try {
			reiniciarVista();
		} catch (IOException e) {
			e.printStackTrace();
		}
    }
    
    public void changeFuncioPare(){  
    	if(!this.checkFuncioPareValid(this.funcionActualizar, funcioPare)) {
    		FacesContext.getCurrentInstance().validationFailed();
    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.funciopare.conflict"), null));
    	}
    }
    
    private Boolean checkFuncioPareValid(FuncioObject funcio, FuncioObject funcioPare) {
    	if(funcio==null) return true;
    	if(funcio.equals(funcioPare)) return false;
    	try {
			if(this.servicesFunciones.loadTree(cuadroSeleccionado.getId(), funcio.getId()).contains(funcioPare)) return false;
		} catch (I18NException e) {
			log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
			return false;
		}    	
    	return true;
    }
    
    public void limpiarFiltros() throws I18NException {
        this.searchText = null;
        this.filterNode = null;
        this.root = this.servicesFunciones.getContent(cuadroSeleccionado);
        this.filterNode = this.root.getParent();
    }
    
    public void reiniciarVista() throws IOException {
        Long quadreIdGuardado = cuadroSeleccionado.getId();

        FacesContext fc = FacesContext.getCurrentInstance();
        String ctx = fc.getExternalContext().getRequestContextPath();

        String url = ctx + "/views/funciones.xhtml" +
                     (quadreIdGuardado != null ? "?quadreId=" + quadreIdGuardado : "");

        fc.getExternalContext().redirect(url);
        fc.responseComplete();
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


	public FuncioFrontService getServicesFunciones() {
		return servicesFunciones;
	}

	public void setServicesFunciones(FuncioFrontService servicesFunciones) {
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

	public QuadreFrontService getServicesCuadroClasificacion() {
		return servicesCuadroClasificacion;
	}

	public void setServicesCuadroClasificacion(QuadreFrontService servicesCuadroClasificacion) {
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

	public Boolean getError() {
		return error;
	}

	public void setError(Boolean error) {
		this.error = error;
	}

	public Locale getLocale() {
		return locale;
	}

	public String getSearchText() {
		return searchText;
	}

	public void setSearchText(String searchText) {
		this.searchText = searchText;
	}


	
	
}

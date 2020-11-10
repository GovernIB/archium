package es.caib.archium.controllers;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.interceptor.Logged;
import es.caib.archium.objects.*;
import es.caib.archium.services.FuncioFrontService;
import es.caib.archium.services.QuadreFrontService;
import es.caib.archium.utils.FrontExceptionTranslate;
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

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.servlet.http.HttpServletRequest;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.*;

@Logged
@Named("funciones")
@ViewScoped
public class FuncionesController implements Serializable {

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
    @Inject
    private ExternalContext externalContext;

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

    private List<String> listaEstats = new ArrayList<>();

    private Long quadreId;

    ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");

    private Boolean error = false;


    @PostConstruct
    public void init() throws ClassNotFoundException {

        try {
            listaEstats.add(messageBundle.getString("general.estats.esborrany"));
            listaEstats.add(messageBundle.getString("general.estats.revisat"));
            listaEstats.add(messageBundle.getString("general.estats.publicable"));
            listaEstats.add(messageBundle.getString("general.estats.vigent"));
            listaEstats.add(messageBundle.getString("general.estats.obsolet"));

            this.listaCuadrosClasificacion = this.servicesCuadroClasificacion.findAll();

            FacesContext context = FacesContext.getCurrentInstance();
            String quadreIdString = context.getExternalContext().getRequestParameterMap().get("quadreId");

            locale = context.getViewRoot().getLocale();

            if (quadreIdString != null) {
                quadreId = Long.parseLong(quadreIdString);
            }

            if (quadreId == null) {
                if (listaCuadrosClasificacion.size() > 0) {
                    quadreId = listaCuadrosClasificacion.get(0).getId();
                }
            }

            if (quadreId == null) {
                TreeNode root = new DefaultTreeNode(new Document(0, "All", "All", "All", null, null, null), null);
            } else {
                this.cuadroSeleccionado = this.servicesCuadroClasificacion.getQuadreById(quadreId);
                this.root = this.servicesFunciones.getContent(cuadroSeleccionado);
                this.filterNode = this.root.getParent();
                this.listaFunciones = this.servicesFunciones.loadTree(quadreId, null);
                this.listaTipusserie = this.servicesFunciones.findAllTipusSerie();
            }

            FacesContext ctxt = FacesContext.getCurrentInstance(); //get your hands on the current request context
            ctxt.getPartialViewContext().getRenderIds().add("panelButtons");

        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
            error = true;
        }
    }


    public void setFuncioActualizar(Document<FuncioObject> d) {
        this.clearSelection();
        this.clearForm();
        if (d == null) {
            this.funcionActualizar = null;
        } else {

            try {
                FuncioObject f = this.servicesFunciones.findById(d.getId());

                if (f != null) {
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

    public void setFuncioSeleccionada(Document<FuncioObject> d) {
        this.clearSelection();
        clearForm();
        this.funcionActualizar = null;

        if (d == null) {
            this.funcionSeleccionada = null;
        } else {
            this.funcionSeleccionada = d.getObject();
            this.funcioPare = d.getObject();
        }
    }

    public void sincronizar(Document<FuncioObject> func) {
        log.debug("Se sincroniza la funcion: " + func.toString());

        try {
            this.servicesFunciones.synchronize(func.getObject().getId());
            //TODO: Cambiar i18n
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Funcion Sincronizada"));
        } catch (Exception e) {
            //TODO: Excepciones...
        }
    }


    public void deleteFuncio(Document<FuncioObject> f) {


        try {
            this.servicesFunciones.deleteFuncio(f.getId());
            TreeNode node = this.getNodeFromFunctionId(f.getId(), "Funcio", "update", f);
            node.getParent().getChildren().remove(node);
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("funciones.delete.ok")));
        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.delete.error"), null));
        }
    }

    public void update() {


        try {
            //TreeNode oldParent = this.getNodeFromFunctionId(funcionActualizar.getFuncioPare() == null ? null:  funcionActualizar.getFuncioPare().getId(), "Funcio", "insert", null);
            FuncioObject f = this.funcionActualizar;
            TreeNode oldParent = null;
            if (f.getFuncioPare() != null) {
                oldParent = this.getNodeFromFunctionId(funcionActualizar.getFuncioPare().getId(), "Funcio", "insert", null);
            }

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
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.funciopare.conflict"), null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            } else {
                FuncioObject upF = this.servicesFunciones.update(f);

                this.listaFunciones = this.servicesFunciones.loadTree(quadreId, null);

                if (oldParent != null) {
                    TreeNode node = this.getNodeFromFunctionId(upF.getId(), "Funcio", "update", f);
                    oldParent.getChildren().remove(node);
                    TreeNode parentNode;
                    if (funcioPare != null) {
                        parentNode = getNodeFromFunctionId(this.funcioPare.getId(), "Funcio", "insert", null);
                    } else {
                        parentNode = root;
                    }
                    node.setParent(parentNode);
                    parentNode.getChildren().add(node);
                } else {
                    TreeNode node = this.getNodeFromFunctionId(upF.getId(), "Funcio", "update", f);
                    if (funcioPare != null) {
                        root.getChildren().remove(node);
                        TreeNode parentNode = getNodeFromFunctionId(this.funcioPare.getId(), "Funcio", "insert", null);
                        parentNode.getChildren().add(node);
                        node.setParent(parentNode);
                    }
                }

                //oldParent.getChildren().remove(node);
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("funciones.update.ok")));
            }

        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.update.error"), null));
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
            TreeNode node = new DefaultTreeNode(new Document<FuncioObject>(newF.getId(), newF.getCodi(), newF.getNom(),
                    "Funcio", newF.getNodeId(), newF.isSynchronized(), newF), (this.funcioPare != null) ?
                    this.getNodeFromFunctionId(this.funcioPare.getId(), "Funcio", "insert", null)
                    : this.root);

            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString("funciones.insert.ok")));

        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.insert.error"), null));
        }
    }

    public <T> TreeNode getNodeFromFunctionId(long id, String type, String action, T updateObject) {
        Optional<TreeNode> node = this.recursiveTreeNode(this.root, id, type, action, updateObject);

        if (node.isEmpty()) {
            return this.root;
        } else {
            return node.get();
        }
    }

    private <T> Optional<TreeNode> recursiveTreeNode(TreeNode node, long id, String type, String action, T updateObject) {
        Document<T> d = (Document<T>) node.getData();
        if (d.getId() == id && d.getType() == type) {
            if (action == "update") {
                String typeObject = updateObject.getClass().getSimpleName();
                switch (typeObject) {
                    case "FuncioObject":
                        FuncioObject f = (FuncioObject) updateObject;
                        Document<FuncioObject> fdoc = new Document<FuncioObject>(f.getId(), f.getCodi(), f.getNom(), "Funcio", f.getNodeId(), f.isSynchronized(), f);
                        ((DefaultTreeNode) node).setData(fdoc);
                        break;
                    case "SerieDocumentalObject":
                        SerieDocumentalObject s = (SerieDocumentalObject) updateObject;
                        Document<SerieDocumentalObject> sdoc = new Document<SerieDocumentalObject>(s.getSerieId(), s.getCodi(), s.getNom(), "Serie", s.getNodeId(), s.isSynchronized(), s);
                        ((DefaultTreeNode) node).setData(sdoc);
                        break;
                    case "DictamenObject":
                        DictamenObject di = (DictamenObject) updateObject;
                        Document<DictamenObject> fun = new Document<DictamenObject>(di.getId(), di.getCodi(), di.getAccioDictaminada(), "Dictamen", di.getEstat(), di);
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
            for (TreeNode n : node.getChildren()) {
                Optional<TreeNode> t = this.recursiveTreeNode(n, id, type, action, updateObject);
                if (t.isPresent()) return t;
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

        if (this.infoSelectedElement.isEmpty() || event.getTreeNode() != this.nodoSeleccionado) {


            this.infoSelectedElement = new LinkedHashMap<String, String>();

            String type = ((Document) dataObject).getType();

            if (type == "Funcio") {
                Document<FuncioObject> f = (Document<FuncioObject>) dataObject;
                this.infoSelectedElement.put("type", messageBundle.getString("nuevafuncion.type"));
                this.infoSelectedElement.put("id", String.valueOf(f.getObject().getId()));
                this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.codi"), String.valueOf(f.getObject().getCodi()));
                this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.nom"), String.valueOf(f.getObject().getNom()));
                this.infoSelectedElement.put("nodeId", String.valueOf(f.getObject().getNodeId()));
                this.infoSelectedElement.put("synchronized", String.valueOf(f.getObject().isSynchronized()));

                String nomcas = f.getObject().getNomcas();
                if (nomcas != null) {
                    this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.nomcas"), nomcas);
                }

                this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.cuadro"), f.getObject().getCodigoCuadro().getNom());

                FuncioObject fpare = f.getObject().getFuncioPare();
                if (fpare != null) {
                    this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.funcionpadre"), fpare.getNom());
                }

                TipusSerieObject tserie = f.getObject().getTipoSerie();
                if (tserie != null) {
                    this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.tiposerie"), tserie.getNom());
                }

                this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.estado"), f.getObject().getEstat());
                this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.orden"), String.valueOf(f.getObject().getOrdre()));
                this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.inici"), String.valueOf(f.getObject().getInici()));
                this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.modificacio"), String.valueOf(f.getObject().getModificacio()));

                Date fi = f.getObject().getFi();
                if (fi != null) {
                    this.infoSelectedElement.put(messageBundle.getString("nuevafuncion.fi"), String.valueOf(fi));
                }
                this.nodoSeleccionado = event.getTreeNode();
            } else if (type == "Serie") {
                Document<SerieDocumentalObject> f = (Document<SerieDocumentalObject>) dataObject;
                this.infoSelectedElement.put("type", messageBundle.getString("nuevaserie.type"));
                this.infoSelectedElement.put("id", String.valueOf(f.getObject().getSerieId()));
                this.infoSelectedElement.put(messageBundle.getString("nuevaserie.codiserie"), String.valueOf(f.getObject().getCodi()));
                this.infoSelectedElement.put(messageBundle.getString("nuevaserie.nombreserie"), String.valueOf(f.getObject().getNom()));
                this.infoSelectedElement.put("nodeId", String.valueOf(f.getObject().getNodeId()));
                this.infoSelectedElement.put("synchronized", String.valueOf(f.getObject().isSynchronized()));
                //Seriedocumental data = serie
                String nomcas = f.getObject().getNomCas();
                if (nomcas != null) {
                    this.infoSelectedElement.put(messageBundle.getString("nuevaserie.nombrecastellano"), nomcas);
                }

                FuncioObject fnc = f.getObject().getFuncio();
                if (fnc != null) {
                    this.infoSelectedElement.put(messageBundle.getString("nuevaserie.codifuncion"), String.valueOf(fnc.getCodi() + "-" + fnc.getNom()));
                }
	    		
	    		/*TipusserieObject tserie = f.getObject().getTipoSerie();
	    		if(tserie!=null) {
	    			this.infoSelectedElement.put("Tipu serie", tserie.getNom());
	    		}*/

                this.infoSelectedElement.put(messageBundle.getString("nuevaserie.descripcio"), f.getObject().getDescripcio());
                if (f.getObject().getDescripcioCas() != null) {
                    this.infoSelectedElement.put(messageBundle.getString("nuevaserie.descripciocas"), String.valueOf(f.getObject().getDescripcioCas()));
                }

                if (f.getObject().getDir3Promotor() != null) {
                    this.infoSelectedElement.put(messageBundle.getString("nuevaserie.dir3prom"), f.getObject().getDir3Promotor());
                }

                if (f.getObject().getCodiIecisa() != null) {
                    this.infoSelectedElement.put(messageBundle.getString("nuevaserie.codiiecisa"), f.getObject().getCodiIecisa());
                }

                this.infoSelectedElement.put(messageBundle.getString("nuevaserie.estat"), f.getObject().getEstat());

                this.nodoSeleccionado = event.getTreeNode();
            } else if (type == "Dictamen") {
                Document<DictamenObject> d = (Document<DictamenObject>) dataObject;
                this.infoSelectedElement.put("type", messageBundle.getString("dictamen.type"));
                this.infoSelectedElement.put("id", String.valueOf(d.getObject().getId()));
                this.infoSelectedElement.put(messageBundle.getString("nuevodictamen.codi"), String.valueOf(d.getObject().getCodi()));
                this.infoSelectedElement.put(messageBundle.getString("dictamen.serieDoc"), String.valueOf(d.getObject().getSerieDocumental().getCodi()));

                TipuDictamenObject td = d.getObject().getTipusdictamen();
                if (td != null) {
                    this.infoSelectedElement.put(messageBundle.getString("dictamen.tipuDic"), td.getNom());
                }

                TipuAccesObject ta = d.getObject().getTipusAcces();
                if (ta != null) {
                    this.infoSelectedElement.put(messageBundle.getString("dictamen.tipuAcc"), ta.getNom());
                }

                this.infoSelectedElement.put(messageBundle.getString("dictamen.normApro"), d.getObject().getNormativaAprovacio().getCodi());

                EnsObject ens = d.getObject().getEns();
                if (ens != null) {
                    this.infoSelectedElement.put(messageBundle.getString("dictamen.ens"), ens.getNom());
                }

                LopdObject lopd = d.getObject().getLopd();
                if (lopd != null) {
                    this.infoSelectedElement.put(messageBundle.getString("dictamen.lopd"), lopd.getNom());
                }

                this.infoSelectedElement.put(messageBundle.getString("dictamen.termini"), d.getObject().getTerminiFormat());


                String accd = d.getObject().getAccioDictaminada();
                if (accd != null) {
                    this.infoSelectedElement.put(messageBundle.getString("dictamen.accion"), accd);
                }

                String cr = d.getObject().getCondicioReutilitzacio();
                if (cr != null) {
                    this.infoSelectedElement.put(messageBundle.getString("dictamen.condicioReu"), cr);
                }

                String dr = d.getObject().getDestinatarisRestrigits();
                if (dr != null) {
                    this.infoSelectedElement.put(messageBundle.getString("dictamen.destRest"), dr);
                }

                Boolean se = d.getObject().getSerieEsencial();
                if (se != null) {
                    this.infoSelectedElement.put(messageBundle.getString("nuevodictamen.serieEsencial"), (se ? messageBundle.getString("si") : messageBundle.getString("no")));
                }

                this.infoSelectedElement.put(messageBundle.getString("dictamen.dataIni"), String.valueOf(d.getObject().getInici()));

                if (d.getObject().getAprovacio() != null) {
                    this.infoSelectedElement.put(messageBundle.getString("dictamen.dataApro"), String.valueOf(d.getObject().getAprovacio()));
                }
                Date fi = d.getObject().getFi();
                if (fi != null) {
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

    private void clearForm() {

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
        if (this.selectedNode != null) {
            this.selectedNode.setSelected(false);
            this.nodoSeleccionado = null;
            this.infoSelectedElement = new LinkedHashMap<String, String>();
        }
    }

    public void onQuadreSelectedListener(AjaxBehaviorEvent event) {
        this.clearSelection();
        try {
            this.root = this.servicesFunciones.getContent(cuadroSeleccionado);
            this.filterNode = this.root.getParent();
            this.listaFunciones = this.servicesFunciones.loadTree(cuadroSeleccionado.getId(), null);
        } catch (I18NException e) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.quadre.load"), null));
            log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
        }
    }

    public void changeFuncioPare() {
        if (!this.checkFuncioPareValid(this.funcionActualizar, funcioPare)) {
            FacesContext.getCurrentInstance().validationFailed();
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("funciones.funciopare.conflict"), null));
        }
    }

    private Boolean checkFuncioPareValid(FuncioObject funcio, FuncioObject funcioPare) {
        if (funcio == null) return true;
        if (funcio.equals(funcioPare)) return false;
        try {
            if (this.servicesFunciones.loadTree(cuadroSeleccionado.getId(), funcio.getId()).contains(funcioPare))
                return false;
        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, this.getLocale()));
            return false;
        }
        return true;
    }

    /**
     * Comprueba el rol con el que está logueado el usuario
     *
     * @param rol
     * @return
     */
    public boolean checkRolLogued(String rol) {
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        if (request.isUserInRole(rol)) {
            return true;
        }
        return false;
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

    public ExternalContext getExternalContext() {
        return externalContext;
    }

    public void setExternalContext(ExternalContext externalContext) {
        this.externalContext = externalContext;
    }
}

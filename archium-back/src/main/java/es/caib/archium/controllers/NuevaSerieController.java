package es.caib.archium.controllers;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.AplicacionObject;
import es.caib.archium.objects.CatalegSeriesObject;
import es.caib.archium.objects.CausaLimitacioObject;
import es.caib.archium.objects.Dir3Object;
import es.caib.archium.objects.Document;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.LimitacioNormativaSerieObject;
import es.caib.archium.objects.NormativaAprobacioObject;
import es.caib.archium.objects.OrganCollegiatObject;
import es.caib.archium.objects.ProcedimentObject;
import es.caib.archium.objects.RelacioSerieObject;
import es.caib.archium.objects.SerieArgenObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.objects.TipuDocumentalObject;
import es.caib.archium.objects.TipuDocumentalSerieObject;
import es.caib.archium.objects.TipuValorObject;
import es.caib.archium.objects.TipusRelacioSerieObject;
import es.caib.archium.objects.TipusSerieObject;
import es.caib.archium.objects.ValorPrimariObject;
import es.caib.archium.objects.ValorSecundariObject;
import es.caib.archium.objects.ValoracioObject;
import es.caib.archium.services.SerieFrontService;
import es.caib.archium.utils.FrontExceptionTranslate;
import org.primefaces.PrimeFaces;
import org.primefaces.model.DefaultTreeNode;
import org.primefaces.model.DualListModel;
import org.primefaces.model.TreeNode;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;

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

    private Boolean enviatSAT;

    private String dir3Promotor;
    private Long tipusSerieId;
    private String codiIecisa;
    private String funcionNom;
    private FuncioObject serieFuncio;
    private String serieEstat;
    private ValoracioObject valoracio;
    private Long oldParentFunction;

    private Long organCollegiatId;

    private SerieDocumentalObject modify;

    @Inject
    private SerieFrontService service;

    private List<Dir3Object> listaDir3;

    private List<OrganCollegiatObject> listaOrganCollegiat;

    private List<TipuDocumentalObject> listatipoDocumental;


    private List<CatalegSeriesObject> listaCatalegSerie;
    private List<FuncioObject> listaFunciones;
    private List<TipusSerieObject> listaTipusSerie;

    private List<ProcedimentObject> llistaProcediments;

    private List<ProcedimentObject> llistaProcedimentsComuns;
    private List<AplicacionObject> listaAplicaciones;

    private List<SerieDocumentalObject> listaSeriesSelected;
    private List<AplicacionObject> listaAplicacionesSelected;
    private DualListModel<AplicacionObject> aplicaciones;

    private DualListModel<ProcedimentObject> procedimentsRelacionats;
    private List<ProcedimentObject> llistaProcedimentsSelected;

    private DualListModel<ProcedimentObject> procedimentsComunsRelacionats;

    private List<ProcedimentObject> llistaProcedimentsComunsSelected;

    private List<NormativaAprobacioObject> listaNormativaAprobacio;

    private List<TipusRelacioSerieObject> listaTipusRelacio;
    private List<CausaLimitacioObject> listaCausaLimitacio;

    private List<LimitacioNormativaSerieObject> listaRelacionLNS;

    private List<RelacioSerieObject> listaRelacionSeries;

    private List<SerieDocumentalObject> listaSerieDocumental;

    private NormativaAprobacioObject selectedNormativa;

    private TipusRelacioSerieObject selectedTipusRelacio;

    private TipuDocumentalObject selectedTipoDocumental;
    private List<TipuDocumentalSerieObject> listaTDP = new ArrayList<TipuDocumentalSerieObject>();

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
    public void init() {

        UNIDAD_PLAZO_SERIE_DEFAULT_MESSAGE = messageBundle.getString("general.plazos.anys");

        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
        this.funcBean = (FuncionesController) viewMap.get("funciones");

        seriesArgenSelected = new ArrayList<>();
        listaSeriesSelected = new ArrayList<>();
        llistaProcedimentsSelected = new ArrayList<>();
        llistaProcedimentsComunsSelected = new ArrayList<>();
        listaAplicacionesSelected = new ArrayList<>();
        normativasSerieSelected = new ArrayList<>();
        listaRelacionLNS = new ArrayList<>();
        listaRelacionSeries = new ArrayList<>();
        valoracio = new ValoracioObject();

        try {
            listaDir3 = service.getListaDir3();
            listaOrganCollegiat = service.getListaOrgansCollegiats();

            listaTiposValor = service.getListaTipusValor();
            listaValoresSecundarios = service.getListaValorsSecundari();

            listatipoDocumental		= 	service.findAllTipoDocumental();
            listaCatalegSerie = service.getListaCatalegSerie();
            listaOrganCollegiat = service.getListaOrgansCollegiats();
            listaFunciones = service.getListaFunciones();
            listaTipusSerie = service.getListaTipusSerie();
            llistaProcediments = service.getListaProcediments();
            llistaProcedimentsComuns = service.getListaProcedimentsSenseSerie();
            listaAplicaciones = service.getListaAplicaciones();
            listaNormativaAprobacio = service.getListaNormativas();
            listaTipusRelacio = service.getListaTipusRelacio();
            listaCausaLimitacio = service.getListaCausaLimitacio();
            listaSerieDocumental = service.getListaSeries(serieId);
            listaSeriesArgen = service.getListaSeriesArgen();

            for (TipuValorObject tv : listaTiposValor) {
                ValorPrimariObject vp = new ValorPrimariObject();
                vp.setAchTipusvalor(tv);
                vp.setTerminiType(UNIDAD_PLAZO_SERIE_DEFAULT_MESSAGE);
                valoracio.addValorprimari(vp);
            }

        } catch (I18NException e) {
            log.error("Problema cargando nueva Serie", e);
            listaAplicaciones = new ArrayList<>();
            listaNormativaAprobacio = new ArrayList<>();
            listaTipusRelacio = new ArrayList<>();
            llistaProcediments = new ArrayList<>();
            llistaProcedimentsComuns = new ArrayList<>();
            listaSeriesArgen = new ArrayList<>();
            funcBean.setError(true);
            log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
        } finally {
            aplicaciones = new DualListModel<>(listaAplicaciones, listaAplicacionesSelected);
            procedimentsRelacionats = new DualListModel<>(llistaProcediments, llistaProcedimentsSelected);
            procedimentsComunsRelacionats = new DualListModel<>(llistaProcedimentsComuns, llistaProcedimentsComunsSelected);
            seriesArgenRelacionadas = new DualListModel<>(listaSeriesArgen, seriesArgenSelected);
            normativasSerie = new DualListModel<>(listaNormativaAprobacio, normativasSerieSelected);
        }

    }

    public void loadFromFunction(Document<FuncioObject> d) {
        clearForm();
        if (d == null) {
            serieFuncio = null;
        } else {
            serieFuncio = d.getObject();
            if (serieFuncio.getTipoSerie() != null) {
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


    /**
     * Carrega els valors de la sèrie al modal d'edició
     * @param obj
     */
    public void updateSerie(Document<SerieDocumentalObject> obj) {

        clearForm();

        try {
            modify = this.service.findById(obj.getId());
            log.debug("modify = " + modify);
            if (modify != null) {
                serieId = obj.getId();
                log.debug("serieId = " + serieId);
                oldParentFunction = obj.getObject().getFuncio().getId();
                codi = modify.getCodi();
                nom = modify.getNom();
                nomCas = modify.getNomCas();
                catalegSeriId = modify.getCatalegSeriId();
                serieFuncio = modify.getFuncio();
                descripcio = modify.getDescripcio();
                descripcioCas = modify.getDescripcioCas();
                resumMigracio = modify.getResumMigracio();
                enviatSAT = modify.getEnviatSAT();
                dir3Promotor = modify.getDir3Promotor();

                tipusSerieId = modify.getTipusSerieId();

                codiIecisa = modify.getCodiIecisa();
                serieFuncio = modify.getFuncio();
                serieEstat = (modify.getEstat() != null ? modify.getEstat() : "ESBORRANY");

                List<AplicacionObject> targetAppsList;

                List<SerieArgenObject> relatedArgensList;
                List<NormativaAprobacioObject> normativasList;

                targetAppsList = service.getListaAplicacionesBySerie(serieId);
                List<AplicacionObject> filteredApps = new ArrayList<>(aplicaciones.getSource());
                filteredApps.removeAll(targetAppsList);
                aplicaciones.setSource(filteredApps);
                aplicaciones.setTarget(targetAppsList);

                // Carregar les dades dels procediments:
                // target: Procediments associats directament a la sèrie
                // source: Procediments sense sèrie vinculada i sense trobar-se a comuns
                List<ProcedimentObject> procedimentsBySerie = service.getListaProcedimentsBySerie(serieId);

                List<ProcedimentObject> procedimentsSenseSerie = service.getListaProcedimentsSenseSerie();
                List<ProcedimentObject> procedimentsComuns = service.getTotsProcedimentsComuns();

                List<ProcedimentObject> procedimentsSource = new ArrayList<>(procedimentsSenseSerie);
                procedimentsSource.removeAll(procedimentsComuns);
                procedimentsRelacionats.setSource(procedimentsSource);
                procedimentsRelacionats.setTarget(procedimentsBySerie);

                // Carregar les dades dels procediments comuns de la sèrie:
                // target: Procediments comuns vinculats a la sèrie
                // source: Procediments sense sèrie vinculada (Taula Procediment amb idSerie NULL)
                List<ProcedimentObject> procedimentsComunsBySerie = service.getProcedimentsComunsBySerie(serieId);

                // Sense sèrie menys els ja assignats a la sèrie
                List<ProcedimentObject> procedimentsComunsSource = new ArrayList<>(procedimentsSenseSerie);
                procedimentsComunsSource.removeAll(procedimentsComunsBySerie);

                procedimentsComunsRelacionats.setSource(procedimentsComunsSource);
                procedimentsComunsRelacionats.setTarget(procedimentsComunsBySerie);


                relatedArgensList = service.getListaSeriesArgenBySerie(serieId);
                List<SerieArgenObject> filteredArgen = new ArrayList<>(seriesArgenRelacionadas.getSource());
                filteredArgen.removeAll(relatedArgensList);
                seriesArgenRelacionadas.setSource(filteredArgen);
                seriesArgenRelacionadas.setTarget(relatedArgensList);

                listaRelacionLNS = service.getListaLNS(serieId);
                for (LimitacioNormativaSerieObject lns : listaRelacionLNS) {
                    List<CausaLimitacioObject> filteredCL = new ArrayList<>(listaCausaLimitacio);
                    filteredCL.removeAll(lns.getListCausaLimitacio());
                    lns.setDualListCausas(new DualListModel<>());
                    lns.getDualListCausas().setSource(filteredCL);
                    lns.getDualListCausas().setTarget(lns.getListCausaLimitacio());
                }

                listaRelacionSeries = service.getListaRelacions(serieId);
                log.debug("Series relacionades (no considerant Argen): " +  listaRelacionSeries.size());
                for (RelacioSerieObject rel : listaRelacionSeries) {
                    List<SerieDocumentalObject> filteredCL = new ArrayList<>(listaSerieDocumental);
                    filteredCL.removeAll(rel.getSeriesRelacionades());
                    rel.setDualListSeries(new DualListModel<>());
                    rel.getDualListSeries().setSource(filteredCL);
                    rel.getDualListSeries().setTarget(new ArrayList<>(rel.getSeriesRelacionades()));
                }

                normativasList = service.getListaNormativasBySerie(serieId);
                List<NormativaAprobacioObject> filteredNormativas = new ArrayList<NormativaAprobacioObject>(normativasSerie.getSource());
                filteredNormativas.removeAll(normativasList);
                normativasSerie.setSource(filteredNormativas);
                normativasSerie.setTarget(normativasList);


                ValoracioObject dbValoracio = service.getValoracioSerie(serieId);
                valoracio = new ValoracioObject(dbValoracio);
                valoracio.getAchValorprimaris().clear();
                for (TipuValorObject tv : listaTiposValor) {

                    boolean existe = false;
                    Iterator<ValorPrimariObject> it = dbValoracio.getAchValorprimaris().iterator();
                    while (it.hasNext() && !existe) {
                        ValorPrimariObject item = it.next();
                        if (Objects.equals(tv.getId(), item.getAchTipusvalor().getId())) {
                            item.setSelected(true);
                            valoracio.addValorprimari(item);
                            existe = true;
                        }
                    }

                    if (!existe) {

                        ValorPrimariObject vp = new ValorPrimariObject();
                        vp.setTerminiType(UNIDAD_PLAZO_SERIE_DEFAULT_MESSAGE);
                        vp.setAchTipusvalor(tv);
                        ValoracioObject val = new ValoracioObject();
                        val.setId(dbValoracio.getId());
                        vp.setAchValoracio(val);
                        valoracio.addValorprimari(vp);
                    }

                }

                listaTDP = service.getListaTDP(serieId);

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

    public void save() {
        try {
            if (this.validateValoracion()) {

                if (!this.service.checkValidClassificationCode(codi, serieId)) {
                    log.error("Error en la creacion de la serie, ya existe otra serie con el codigo [" + codi + "]");
                    FacesContext.getCurrentInstance().validationFailed();
                    FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.codigo"), null);
                    FacesContext.getCurrentInstance().addMessage(null, message);
                    return;
                }

                String messageKey;
                if (serieId == null) {
                    SerieDocumentalObject newS = service.createNuevaSerie(codi, nom, nomCas, catalegSeriId, serieFuncio.getId()
                            , descripcio, descripcioCas, resumMigracio, enviatSAT, dir3Promotor, serieEstat,
                            tipusSerieId, codiIecisa, aplicaciones.getTarget(), procedimentsRelacionats.getTarget(), procedimentsComunsRelacionats.getTarget(), listaRelacionSeries, seriesArgenRelacionadas.getTarget(), listaRelacionLNS, normativasSerie.getTarget(), valoracio, organCollegiatId, listaTDP);


                    TreeNode node = new DefaultTreeNode(new Document<SerieDocumentalObject>(newS.getSerieId(), newS.getCodi(), newS.getNom(), "Serie", newS.getEnviatSAT(), newS),
                            funcBean.getNodeFromFunctionId(serieFuncio.getId(), "Funcio", "insert", null));

                    messageKey = "nuevaserie.ok";

                } else {
                    TreeNode oldParent = null;

                    // Obtenemos el antiguo padre de la funcion
                    if (!serieFuncio.getId().equals(oldParentFunction)) {
                        oldParent = funcBean.getNodeFromFunctionId(oldParentFunction, "Funcio", "inset", null);
                    }
                    SerieDocumentalObject upS = service.updateSerieDocumental(serieId, codi, nom, nomCas,
                            catalegSeriId, serieFuncio.getId(), descripcio, descripcioCas, resumMigracio, enviatSAT, dir3Promotor, serieEstat, tipusSerieId, codiIecisa, aplicaciones.getTarget(), procedimentsRelacionats.getTarget(), procedimentsComunsRelacionats.getTarget(), listaRelacionSeries, seriesArgenRelacionadas.getTarget(), listaRelacionLNS,
                            normativasSerie.getTarget(), valoracio, organCollegiatId, listaTDP);
                    TreeNode node = funcBean.getNodeFromFunctionId(serieId, "Serie", "update", upS);

                    log.debug("Se completa el proceso de modificar serie");
                    if (!serieFuncio.getId().equals(oldParentFunction)) {
                        log.debug("Actualizamos el arbol");
                        oldParent.getChildren().removeIf(x -> ((Document) x.getData()).getObject() instanceof SerieDocumentalObject
                                && ((Document<SerieDocumentalObject>) x.getData()).getId() == serieId);

                        TreeNode parent;
                        // Si la funcion es null es que es hijo directo del cuadro
                        parent = funcBean.getNodeFromFunctionId(serieFuncio.getId(), "Funcio", "insert", null);

                        // Lo añadimos en la primera posición
                        parent.getChildren().add(0, node);
                    }
                    messageKey = "nuevaserie.update.ok";
                }
                clearForm();
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(messageBundle.getString(messageKey)));

            } else {
                FacesContext.getCurrentInstance().validationFailed();
                FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.valoracio"), null);
                FacesContext.getCurrentInstance().addMessage(null, message);
            }
        } catch (I18NException e) {
            log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
            FacesMessage message;
            if (serieId == null) {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.insert.error"), null);
            } else {
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.update.error"), null);
            }
            FacesContext.getCurrentInstance().addMessage(null, message);
        }

    }

    public void reloadProcediments(boolean ambMissatges) throws I18NException {
        llistaProcediments = service.getListaProcediments();
        llistaProcediments.removeAll(procedimentsRelacionats.getTarget());
        procedimentsRelacionats.setSource(llistaProcediments);

        if (ambMissatges) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageBundle.getString("nuevaserie.prcrel.actualizado"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado-prcRel", message);
        }
    }

    public void reloadProcedimentsComuns(boolean ambMissatges) throws I18NException {
        llistaProcedimentsComuns = service.getListaProcedimentsSenseSerie();
        llistaProcedimentsComuns.removeAll(procedimentsComunsRelacionats.getTarget());
        procedimentsComunsRelacionats.setSource(llistaProcedimentsComuns);

        if (ambMissatges) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageBundle.getString("nuevaserie.prcrel.actualizado"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado-prcComu", message);
        }
    }

    private Boolean validateValoracion() {

        if (valoracio.getAchValorsecundari() == null
                && (valoracio.getAchValorprimaris() == null || !valoracio.hashValorPrimariSelected())) {
            return true;
        } else if (valoracio.getAchValorsecundari() != null
                && valoracio.getAchValorprimaris() != null
                && valoracio.hashValorPrimariSelected()) {
            return true;
        }
        return false;
    }


    private void clearForm() {
        aplicaciones.setSource(listaAplicaciones);
        aplicaciones.setTarget(new ArrayList<>());
        seriesArgenRelacionadas.setSource(listaSeriesArgen);
        seriesArgenRelacionadas.setTarget(new ArrayList<>());
        normativasSerie.setSource(listaNormativaAprobacio);
        normativasSerie.setTarget(new ArrayList<>());
        procedimentsRelacionats.setSource(llistaProcediments);
        procedimentsRelacionats.setTarget(new ArrayList<>());

        procedimentsComunsRelacionats.setSource(llistaProcedimentsComuns);
        procedimentsComunsRelacionats.setTarget(new ArrayList<>());

        listaRelacionLNS.clear();
        listaRelacionSeries.clear();

        serieId = null;
        codi = null;
        nom = null;
        nomCas = null;
        catalegSeriId = null;
        funcioId = null;
        descripcio = null;
        descripcioCas = null;
        resumMigracio = null;
        enviatSAT = null;
        dir3Promotor = null;
        tipusSerieId = null;
        codiIecisa = null;
        serieEstat = "ESBORRANY";

        this.selectedTipoDocumental = null;
        this.listaTDP = new ArrayList<>();

        valoracio = new ValoracioObject();
        for (TipuValorObject tv : listaTiposValor) {
            ValorPrimariObject vp = new ValorPrimariObject();
            vp.setAchTipusvalor(tv);
            vp.setTerminiType(UNIDAD_PLAZO_SERIE_DEFAULT_MESSAGE);
            valoracio.addValorprimari(vp);
        }
    }

    public void deleteTDP(TipuDocumentalSerieObject tdpitem) {
        if(listaTDP.remove(tdpitem)) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageBundle.getString("serie.tdp.delete.ok"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado-agr", message);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("serie.tdp.delete.error"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado-agr", message);
        }
    }

    public void onTipoDocumentalSelectedListener(AjaxBehaviorEvent event){

        if (this.validateTDP()) {
            TipuDocumentalSerieObject tdp = new TipuDocumentalSerieObject();
            tdp.setTipusDocumental(selectedTipoDocumental);
            log.debug("Añadiendo tipo documental a la serie... ¿tenemos serie? " + serieId);
            // Si estamos en una operacion de modificacion, le establecemos el procedimiento que se esta modificando
            if (serieId != null) {
                log.debug("Vinculando al tipo documental la serie modify = " + modify);
                tdp.setSerie(modify);
            }

            listaTDP.add(tdp);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.duplicateTDP"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado-agr", message);
        }

        selectedTipoDocumental = null;
    }

    private Boolean validateTDP() {
        if(listaTDP == null || listaTDP.size() == 0) {
            return true;
        }

        boolean duplicado = false;
        Iterator<TipuDocumentalSerieObject> it = listaTDP.iterator();

        while(it.hasNext() && !duplicado) {
            TipuDocumentalSerieObject item = it.next();
            if(Objects.equals(selectedTipoDocumental.getId(), item.getTipusDocumental().getId())) {
                duplicado=true;
            }
        }
        return !duplicado;
    }

    private Boolean validNormativa() {
        boolean valid = true;

        Iterator<LimitacioNormativaSerieObject> i = listaRelacionLNS.iterator();

        while (i.hasNext() && valid) {
            if (i.next().getNormativa().equals(selectedNormativa)) {
                valid = false;
            }
        }

        return valid;
    }

    /*
    Permet evitar repeticions de tipus de relacions...
     */
    private Boolean validTipusRelacio() {
        boolean valid = true;

        // TODO
        Iterator<LimitacioNormativaSerieObject> i = listaRelacionLNS.iterator();

        while (i.hasNext() && valid) {
            if (i.next().getNormativa().equals(selectedNormativa)) {
                valid = false;
            }
        }

        return valid;
    }

    public void onLimitacioSelectedListener(AjaxBehaviorEvent event) {
        if (this.validNormativa()) {
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

    public void onTipusRelacioSelectedListener(AjaxBehaviorEvent event) {
        if (this.validTipusRelacio()) {
            RelacioSerieObject relacio = new RelacioSerieObject();
            relacio.setSeriesRelacionades(new HashSet<>());
            relacio.setDualListSeries(new DualListModel<>());
            relacio.getDualListSeries().setSource(listaSerieDocumental);
            relacio.getDualListSeries().setTarget(new ArrayList<>());
            relacio.setTipusRelacio(selectedTipusRelacio);
            listaRelacionSeries.add(relacio);
            selectedTipusRelacio = null;
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.duptiprel"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado-relser", message);
        }
    }

    public void deleteRelacioSerie(RelacioSerieObject relitem) {
        int index = listaRelacionSeries.indexOf(relitem);
        if (index >= 0) {
            listaRelacionSeries.remove(index);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageBundle.getString("datos.eliminados"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado-relser", message);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.delrelserie"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado-relser", message);
        }
    }

    public void deleteLNS(LimitacioNormativaSerieObject lnsitem) {
        int index = listaRelacionLNS.indexOf(lnsitem);
        if (index >= 0) {
            listaRelacionLNS.remove(index);
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageBundle.getString("datos.eliminados"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
        } else {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.delcausanorm"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
        }
    }

    public void saveLNS(LimitacioNormativaSerieObject lnsitem) {

        if (lnsitem.getDualListCausas().getTarget().size() > 0) {
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageBundle.getString("datos.salvados"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
        } else {
            FacesContext.getCurrentInstance().validationFailed();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.causanorm"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
        }
    }

    public void saveRelacioSerie(RelacioSerieObject relitem) {
        log.debug("Afegint missatge...");
        if (relitem.getDualListSeries().getTarget().size() > 0) {
            log.debug("Hi ha elements a la llista");
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_INFO, messageBundle.getString("datos.salvados"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado-relser", message);
        } else {
            log.debug("No hi ha elements a la llista");
            FacesContext.getCurrentInstance().validationFailed();
            FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString("nuevaserie.validate.relserie"), null);
            FacesContext.getCurrentInstance().addMessage("mensaje-estado-relser", message);
        }
        log.debug("Missatge afegit!");
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

    public Boolean getEnviatSAT() {
        return enviatSAT;
    }

    public void setEnviatSAT(Boolean enviatSAT) {
        this.enviatSAT = enviatSAT;
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

    public List<AplicacionObject> getListaAplicaciones() {
        return listaAplicaciones;
    }

    public void setListaAplicaciones(List<AplicacionObject> listaAplicaciones) {
        this.listaAplicaciones = listaAplicaciones;
    }

    public List<ProcedimentObject> getLlistaProcediments() {
        return llistaProcediments;
    }

    public void setLlistaProcediments(List<ProcedimentObject> llistaProcediments) {
        this.llistaProcediments = llistaProcediments;
    }

    public List<ProcedimentObject> getLlistaProcedimentsComuns() {
        return llistaProcedimentsComuns;
    }

    public void setLlistaProcedimentsComuns(List<ProcedimentObject> llistaProcedimentsComuns) {
        this.llistaProcedimentsComuns = llistaProcedimentsComuns;
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

    public List<ProcedimentObject> getLlistaProcedimentsSelected() {
        return llistaProcedimentsSelected;
    }

    public void setLlistaProcedimentsSelected(List<ProcedimentObject> llistaProcedimentsSelected) {
        this.llistaProcedimentsSelected = llistaProcedimentsSelected;
    }

    public List<ProcedimentObject> getLlistaProcedimentsComunsSelected() {
        return llistaProcedimentsComunsSelected;
    }

    public void setLlistaProcedimentsComunsSelected(List<ProcedimentObject> llistaProcedimentsComunsSelected) {
        this.llistaProcedimentsComunsSelected = llistaProcedimentsComunsSelected;
    }

    public DualListModel<AplicacionObject> getAplicaciones() {
        return aplicaciones;
    }

    public void setAplicaciones(DualListModel<AplicacionObject> aplicaciones) {
        this.aplicaciones = aplicaciones;
    }

    public DualListModel<ProcedimentObject> getProcedimentsRelacionats() {
        return procedimentsRelacionats;
    }

    public void setProcedimentsRelacionats(DualListModel<ProcedimentObject> procedimentsRelacionats) {
        this.procedimentsRelacionats = procedimentsRelacionats;
    }

    public DualListModel<ProcedimentObject> getProcedimentsComunsRelacionats() {
        return procedimentsComunsRelacionats;
    }

    public void setProcedimentsComunsRelacionats(DualListModel<ProcedimentObject> procedimentsComunsRelacionats) {
        this.procedimentsComunsRelacionats = procedimentsComunsRelacionats;
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

    public List<TipusRelacioSerieObject> getListaTipusRelacio() {
        return listaTipusRelacio;
    }

    public void setListaTipusRelacio(List<TipusRelacioSerieObject> listaTipusRelacio) {
        this.listaTipusRelacio = listaTipusRelacio;
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

    public Long getOrganCollegiatId() {
        return organCollegiatId;
    }

    public void setOrganCollegiatId(Long organCollegiatId) {
        this.organCollegiatId = organCollegiatId;
    }

    public TipusRelacioSerieObject getSelectedTipusRelacio() {
        return selectedTipusRelacio;
    }

    public void setSelectedTipusRelacio(TipusRelacioSerieObject selectedTipusRelacio) {
        this.selectedTipusRelacio = selectedTipusRelacio;
    }

    public List<RelacioSerieObject> getListaRelacionSeries() {
        return listaRelacionSeries;
    }

    public void setListaRelacionSeries(List<RelacioSerieObject> listaRelacionSeries) {
        this.listaRelacionSeries = listaRelacionSeries;
    }

    public TipuDocumentalObject getSelectedTipoDocumental() {
        return selectedTipoDocumental;
    }

    public void setSelectedTipoDocumental(TipuDocumentalObject selectedTipoDocumental) {
        this.selectedTipoDocumental = selectedTipoDocumental;
    }

    public List<TipuDocumentalSerieObject> getListaTDP() {
        return listaTDP;
    }

    public void setListaTDP(List<TipuDocumentalSerieObject> listaTDP) {
        this.listaTDP = listaTDP;
    }

    public List<OrganCollegiatObject> getListaOrganCollegiat() {
        return listaOrganCollegiat;
    }

    public void setListaOrganCollegiat(List<OrganCollegiatObject> listaOrganCollegiat) {
        this.listaOrganCollegiat = listaOrganCollegiat;
    }

    public List<TipuDocumentalObject> getListatipoDocumental() {
        return listatipoDocumental;
    }

    public void setListatipoDocumental(List<TipuDocumentalObject> listatipoDocumental) {
        this.listatipoDocumental = listatipoDocumental;
    }
}

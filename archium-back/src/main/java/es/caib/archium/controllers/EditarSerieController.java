package es.caib.archium.controllers;

import java.io.Serializable;
import java.text.Normalizer;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.ResourceBundle;
import java.util.Set;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

import javax.annotation.PostConstruct;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.faces.event.AjaxBehaviorEvent;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;

import org.primefaces.component.tabview.Tab;
import org.primefaces.event.SelectEvent;
import org.primefaces.event.TabChangeEvent;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.objects.AplicacioObject;
import es.caib.archium.objects.AplicacionObject;
import es.caib.archium.objects.CatalegSeriesObject;
import es.caib.archium.objects.CausaLimitacioObject;
import es.caib.archium.objects.Dir3Object;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.LimitacioNormativaSerieObject;
import es.caib.archium.objects.NormativaAprobacioObject;
import es.caib.archium.objects.OrganCollegiatObject;
import es.caib.archium.objects.ProcedimentObject;
import es.caib.archium.objects.ProcedimentVisorObject;
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
import es.caib.archium.services.FuncioFrontService;
import es.caib.archium.services.SerieFrontService;

@Named
@ViewScoped
public class EditarSerieController implements Serializable {

	private static final long serialVersionUID = -8940713018192370062L;
	protected final Logger log = LoggerFactory.getLogger(this.getClass());

	private FuncioObject serieFuncio;
	private Long tipusSerieId;

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
	private String codiIecisa;
	private String funcionNom;
	private String serieEstat;
	private ValoracioObject valoracio;
	private Long oldParentFunction;

	private Long organCollegiatId;

	private SerieDocumentalObject modify;

	private boolean normativasCargadas;
	
	FuncioObject serieFuncioParametro;

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
	private List<AplicacioObject> listaAplicaciones;

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
	
	List<NormativaAprobacioObject> normativasList;

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
	
	private Map<Long, SerieDocumentalObject> seriesById;
	
	private NormativaAprobacioObject normativaABuscar;
	private List<NormativaAprobacioObject> normativasSeleccionadas = new ArrayList<>();
	
	private CausaLimitacioObject causaABuscar;
	private List<CausaLimitacioObject> accessSeleccionadas = new ArrayList<>();
	
	private AplicacioObject aplicacionsABuscar;
	private List<AplicacioObject> aplicacionsSeleccionadas = new ArrayList<>();
	
	private SerieArgenObject seriesArgenABuscar;
	private List<SerieArgenObject> seriesArgenSeleccionadas = new ArrayList<>();
	
	private SerieDocumentalObject serieABuscar;
	private List<SerieDocumentalObject> seriesSeleccionadas = new ArrayList<>();
	
	private ProcedimentVisorObject prcComunsABuscar;
	private List<ProcedimentVisorObject> prcComunsSeleccionadas = new ArrayList<>();
	
	private ProcedimentVisorObject prcRelacionatsABuscar;
	private List<ProcedimentVisorObject> prcRelacionatsSeleccionadas = new ArrayList<>();

	
	Long idParaEditar = null;

	private static String UNIDAD_PLAZO_SERIE_DEFAULT_MESSAGE = null;
	private static final Pattern DIACRITICS = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");


	ResourceBundle messageBundle = ResourceBundle.getBundle("messages.messages");
	FuncionesController funcBean = null;
	
	private Long quadreId;
	
	@Inject
	SerieFrontService serieFrontService;

	@Inject
	FuncioFrontService funcioService;

	@PostConstruct
	public void init() {
		log.debug("Entrando en el init de NuevaSerieVistaController");
		UNIDAD_PLAZO_SERIE_DEFAULT_MESSAGE = messageBundle.getString("general.plazos.anys");

		try {
	        Map<String, Object> viewMap = FacesContext.getCurrentInstance().getViewRoot().getViewMap();
			this.funcBean = (FuncionesController) viewMap.get("funciones");
			
			listaDir3 = new ArrayList();
			listaOrganCollegiat = new ArrayList();

			listaTiposValor = new ArrayList();
			listaValoresSecundarios = new ArrayList();

			listatipoDocumental = new ArrayList();
			listaCatalegSerie = new ArrayList();
			listaOrganCollegiat = new ArrayList();
			listaFunciones = new ArrayList();
			listaTipusSerie = new ArrayList();
			llistaProcediments = new ArrayList();
			llistaProcedimentsComuns = new ArrayList();
			listaAplicaciones = new ArrayList();
			listaNormativaAprobacio = new ArrayList();
			listaTipusRelacio = new ArrayList();
			listaCausaLimitacio = new ArrayList();
			listaSerieDocumental = new ArrayList();
			listaSeriesArgen = new ArrayList();

			aplicaciones = new DualListModel<>(new ArrayList<>(), new ArrayList<>());
			procedimentsRelacionats = new DualListModel<>(new ArrayList<>(), new ArrayList<>());
			procedimentsComunsRelacionats = new DualListModel<>(new ArrayList<>(), new ArrayList<>());
			seriesArgenRelacionadas = new DualListModel<>(new ArrayList<>(), new ArrayList<>());
			normativasSerie = new DualListModel<>(new ArrayList<>(), new ArrayList<>());
			
//			valoracio = new ValoracioObject();

			idParaEditar = (Long) FacesContext.getCurrentInstance().getExternalContext().getFlash()
					.get("serieIdEditar");

			if (idParaEditar != null) {
				log.debug("Id de la serie documental a editar: {} ", idParaEditar);
				this.cargarDatosParaEditar(idParaEditar);
			} 
			
			Long param = (Long) FacesContext.getCurrentInstance()
		            .getExternalContext()
		            .getFlash()
		            .get("quadreId");
			if (param != null) {
				quadreId = param;
			} 
			
		} catch (Exception e) {
			log.error("Error: {}", e);
		}
	}

	private void cargarDatosParaEditar(Long id) {
		log.debug("Cargando datos de la serie documental para editarla");
		try {
			modify = this.service.findById(id);
			if (modify != null) {
				serieId = id;
				log.debug("serieId = {}",serieId);
	            oldParentFunction = modify.getFuncio().getId();
				codi = modify.getCodi();
				nom = modify.getNom();
				nomCas = modify.getNomCas();
				catalegSeriId = modify.getCatalegSeriId();
				descripcio = modify.getDescripcio();
				descripcioCas = modify.getDescripcioCas();
				resumMigracio = modify.getResumMigracio();
				enviatSAT = modify.getEnviatSAT();
				dir3Promotor = modify.getDir3Promotor();

				tipusSerieId = modify.getTipusSerieId();
				codiIecisa = modify.getCodiIecisa();
				serieFuncio = modify.getFuncio();
				funcioId = serieFuncio.getId();
				log.debug("FUNCIO ID QUE SE CARGA DE ANTES: {}", funcioId);
				serieEstat = (modify.getEstat() != null ? modify.getEstat() : "ESBORRANY");

				listaDir3 = service.getListaDir3();
				listaTipusSerie = service.getListaTipusSerie();
				listaCatalegSerie = service.getListaCatalegSerie();
				//listaFunciones = service.getListaFunciones();
				if (quadreId != null) {
					listaFunciones = funcioService.loadTree(quadreId, null);
				}

				List<AplicacionObject> targetAppsList;

				List<SerieArgenObject> relatedArgensList;

				aplicacionsSeleccionadas = service.getListaAplicacionesBySerie(serieId);

		
				prcRelacionatsSeleccionadas = service.getListaProcedimentsVisorBySerie(serieId);

				prcComunsSeleccionadas = service.getProcedimentsComunsVisorBySerie(serieId);
				
				seriesArgenSeleccionadas = service.getListaSeriesArgenBySerie(serieId);

				listaRelacionLNS = service.getListaLNS(serieId);
				
				listaRelacionSeries = service.getListaRelacions(serieId);
				
				normativasSeleccionadas = service.getListaNormativasBySerie(serieId);

				listaTDP = service.getListaTDP(serieId);
				
				listaTiposValor = service.getListaTipusValor();
	            listaValoresSecundarios = service.getListaValorsSecundari();

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
	        

			} else {
				FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
						messageBundle.getString("nuevaserie.update.abrirUpdate.error"),
						messageBundle.getString("procediment.update.abrirUpdate.error"));
				FacesContext.getCurrentInstance().addMessage(null, msg);
			}

		} catch (I18NException e) {
			log.error("Error: {}", e);
			//log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
			FacesMessage msg = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					messageBundle.getString("nuevaserie.update.abrirUpdate.error"),
					messageBundle.getString("procediment.update.abrirUpdate.error"));
			FacesContext.getCurrentInstance().addMessage(null, msg);
		}
	}


	public void onTabChange(TabChangeEvent event) throws I18NException {
		log.debug("Entrando en el metodo OnTabChange");
		Tab tab = event.getTab();
		String tabId = tab.getId();

		switch (tabId) {
		case "dadesBasiques":
			if (listaDir3.isEmpty()) {
				
			}
			break;

		case "prcRelacionats":
			if (llistaProcediments.isEmpty()) {
			}
			break;

		case "prcComuns":
			if (llistaProcedimentsComuns.isEmpty()) {
			}
			break;

		case "Agrupacions":
			if (listatipoDocumental.isEmpty()) {
				listatipoDocumental = service.findAllTipoDocumental();
				listaOrganCollegiat = service.getListaOrgansCollegiats();

			}
			break;

		case "seriesRel":
			if (listaTipusRelacio.isEmpty()) {
				listaTipusRelacio = service.getListaTipusRelacio();


			}
			break;

		case "argen":
			if (listaSeriesArgen.isEmpty()) {

			}
			break;

		case "aplicacions":
			if (listaAplicaciones.isEmpty()) {
			}
			break;

		case "acces":
			if (listaCausaLimitacio.isEmpty()) {

			}
			break;

		case "valoracio":
			if (listaTiposValor.isEmpty()) {

			}
			break;

		case "normatives":
			if (listaNormativaAprobacio.isEmpty()) {

			}
			break;
		}
	}
	

	
	public String save() { // Cambiado de void a String
		log.debug("Entrando en el metodo save de EditarSerieController");
		log.debug("ListaRelacionSeries: {}", listaRelacionSeries);
		FacesContext context = FacesContext.getCurrentInstance();

		try {
			if (this.validateValoracion()) {
				if (!this.service.checkValidClassificationCode(codi, serieId)) {
					log.error("Error en la creacion de la serie, ya existe...");
					context.validationFailed();
					// Persistir mensaje antes de cualquier posible redirección
					context.getExternalContext().getFlash().setKeepMessages(true);
					context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
							messageBundle.getString("nuevaserie.validate.codigo"), null));
					return null; // Se mantiene en la página actual
				}

				if (!this.service.checkValidClassificationCode(codi, serieId)) {
					log.error("Error en la creacion de la serie, ya existe otra serie con el codigo [" + codi + "]");
					FacesContext.getCurrentInstance().validationFailed();
					FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
							messageBundle.getString("nuevaserie.validate.codigo"), null);
					FacesContext.getCurrentInstance().addMessage(null, message);
					return null;
				}
				Long newSerieId = null;
				String messageKey = null;
				if (serieId != null) {
		        	log.debug("Actualizando serie documental...");
		            SerieDocumentalObject upS = service.updateSerieDocumental(
		                    serieId, codi, nom, nomCas, catalegSeriId,
		                    funcioId, descripcio, descripcioCas, resumMigracio,
		                    enviatSAT, dir3Promotor, serieEstat, tipusSerieId, codiIecisa,
		                    aplicacionsSeleccionadas, prcRelacionatsSeleccionadas,
		                    prcComunsSeleccionadas, listaRelacionSeries,
		                    seriesArgenSeleccionadas, listaRelacionLNS,
		                    normativasSeleccionadas, valoracio, organCollegiatId, listaTDP
		            );
		            
		            

		            log.debug("Serie actualizada en BD: {}", upS.getCodi());
		            messageKey = "nuevaserie.update.ok";
		        }

		        context.getExternalContext().getFlash().setKeepMessages(true);
		        context.addMessage(null, new FacesMessage(messageBundle.getString(messageKey)));

		        //return "funciones.xhtml?faces-redirect=true";
		        return null;


			} else {
				context.validationFailed();
				context.getExternalContext().getFlash().setKeepMessages(true);
				context.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
						messageBundle.getString("nuevaserie.validate.valoracio"), null));
				return null;
			}
		} catch (I18NException e) {
			log.error("Error: {}", e);
			//log.error(FrontExceptionTranslate.translate(e, funcBean.getLocale()));
			context.getExternalContext().getFlash().setKeepMessages(true);
			String errorKey = (serieId == null) ? "nuevaserie.insert.error" : "nuevaserie.update.error";
			context.addMessage(null,
					new FacesMessage(FacesMessage.SEVERITY_ERROR, messageBundle.getString(errorKey), null));
			return null;
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
			FacesMessage message = new FacesMessage(FacesMessage.SEVERITY_ERROR,
					messageBundle.getString("nuevaserie.validate.dupnorm"), null);
			FacesContext.getCurrentInstance().addMessage("mensaje-estado", message);
		}
	}

    public void onTipusRelacioSelectedListener(AjaxBehaviorEvent event) {
        if (this.validTipusRelacio()) {
            RelacioSerieObject relacio = new RelacioSerieObject();
            List<Long> sourceIds = listaSerieDocumental.stream()
	                .map(SerieDocumentalObject::getSerieId)
	                .collect(Collectors.toList());
	        List<Long> targetIds = new ArrayList<>(); // inicialmente vacía

	        relacio.setDualListSeriesIds(new DualListModel<>(sourceIds, targetIds));

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
        if (!relitem.getDualListSeriesIds().getTarget().isEmpty()) {
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
    
    public List<TipusRelacioSerieObject> getListaTipusRelacioDisponibles() {
	    // Si la lista de relaciones está vacía, devolvemos todas
	    if (listaRelacionSeries == null || listaRelacionSeries.isEmpty()) {
	        return listaTipusRelacio;
	    }

	    // Obtenemos los IDs de los tipos que ya están en el acordeón
	    Set<Long> tiposYaUtilizados = listaRelacionSeries.stream()
	            .map(rel -> rel.getTipusRelacio().getId())
	            .collect(Collectors.toSet());

	    // Devolvemos solo los que no están en el Set
	    return listaTipusRelacio.stream()
	            .filter(tipo -> !tiposYaUtilizados.contains(tipo.getId()))
	            .collect(Collectors.toList());
	}
    
    
    private String limpiarAcentos(String texto) {
	    if (texto == null) return "";
	    
	    String normalizado = Normalizer.normalize(texto, Normalizer.Form.NFD);
	    return DIACRITICS.matcher(normalizado)
	                     .replaceAll("")
	                     .toLowerCase();
	}

	
// --------------- NORMATIVAS ----------------------------------------------
	public List<NormativaAprobacioObject> completarNormativas(String query) throws I18NException {

	    if (query == null || query.trim().isEmpty()) {
	        return new ArrayList<>();
	    }
	    String q = limpiarAcentos(query); //Ahora mismo no se limpian
	    
	    List<NormativaAprobacioObject> res = service.buscarNormativaPorTexto(q);
	    return res;
	}

	public void onItemSelect(SelectEvent<Object> event) {
	    Object seleccionada = event.getObject();
	    
	    if (seleccionada != null && (!normativasSeleccionadas.contains(seleccionada))) {
	            normativasSeleccionadas.add((NormativaAprobacioObject) seleccionada);        
	    }
	    this.normativaABuscar = null;
	}

	public void eliminarSeleccion(NormativaAprobacioObject item) {
	    normativasSeleccionadas.remove(item);
	}
	
// --------------- CAUSA LIMITACIO ----------------------------------------------
	public List<CausaLimitacioObject> completarCausas(String query) throws I18NException {

	    if (query == null || query.trim().isEmpty()) {
	        return new ArrayList<>();
	    }
	    
	    String q = limpiarAcentos(query);
	    
	    List<CausaLimitacioObject> res = service.buscarCausaLimitacioPorTexto(q);
	    return res;
	}

	public void onItemSelectAccess(LimitacioNormativaSerieObject itemActual) {
	    CausaLimitacioObject seleccionada = this.causaABuscar; 
	    
	    if (seleccionada != null && itemActual != null) {
	        if (itemActual.getListCausaLimitacio() == null) {
	            itemActual.setListCausaLimitacio(new ArrayList<>());
	        }

	        boolean yaExiste = itemActual.getListCausaLimitacio()
	            .stream()
	            .anyMatch(c -> c.getId().equals(seleccionada.getId()));

	        if (!yaExiste) {
	            itemActual.getListCausaLimitacio().add(seleccionada);
	        }
	    }
	    
	    this.causaABuscar = null; 
	}

	public void eliminarSeleccionAccess(LimitacioNormativaSerieObject itemActual, CausaLimitacioObject causaAEliminar) {
	    if (itemActual != null && itemActual.getListCausaLimitacio() != null) {
	        itemActual.getListCausaLimitacio().remove(causaAEliminar);
	    }
	}
	
// --------------- APLICACIONS ----------------------------------------------
	public List<AplicacioObject> completarAplicacions(String query) throws I18NException {

	    if (query == null || query.trim().isEmpty()) {
	        return new ArrayList<>();
	    }

	    String q = limpiarAcentos(query);
	    
	    List<AplicacioObject> res = service.buscarAplicacioPorTexto(q);
	    return res;
	}

	public void onItemSelectAplicacions(SelectEvent<Object> event) {
	    Object seleccionada = event.getObject();
	    
	    if (seleccionada != null && (!aplicacionsSeleccionadas.contains(seleccionada))) {
	            aplicacionsSeleccionadas.add((AplicacioObject) seleccionada);        
	    }
	    this.aplicacionsABuscar = null;
	}

	public void eliminarSeleccionAplicacions(AplicacioObject item) {
	    aplicacionsSeleccionadas.remove(item);
	}
	
// --------------- SERIES ARGEN ----------------------------------------------
	public List<SerieArgenObject> completarSeriesArgen(String query) throws I18NException {

	    if (query == null || query.trim().isEmpty()) {
	        return new ArrayList<>();
	    }

	    String q = limpiarAcentos(query);
	    
	    List<SerieArgenObject> res = service.buscarSerieArgenPorTexto(q);
	    return res;
	}

	public void onItemSelectSeriesArgen(SelectEvent<Object> event) {
	    Object seleccionada = event.getObject();
	    
	    if (seleccionada != null && (!seriesArgenSeleccionadas.contains(seleccionada))) {
	    	seriesArgenSeleccionadas.add((SerieArgenObject) seleccionada);        
	    }
	    this.seriesArgenABuscar = null;
	}

	public void eliminarSeleccionSeriesArgen(SerieArgenObject item) {
		seriesArgenSeleccionadas.remove(item);
	}
		
		
// --------------- SERIES REL. ----------------------------------------------
	public List<SerieDocumentalObject> completarSeriesRel(String query) throws I18NException {

	    if (query == null || query.trim().isEmpty()) {
	        return new ArrayList<>();
	    }
	    
	    String q = limpiarAcentos(query);
	    
	    List<SerieDocumentalObject> res = service.buscarSerieDocumentalPorTexto(q);
	    return res;
	}

	public void onItemSelectSeriesRel(RelacioSerieObject itemActual) {
	    SerieDocumentalObject seleccionada = this.serieABuscar; 
	    
	    if (seleccionada != null && itemActual != null) {
	        if (itemActual.getSeriesRelacionades() == null) {
	            itemActual.setSeriesRelacionades(new HashSet<>());
	        }
	        itemActual.getSeriesRelacionades().add(seleccionada);
	    }
	    
	    this.serieABuscar = null; 
	}

	public void eliminarSeleccionSeriesRel(RelacioSerieObject itemActual, SerieDocumentalObject causaAEliminar) {
	    if (itemActual != null && itemActual.getSeriesRelacionades() != null) {
	        itemActual.getSeriesRelacionades().remove(causaAEliminar);
	    }
	}
	
// --------------- PRC COMUNS ----------------------------------------------
	public List<ProcedimentVisorObject> completarPrcComuns(String query) throws I18NException {

	    if (query == null || query.trim().isEmpty()) {
	        return new ArrayList<>();
	    }

	    String q = limpiarAcentos(query);
	    
	    List<ProcedimentVisorObject> res = service.buscarProcedimentPorTexto(q);
	    return res;
	}

	public void onItemSelectPrcComuns(SelectEvent<Object> event) {
	    Object seleccionada = event.getObject();
	    
	    if (seleccionada != null && (!prcComunsSeleccionadas.contains(seleccionada))) {
	    	prcComunsSeleccionadas.add((ProcedimentVisorObject) seleccionada);        
	    }
	    this.prcComunsABuscar = null;
	}

	public void eliminarSeleccionPrcComuns(ProcedimentVisorObject item) {
		prcComunsSeleccionadas.remove(item);
	}
	
	
// --------------- PRC RELACIONATS ----------------------------------------------
	public List<ProcedimentVisorObject> completarPrcRelacionats(String query) throws I18NException {

	    if (query == null || query.trim().isEmpty()) {
	        return new ArrayList<>();
	    }

	    String q = limpiarAcentos(query);
	    
	    List<ProcedimentVisorObject> res = service.buscarProcedimentPorTexto(q);
	    return res;
	}

	public void onItemSelectPrcRelacionats(SelectEvent<Object> event) {
	    Object seleccionada = event.getObject();
	    
	    if (seleccionada != null && (!prcRelacionatsSeleccionadas.contains(seleccionada))) {
	    	prcRelacionatsSeleccionadas.add((ProcedimentVisorObject) seleccionada);        
	    }
	    this.prcRelacionatsABuscar = null;
	}

	public void eliminarSeleccionPrcRelacionats(ProcedimentVisorObject item) {
		prcRelacionatsSeleccionadas.remove(item);
	}
	
// ---------------------------------------------------------------------------

    
    public SerieDocumentalObject getSerieById(Long id) {
        return seriesById.get(id);
    }

	public FuncioObject getSerieFuncio() {
		return serieFuncio;
	}

	public void setSerieFuncio(FuncioObject serieFuncio) {
		this.serieFuncio = serieFuncio;
	}

	public Long getTipusSerieId() {
		return tipusSerieId;
	}

	public void setTipusSerieId(Long tipusSerieId) {
		this.tipusSerieId = tipusSerieId;
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

	public String getDescripcio() {
		return descripcio;
	}

	public void setDescripcio(String descripcio) {
		this.descripcio = descripcio;
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

	public String getCodiIecisa() {
		return codiIecisa;
	}

	public void setCodiIecisa(String codiIecisa) {
		this.codiIecisa = codiIecisa;
	}

	public String getFuncionNom() {
		return funcionNom;
	}

	public void setFuncionNom(String funcionNom) {
		this.funcionNom = funcionNom;
	}

	public String getSerieEstat() {
		return serieEstat;
	}

	public void setSerieEstat(String serieEstat) {
		this.serieEstat = serieEstat;
	}

	public List<Dir3Object> getListaDir3() {
		return listaDir3;
	}

	public void setListaDir3(List<Dir3Object> listaDir3) {
		this.listaDir3 = listaDir3;
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

	public List<AplicacioObject> getListaAplicaciones() {
		return listaAplicaciones;
	}

	public void setListaAplicaciones(List<AplicacioObject> listaAplicaciones) {
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

	public DualListModel<ProcedimentObject> getProcedimentsRelacionats() {
		return procedimentsRelacionats;
	}

	public void setProcedimentsRelacionats(DualListModel<ProcedimentObject> procedimentsRelacionats) {
		this.procedimentsRelacionats = procedimentsRelacionats;
	}

	public List<ProcedimentObject> getLlistaProcedimentsSelected() {
		return llistaProcedimentsSelected;
	}

	public void setLlistaProcedimentsSelected(List<ProcedimentObject> llistaProcedimentsSelected) {
		this.llistaProcedimentsSelected = llistaProcedimentsSelected;
	}

	public DualListModel<ProcedimentObject> getProcedimentsComunsRelacionats() {
		return procedimentsComunsRelacionats;
	}

	public void setProcedimentsComunsRelacionats(DualListModel<ProcedimentObject> procedimentsComunsRelacionats) {
		this.procedimentsComunsRelacionats = procedimentsComunsRelacionats;
	}

	public List<ProcedimentObject> getLlistaProcedimentsComunsSelected() {
		return llistaProcedimentsComunsSelected;
	}

	public void setLlistaProcedimentsComunsSelected(List<ProcedimentObject> llistaProcedimentsComunsSelected) {
		this.llistaProcedimentsComunsSelected = llistaProcedimentsComunsSelected;
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

	public List<RelacioSerieObject> getListaRelacionSeries() {
		return listaRelacionSeries;
	}

	public void setListaRelacionSeries(List<RelacioSerieObject> listaRelacionSeries) {
		this.listaRelacionSeries = listaRelacionSeries;
	}

	public List<SerieDocumentalObject> getListaSerieDocumental() {
		return listaSerieDocumental;
	}

	public void setListaSerieDocumental(List<SerieDocumentalObject> listaSerieDocumental) {
		this.listaSerieDocumental = listaSerieDocumental;
	}

	public NormativaAprobacioObject getSelectedNormativa() {
		return selectedNormativa;
	}

	public void setSelectedNormativa(NormativaAprobacioObject selectedNormativa) {
		this.selectedNormativa = selectedNormativa;
	}

	public TipusRelacioSerieObject getSelectedTipusRelacio() {
		return selectedTipusRelacio;
	}

	public void setSelectedTipusRelacio(TipusRelacioSerieObject selectedTipusRelacio) {
		this.selectedTipusRelacio = selectedTipusRelacio;
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

	public Long getOrganCollegiatId() {
		return organCollegiatId;
	}

	public void setOrganCollegiatId(Long organCollegiatId) {
		this.organCollegiatId = organCollegiatId;
	}

	public Long getSerieId() {
		return serieId;
	}

	public void setSerieId(Long serieId) {
		this.serieId = serieId;
	}

	public Boolean getEnviatSAT() {
		return enviatSAT;
	}

	public void setEnviatSAT(Boolean enviatSAT) {
		this.enviatSAT = enviatSAT;
	}

	public DualListModel<AplicacionObject> getAplicaciones() {
		return aplicaciones;
	}

	public void setAplicaciones(DualListModel<AplicacionObject> aplicaciones) {
		this.aplicaciones = aplicaciones;
	}

	public ValoracioObject getValoracio() {
		return valoracio;
	}

	public void setValoracio(ValoracioObject valoracio) {
		this.valoracio = valoracio;
	}

	public Map<Long, SerieDocumentalObject> getSeriesById() {
		return seriesById;
	}

	public void setSeriesById(Map<Long, SerieDocumentalObject> seriesById) {
		this.seriesById = seriesById;
	}

	public Long getFuncioId() {
		return funcioId;
	}

	public void setFuncioId(Long funcioId) {
		this.funcioId = funcioId;
	}

	public NormativaAprobacioObject getNormativaABuscar() {
		return normativaABuscar;
	}

	public void setNormativaABuscar(NormativaAprobacioObject normativaABuscar) {
		this.normativaABuscar = normativaABuscar;
	}

	public List<NormativaAprobacioObject> getNormativasSeleccionadas() {
		return normativasSeleccionadas;
	}

	public void setNormativasSeleccionadas(List<NormativaAprobacioObject> normativasSeleccionadas) {
		this.normativasSeleccionadas = normativasSeleccionadas;
	}

	public CausaLimitacioObject getCausaABuscar() {
		return causaABuscar;
	}

	public void setCausaABuscar(CausaLimitacioObject causaABuscar) {
		this.causaABuscar = causaABuscar;
	}

	public List<CausaLimitacioObject> getAccessSeleccionadas() {
		return accessSeleccionadas;
	}

	public void setAccessSeleccionadas(List<CausaLimitacioObject> accessSeleccionadas) {
		this.accessSeleccionadas = accessSeleccionadas;
	}

	public AplicacioObject getAplicacionsABuscar() {
		return aplicacionsABuscar;
	}

	public void setAplicacionsABuscar(AplicacioObject aplicacionsABuscar) {
		this.aplicacionsABuscar = aplicacionsABuscar;
	}

	public List<AplicacioObject> getAplicacionsSeleccionadas() {
		return aplicacionsSeleccionadas;
	}

	public void setAplicacionsSeleccionadas(List<AplicacioObject> aplicacionsSeleccionadas) {
		this.aplicacionsSeleccionadas = aplicacionsSeleccionadas;
	}

	public SerieArgenObject getSeriesArgenABuscar() {
		return seriesArgenABuscar;
	}

	public void setSeriesArgenABuscar(SerieArgenObject seriesArgenABuscar) {
		this.seriesArgenABuscar = seriesArgenABuscar;
	}

	public List<SerieArgenObject> getSeriesArgenSeleccionadas() {
		return seriesArgenSeleccionadas;
	}

	public void setSeriesArgenSeleccionadas(List<SerieArgenObject> seriesArgenSeleccionadas) {
		this.seriesArgenSeleccionadas = seriesArgenSeleccionadas;
	}

	public SerieDocumentalObject getSerieABuscar() {
		return serieABuscar;
	}

	public void setSerieABuscar(SerieDocumentalObject serieABuscar) {
		this.serieABuscar = serieABuscar;
	}

	public List<SerieDocumentalObject> getSeriesSeleccionadas() {
		return seriesSeleccionadas;
	}

	public void setSeriesSeleccionadas(List<SerieDocumentalObject> seriesSeleccionadas) {
		this.seriesSeleccionadas = seriesSeleccionadas;
	}

	public ProcedimentVisorObject getPrcComunsABuscar() {
		return prcComunsABuscar;
	}

	public void setPrcComunsABuscar(ProcedimentVisorObject prcComunsABuscar) {
		this.prcComunsABuscar = prcComunsABuscar;
	}

	public List<ProcedimentVisorObject> getPrcComunsSeleccionadas() {
		return prcComunsSeleccionadas;
	}

	public void setPrcComunsSeleccionadas(List<ProcedimentVisorObject> prcComunsSeleccionadas) {
		this.prcComunsSeleccionadas = prcComunsSeleccionadas;
	}

	public ProcedimentVisorObject getPrcRelacionatsABuscar() {
		return prcRelacionatsABuscar;
	}

	public void setPrcRelacionatsABuscar(ProcedimentVisorObject prcRelacionatsABuscar) {
		this.prcRelacionatsABuscar = prcRelacionatsABuscar;
	}

	public List<ProcedimentVisorObject> getPrcRelacionatsSeleccionadas() {
		return prcRelacionatsSeleccionadas;
	}

	public void setPrcRelacionatsSeleccionadas(List<ProcedimentVisorObject> prcRelacionatsSeleccionadas) {
		this.prcRelacionatsSeleccionadas = prcRelacionatsSeleccionadas;
	}
	

}

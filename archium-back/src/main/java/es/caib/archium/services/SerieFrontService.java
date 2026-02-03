package es.caib.archium.services;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.ejb.objects.Dir3;
import es.caib.archium.ejb.service.AplicacioSerieService;
import es.caib.archium.ejb.service.AplicacioService;
import es.caib.archium.ejb.service.CatalegSerieService;
import es.caib.archium.ejb.service.CausaLimitacioService;
import es.caib.archium.ejb.service.Dir3Service;
import es.caib.archium.ejb.service.FuncioService;
import es.caib.archium.ejb.service.LimitacioNormativaSerieService;
import es.caib.archium.ejb.service.NormativaSerieService;
import es.caib.archium.ejb.service.NormativaService;
import es.caib.archium.ejb.service.OrganCollegiatService;
import es.caib.archium.ejb.service.ProcedimentSerieService;
import es.caib.archium.ejb.service.ProcedimientoService;
import es.caib.archium.ejb.service.SerieArgenService;
import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.ejb.service.SerieRelacionadaService;
import es.caib.archium.ejb.service.TipusDocumentSerieService;
import es.caib.archium.ejb.service.TipusDocumentalService;
import es.caib.archium.ejb.service.TipusRelacioSerieService;
import es.caib.archium.ejb.service.TipusSerieService;
import es.caib.archium.ejb.service.TipusValorService;
import es.caib.archium.ejb.service.ValorSecundariService;
import es.caib.archium.ejb.service.ValoracioService;
import es.caib.archium.objects.AplicacionObject;
import es.caib.archium.objects.CatalegSeriesObject;
import es.caib.archium.objects.CausaLimitacioObject;
import es.caib.archium.objects.Dir3Object;
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
import es.caib.archium.persistence.model.Aplicacio;
import es.caib.archium.persistence.model.AplicacioSerie;
import es.caib.archium.persistence.model.AplicacioSeriePK;
import es.caib.archium.persistence.model.Catalegsery;
import es.caib.archium.persistence.model.CausaLimitacio;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.LimitacioNormativaSerie;
import es.caib.archium.persistence.model.Normativa;
import es.caib.archium.persistence.model.NormativaSeriedocumental;
import es.caib.archium.persistence.model.NormativaSeriedocumentalPK;
import es.caib.archium.persistence.model.OrganCollegiat;
import es.caib.archium.persistence.model.Procediment;
import es.caib.archium.persistence.model.ProcedimentSerie;
import es.caib.archium.persistence.model.ProcedimentSeriePK;
import es.caib.archium.persistence.model.SerieArgen;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.Serierelacionada;
import es.caib.archium.persistence.model.TipusDocumental;
import es.caib.archium.persistence.model.TipusRelacioSerie;
import es.caib.archium.persistence.model.TipusSerie;
import es.caib.archium.persistence.model.TipusValor;
import es.caib.archium.persistence.model.TipusdocumentSerie;
import es.caib.archium.persistence.model.ValorSecundari;
import es.caib.archium.persistence.model.Valoracio;
import es.caib.archium.persistence.model.Valorprimari;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

@Named
@ApplicationScoped
public class SerieFrontService {
    protected final Logger log = LoggerFactory.getLogger(this.getClass());

    //Funciones
    @Inject
    private FuncioService funcioService;
    //CatalegSeries
    @Inject
    private CatalegSerieService catalegSerieService;
    //TipusSerie
    @Inject
    private TipusSerieService tipusSerieEJB;
    @Inject
    private SerieDocumentalService serieService;
    @Inject
    private AplicacioService aplicacioService;
    @Inject
    private AplicacioSerieService aplicacioSerieService;
    @Inject
    private SerieRelacionadaService serieRelacionadaService;
    @Inject
    private NormativaService normativaService;
    @Inject
    private CausaLimitacioService causaLimitacioService;
    @Inject
    private LimitacioNormativaSerieService limitacioNormativaSerieService;
    @Inject
    private SerieArgenService serieArgenService;
    @Inject
    private NormativaSerieService normativaSerieEJB;
    @Inject
    private TipusValorService tipusValorEJB;
    @Inject
    private ValorSecundariService valorSecundariEJB;
    @Inject
    private ValoracioService valoracioEJB;
    @Inject
    private Dir3Service dir3EJB;

    @Inject
    private TipusDocumentSerieService tipusdocumentalSerieEJB;

    @Inject
    private OrganCollegiatService organCollegiatEJB;

    @Inject
    private TipusRelacioSerieService tipusRelacioSerieEJB;

    @Inject
    private ProcedimientoService procedimientoService;

    @Inject
    TipusDocumentalService tipusDocumentaEJB;

    @Inject
    private ProcedimentSerieService procedimentSerieEJB;

    public List<Dir3Object> getListaDir3() throws I18NException {

        try {
            List<Dir3Object> lista = new ArrayList<Dir3Object>();

            for (Dir3 dir3 : dir3EJB.getAll()) {
                lista.add(new Dir3Object(dir3.getCodi(), dir3.getNom()));
            }

            return lista;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaDir3");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaDir3");
        }
    }

    public List<OrganCollegiatObject> getListaOrgansCollegiats() throws I18NException {

        try {
            List<OrganCollegiatObject> lista = new ArrayList<>();

            OrderBy orderByNom = new OrderBy("nom", OrderType.ASC);

            for (OrganCollegiat oc : organCollegiatEJB.findAll(orderByNom)) {
                lista.add(new OrganCollegiatObject(oc.getId(), oc.getCodi(), oc.getNom()));
            }

            return lista;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaOrgansCollegiats");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaOrgansCollegiats");
        }
    }

    @Transactional
    public ValoracioObject getValoracioSerie(Long serieId) throws I18NException {
        try {
            return new ValoracioObject(valoracioEJB.getBySerie(serieId));
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getValoracioSerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getValoracioSerie");
        }
    }

    public List<TipuValorObject> getListaTipusValor() throws I18NException {

        try {
            List<TipuValorObject> res = new ArrayList<TipuValorObject>();

            for (TipusValor db : tipusValorEJB.findAll()) {
                res.add(new TipuValorObject(db));
            }

            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaTipusValor");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaTipusValor");
        }


    }

    @Transactional
    public List<ProcedimentObject> getProcedimentsComunsBySerie(Long idSerie) throws I18NException{

        try {
            List<ProcedimentObject> llista = new ArrayList<>();
            List<ProcedimentSerie> procedimentsComuns  = procedimentSerieEJB.getProcedimentComuns(idSerie);

            for(ProcedimentSerie  prcComu : procedimentsComuns) {
                ProcedimentObject prc = new ProcedimentObject(prcComu.getAchProcediment());
                llista.add(prc);
            }
            return llista;
        } catch(NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getProcedimentsComunsBySerie");
        } catch(Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getProcedimentsComunsBySerie");
        }
    }

    /**
     * Retorna tots els procediments comuns (els que es troben lligats a alguna sèrie de manera no directa, relació N:M)
     * @return
     * @throws I18NException
     */
    @Transactional
    public List<ProcedimentObject> getTotsProcedimentsComuns() throws I18NException{

        try {
            List<ProcedimentObject> llista = new ArrayList<>();
            Set<Procediment> procedimentsComuns  = procedimentSerieEJB.getTotProcedimentComuns();

            for(Procediment  prcComu : procedimentsComuns) {
                ProcedimentObject prc = new ProcedimentObject(prcComu);
                llista.add(prc);
            }
            return llista;
        } catch(NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getTotsProcedimentsComuns");
        } catch(Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getTotsProcedimentsComuns");
        }
    }

    public List<ValorSecundariObject> getListaValorsSecundari() throws I18NException {

        try {
            List<ValorSecundariObject> res = new ArrayList<ValorSecundariObject>();

            for (ValorSecundari db : valorSecundariEJB.findAll()) {
                res.add(new ValorSecundariObject(db));
            }

            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaValorsSecundari");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaValorsSecundari");
        }
    }

    public ValorSecundariObject findValorSecundariById(Long valorSecundariId) throws I18NException {

        try {
            return new ValorSecundariObject(valorSecundariEJB.findById(valorSecundariId));
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findValorSecundariById");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findValorSecundariById");
        }
    }

    public List<FuncioObject> getListaFunciones() throws I18NException {

        try {
            List<FuncioObject> res = new ArrayList<>();

            for (Funcio it : funcioService.findAll()) {
                FuncioObject toInsert = new FuncioObject();
                toInsert.setId(it.getId());
                toInsert.setCodi(it.getCodi());
                toInsert.setNom(it.getNom());
                toInsert.setNomcas(it.getNomcas());
                res.add(toInsert);
            }

            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaFunciones");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaFunciones");
        }


    }

    public List<CatalegSeriesObject> getListaCatalegSerie() throws I18NException {

        try {
            List<CatalegSeriesObject> res = new ArrayList<>();

            for (Catalegsery it : catalegSerieService.findAll()) {
                CatalegSeriesObject toInsert = new CatalegSeriesObject();
                toInsert.setId(it.getId());
                toInsert.setNom(it.getNom());
                toInsert.setNomCas(it.getNomcas());
                res.add(toInsert);
            }

            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaCatalegSerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaCatalegSerie");
        }
    }

    public List<TipusSerieObject> getListaTipusSerie() throws I18NException {

        try {
            List<TipusSerieObject> res = new ArrayList<>();

            for (TipusSerie it : tipusSerieEJB.findAll()) {
                TipusSerieObject toInsert = new TipusSerieObject();
                toInsert.setId(it.getId());
                toInsert.setNom(it.getNom());
                toInsert.setNomCas(it.getNomcas());
                res.add(toInsert);
            }

            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaTipusSerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaTipusSerie");
        }

    }

    /**
     * Retorna la llista de totes les sèries documentals, a efectes d'omplir llistes desplegables de sèries (i relacionar amb altres sèries).
     * Si s'especifica un idenficiador de sèrie, llavors l'exclou de la llista.
     *
     * @param serieId
     * @return
     * @throws I18NException
     */
    public List<SerieDocumentalObject> getListaSeries(Long serieId) throws I18NException {

        try {
            List<SerieDocumentalObject> res = new ArrayList<>();
            List<Seriedocumental> list = serieService.findAll();

            for (Seriedocumental s : list) {
                if (serieId == null || !serieId.equals(s.getId())) {
                    SerieDocumentalObject toInsert = new SerieDocumentalObject();
                    toInsert.setSerieId(s.getId());
                    toInsert.setNom(s.getNom());
                    toInsert.setCodi(s.getCodi());
                    res.add(toInsert);
                }
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaSeries");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaSeries");
        }
    }

    public List<ProcedimentObject> getListaProcediments() throws I18NException {

        try {
            List<ProcedimentObject> res = new ArrayList<>();
            List<Procediment> list = procedimientoService.findAll();

            for (Procediment prc : list) {
                ProcedimentObject procedimentObject = new ProcedimentObject();
                procedimentObject.setId(prc.getId());
                procedimentObject.setCodisia(prc.getCodisia());
                procedimentObject.setNom(prc.getNom());
                res.add(procedimentObject);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaProcediments");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaProcediments");
        }
    }

    /**
     * Només els procediments que no estiguin lligats directament a una sèrie (relació 1:N) són candidats de figurar com
     * a procediments "comuns" de les series
     *
     * @return
     * @throws I18NException
     */
    public List<ProcedimentObject> getListaProcedimentsSenseSerie() throws I18NException {

        try {
            List<ProcedimentObject> res = new ArrayList<>();

            List<Procediment> list = procedimientoService.findProcedimentsSenseSerie();

            for (Procediment prc : list) {
                ProcedimentObject procedimentObject = new ProcedimentObject();
                procedimentObject.setId(prc.getId());
                procedimentObject.setCodisia(prc.getCodisia());
                procedimentObject.setNom(prc.getNom());
                res.add(procedimentObject);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaProcedimentsSenseSerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaProcedimentsSenseSerie");
        }

    }

    public List<SerieArgenObject> getListaSeriesArgen() throws I18NException {

        try {
            List<SerieArgenObject> res = new ArrayList<>();
            List<SerieArgen> list = serieArgenService.findAll();

            for (SerieArgen s : list) {
                SerieArgenObject toInsert = new SerieArgenObject();
                toInsert.setId(s.getId());
                toInsert.setNom(s.getNom());
                toInsert.setCodi(s.getCodi());
                res.add(toInsert);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaSeriesArgen");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaSeriesArgen");
        }

    }

    public NormativaAprobacioObject getNormativaById(Long normativaId) throws I18NException {

        try {
            return new NormativaAprobacioObject(normativaService.findById(normativaId));
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaNormativas");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaNormativas");
        }
    }

    public List<NormativaAprobacioObject> getListaNormativas() throws I18NException {

        try {
            List<NormativaAprobacioObject> res = new ArrayList<>();
            List<Normativa> list = normativaService.findAll();

            for (Normativa s : list) {
                NormativaAprobacioObject toInsert = new NormativaAprobacioObject();
                toInsert.setId(s.getId());
                toInsert.setNom(s.getNom());
                res.add(toInsert);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaNormativas");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaNormativas");
        }
    }

    public List<TipusRelacioSerieObject> getListaTipusRelacio() throws I18NException {

        try {
            List<TipusRelacioSerieObject> res = new ArrayList<>();
            List<TipusRelacioSerie> list = tipusRelacioSerieEJB.findAll();

            for (TipusRelacioSerie s : list) {
                TipusRelacioSerieObject toInsert = new TipusRelacioSerieObject();
                toInsert.setId(s.getId());
                toInsert.setNom(s.getNom());
                res.add(toInsert);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaTipusRelacio");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaTipusRelacio");
        }


    }

    public CausaLimitacioObject getListaCausaLimitacioById(Long id) {
        CausaLimitacio cl = causaLimitacioService.findById(id);
        return new CausaLimitacioObject(cl);
    }


    public List<CausaLimitacioObject> getListaCausaLimitacio() throws I18NException {

        try {
            List<CausaLimitacioObject> res = new ArrayList<>();
            List<CausaLimitacio> list = causaLimitacioService.findAll();

            for (CausaLimitacio s : list) {
                CausaLimitacioObject toInsert = new CausaLimitacioObject();
                toInsert.setId(s.getId());
                toInsert.setNom(s.getNom());
                res.add(toInsert);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaCausaLimitacio");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaCausaLimitacio");
        }


    }

    public List<AplicacionObject> getListaAplicaciones() throws I18NException {

        try {
            List<AplicacionObject> res = new ArrayList<>();
            List<Aplicacio> list = aplicacioService.findAll();

            for (Aplicacio s : list) {
                AplicacionObject toInsert = new AplicacionObject();
                toInsert.setId(s.getId());
                toInsert.setNom(s.getNom());
                res.add(toInsert);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaAplicaciones");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaAplicaciones");
        }


    }

    public List<SerieDocumentalObject> getListaSeriesBySerie(Long serieId) throws I18NException {

        try {
            List<SerieDocumentalObject> res = new ArrayList<>();
            List<Serierelacionada> list = serieRelacionadaService.findSeriesBySerie(serieId);

            for (Serierelacionada s : list) {
                if (s.getAchSeriedocumental2() == null)
                    continue;
                SerieDocumentalObject toInsert = new SerieDocumentalObject();
                toInsert.setSerieId(s.getAchSeriedocumental2().getId());
                toInsert.setNom(s.getAchSeriedocumental2().getNom());
                toInsert.setCodi(s.getAchSeriedocumental2().getCodi());
                res.add(toInsert);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaSeriesBySerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaSeriesBySerie");
        }
    }

    public List<ProcedimentObject> getListaProcedimentsBySerie(Long serieId) throws I18NException {

        try {
            List<ProcedimentObject> res = new ArrayList<>();
            List<Procediment> list = procedimientoService.findProcedimentsBySerie(serieId);

            for (Procediment prc : list) {
                ProcedimentObject toInsert = new ProcedimentObject();
                toInsert.setId(prc.getId());
                toInsert.setNom(prc.getNom());
                toInsert.setCodisia(prc.getCodisia());
                res.add(toInsert);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaProcedimentsBySerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaProcedimentsBySerie");
        }
    }

    public List<SerieArgenObject> getListaSeriesArgenBySerie(Long serieId) throws I18NException {

        try {
            List<SerieArgenObject> res = new ArrayList<>();
            List<Serierelacionada> list = serieRelacionadaService.findArgenBySerie(serieId);

            for (Serierelacionada s : list) {
                SerieArgenObject toInsert = new SerieArgenObject();
                toInsert.setId(s.getAchSerieargen().getId());
                toInsert.setNom(s.getAchSerieargen().getNom());
                toInsert.setCodi(s.getAchSerieargen().getCodi());
                res.add(toInsert);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaSeriesArgenBySerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaSeriesArgenBySerie");
        }
    }

    public List<NormativaAprobacioObject> getListaNormativasBySerie(Long serieId) throws I18NException {

        try {
            List<NormativaAprobacioObject> res = new ArrayList<>();
            List<NormativaSeriedocumental> list = normativaSerieEJB.getBySerie(serieId);
            for (NormativaSeriedocumental s : list) {
                NormativaAprobacioObject toinsert = new NormativaAprobacioObject();
                toinsert.setId(s.getAchNormativa().getId());
                toinsert.setCodi(s.getAchNormativa().getCodi());
                toinsert.setNom(s.getAchNormativa().getNom());
                res.add(toinsert);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaNormativasBySerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaNormativasBySerie");
        }

    }

    public List<AplicacionObject> getListaAplicacionesBySerie(Long serieId) throws I18NException {


        try {
            List<AplicacionObject> res = new ArrayList<>();
            List<AplicacioSerie> list = aplicacioSerieService.getBySerie(serieId);

            for (AplicacioSerie s : list) {
                AplicacionObject toInsert = new AplicacionObject();
                toInsert.setId(s.getAchAplicacio().getId());
                toInsert.setNom(s.getAchAplicacio().getNom());
                res.add(toInsert);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaAplicacionesBySerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaAplicacionesBySerie");
        }


    }

    public List<LimitacioNormativaSerieObject> getListaLNS(Long serieId) throws I18NException {

        try {
            List<LimitacioNormativaSerieObject> res = new ArrayList<>();
            List<LimitacioNormativaSerie> list = limitacioNormativaSerieService.getBySerie(serieId);

            for (LimitacioNormativaSerie lns : list) {
                int index = res.indexOf(new LimitacioNormativaSerieObject(lns));
                if (index >= 0) {
                    res.get(index).addCausaLimitacio(new CausaLimitacioObject(lns.getAchCausalimitacio()));
                } else {
                    LimitacioNormativaSerieObject lnsOb = new LimitacioNormativaSerieObject(lns);
                    lnsOb.addCausaLimitacio(new CausaLimitacioObject(lns.getAchCausalimitacio()));
                    lnsOb.setDualListCausas(new DualListModel<CausaLimitacioObject>(new ArrayList<>(), new ArrayList<>()));
                    res.add(lnsOb);
                }
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaLNS");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaLNS");
        }
    }

    public List<RelacioSerieObject> getListaRelacions(Long serieId) throws I18NException {

        try {
            List<RelacioSerieObject> res = new ArrayList<>();
            List<Serierelacionada> list = serieRelacionadaService.findSeriesBySerie(serieId);

            for (Serierelacionada lns : list) {
                int index = res.indexOf(new RelacioSerieObject(lns));
                if (index >= 0) {
                    res.get(index).addSerieDocumental(new SerieDocumentalObject(lns.getAchSeriedocumental2()));
                } else {
                    RelacioSerieObject lnsOb = new RelacioSerieObject(lns);
                    lnsOb.addSerieDocumental(new SerieDocumentalObject(lns.getAchSeriedocumental2()));
                    lnsOb.setDualListSeries(new DualListModel<>(new ArrayList<>(), new ArrayList<>()));
                    res.add(lnsOb);
                }
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaLNS");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaLNS");
        }
    }

    public SerieDocumentalObject findById(Long id) throws I18NException {
        try {
            Seriedocumental s = this.serieService.findById(id);

            if (s != null) {
                return new SerieDocumentalObject(s);
            }
            return null;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findById");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findById");
        }
    }

    @Transactional
    public SerieDocumentalObject createNuevaSerie(String codi, String nom, String nomCas, Long catalegSeriId, Long idFuncio,
                                                  String descripcio, String descripcioCas, String resumMigracio, Boolean enviatSAT, String dir3Promotor, String estat, Long tipusSerieId,
                                                  String codiIecisa, List<AplicacionObject> listaApps, List<ProcedimentObject> listaPrcs, List<ProcedimentObject> listaPrcsComuns, List<RelacioSerieObject> relacionsSeries, List<SerieArgenObject> argensList, List<LimitacioNormativaSerieObject> listaLNS
            , List<NormativaAprobacioObject> normativasList, ValoracioObject valoracio
            , Long organCollegiatId, List<TipuDocumentalSerieObject> listaTDS) throws I18NException {


        // Control ACH-033: No ha de ser possible introduir a la sèrie procediments i procediments comuns a la vegada
        if (listaPrcs != null && listaPrcs.size() > 0 && listaPrcsComuns != null && listaPrcsComuns.size() > 0) {
            log.debug("Intent de crear sèrie amb procediments i procediments comuns!");
            throw new I18NException("excepcion.createSerie.ExceptionPrc", this.getClass().getSimpleName(), "createNuevaSerie");
        }

        try {
            Seriedocumental toPersist = new Seriedocumental();

            toPersist.setCodi(codi);
            if (tipusSerieId != null) toPersist.setAchTipusserie(tipusSerieEJB.getReference(tipusSerieId));
            toPersist.setAchFuncio(funcioService.getReference(idFuncio));
            toPersist.setAchCatalegsery(catalegSerieService.getReference(catalegSeriId));
            toPersist.setDescripcio(descripcio);
            toPersist.setDescripciocas(descripcioCas);
            toPersist.setResummigracio(resumMigracio);
            toPersist.setEnviatSAT(enviatSAT);
            toPersist.setDir3Promotor(dir3Promotor);
            toPersist.setCodiiecisa(codiIecisa);
            toPersist.setNom(nom);
            toPersist.setNomcas(nomCas);
            toPersist.setEstat(estat);

            if (organCollegiatId != null) {
                toPersist.setAchOrganCollegiat(organCollegiatEJB.getReference(organCollegiatId));
            } else {
                toPersist.setAchOrganCollegiat(null);
            }

            toPersist.setAchAplicacioSeries(new ArrayList<>());

            for (AplicacionObject b : listaApps) {
                AplicacioSerie aplicacioSeri = new AplicacioSerie();
                AplicacioSeriePK pk = new AplicacioSeriePK();
                pk.setAplicacioId(b.getId());
                aplicacioSeri.setId(pk);
                aplicacioSeri.setAchAplicacio(aplicacioService.getReference(b.getId()));
                aplicacioSeri.setDescripcio("Relacion entre " + toPersist.getNom() + " y " + b.getNom());
                aplicacioSeri.setInici(new Date());
                toPersist.addAchAplicacioSery(aplicacioSeri);
            }

            toPersist.setAchSerierelacionadas1(new HashSet<>());
            toPersist.setAchSerierelacionadas2(new HashSet<>());
            for (RelacioSerieObject relSer : relacionsSeries) {
                TipusRelacioSerie tipusRelacio = tipusRelacioSerieEJB.getReference(relSer.getTipusRelacio().getId());
                for (SerieDocumentalObject sd : relSer.getDualListSeries().getTarget()) {
                    Serierelacionada rel = new Serierelacionada();
                    rel.setTipusRelacioSerie(tipusRelacio);
                    rel.setAchSeriedocumental2(serieService.getReference(relSer.getSeriedocumental().getSerieId()));
                    toPersist.addAchSerierelacionadas1(rel);
                }
            }

            for (SerieArgenObject argen : argensList) {
                SerieArgen sargen = serieArgenService.getReference(argen.getId());
                Serierelacionada rel = new Serierelacionada();
                rel.setAchSerieargen(sargen);
                toPersist.addAchSerierelacionadas1(rel);
            }

            toPersist.setAchNormativaSeriedocumentals(new ArrayList<>());
            for (NormativaAprobacioObject normativaSerie : normativasList) {
                NormativaSeriedocumentalPK pk = new NormativaSeriedocumentalPK();
                pk.setNormativaId(normativaSerie.getId());
                NormativaSeriedocumental normSerie = new NormativaSeriedocumental();
                normSerie.setId(pk);
                normSerie.setAchNormativa(normativaService.getReference(normativaSerie.getId()));
                normSerie.setInici(new Date());
                toPersist.addAchNormativaSeriedocumental(normSerie);
            }

            toPersist.setAchLimitacioNormativaSeries(new ArrayList<>());
            for (LimitacioNormativaSerieObject lns : listaLNS) {
                for (CausaLimitacioObject cl : lns.getDualListCausas().getTarget()) {
                    LimitacioNormativaSerie lnsDB = lns.toDbObject();
                    lnsDB.setAchNormativa(normativaService.getReference(lnsDB.getId().getNormativaId()));
                    lnsDB.setAchCausalimitacio(causaLimitacioService.getReference(cl.getId()));
                    lnsDB.setInici(new Date());
                    toPersist.addAchLimitacioNormativaSery(lnsDB);
                }
            }

            toPersist.setAchValoracios(new ArrayList<>());
            if (valoracio != null && valoracio.getAchValorsecundari() != null && valoracio.getAchValorprimaris() != null) {
                Valoracio v = new Valoracio();
                v.setAchValorprimaris(new ArrayList<>());
                v.setAchValorsecundari(valorSecundariEJB.getReference(valoracio.getAchValorsecundari().getId()));
                v.setInici(new Date());
                for (ValorPrimariObject vp : valoracio.getAchValorprimaris()) {
                    if (vp.getSelected()) {
                        Valorprimari valorPrimari = vp.toDbObject();
                        valorPrimari.setAchTipusvalor(tipusValorEJB.getReference(vp.getAchTipusvalor().getId()));
                        v.addAchValorprimari(valorPrimari);
                    }
                }
                toPersist.addAchValoracio(v);
            }

            // ACH-007
            if (listaPrcs != null) {
                toPersist.setAchProcediments(new ArrayList<>());
                for (ProcedimentObject prc : listaPrcs) {
                    Procediment p = procedimientoService.getReference(prc.getId());
                    toPersist.addAchProcediment(p);
                }
            }

            // ACH-033
            if (listaPrcsComuns != null) {
                toPersist.setAchProcedimentsComuns(new ArrayList<>());

                for (ProcedimentObject prc : listaPrcsComuns) {
                    ProcedimentSeriePK pk = new ProcedimentSeriePK();
                    pk.setProcedimentId(prc.getId());

                    ProcedimentSerie prcSerie = new ProcedimentSerie();
                    prcSerie.setId(pk);
                    prcSerie.setAchProcediment(procedimientoService.getReference(prc.getId()));

                    toPersist.addAchProcedimentComu(prcSerie);
                }
            }

            toPersist.setAchTipusdocumentSeries(new HashSet<>());
            for(TipuDocumentalSerieObject tdp : listaTDS) {
                TipusdocumentSerie tdpDB = tdp.toDbObject();
                tdpDB.setAchTipusdocumental(tipusDocumentaEJB.getReference(tdp.getTipusDocumental().getId()));;
                toPersist.addAchTipusdocumentSerie(tdpDB);
            }
            Seriedocumental res = serieService.create(toPersist);


            return new SerieDocumentalObject(res);
        } catch (NullPointerException e) {
            log.error("Error creant la sèrie documental", e);
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "createNuevaSerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "createNuevaSerie");
        }


    }

    @Transactional
    public SerieDocumentalObject updateSerieDocumental(Long idSerie, String codi, String nom, String nomCas, Long catalegSeriId, Long funcioId,
                                                       String descripcio, String descripcioCas, String resumMigracio, Boolean enviatSAT, String dir3Promotor, String estat, Long tipusSerieId,
                                                       String codiIecisa, List<AplicacionObject> listaApps, List<ProcedimentObject> listaPrcs, List<ProcedimentObject> listaPrcsComuns, List<RelacioSerieObject> seriesRelacionades, List<SerieArgenObject> argenRelateds, List<LimitacioNormativaSerieObject> listaLNS
            , List<NormativaAprobacioObject> normativasList, ValoracioObject valoracio
    , Long organCollegiatId, List<TipuDocumentalSerieObject> listaTDS) throws I18NException {

        // Control ACH-033: No ha de ser possible introduir a la sèrie procediments i procediments comuns a la vegada
        if (listaPrcs != null && listaPrcs.size() > 0 && listaPrcsComuns != null && listaPrcsComuns.size() > 0) {
            log.debug("Intent de crear sèrie amb procediments i procediments comuns!");
            throw new I18NException("excepcion.createSerie.ExceptionPrc", this.getClass().getSimpleName(), "updateSerieDocumental");
        }

        try {
            Seriedocumental toPersist = serieService.getReference(idSerie);

            if (codi != null) toPersist.setCodi(codi);
            if (tipusSerieId != null) toPersist.setAchTipusserie(tipusSerieEJB.getReference(tipusSerieId));
            if (funcioId != null) toPersist.setAchFuncio(funcioService.getReference(funcioId));
            if (catalegSeriId != null) toPersist.setAchCatalegsery(catalegSerieService.getReference(catalegSeriId));
            if (descripcio != null) toPersist.setDescripcio(descripcio);
            if (descripcioCas != null) toPersist.setDescripciocas(descripcioCas);
            if (resumMigracio != null) toPersist.setResummigracio(resumMigracio);
            toPersist.setEnviatSAT(enviatSAT);
            if (dir3Promotor != null) toPersist.setDir3Promotor(dir3Promotor);
            if (codiIecisa != null) toPersist.setCodiiecisa(codiIecisa);
            if (nom != null) toPersist.setNom(nom);
            if (nomCas != null) toPersist.setNomcas(nomCas);
            if (estat != null) toPersist.setEstat(estat);

            if (organCollegiatId != null) {
                toPersist.setAchOrganCollegiat(organCollegiatEJB.getReference(organCollegiatId));
            } else {
                toPersist.setAchOrganCollegiat(null);
            }

            for (AplicacionObject b : listaApps) {
                AplicacioSerie existe = null;
                Iterator<AplicacioSerie> it = toPersist.getAchAplicacioSeries().iterator();

                while (it.hasNext() && existe == null) {
                    AplicacioSerie as = it.next();
                    if (as.getAchAplicacio().getId().equals(b.getId())) {
                        existe = as;
                    }
                }

                if (existe == null) {
                    AplicacioSerie aplicacioSeri = new AplicacioSerie();
                    AplicacioSeriePK pk = new AplicacioSeriePK();
                    pk.setAplicacioId(b.getId());
                    pk.setSeriedocumentalId(toPersist.getId());
                    aplicacioSeri.setId(pk);
                    aplicacioSeri.setAchAplicacio(aplicacioService.getReference(b.getId()));
                    aplicacioSeri.setDescripcio("Relacion entre " + toPersist.getNom() + " y " + b.getNom());
                    aplicacioSeri.setInici(new Date());
                    toPersist.addAchAplicacioSery(aplicacioSeri);

                }

            }
            toPersist.getAchAplicacioSeries().removeIf(item -> !listaApps.contains(new AplicacionObject(item.getAchAplicacio())));


            // ACH-007 Manteniment dels procediments vinculats a la sèrie
            log.debug("Llista de procediments: " + listaPrcs.size());
            for (ProcedimentObject prc : listaPrcs) {
                log.debug("Avaluant procediment " + prc + "...");
                Procediment procedimentJaVinculat = null;
                Iterator<Procediment> itPrc = toPersist.getAchProcediments().iterator();

                while (itPrc.hasNext() && procedimentJaVinculat == null) {
                    Procediment p = itPrc.next();
                    if (p.getId().equals(prc.getId())) {
                        log.debug("Procediment ja vinculat: " + p);
                        procedimentJaVinculat = p;
                    }
                }

                if (procedimentJaVinculat == null) {
                    log.debug("Procediment encara no vinculat: Afegint a la sèrie");
                    Procediment procedimentAVincular = procedimientoService.getReference(prc.getId());
                    toPersist.addAchProcediment(procedimentAVincular);
                }
            }
            // Una vegada afegits els nous procediments vinculats a la sèrie, llevarem els que ja no siguin a la llista nova de procediments
            Iterator<Procediment> itPrcs = toPersist.getAchProcediments().iterator();
            while (itPrcs.hasNext()) {
                Procediment item = itPrcs.next();
                log.debug("La llista de procediments que han actualitzat no conté el procediment que actualment està vinculat a la sèrie? id = " + item.getId());
                if (!listaPrcs.contains(new ProcedimentObject(item.getId()))) {
                    log.debug("No el conté, per tant l'eliminam!");
                    itPrcs.remove();
                    item.setAchSeriedocumental(null);
                }
            }
            ///// Fi ACH-007


            /////// SÈRIES RELACIONADES
            for (RelacioSerieObject serieRelacionadaForm : seriesRelacionades) {
                log.debug("Analizando serieRelacionadaForm de tipo : " + serieRelacionadaForm.getTipusRelacio().getId());
                log.debug("Analizando serieRelacionadaForm, series relacionadas : " + serieRelacionadaForm.getSeriesRelacionades().size());
                log.debug("Analizando serieRelacionadaForm, series relacionadas <target> : " + serieRelacionadaForm.getDualListSeries().getTarget().size());

                for (SerieDocumentalObject serieDocumentalTarget : serieRelacionadaForm.getDualListSeries().getTarget()) {
                    Serierelacionada existe = null;
                    Iterator<Serierelacionada> itSerRel1 = toPersist.getAchSerierelacionadas1().iterator();

                    log.debug("La serie de BD (toPersist) tiene " + toPersist.getAchSerierelacionadas1().size() + "en serRel1");
                    log.debug("La serie de BD (toPersist) tiene " + toPersist.getAchSerierelacionadas2().size() + "en serRel2");

                    while (itSerRel1.hasNext() && existe == null) {
                        Serierelacionada serieRelacionadaExistent = itSerRel1.next();
                        log.debug("serieRelacionadaExistent.tipusRelacio.id = " + serieRelacionadaExistent.getTipusRelacioSerie().getId());
                        log.debug("serieRelacionadaExistent.serieDocumental2.id = " + (serieRelacionadaExistent.getAchSeriedocumental2() != null ? serieRelacionadaExistent.getAchSeriedocumental2().getId() : "null"));
                        log.debug("serieDocumentalTarget.serie.id = " + serieDocumentalTarget.getSerieId());
                        if (serieRelacionadaExistent.getTipusRelacioSerie().getId().equals(serieRelacionadaForm.getTipusRelacio().getId())
                                && serieRelacionadaExistent.getAchSeriedocumental2() != null && serieRelacionadaExistent.getAchSeriedocumental2().getId().equals(serieDocumentalTarget.getSerieId())) {
                            log.debug("Ya existe la serie documental en la BD!!!");
                            existe = serieRelacionadaExistent;
                        }
                    }

                    // No existeix encara la sèrie relacionada per aquest tipus de relació... inserir
                    if (existe == null) {
                        log.debug("Insertando la serie relacionada porque todavía no existe en BD");
                        serieRelacionadaForm.setSeriedocumental(new SerieDocumentalObject(toPersist));

                        Serierelacionada serRel = new Serierelacionada();
                        serRel.setTipusRelacioSerie(tipusRelacioSerieEJB.getReference(serieRelacionadaForm.getTipusRelacio().getId()));
                        serRel.setAchSeriedocumental1(toPersist);
                        serRel.setAchSeriedocumental2(serieService.getReference(serieDocumentalTarget.getSerieId()));
                        toPersist.addAchSerierelacionadas1(serRel);
                    }
                }
            }
            // Eliminació de sèries que es trobaven a la BD i s'han deseleccionat del picklist...
            Iterator<Serierelacionada> itlsr = toPersist.getAchSerierelacionadas1().iterator();
            log.debug("Analizando qué ELIMINAR si se ha deseleccionado de la picklist");
            log.debug("toPersist.serieRelacionadas1.size = " + toPersist.getAchSerierelacionadas1().size());
            log.debug("toPersist.serieRelacionadas2.size = " + toPersist.getAchSerierelacionadas2().size());
            while (itlsr.hasNext()) {
                Serierelacionada item = itlsr.next();
                if (item.getAchSerieargen() == null) {
                    int id = seriesRelacionades.indexOf(new RelacioSerieObject(item));
                    if (id < 0) {
                        log.debug("El tipo de relación para la serie en cuestión ha dejado de existir... se elimina");
                        itlsr.remove();
                    } else {
                        log.debug("El tipo de relación para la serie en cuestión sigue existiendo... Vamos a ver si sigue existiendo la serie");
                        if (!seriesRelacionades.get(id).getDualListSeries().getTarget().contains(new SerieDocumentalObject(item.getAchSeriedocumental2()))) {
                            itlsr.remove();
                        }
                    }
                }
            }


            log.debug("Series relacionades: " + toPersist.getAchSerierelacionadas1().size());
            ///////// ARGEN
            for (SerieArgenObject argen : argenRelateds) {
                SerieArgen sargen = serieArgenService.getReference(argen.getId());
                Serierelacionada rel = new Serierelacionada();
                rel.setAchSerieargen(sargen);
                rel.setTipusRelacioSerie(tipusRelacioSerieEJB.findByCodi("TEM"));
                rel.setAchSeriedocumental1(toPersist);
                toPersist.addAchSerierelacionadas1(rel);
                log.debug("Series relacionades després d'afegir Argen: " + toPersist.getAchSerierelacionadas1().size());
            }

            Iterator<Serierelacionada> itarg = toPersist.getAchSerierelacionadas1().iterator();
            while (itarg.hasNext()) {
                Serierelacionada item = itarg.next();
                if (item.getAchSerieargen() != null && !argenRelateds.contains(new SerieArgenObject(item.getAchSerieargen()))) {
                    itarg.remove();
                }
            }



            for (NormativaAprobacioObject na : normativasList) {

                NormativaSeriedocumental existe = null;
                Iterator<NormativaSeriedocumental> itna = toPersist.getAchNormativaSeriedocumentals().iterator();

                while (itna.hasNext() && existe == null) {
                    NormativaSeriedocumental ns = itna.next();
                    if (ns.getAchNormativa().getId().equals(na.getId())) {
                        existe = ns;
                    }
                }

                if (existe == null) {

                    NormativaSeriedocumentalPK pk = new NormativaSeriedocumentalPK();
                    pk.setNormativaId(na.getId());
                    pk.setSeriedocumentalId(toPersist.getId());
                    NormativaSeriedocumental normSerie = new NormativaSeriedocumental();
                    normSerie.setId(pk);
                    normSerie.setAchNormativa(normativaService.getReference(na.getId()));
                    normSerie.setAchSeriedocumental(toPersist);
                    normSerie.setInici(new Date());
                    toPersist.addAchNormativaSeriedocumental(normSerie);

                }

            }

            Iterator<NormativaSeriedocumental> itnsd = toPersist.getAchNormativaSeriedocumentals().iterator();

            while (itnsd.hasNext()) {
                NormativaSeriedocumental item = itnsd.next();
                if (!normativasList.contains(new NormativaAprobacioObject(item.getAchNormativa()))) {
                    itnsd.remove();
                }
            }


            log.debug("Procediments comuns que han quedat al target: " + listaPrcsComuns);
            log.debug("Abans de començar. Procediments comuns de la sèrie: " + toPersist.getAchProcedimentsComuns().size());
            ///// ACH-033 Procediments comuns
            for (ProcedimentObject na : listaPrcsComuns) {

                ProcedimentSerie existe = null;
                Iterator<ProcedimentSerie> itPrcSerie = toPersist.getAchProcedimentsComuns().iterator();

                log.debug("Analitzant els procediments comuns de la sèrie: " + toPersist.getAchProcedimentsComuns().size());
                while (itPrcSerie.hasNext() && existe == null) {

                    ProcedimentSerie prcSerie = itPrcSerie.next();
                    log.debug("Analitzant prcSerie: " + prcSerie);
                    if (prcSerie.getAchProcediment().getId().equals(na.getId())) {
                        existe = prcSerie;
                    }
                }

                if (existe == null) {
                    log.debug("No existeix el procediment comú... el ficam!");
                    ProcedimentSeriePK pk = new ProcedimentSeriePK();
                    pk.setProcedimentId(na.getId());
                    pk.setSeriedocumentalId(toPersist.getId());

                    log.debug("procedimentId = " + na.getId() + "; serieDocumentalId = " + toPersist.getId());

                    ProcedimentSerie prcSerie = new ProcedimentSerie();
                    prcSerie.setId(pk);
                    prcSerie.setAchProcediment(procedimientoService.getReference(na.getId()));
                    prcSerie.setAchSerieDocumental(toPersist);
                    toPersist.addAchProcedimentComu(prcSerie);
                }
            }

            toPersist.getAchProcedimentsComuns().removeIf(item -> !listaPrcsComuns.contains(new ProcedimentObject(item.getAchProcediment())));

            log.debug("Procediments comuns queda amb: " + toPersist.getAchProcedimentsComuns().size());
            ///// Fi Procediments comuns


            for (LimitacioNormativaSerieObject lns : listaLNS) {
                for (CausaLimitacioObject cl : lns.getDualListCausas().getTarget()) {

                    LimitacioNormativaSerie existe = null;
                    Iterator<LimitacioNormativaSerie> itnas = toPersist.getAchLimitacioNormativaSeries().iterator();

                    while (itnas.hasNext() && existe == null) {
                        LimitacioNormativaSerie lnsitem = itnas.next();
                        if (lnsitem.getAchNormativa().getId().equals(lns.getNormativa().getId())
                                && lnsitem.getAchCausalimitacio().getId().equals(cl.getId())) {
                            existe = lnsitem;
                        }
                    }

                    if (existe == null) {
                        lns.setSeriedocumental(new SerieDocumentalObject(toPersist));
                        LimitacioNormativaSerie lnsDB = lns.toDbObject();
                        lnsDB.getId().setCausalimitacioId(cl.getId());
                        lnsDB.setInici(new Date());
                        lnsDB.setAchNormativa(normativaService.getReference(lnsDB.getId().getNormativaId()));
                        lnsDB.setAchCausalimitacio(causaLimitacioService.getReference(cl.getId()));
                        lnsDB.setAchSeriedocumental(toPersist);
                        toPersist.addAchLimitacioNormativaSery(lnsDB);
                    }
                }
            }

            Iterator<LimitacioNormativaSerie> itlnsd = toPersist.getAchLimitacioNormativaSeries().iterator();

            while (itlnsd.hasNext()) {
                LimitacioNormativaSerie item = itlnsd.next();

                int id = listaLNS.indexOf(new LimitacioNormativaSerieObject(item));
                if (id < 0) {
                    itlnsd.remove();
                } else {
                    if (!listaLNS.get(id).getDualListCausas().getTarget().contains(new CausaLimitacioObject(item.getAchCausalimitacio()))) {
                        itlnsd.remove();
                    }
                }
            }
			
			/*
			 * toPersist.getAchLimitacioNormativaSeries().clear();
			for(LimitacioNormativaSerieObject lns : listaLNS) {
				for(CausaLimitacioObject cl: lns.getDualListCausas().getTarget()) {
					lns.setSeriedocumental(new SerieDocumentalObject(toPersist));
					LimitacioNormativaSerie lnsDB = lns.toDbObject();
					lnsDB.getId().setCausalimitacioId(cl.getId());
					
					LimitacioNormativaSerie current = limitacioNormativaSerieService.getReference(lnsDB.getId());
					if(current == null) {
					
					lnsDB.setInici(new Date());
					} else {
						lnsDB.setInici(current.getInici());
					}
					
					lnsDB.setAchNormativa(normativaService.getReference(lnsDB.getId().getNormativaId()));
					lnsDB.setAchCausalimitacio(causaLimitacioService.getReference(cl.getId()));
					lnsDB.setAchSeriedocumental(toPersist);
					toPersist.addAchLimitacioNormativaSery(lnsDB);
				}
				
			}
			 * 
			 * 
			 * */

            toPersist.getAchValoracios().clear();
            if (valoracio != null && valoracio.getAchValorsecundari() != null && valoracio.hashValorPrimariSelected()) {
                Valoracio v = (valoracio.getId() != null ? valoracioEJB.getReference(valoracio.getId()) : new Valoracio());

                v.setAchSeriedocumental(toPersist);
                if (v.getAchValorprimaris() != null) {
                    v.getAchValorprimaris().clear();
                } else {
                    v.setAchValorprimaris(new ArrayList<>());
                }

                if (v.getInici() == null) v.setInici(new Date());

                v.setAchValorsecundari(valorSecundariEJB.getReference(valoracio.getAchValorsecundari().getId()));
                for (ValorPrimariObject vp : valoracio.getAchValorprimaris()) {
                    if (vp.getSelected()) {
                        Valorprimari valorPrimari = vp.toDbObject();
                        valorPrimari.setAchTipusvalor(tipusValorEJB.getReference(vp.getAchTipusvalor().getId()));
                        v.addAchValorprimari(valorPrimari);
                    }
                }
                toPersist.addAchValoracio(v);
            }


            log.debug("Llista de tipus documentals seleccionats: " + listaTDS.size());
            log.debug("Abans de començar. Llista de tipus a la sèrie: " + toPersist.getAchTipusdocumentSeries().size());
            for (TipuDocumentalSerieObject tdpOb: listaTDS)
            {

                log.debug("tdpOb: " + tdpOb);
                TipusdocumentSerie tdpDB = tdpOb.toDbObject();
                tdpDB.setAchTipusdocumental(tipusDocumentaEJB.getReference(tdpOb.getTipusDocumental().getId()));
                log.debug("tdpDB: " + tdpDB);

                // Al ser un Set i no tener los checks (obligatori, multiple) no reflejaba los cambios de la pantalla en la BD
                // Por eso, si ya existe, lo quitamos y lo volvemos a poner
                if (toPersist.getAchTipusdocumentSeries().contains(tdpDB)) {
                    toPersist.removeAchTipusdocumentSerie(tdpDB);
                    toPersist.addAchTipusdocumentSerie(tdpDB);
                } else {
                    toPersist.addAchTipusdocumentSerie(tdpDB);
                }



                log.debug("tdpDB després assignació: " + tdpDB);
            }
            toPersist.getAchTipusdocumentSeries().removeIf(item -> !listaTDS.contains(new TipuDocumentalSerieObject(item)));

            log.debug("Tipus documentals a la sèrie queda amb: " + toPersist.getAchTipusdocumentSeries().size());

            Seriedocumental res = this.serieService.update(toPersist);
            return new SerieDocumentalObject(res);
        } catch (NullPointerException e) {
            log.error("Excepció actualitzant la sèrie documental: ", e);
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "updateSerieDocumental");
        } catch (Exception e) {
            log.error("Excepció actualitzant la sèrie documental: ", e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "updateSerieDocumental");
        }

    }

    @Transactional
    public void deleteSerie(Long idSerie) throws I18NException {
        try {
            this.serieService.delete(idSerie);
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "deleteSerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "deleteSerie");
        }
    }


    /**
     * Comprueba que el codigo de la serie sea unico
     *
     * @param codi
     * @param serieId
     * @return true si es un codigo nuevo, false si ya existe serie con dicho codigo
     */
    public boolean checkValidClassificationCode(String codi, Long serieId) throws I18NException {
        Map<String, Object> map = new HashMap<>();
        map.put("codi", codi);
        List<Seriedocumental> result = this.serieService.findFiltered(map, null);
        if (result == null
                || result.isEmpty()
                || (result.size() == 1 && serieId != null && result.get(0).getId().equals(serieId))) {
            return true;
        }
        return false;
    }

    @Transactional
    public List<TipuDocumentalSerieObject> getListaTDP(Long id) throws I18NException{

        try {
            List<TipuDocumentalSerieObject> lista = new ArrayList< >();
            List<TipusdocumentSerie> res = tipusdocumentalSerieEJB.getTipusDocument(id);

            for(TipusdocumentSerie  tdp : res)
            {
                TipuDocumentalSerieObject newTDP = new TipuDocumentalSerieObject(tdp);
                lista.add(newTDP);
            }
            return lista;
        } catch(NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaTDP");
        } catch(Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaTDP");
        }
    }

    @Transactional
    public List<TipuDocumentalObject> findAllTipoDocumental() throws I18NException{

        try {
            List<TipuDocumentalObject> 	lista 	= new ArrayList< >();
            List<TipusDocumental> 			res		= tipusDocumentaEJB.findAll();

            for(TipusDocumental i : res)
            {
                lista.add(new TipuDocumentalObject(i));
            }
            return lista;
        } catch(NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllTipoDocumental");
        } catch(Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllTipoDocumental");
        }
    }
}

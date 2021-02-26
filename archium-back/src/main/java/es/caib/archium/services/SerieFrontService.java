package es.caib.archium.services;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.iface.CSGDSerieService;
import es.caib.archium.csgd.apirest.constantes.*;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.csgd.apirest.facade.pojos.comun.SerieId;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverSerie;
import es.caib.archium.ejb.objects.Dir3;
import es.caib.archium.ejb.service.*;
import es.caib.archium.objects.*;
import es.caib.archium.persistence.model.*;
import es.caib.archium.utils.CalculoUtils;
import es.caib.archium.utils.CreateSerieXMLUtils;
import es.caib.archium.validators.CSGDValidator;
import org.apache.commons.lang3.StringUtils;
import org.primefaces.model.DualListModel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.EJBAccessException;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.IOException;
import java.math.BigDecimal;
import java.util.*;
import java.util.stream.Collectors;

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
    SerieService serieEJB;
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
    private CSGDSerieService csgdSerieService;
    @Inject
    private CreateSerieXMLUtils toXmlUtil;
    @Inject
    private CSGDValidator serieValidator;
    @Inject
    private CalculoUtils calculoUtils;

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

            for (Tipusvalor db : tipusValorEJB.findAll()) {
                res.add(new TipuValorObject(db));
            }

            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaTipusValor");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaTipusValor");
        }


    }

    public List<ValorSecundariObject> getListaValorsSecundari() throws I18NException {

        try {
            List<ValorSecundariObject> res = new ArrayList<ValorSecundariObject>();

            for (Valorsecundari db : valorSecundariEJB.findAll()) {
                res.add(new ValorSecundariObject(db));
            }

            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaValorsSecundari");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaValorsSecundari");
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

            for (Tipusserie it : tipusSerieEJB.findAll()) {
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

    public List<SerieDocumentalObject> getListaSeries() throws I18NException {

        try {
            List<SerieDocumentalObject> res = new ArrayList<>();
            List<Seriedocumental> list = serieService.findAll();

            for (Seriedocumental s : list) {
                SerieDocumentalObject toInsert = new SerieDocumentalObject();
                toInsert.setSerieId(s.getId());
                toInsert.setNom(s.getNom());
                toInsert.setCodi(s.getCodi());
                res.add(toInsert);
            }
            return res;
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaSeries");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaSeries");
        }

    }

    public List<SerieArgenObject> getListaSeriesArgen() throws I18NException {

        try {
            List<SerieArgenObject> res = new ArrayList<>();
            List<Serieargen> list = serieArgenService.findAll();

            for (Serieargen s : list) {
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

    public List<CausaLimitacioObject> getListaCausaLimitacio() throws I18NException {

        try {
            List<CausaLimitacioObject> res = new ArrayList<>();
            List<Causalimitacio> list = causaLimitacioService.findAll();

            for (Causalimitacio s : list) {
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
                                                  String descripcio, String descripcioCas, String resumMigracio, String dir3Promotor, String estat, Long tipusSerieId,
                                                  String codiIecisa, List<AplicacionObject> listaApps, List<SerieDocumentalObject> relateds, List<SerieArgenObject> argensList, List<LimitacioNormativaSerieObject> listaLNS
            , List<NormativaAprobacioObject> normativasList, ValoracioObject valoracio) throws I18NException {

        try {
            Seriedocumental toPersist = new Seriedocumental();

            toPersist.setCodi(codi);
            if (tipusSerieId != null) toPersist.setAchTipusserie(tipusSerieEJB.getReference(tipusSerieId));
            toPersist.setAchFuncio(funcioService.getReference(idFuncio));
            toPersist.setAchCatalegsery(catalegSerieService.getReference(catalegSeriId));
            toPersist.setDescripcio(descripcio);
            toPersist.setDescripciocas(descripcioCas);
            toPersist.setResummigracio(resumMigracio);
            toPersist.setDir3Promotor(dir3Promotor);
            toPersist.setCodiiecisa(codiIecisa);
            toPersist.setNom(nom);
            toPersist.setNomcas(nomCas);
            toPersist.setEstat(estat);
            toPersist.setSynchronized(false);

            toPersist.setAchAplicacioSeries(new ArrayList<AplicacioSerie>());

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


            toPersist.setAchSerierelacionadas1(new ArrayList<Serierelacionada>());
            toPersist.setAchSerierelacionadas2(new ArrayList<Serierelacionada>());
            for (SerieDocumentalObject b : relateds) {
                Seriedocumental serieRel = serieService.getReference(b.getSerieId());
                Serierelacionada rel = new Serierelacionada();
                rel.setAchSeriedocumental2(serieRel);
                toPersist.addAchSerierelacionadas1(rel);
            }

            for (SerieArgenObject argen : argensList) {
                Serieargen sargen = serieArgenService.getReference(argen.getId());
                Serierelacionada rel = new Serierelacionada();
                rel.setAchSerieargen(sargen);
                toPersist.addAchSerierelacionadas1(rel);
            }

            toPersist.setAchNormativaSeriedocumentals(new ArrayList<NormativaSeriedocumental>());
            for (NormativaAprobacioObject normativaSerie : normativasList) {
                NormativaSeriedocumentalPK pk = new NormativaSeriedocumentalPK();
                pk.setNormativaId(normativaSerie.getId());
                NormativaSeriedocumental normSerie = new NormativaSeriedocumental();
                normSerie.setId(pk);
                normSerie.setAchNormativa(normativaService.getReference(normativaSerie.getId()));
                normSerie.setInici(new Date());
                toPersist.addAchNormativaSeriedocumental(normSerie);
            }

            toPersist.setAchLimitacioNormativaSeries(new ArrayList<LimitacioNormativaSerie>());
            for (LimitacioNormativaSerieObject lns : listaLNS) {
                for (CausaLimitacioObject cl : lns.getDualListCausas().getTarget()) {
                    //lns.setSeriedocumental(new SerieDocumentalObject(res));
                    LimitacioNormativaSerie lnsDB = lns.toDbObject();
                    lnsDB.setAchNormativa(normativaService.getReference(lnsDB.getId().getNormativaId()));
                    lnsDB.setAchCausalimitacio(causaLimitacioService.getReference(cl.getId()));
                    lnsDB.setInici(new Date());
                    toPersist.addAchLimitacioNormativaSery(lnsDB);
                }
            }

            toPersist.setAchValoracios(new ArrayList<Valoracio>());
            if (valoracio != null && valoracio.getAchValorsecundari() != null && valoracio.getAchValorprimaris() != null) {
                Valoracio v = new Valoracio();
                v.setAchValorprimaris(new ArrayList<Valorprimari>());
                v.setAchValorsecundari(valorSecundariEJB.getReference(valoracio.getAchValorsecundari().getId()));
                v.setInici(new Date());
                for (ValorPrimariObject vp : valoracio.getAchValorprimaris()) {
                    if (vp.getSelected() == true) {
                        Valorprimari valorPrimari = vp.toDbObject();
                        valorPrimari.setAchTipusvalor(tipusValorEJB.getReference(vp.getAchTipusvalor().getId()));
                        v.addAchValorprimari(valorPrimari);
                    }
                }
                toPersist.addAchValoracio(v);
            }


            Seriedocumental res = serieService.create(toPersist);
            return new SerieDocumentalObject(res);
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "createNuevaSerie");
        } catch (Exception e) {
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "createNuevaSerie");
        }


    }

    @Transactional
    public SerieDocumentalObject updateSerieDocumental(Long idSerie, String codi, String nom, String nomCas, Long catalegSeriId, Long funcioId,
                                                       String descripcio, String descripcioCas, String resumMigracio, String dir3Promotor, String estat, Long tipusSerieId,
                                                       String codiIecisa, List<AplicacionObject> listaApps, List<SerieDocumentalObject> relateds, List<SerieArgenObject> argenRelateds, List<LimitacioNormativaSerieObject> listaLNS
            , List<NormativaAprobacioObject> normativasList, ValoracioObject valoracio, String nuevoNodeId) throws I18NException {

        try {
            Seriedocumental toPersist = serieService.getReference(idSerie);

            if (codi != null) toPersist.setCodi(codi);
            if (tipusSerieId != null) toPersist.setAchTipusserie(tipusSerieEJB.getReference(tipusSerieId));
            if (funcioId != null) toPersist.setAchFuncio(funcioService.getReference(funcioId));
            if (catalegSeriId != null) toPersist.setAchCatalegsery(catalegSerieService.getReference(catalegSeriId));
            if (descripcio != null) toPersist.setDescripcio(descripcio);
            if (descripcioCas != null) toPersist.setDescripciocas(descripcioCas);
            if (resumMigracio != null) toPersist.setResummigracio(resumMigracio);
            if (dir3Promotor != null) toPersist.setDir3Promotor(dir3Promotor);
            if (codiIecisa != null) toPersist.setCodiiecisa(codiIecisa);
            if (nom != null) toPersist.setNom(nom);
            if (nomCas != null) toPersist.setNomcas(nomCas);
            if (estat != null) toPersist.setEstat(estat);
            if (StringUtils.trimToNull(nuevoNodeId) != null) {
                toPersist.setNodeId(nuevoNodeId);
            }
            // Al modificar ponemos siempre el synchronized a false
            toPersist.setSynchronized(false);

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
                    //aplicacioSeri.setAchSeriedocumental(toPersist);
                    aplicacioSeri.setDescripcio("Relacion entre " + toPersist.getNom() + " y " + b.getNom());
                    aplicacioSeri.setInici(new Date());
                    toPersist.addAchAplicacioSery(aplicacioSeri);

                }

            }

            Iterator<AplicacioSerie> it = toPersist.getAchAplicacioSeries().iterator();

            while (it.hasNext()) {
                AplicacioSerie item = it.next();
                if (!listaApps.contains(new AplicacionObject(item.getAchAplicacio()))) {
                    it.remove();
                }
            }


            toPersist.getAchSerierelacionadas1().clear();
            for (SerieDocumentalObject b : relateds) {
                Seriedocumental serieRel = serieService.getReference(b.getSerieId());
                Serierelacionada rel = new Serierelacionada();
                rel.setAchSeriedocumental2(serieRel);
                rel.setAchSeriedocumental1(toPersist);
                toPersist.addAchSerierelacionadas1(rel);
            }

            for (SerieArgenObject argen : argenRelateds) {
                Serieargen sargen = serieArgenService.getReference(argen.getId());
                Serierelacionada rel = new Serierelacionada();
                rel.setAchSerieargen(sargen);
                rel.setAchSeriedocumental1(toPersist);
                toPersist.addAchSerierelacionadas1(rel);
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
            if (valoracio != null && valoracio.getAchValorsecundari() != null && valoracio.hashValorPrimariSelected() == true) {
                Valoracio v = (valoracio.getId() != null ? valoracioEJB.getReference(valoracio.getId()) : new Valoracio());

                v.setAchSeriedocumental(toPersist);
                if (v.getAchValorprimaris() != null) {
                    v.getAchValorprimaris().clear();
                } else {
                    v.setAchValorprimaris(new ArrayList<Valorprimari>());
                }

                if (v.getInici() == null) v.setInici(new Date());

                v.setAchValorsecundari(valorSecundariEJB.getReference(valoracio.getAchValorsecundari().getId()));
                for (ValorPrimariObject vp : valoracio.getAchValorprimaris()) {
                    if (vp.getSelected() == true) {
                        Valorprimari valorPrimari = vp.toDbObject();
                        valorPrimari.setAchTipusvalor(tipusValorEJB.getReference(vp.getAchTipusvalor().getId()));
                        v.addAchValorprimari(valorPrimari);
                    }
                }
                toPersist.addAchValoracio(v);
            }

            Seriedocumental res = this.serieService.update(toPersist);
            return new SerieDocumentalObject(res);
        } catch (NullPointerException e) {
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "updateSerieDocumental");
        } catch (Exception e) {
            e.printStackTrace();
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "updateSerieDocumental");
        }

    }

    @Transactional
    public void deleteSerie(Long idSerie) throws I18NException {
        log.debug("Se procede a eliminar la serie [" + idSerie + "]");
        Seriedocumental serie = serieService.getReference(idSerie);

        if (StringUtils.isNotEmpty(serie.getNodeId())) {
            log.debug("La serie existe en Alfresco, así que procedemos a eliminarla");
            try {
                this.csgdSerieService.deleteNode(new SerieId(serie.getNodeId()));
                log.info("La serie [" + idSerie + "] ha sido eliminada de Alfresco");
            } catch (CSGDException e) {
                throw new I18NException(getExceptionI18n(e.getClientErrorCode()), this.getClass().getSimpleName(), "deleteSerie");
            } catch (EJBAccessException e) {
                log.error("No se cuenta con los permisos adecuados para realiziar la llamada al csgd");
                throw new I18NException("csgd.permiso.denegado", this.getClass().getSimpleName(), "deleteSerie");
            } catch (Exception e) {
                throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "deleteSerie");
            }
        }

        // Si se elimina correctamente de gdib, se borra en la bbdd
        try {
            this.serieService.delete(idSerie);

        } catch (NullPointerException e) {
            log.error("Se ha producido un error de NullPointerException borrando la entidad: " + e);
            throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "deleteSerie");
        } catch (Exception e) {
            log.error("Se ha producido un error desconocido borrando la entidad: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "deleteSerie");
        }

    }

    private String getExceptionI18n(String clientErrorCode) {
        if (Constants.ExceptionConstants.CLIENT_ERROR.getValue().equals(clientErrorCode)) {
            return "csgd.client.error";
        } else if (Constants.ExceptionConstants.ERROR_RETURNED.getValue().equals(clientErrorCode)) {
            return "csgd.error.returned";
        } else if (Constants.ExceptionConstants.GENERIC_ERROR.getValue().equals(clientErrorCode)) {
            return "nuevaserie.sinc.error";
        } else if (Constants.ExceptionConstants.MALFORMED_RESULT.getValue().equals(clientErrorCode)) {
            return "csgd.malformed.result";
        }
        return "nuevaserie.sinc.error";
    }


    /**
     * Prepara el objeto para sincronizar con todos sus datos, se utilizará para preparar la modal de confirmación antes de
     *
     * @param serieId
     * @param serieWs
     * @return
     */
    @Transactional
    public Long prepareSync(Long s, Serie serieWs) throws I18NException {
        log.debug("Procedemos a preparar el objeto a sincronizar para la serie con id=[" + s + "]");
        log.debug("Obtenemos los datos de la serie...");

        Seriedocumental serie = null;
        try {
            //Obtenemos toda la informacion de la serie y dictamen activo relacionado para sincronizar en alfresco
            serie = serieEJB.getReference(s);
        } catch (Exception e) {
            log.error("Error obteniendo la entidad de la serie: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "get entity");
        }

        //Comprobamos que los padres esten sincronizados y no obsoletos
        this.serieValidator.checkSynchronizedParents(serie);

        // Validamos requisitos de negocio para poder enviar la serie a Alfresco
        this.serieValidator.validarSincronizarSerie(serie);

        //Rellenamos el dto
        serieWs.setNodeId(serie.getNodeId());
        serieWs.setCodigo(serie.getCodi());
        serieWs.setDenominacionClase(serie.getNomcas());
        serieWs.setCodigoLimitacion(this.calculoUtils.extraerCodigoLimitacion(serie));
        serieWs.setSeriesArgen(serie.getAchSerieargens() == null ? null : serie.getAchSerieargens().stream()
                .map(x -> x.getCodi()).collect(Collectors.toList()));
        serieWs.setUsuariosAplicacion(serie.getAchAplicacioSeries() == null ? null : serie.getAchAplicacioSeries().stream()
                .map(x -> x.getAchAplicacio().getNom()).collect(Collectors.toList()));
        this.calculoUtils.extraerValoresYPlazo(serie, serieWs);

        // Recuperamos el dictamen en estado vigente (solo puede tener uno cada serie) o el dictamen en estado esborrany
        // mas reciente
        Dictamen dictamen = this.calculoUtils.getDictamenActivo(serie);

        serieWs.setAccionDictaminada(this.calculoUtils.calcularAccionDictaminada(dictamen));
        String plazoAcccionDictaminada = this.calculoUtils.calcularPlazoAccionDictaminada(dictamen);
        serieWs.setPlazoAccionDictaminada(this.calculoUtils.extraerValorPlazo(plazoAcccionDictaminada));
        serieWs.setUnidadPlazoAccionDictaminada(this.calculoUtils.extraerUnidadPlazo(plazoAcccionDictaminada));

        serieWs.setLopd(LOPD.getLopd(dictamen.getAchLopd().getNomcas()));
        serieWs.setTipoAcceso(TipoAcceso.getTipoAcceso(dictamen.getAchTipusacce().getNomcas()));
        serieWs.setConfidencialidad(Confidencialidad.getConfidencialidad(dictamen.getAchEn().getNomcas()));
        // Para el caso de las series documentales, siempre será un valor por defecto
        serieWs.setTipoClasificacion(Constants.ArchiumConstants.TIPO_CLASIFICACION_VALOR_POR_DEFECTO.getValue());
        serieWs.setCondicionReutilizacion(dictamen.getCondicioreutilitzacio());
        serieWs.setTipoDictamen(TipoDictamen.getTipoDictamen(dictamen.getAchTipusdictamen().getCodi()));
        serieWs.setEsencial(BigDecimal.ONE.equals(dictamen.getSerieessencial()));


        // Se crea xml que  contiene todos los metadatos descriptivos de la serie documental
        // Se guarda transformado en base64
        try {
            serieWs.setBinaryContent(toXmlUtil.createXMLDocument(serie, dictamen));

        } catch (ParserConfigurationException | TransformerException e) {
            log.error("Se ha producido un error generando el documento con los datos de la serie: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "synchronize");
        } catch (I18NException e) {
            throw e;
        }
        // Una vez creado el objeto a sincronizar, devolvemos el codigo de la funcion padre
        return serie.getAchFuncio().getId();
    }


    /**
     * roceso de sincronizacion de la serie en GDIB
     *
     * @param serieWs
     * @param serieId
     * @return
     * @throws I18NException
     */
    @Transactional
    public SerieDocumentalObject synchronize(Serie serieWs, Long serieId) throws I18NException {
        log.debug("Procedemos a sincronizar la serie con id=[" + serieId + "]");
        log.debug("Obtenemos los datos de la serie...");

        Seriedocumental serie = null;
        try {
            //Obtenemos toda la informacion de la serie y dictamen activo relacionado para sincronizar en alfresco
            serie = serieEJB.getReference(serieId);
        } catch (Exception e) {
            log.error("Error obteniendo la entidad de la serie: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "get entity");
        }

        try {
            // Se crea xml que  contiene todos los metadatos descriptivos de la serie documental
            // Se guarda transformado en base64
            serieWs.setContent(toXmlUtil.toBase64Content(serieWs.getBinaryContent()));
            // Seteamos los metadatos del contenido, que serán siempre los mismos
            serieWs.setMimeType(toXmlUtil.MIME_TYPE);
            serieWs.setEncoding(toXmlUtil.ENCODING);
            serieWs.setBinaryContent(null);
        } catch (IOException e) {
            log.error("Se ha producido un error generando el documento con los datos de la serie: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "synchronize");
        }

        log.debug("Dto creado, llamamos al servicio de sincronizacion");
        String nodeId = null;
        try {
            // Enviamos la informacion para sincronizar
            nodeId = this.csgdSerieService.synchronizeNode(serieWs, serie.getAchFuncio().getNodeId());
        } catch (CSGDException e) {
            throw new I18NException(getExceptionI18n(e.getClientErrorCode()), this.getClass().getSimpleName(), "synchronizeSerie");
        } catch (EJBAccessException e) {
            log.error("No se cuenta con los permisos adecuados para realiziar la llamada al csgd");
            throw new I18NException("csgd.permiso.denegado", this.getClass().getSimpleName(), "synchronizeFunction");
        } catch (Exception e) {
            log.error("Error sincronizando la serie: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "synchronizeSerie");
        }

        log.info("Creada serie con id [" + nodeId + "]. Se procede a guardar en base de datos");

        // Una vez creada la serie en el arbol de alfresco, guardamos la referencia con el nodo creado
        if (StringUtils.isNotEmpty(nodeId)) {
            try {
                if (StringUtils.trimToNull(serie.getNodeId()) == null) {
                    serie.setNodeId(nodeId);
                }
                serie.setSynchronized(true);
                serieEJB.update(serie);
                return new SerieDocumentalObject(serie);
            } catch (I18NException e) {
                log.error("Error sincronizando la informacion de la serie: " + e);
                throw e;
            } catch (Exception e) {
                log.error("Error sincronizando la informacion de la serie: " + e);
                throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "update");
            }
        } else {
            log.error("Se ha devuelto null como nodo de alfresco en la sincronizacion de la serie");
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "synchronizeError");
        }
    }


    /**
     * Comprueba si el dictamen que se pasa por parametro es el dictamen activo de su serie, en caso de serlo, marca el
     * booleano a true, el cual es encesario para moestrar el icono apropiado en la vista.
     * Si hay un nuevo dictamen activo, y la serie esta sincronizada, la actualizamos para marcarla a sincronizar
     *
     * @param newD
     */
    @Transactional
    public boolean checkIfNewDictamenActivo(DictamenObject dictamen) throws I18NException {
        Seriedocumental seriedb = this.serieEJB.getReference(dictamen.getSerieDocumental().getSerieId());
        // Comprobamos que el estado del dictamen sea un estado valido para la sincronizacion con gdib
        if (Estado.isEstadoSincronizable(dictamen.getEstat())) {
            Dictamen activoNuevo = this.calculoUtils.getDictamenActivo(seriedb);
            // Si el nuevo dictamen activo de la serie es el que acabamos de crear...
            if (activoNuevo != null && activoNuevo.getId().equals(dictamen.getId())) {
                // Marcamos el dictamen como el dictamen activo de la serie
                dictamen.setDictamenActivo(true);

                if (seriedb.isSynchronized()) {
                    seriedb.setSynchronized(false);
                    seriedb = serieEJB.update(seriedb);
                    dictamen.setSerieDocumental(new SerieDocumentalObject(seriedb));
                }
                return true;
            }
        }
        return false;
    }

    /**
     * Devuelve el dictamen activo de la serie indicada
     *
     * @param serieId
     * @return
     */
    @Transactional
    public DictamenObject getDictamenActivo(Long serieId) {
        Seriedocumental seriedb = this.serieEJB.getReference(serieId);
        return new DictamenObject(this.calculoUtils.getDictamenActivo(seriedb));
    }

    /**
     * Mueve una serie cambiándola de padre, lo que hara que su arbol completo de hijos se mueva con ella (dictamenes)
     *
     * @param elementId
     * @param rootId
     * @param parentFunctionId
     * @return
     */
    @Transactional
    public SerieDocumentalObject moveSerie(long serieId, Long rootId, Long parentFunctionId) throws I18NException {
        log.info("Se comienza servicio de mover serie");
        Seriedocumental seriedb = this.serieEJB.getReference(serieId);
        Funcio parentFunction = null;

        // Buscamos el padre nuevo del nodo
        if (parentFunctionId == null) {
            log.error("No se ha elegido padre para la serie");
            throw new I18NException("csgd.permiso.denegado", this.getClass().getSimpleName(), "moveSerie");
        } else {
            // Si el padre sigue siendo el mismo no hacemos nada
            if (seriedb.getAchFuncio().getId().equals(parentFunctionId)) {
                return new SerieDocumentalObject(seriedb);
            }

            parentFunction = this.funcioService.getReference(parentFunctionId);
            log.debug("Obtenida funcion padre [" + parentFunction.getCodi() + "] de la serie");
        }

        seriedb.setAchFuncio(parentFunction);

        // Si la serie ya tiene asignado un nodeId en alfresco, la actualizamos
        if (StringUtils.trimToNull(seriedb.getNodeId()) != null) {
            log.debug("Se procede a sincronizar con Alfresco el movimiento de la funcion");
            // Preparamos el dto para enviar a GDIB
            MoverSerie moveSerie = new MoverSerie();
            moveSerie.setParentId(seriedb.getAchFuncio().getNodeId());
            moveSerie.setNodoId(seriedb.getNodeId());

            // Enviamos la información a Gdib
            try {
                this.csgdSerieService.moveNode(moveSerie);
            } catch (CSGDException e) {
                throw new I18NException(getExceptionI18n(e.getClientErrorCode()), this.getClass().getSimpleName(), "moveSerie");
            } catch (EJBAccessException e) {
                log.error("No se cuenta con los permisos adecuados para realiziar la llamada al csgd");
                throw new I18NException("csgd.permiso.denegado", this.getClass().getSimpleName(), "moveSerie");
            } catch (Exception e) {
                log.error("Error moviendo la serie: " + e);
                throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "moveSerie");
            }
            log.debug("Completado proceso de mover en el CSGD, se procede a guardar en base de datos");
        }

        // Modificamos la serie
        try {
            seriedb = this.serieEJB.update(seriedb);
            log.debug("Serie modificada");
        } catch (I18NException e) {
            log.error("Error moviendo la serie: " + e);
            throw e;
        } catch (Exception e) {
            log.error("Error moviendo la serie: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "update");
        }

        return new SerieDocumentalObject(seriedb);
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

    /**
     * Hace llamada al getSerie del CSGD, se pasa el id del nodo de Alfresco
     *
     * @param serieId
     * @return
     */
    public Serie getSerie(String serieId) throws I18NException {
        try {
            return this.csgdSerieService.getNode(new SerieId(serieId));
        } catch (CSGDException e) {
            throw new I18NException(getExceptionI18n(e.getClientErrorCode()), this.getClass().getSimpleName(), "getSerie");
        } catch (EJBAccessException e) {
            log.error("No se cuenta con los permisos adecuados para realiziar la llamada al csgd");
            throw new I18NException("csgd.permiso.denegado", this.getClass().getSimpleName(), "getSerie");
        } catch (Exception e) {
            log.error("Error obteniendo la serie: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getSerie");
        }
    }

    /**
     * Llama al metodo del cliente para cancelar permisos de un nodo
     *
     * @param nodeId
     * @param usersCancelPermissions
     */
    public void cancelPermissionsOnSerie(String nodeId, List<String> usersCancelPermissions) throws I18NException {

        try {
            this.csgdSerieService.cancelPermissions(Arrays.asList(new SerieId(nodeId)),usersCancelPermissions);
        } catch (CSGDException e) {
            throw new I18NException(getExceptionI18n(e.getClientErrorCode()), this.getClass().getSimpleName(), "cancelPermissionsOnSerie");
        } catch (EJBAccessException e) {
            log.error("No se cuenta con los permisos adecuados para realiziar la llamada al csgd");
            throw new I18NException("csgd.permiso.denegado", this.getClass().getSimpleName(), "cancelPermissionsOnSerie");
        } catch (Exception e) {
            log.error("Error cancelando permisos a usuarios para la serie: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "cancelPermissionsOnSerie");
        }
    }

    /**
     * Llama al metodo del cliente para establecer permisos sobre un nodo
     *
     * @param nodeId
     * @param usersGrantPermissions
     */
    public void grantPermissionsOnSerie(String nodeId, List<String> usersGrantPermissions) throws I18NException {
        try {
            this.csgdSerieService.grantPermissions(Arrays.asList(new SerieId(nodeId)),usersGrantPermissions);
        } catch (CSGDException e) {
            throw new I18NException(getExceptionI18n(e.getClientErrorCode()), this.getClass().getSimpleName(), "grantPermissionsOnSerie");
        } catch (EJBAccessException e) {
            log.error("No se cuenta con los permisos adecuados para realiziar la llamada al csgd");
            throw new I18NException("csgd.permiso.denegado", this.getClass().getSimpleName(), "grantPermissionsOnSerie");
        } catch (Exception e) {
            log.error("Error estableciendo permisos a usuarios para la serie: " + e);
            throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "grantPermissionsOnSerie");
        }
    }
}

package es.caib.archium.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityNotFoundException;
import javax.transaction.Transactional;

import org.primefaces.model.DualListModel;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.AplicacioSerieEJB;
import es.caib.archium.ejb.AplicacioEJB;
import es.caib.archium.ejb.CatalegSerieEJB;
import es.caib.archium.ejb.CausaLimitacioEJB;
import es.caib.archium.ejb.FuncioEJB;
import es.caib.archium.ejb.LimitacioNormativaSerieEJB;
import es.caib.archium.ejb.NormativaEJB;
import es.caib.archium.ejb.NormativaSerieEJB;
import es.caib.archium.ejb.SerieArgenEJB;
import es.caib.archium.ejb.SerieDocumentalEJB;
import es.caib.archium.ejb.SerieRelacionadaEJB;
import es.caib.archium.ejb.TipusSerieEJB;
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
import es.caib.archium.ejb.service.SerieArgenService;
import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.ejb.service.SerieRelacionadaService;
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
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.objects.SerieArgenObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.objects.TipuDocumentalProcedimentObject;
import es.caib.archium.objects.TipuValorObject;
import es.caib.archium.objects.TipusSerieObject;
import es.caib.archium.objects.ValorPrimariObject;
import es.caib.archium.objects.ValorSecundariObject;
import es.caib.archium.objects.ValoracioObject;
import es.caib.archium.persistence.model.Aplicacio;
import es.caib.archium.persistence.model.AplicacioSerie;
import es.caib.archium.persistence.model.AplicacioSeriePK;
import es.caib.archium.persistence.model.Catalegsery;
import es.caib.archium.persistence.model.Causalimitacio;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.LimitacioNormativaSerie;
import es.caib.archium.persistence.model.LimitacioNormativaSeriePK;
import es.caib.archium.persistence.model.Normativa;
import es.caib.archium.persistence.model.NormativaSeriedocumental;
import es.caib.archium.persistence.model.NormativaSeriedocumentalPK;
import es.caib.archium.persistence.model.Quadreclassificacio;
import es.caib.archium.persistence.model.Serieargen;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.Serierelacionada;
import es.caib.archium.persistence.model.Tipusserie;
import es.caib.archium.persistence.model.Tipusvalor;
import es.caib.archium.persistence.model.Valoracio;
import es.caib.archium.persistence.model.Valorprimari;
import es.caib.archium.persistence.model.Valorsecundari;

@Named
@ApplicationScoped
public class SerieFrontService {

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
	
	public List<Dir3Object> getListaDir3() throws I18NException{
		
		try {
			List<Dir3Object> lista = new ArrayList<Dir3Object>();
			
			for(Dir3 dir3:dir3EJB.getAll()) {
				lista.add(new Dir3Object(dir3.getCodi(), dir3.getNom()));
			}
			
			return lista;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaDir3");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaDir3");
		}
	}
	
	@Transactional
	public ValoracioObject getValoracioSerie(Long serieId) throws I18NException{
		try {
			return new ValoracioObject(valoracioEJB.getBySerie(serieId));
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getValoracioSerie");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getValoracioSerie");
		}
	}
	
	public List<TipuValorObject> getListaTipusValor() throws I18NException {
		
		try {
			List<TipuValorObject> res = new ArrayList<TipuValorObject>();
			
			for(Tipusvalor db: tipusValorEJB.findAll()) {
				res.add(new TipuValorObject(db));
			}
			
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaTipusValor");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaTipusValor");
		}
		
		
	}
	
	public List<ValorSecundariObject> getListaValorsSecundari() throws I18NException {
		
		try {
			List<ValorSecundariObject> res = new ArrayList<ValorSecundariObject>();
			
			for(Valorsecundari db: valorSecundariEJB.findAll()) {
				res.add(new ValorSecundariObject(db));
			}
			
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaValorsSecundari");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaValorsSecundari");
		}
		
		
	}
	
	public List<FuncioObject> getListaFunciones() throws I18NException{
		
		try {
			List<FuncioObject> res = new ArrayList<>();
			
			for(Funcio it : funcioService.findAll()) {
				FuncioObject toInsert = new FuncioObject();
				toInsert.setId(it.getId());
				toInsert.setCodi(it.getCodi());
				toInsert.setNom(it.getNom());
				toInsert.setNomcas(it.getNomcas());
				res.add(toInsert);
			}
			
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaFunciones");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaFunciones");
		}
		
		
	}
	public List<CatalegSeriesObject> getListaCatalegSerie() throws I18NException{
		
		try {
			List<CatalegSeriesObject> res = new ArrayList<>();
			
			for(Catalegsery it : catalegSerieService.findAll()) {
				CatalegSeriesObject toInsert = new CatalegSeriesObject();
				toInsert.setId(it.getId());
				toInsert.setNom(it.getNom());
				toInsert.setNomCas(it.getNomcas());
				res.add(toInsert);
			}
			
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaCatalegSerie");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaCatalegSerie");
		}
	}
	
	public List<TipusSerieObject> getListaTipusSerie() throws I18NException{
		
		try {
			List<TipusSerieObject> res = new ArrayList<>();
			
			for(Tipusserie it : tipusSerieEJB.findAll()) {
				TipusSerieObject toInsert = new TipusSerieObject();
				toInsert.setId(it.getId());
				toInsert.setNom(it.getNom());
				toInsert.setNomCas(it.getNomcas());
				res.add(toInsert);
			}
			
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaTipusSerie");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaTipusSerie");
		}
		
	}

	public List<SerieDocumentalObject> getListaSeries() throws I18NException{
		
		try {
			List<SerieDocumentalObject> res = new ArrayList<>();
			List<Seriedocumental> list = serieService.findAll();
			
			for(Seriedocumental s : list) {
				SerieDocumentalObject toInsert = new SerieDocumentalObject();
				toInsert.setSerieId(s.getId());
				toInsert.setNom(s.getNom());
				toInsert.setCodi(s.getCodi());
				res.add(toInsert);
			}
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaSeries");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaSeries");
		}
		
	}

	public List<SerieArgenObject> getListaSeriesArgen() throws I18NException{
		
		try {
			List<SerieArgenObject> res = new ArrayList<>();
			List<Serieargen> list = serieArgenService.findAll();
			
			for(Serieargen s : list) {
				SerieArgenObject toInsert = new SerieArgenObject();
				toInsert.setId(s.getId());
				toInsert.setNom(s.getNom());
				toInsert.setCodi(s.getCodi());
				res.add(toInsert);
			}
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaSeriesArgen");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaSeriesArgen");
		}

	}
	
	public List<NormativaAprobacioObject> getListaNormativas() throws I18NException{
		
		try {
			List<NormativaAprobacioObject> res = new ArrayList<>();
			List<Normativa> list = normativaService.findAll();
			
			for(Normativa s : list) {
				NormativaAprobacioObject toInsert = new NormativaAprobacioObject();
				toInsert.setId(s.getId());
				toInsert.setNom(s.getNom());
				res.add(toInsert);
			}
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaNormativas");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaNormativas");
		}
		
		
	}
	
	public List<CausaLimitacioObject> getListaCausaLimitacio() throws I18NException{
		
		try {
			List<CausaLimitacioObject> res = new ArrayList<>();
			List<Causalimitacio> list = causaLimitacioService.findAll();
			
			for(Causalimitacio s : list) {
				CausaLimitacioObject toInsert = new CausaLimitacioObject();
				toInsert.setId(s.getId());
				toInsert.setNom(s.getNom());
				res.add(toInsert);
			}
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaCausaLimitacio");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaCausaLimitacio");
		}
		
		
	}
	
	public List<AplicacionObject> getListaAplicaciones() throws I18NException{
		
		try {
			List<AplicacionObject> res = new ArrayList<>();
			List<Aplicacio> list = aplicacioService.findAll();
			
			for(Aplicacio s : list) {
				AplicacionObject toInsert = new AplicacionObject();
				toInsert.setId(s.getId());
				toInsert.setNom(s.getNom());
				res.add(toInsert);
			}
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaAplicaciones");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaAplicaciones");
		}
		
		
	}
	public List<SerieDocumentalObject> getListaSeriesBySerie(Long serieId) throws I18NException{
		
		try {
			List<SerieDocumentalObject> res = new ArrayList<>();
			List<Serierelacionada> list = serieRelacionadaService.findSeriesBySerie(serieId);
			
			for(Serierelacionada s : list) {
				if(s.getAchSeriedocumental2() == null )
					continue;
				SerieDocumentalObject toInsert = new SerieDocumentalObject();
				toInsert.setSerieId(s.getAchSeriedocumental2().getId());
				toInsert.setNom(s.getAchSeriedocumental2().getNom());
				toInsert.setCodi(s.getAchSeriedocumental2().getCodi());
				res.add(toInsert);
			}
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaSeriesBySerie");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaSeriesBySerie");
		}
		
		
	}
	public List<SerieArgenObject> getListaSeriesArgenBySerie(Long serieId) throws I18NException{
		
		try {
			List<SerieArgenObject> res = new ArrayList<>();
			List<Serierelacionada> list = serieRelacionadaService.findArgenBySerie(serieId);
			
			for(Serierelacionada s : list) {
				SerieArgenObject toInsert = new SerieArgenObject();
				toInsert.setId(s.getAchSerieargen().getId());
				toInsert.setNom(s.getAchSerieargen().getNom());
				toInsert.setCodi(s.getAchSerieargen().getCodi());
				res.add(toInsert);
			}
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaSeriesArgenBySerie");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaSeriesArgenBySerie");
		}
		
		
	}
	public List<NormativaAprobacioObject> getListaNormativasBySerie(Long serieId) throws I18NException{
		
		try {
			List<NormativaAprobacioObject> res = new ArrayList<>();
			List<NormativaSeriedocumental> list = normativaSerieEJB.getBySerie(serieId);
			for(NormativaSeriedocumental s : list)
			{
				NormativaAprobacioObject toinsert = new NormativaAprobacioObject();
				toinsert.setId(s.getAchNormativa().getId());
				toinsert.setCodi(s.getAchNormativa().getCodi());
				toinsert.setNom(s.getAchNormativa().getNom());
				res.add(toinsert);
			}
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaNormativasBySerie");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaNormativasBySerie");
		}
		
	}
	
	public List<AplicacionObject> getListaAplicacionesBySerie(Long serieId) throws I18NException{
	
		
		try {
			List<AplicacionObject> res = new ArrayList<>();
			List<AplicacioSerie> list = aplicacioSerieService.getBySerie(serieId);

			for(AplicacioSerie s : list) {
				AplicacionObject toInsert = new AplicacionObject();
				toInsert.setId(s.getAchAplicacio().getId());
				toInsert.setNom(s.getAchAplicacio().getNom());
				res.add(toInsert);
			}
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaAplicacionesBySerie");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaAplicacionesBySerie");
		}
		
		
	}
	
	public List<LimitacioNormativaSerieObject> getListaLNS(Long serieId) throws I18NException{
		
		try {
			List<LimitacioNormativaSerieObject> res = new ArrayList<>();
			List<LimitacioNormativaSerie> list = limitacioNormativaSerieService.getBySerie(serieId);

			for(LimitacioNormativaSerie lns : list) {
				int index = res.indexOf(new LimitacioNormativaSerieObject(lns));
				if(index>=0) {
					res.get(index).addCausaLimitacio(new CausaLimitacioObject(lns.getAchCausalimitacio()));
				} else {
					LimitacioNormativaSerieObject lnsOb = new LimitacioNormativaSerieObject(lns);
					lnsOb.addCausaLimitacio(new CausaLimitacioObject(lns.getAchCausalimitacio()));
					lnsOb.setDualListCausas(new DualListModel<CausaLimitacioObject>(new ArrayList<>(), new ArrayList<>()));
					res.add(lnsOb);
				}
			}
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getListaLNS");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getListaLNS");
		}
		
		
	}
	
	@Transactional
	public SerieDocumentalObject createNuevaSerie(String codi, String nom, String nomCas, Long catalegSeriId, Long idFuncio,
			String descripcio, String descripcioCas, String resumMigracio, String dir3Promotor, String estat, Long tipusSerieId,
			String codiIecisa,List<AplicacionObject> listaApps,List<SerieDocumentalObject> relateds,List<SerieArgenObject> argensList, List<LimitacioNormativaSerieObject> listaLNS
			,List<NormativaAprobacioObject> normativasList, ValoracioObject valoracio) throws I18NException {
		
		try {
			Seriedocumental toPersist = new Seriedocumental();
			
			toPersist.setCodi(codi);
			if(tipusSerieId!=null)toPersist.setAchTipusserie(tipusSerieEJB.getReference(tipusSerieId));
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
			
			toPersist.setAchAplicacioSeries(new ArrayList<AplicacioSerie>());
			
			for(AplicacionObject b : listaApps)
			{
				AplicacioSerie aplicacioSeri = new AplicacioSerie();
				AplicacioSeriePK pk = new AplicacioSeriePK();
				pk.setAplicacioId(b.getId());
				aplicacioSeri.setId(pk);
				aplicacioSeri.setAchAplicacio(aplicacioService.getReference(b.getId()));
				aplicacioSeri.setDescripcio("Relacion entre "+toPersist.getNom()+ " y "+b.getNom());
				aplicacioSeri.setInici(new Date());
				toPersist.addAchAplicacioSery(aplicacioSeri);
			}
			
			
			toPersist.setAchSerierelacionadas1(new ArrayList<Serierelacionada>());
			toPersist.setAchSerierelacionadas2(new ArrayList<Serierelacionada>());
			for(SerieDocumentalObject b : relateds)
			{
				Seriedocumental serieRel = serieService.getReference(b.getSerieId());
				Serierelacionada rel = new Serierelacionada();
				rel.setAchSeriedocumental2(serieRel);
				toPersist.addAchSerierelacionadas1(rel);
			}	
			
			for(SerieArgenObject argen : argensList)
			{
				Serieargen sargen = serieArgenService.getReference(argen.getId());
				Serierelacionada rel = new Serierelacionada();
				rel.setAchSerieargen(sargen);
				toPersist.addAchSerierelacionadas1(rel);
			}
			
			toPersist.setAchNormativaSeriedocumentals(new ArrayList<NormativaSeriedocumental>());
			for(NormativaAprobacioObject normativaSerie: normativasList)
			{
				NormativaSeriedocumentalPK pk = new NormativaSeriedocumentalPK();
				pk.setNormativaId(normativaSerie.getId());
				NormativaSeriedocumental normSerie = new NormativaSeriedocumental();
				normSerie.setId(pk);
				normSerie.setAchNormativa(normativaService.getReference(normativaSerie.getId()));
				normSerie.setInici(new Date());
				toPersist.addAchNormativaSeriedocumental(normSerie);
			}
			
			toPersist.setAchLimitacioNormativaSeries(new ArrayList<LimitacioNormativaSerie>());
			for(LimitacioNormativaSerieObject lns : listaLNS) {
				for(CausaLimitacioObject cl: lns.getDualListCausas().getTarget()) {
					//lns.setSeriedocumental(new SerieDocumentalObject(res));
					LimitacioNormativaSerie lnsDB = lns.toDbObject();
					lnsDB.setAchNormativa(normativaService.getReference(lnsDB.getId().getNormativaId()));
					lnsDB.setAchCausalimitacio(causaLimitacioService.getReference(cl.getId()));
					lnsDB.setInici(new Date());
					toPersist.addAchLimitacioNormativaSery(lnsDB);
				}
			}			
			
			toPersist.setAchValoracios(new ArrayList<Valoracio>());
			if(valoracio!=null && valoracio.getAchValorsecundari()!=null && valoracio.getAchValorprimaris()!=null) {
				Valoracio v = new Valoracio();
				v.setAchValorprimaris(new ArrayList<Valorprimari>());
				v.setAchValorsecundari(valorSecundariEJB.getReference(valoracio.getAchValorsecundari().getId()));
				v.setInici(new Date());					
				for(ValorPrimariObject vp: valoracio.getAchValorprimaris()) {
					if(vp.getSelected()==true) {
						Valorprimari valorPrimari = vp.toDbObject();
						valorPrimari.setAchTipusvalor(tipusValorEJB.getReference(vp.getAchTipusvalor().getId()));
						v.addAchValorprimari(valorPrimari);
					}
				}
				toPersist.addAchValoracio(v);			
			}
			
			
			Seriedocumental res = serieService.create(toPersist);
			return new SerieDocumentalObject(res);
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "createNuevaSerie");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "createNuevaSerie");
		}
		
		
	}

	@Transactional
	public SerieDocumentalObject updateSerieDocumental(Long idSerie,String codi, String nom, String nomCas, Long catalegSeriId, Long funcioId,
			String descripcio, String descripcioCas, String resumMigracio, String dir3Promotor, String estat, Long tipusSerieId,
			String codiIecisa,List<AplicacionObject> listaApps,List<SerieDocumentalObject> relateds,List<SerieArgenObject> argenRelateds ,List<LimitacioNormativaSerieObject> listaLNS
			,List<NormativaAprobacioObject> normativasList, ValoracioObject valoracio) throws I18NException {
		
		try {
			Seriedocumental toPersist = serieService.getReference(idSerie);
						
			if(codi!= null)toPersist.setCodi(codi);
			if(tipusSerieId!=null)toPersist.setAchTipusserie(tipusSerieEJB.getReference(tipusSerieId));
			if(funcioId!= null)toPersist.setAchFuncio(funcioService.getReference(funcioId));
			if(catalegSeriId!= null)toPersist.setAchCatalegsery(catalegSerieService.getReference(catalegSeriId));
			if(descripcio!= null)toPersist.setDescripcio(descripcio);
			if(descripcioCas!= null)toPersist.setDescripciocas(descripcioCas);
			if(resumMigracio!= null)toPersist.setResummigracio(resumMigracio);
			if(dir3Promotor!= null)toPersist.setDir3Promotor(dir3Promotor);
			if(codiIecisa!= null)toPersist.setCodiiecisa(codiIecisa);
			if(nom!= null)toPersist.setNom(nom);
			if(nomCas!= null)toPersist.setNomcas(nomCas);
			if(estat!= null)toPersist.setEstat(estat);
								
			for(AplicacionObject b : listaApps) {
				
				AplicacioSerie existe = null;
				Iterator<AplicacioSerie> it = toPersist.getAchAplicacioSeries().iterator();
				
				while(it.hasNext() && existe==null) {
					AplicacioSerie as = it.next();
					if(as.getAchAplicacio().getId().equals(b.getId())) {
						existe = as;
					}
				} 
				
				if(existe==null) {
					AplicacioSerie aplicacioSeri = new AplicacioSerie();
					AplicacioSeriePK pk = new AplicacioSeriePK();
					pk.setAplicacioId(b.getId());
					pk.setSeriedocumentalId(toPersist.getId());
					aplicacioSeri.setId(pk);
					aplicacioSeri.setAchAplicacio(aplicacioService.getReference(b.getId()));
					//aplicacioSeri.setAchSeriedocumental(toPersist);
					aplicacioSeri.setDescripcio("Relacion entre "+toPersist.getNom()+ " y "+b.getNom());
					aplicacioSeri.setInici(new Date());
					toPersist.addAchAplicacioSery(aplicacioSeri);
					
				}
				
			}
			
			Iterator<AplicacioSerie> it = toPersist.getAchAplicacioSeries().iterator();
			
			while(it.hasNext()) {
				AplicacioSerie item = it.next();
				if(!listaApps.contains(new AplicacionObject(item.getAchAplicacio()))) {
					it.remove();
				}
			}
			
			
			
			toPersist.getAchSerierelacionadas1().clear();
			for(SerieDocumentalObject b : relateds)
			{
				Seriedocumental serieRel = serieService.getReference(b.getSerieId());
				Serierelacionada rel = new Serierelacionada();
				rel.setAchSeriedocumental2(serieRel);
				rel.setAchSeriedocumental1(toPersist);
				toPersist.addAchSerierelacionadas1(rel);
			}	
			
			for(SerieArgenObject argen : argenRelateds)
			{
				Serieargen sargen = serieArgenService.getReference(argen.getId());
				Serierelacionada rel = new Serierelacionada();
				rel.setAchSerieargen(sargen);
				rel.setAchSeriedocumental1(toPersist);
				toPersist.addAchSerierelacionadas1(rel);
			}
			
			
			for(NormativaAprobacioObject na : normativasList) {
				
				NormativaSeriedocumental existe = null;
				Iterator<NormativaSeriedocumental> itna = toPersist.getAchNormativaSeriedocumentals().iterator();
				
				while(itna.hasNext() && existe==null) {
					NormativaSeriedocumental ns = itna.next();
					if(ns.getAchNormativa().getId().equals(na.getId())) {
						existe = ns;
					}
				} 
				
				if(existe==null) {
					
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
			
			while(itnsd.hasNext()) {
				NormativaSeriedocumental item = itnsd.next();
				if(!normativasList.contains(new NormativaAprobacioObject(item.getAchNormativa()))) {
					itnsd.remove();
				}
			}
			
			
			for(LimitacioNormativaSerieObject lns : listaLNS) {
				for(CausaLimitacioObject cl: lns.getDualListCausas().getTarget()) {
					
					LimitacioNormativaSerie existe = null;
					Iterator<LimitacioNormativaSerie> itnas = toPersist.getAchLimitacioNormativaSeries().iterator();
					
					while(itnas.hasNext() && existe==null) {
						LimitacioNormativaSerie lnsitem = itnas.next();
						if(lnsitem.getAchNormativa().getId().equals(lns.getNormativa().getId())
								&& lnsitem.getAchCausalimitacio().getId().equals(cl.getId())) {
							existe = lnsitem;
						}
					} 
					
					if(existe==null) {
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
			
			while(itlnsd.hasNext()) {
				LimitacioNormativaSerie item = itlnsd.next();
				
				int id = listaLNS.indexOf(new LimitacioNormativaSerieObject(item));
				if(id<0) {
					itlnsd.remove();
				} else {
					if(!listaLNS.get(id).getDualListCausas().getTarget().contains(new CausaLimitacioObject(item.getAchCausalimitacio()))) {
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
			if(valoracio!=null && valoracio.getAchValorsecundari()!=null && valoracio.hashValorPrimariSelected()==true) {
				Valoracio v = (valoracio.getId()!=null ? valoracioEJB.getReference(valoracio.getId()) : new Valoracio());

				v.setAchSeriedocumental(toPersist);
				if(v.getAchValorprimaris()!=null) {
					v.getAchValorprimaris().clear();
				} else {
					v.setAchValorprimaris(new ArrayList<Valorprimari>());
				}
				
				if(v.getInici()==null) v.setInici(new Date());
				
				v.setAchValorsecundari(valorSecundariEJB.getReference(valoracio.getAchValorsecundari().getId()));
				for(ValorPrimariObject vp: valoracio.getAchValorprimaris()) {
					if(vp.getSelected()==true) {
						Valorprimari valorPrimari = vp.toDbObject();
						valorPrimari.setAchTipusvalor(tipusValorEJB.getReference(vp.getAchTipusvalor().getId()));
						v.addAchValorprimari(valorPrimari);
					}
				}
				toPersist.addAchValoracio(v);			
			}
			
			Seriedocumental res = this.serieService.update(toPersist);
			
			return new SerieDocumentalObject(res);
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "updateSerieDocumental");
		} catch(Exception e) {
			e.printStackTrace();
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "updateSerieDocumental");
		}
		
	}
	
	
}

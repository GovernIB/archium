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

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.AplicacioSerieEJB;
import es.caib.archium.ejb.AplicacionEJB;
import es.caib.archium.ejb.CatalegserieEJB;
import es.caib.archium.ejb.FuncioEJB;
import es.caib.archium.ejb.LimitacioNormativaSerieEJB;
import es.caib.archium.ejb.NormativaEJB;
import es.caib.archium.ejb.NormativaSerieEJB;
import es.caib.archium.ejb.SerieArgenEJB;
import es.caib.archium.ejb.SerieDocumentalEJB;
import es.caib.archium.ejb.SerieRelacionadaEJB;
import es.caib.archium.ejb.TipusserieEJB;
import es.caib.archium.ejb.service.LimitacioNormativaSerieService;
import es.caib.archium.ejb.tablasMaestras.CausalimitacioEJB;
import es.caib.archium.objects.AplicacionObject;
import es.caib.archium.objects.CatalegSeriesObject;
import es.caib.archium.objects.CausaLimitacioObject;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.LimitacioNormativaSerieObject;
import es.caib.archium.objects.NormativaAprobacioObject;
import es.caib.archium.objects.SerieArgenObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.objects.TipusSerieObject;
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
import es.caib.archium.persistence.model.Serieargen;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.Serierelacionada;
import es.caib.archium.persistence.model.Tipusserie;

@Named
@ApplicationScoped
public class NuevaSerieService {

	//Funciones
	@Inject
	private FuncioEJB funcioService;
	//CatalegSeries
	@Inject
	private CatalegserieEJB catalegSerieService;
	//TipusSerie
	@Inject
	private TipusserieEJB tipuSerieService;
	@Inject 
	private SerieDocumentalEJB serieService;
	@Inject
	private AplicacionEJB aplicacioService;
	@Inject
	private AplicacioSerieEJB aplicacioSerieService;
	@Inject
	private SerieRelacionadaEJB serieRelacionadaService;
	@Inject
	private NormativaEJB normativaService;
	@Inject
	private CausalimitacioEJB causaLimitacioService;
	@Inject 
	private LimitacioNormativaSerieEJB limitacioNormativaSerieService;
	@Inject 
	private SerieArgenEJB serieArgenService;
	@Inject
	private NormativaSerieEJB normativaSerieService;
	
	public List<FuncioObject> getListaFunciones() throws I18NException{
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
	}
	public List<CatalegSeriesObject> getListaCatalegSerie() throws I18NException{
		List<CatalegSeriesObject> res = new ArrayList<>();
		
		for(Catalegsery it : catalegSerieService.findAll()) {
			CatalegSeriesObject toInsert = new CatalegSeriesObject();
			toInsert.setId(it.getId());
			toInsert.setNom(it.getNom());
			toInsert.setNomCas(it.getNomcas());
			res.add(toInsert);
		}
		
		return res;
	}
	
	public List<TipusSerieObject> getListaTipusSerie() throws I18NException{
		List<TipusSerieObject> res = new ArrayList<>();
		
		for(Tipusserie it : tipuSerieService.findAll()) {
			TipusSerieObject toInsert = new TipusSerieObject();
			toInsert.setId(it.getId());
			toInsert.setNom(it.getNom());
			toInsert.setNomCas(it.getNomcas());
			res.add(toInsert);
		}
		
		return res;
	}
	public List<SerieDocumentalObject> getListaSeries() throws I18NException{
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
	}
	public List<SerieArgenObject> getListaSeriesArgen() throws I18NException{
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
	}
	
	public List<NormativaAprobacioObject> getListaNormativas() throws I18NException{
		List<NormativaAprobacioObject> res = new ArrayList<>();
		List<Normativa> list = normativaService.findAll();
		
		for(Normativa s : list) {
			NormativaAprobacioObject toInsert = new NormativaAprobacioObject();
			toInsert.setId(s.getId());
			toInsert.setNom(s.getNom());
			res.add(toInsert);
		}
		return res;
	}
	
	public List<CausaLimitacioObject> getListaCausaLimitacio() throws I18NException{
		List<CausaLimitacioObject> res = new ArrayList<>();
		List<Causalimitacio> list = causaLimitacioService.findAll();
		
		for(Causalimitacio s : list) {
			CausaLimitacioObject toInsert = new CausaLimitacioObject();
			toInsert.setId(s.getId());
			toInsert.setNom(s.getNom());
			res.add(toInsert);
		}
		return res;
	}
	
	public List<AplicacionObject> getListaAplicaciones() throws I18NException{
		List<AplicacionObject> res = new ArrayList<>();
		List<Aplicacio> list = aplicacioService.findAll();
		
		for(Aplicacio s : list) {
			AplicacionObject toInsert = new AplicacionObject();
			toInsert.setId(s.getId());
			toInsert.setNom(s.getNom());
			res.add(toInsert);
		}
		return res;
	}
	public List<SerieDocumentalObject> getListaSeriesBySerie(Long serieId) throws I18NException{
		List<SerieDocumentalObject> res = new ArrayList<>();
		Map<String,Object> filters = new HashMap<>();
		filters.put("achSeriedocumental1", serieId );
		filters.put("achSerieargen",null);
		List<Serierelacionada> list = serieRelacionadaService.findFiltered(filters);
		
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
	}
	public List<SerieArgenObject> getListaSeriesArgenBySerie(Long serieId) throws I18NException{
		List<SerieArgenObject> res = new ArrayList<>();
		Map<String,Object> filters = new HashMap<>();
		filters.put("achSeriedocumental1", serieId );
		filters.put("achSeriedocumental2",null);
		List<Serierelacionada> list = serieRelacionadaService.findFiltered(filters);
		
		for(Serierelacionada s : list) {
			if(s.getAchSerieargen() == null )
				continue;
			SerieArgenObject toInsert = new SerieArgenObject();
			toInsert.setId(s.getAchSerieargen().getId());
			toInsert.setNom(s.getAchSerieargen().getNom());
			toInsert.setCodi(s.getAchSerieargen().getCodi());
			res.add(toInsert);
		}
		return res;
	}
	public List<NormativaAprobacioObject> getListaNormativasBySerie(Long serieId) throws I18NException{
		List<NormativaAprobacioObject> res = new ArrayList<>();
		Map<String,Object> filters  = new HashMap<>();
		filters.put("achSeriedocumental", serieId);
		List<NormativaSeriedocumental> list = normativaSerieService.findFiltered(filters);
		for(NormativaSeriedocumental s : list)
		{
			NormativaAprobacioObject toinsert = new NormativaAprobacioObject();
			toinsert.setId(s.getAchNormativa().getId());
			toinsert.setCodi(s.getAchNormativa().getCodi());
			toinsert.setNom(s.getAchNormativa().getNom());
			res.add(toinsert);
		}
		return res;
	}
	
	public List<AplicacionObject> getListaAplicacionesBySerie(Long serieId) throws I18NException{
	
		List<AplicacionObject> res = new ArrayList<>();
		Map<String,Object> filters = new HashMap<>();
		filters.put("achSeriedocumental", serieId );
		List<AplicacioSerie> list = aplicacioSerieService.findFiltered(filters);

		for(AplicacioSerie s : list) {
			AplicacionObject toInsert = new AplicacionObject();
			toInsert.setId(s.getAchAplicacio().getId());
			toInsert.setNom(s.getAchAplicacio().getNom());
			res.add(toInsert);
		}
		return res;
	}
	
	public List<LimitacioNormativaSerieObject> getListaLNS(Long serieId) throws I18NException{
		
		List<LimitacioNormativaSerieObject> res = new ArrayList<>();
		Map<String,Object> filters = new HashMap<>();

		filters.put("achSeriedocumental", serieService.getReference(serieId) );
		List<LimitacioNormativaSerie> list = limitacioNormativaSerieService.findFiltered(filters);

		int i=0;
		for(LimitacioNormativaSerie lns : list) {
			LimitacioNormativaSerieObject lnsOb = new LimitacioNormativaSerieObject(lns);
			lnsOb.setId(i);
			res.add(lnsOb);
			i++;
		}
		return res;
	}
	
	@Transactional
	public SerieDocumentalObject createNuevaSerie(String codi, String nom, String nomCas, Long catalegSeriId, Long idFuncio,
			String descripcio, String descripcioCas, String resumMigracio, String dir3Promotor, String estat, Long tipusSerieId,
			String codiIecisa,List<AplicacionObject> listaApps,List<SerieDocumentalObject> relateds,List<SerieArgenObject> argensList, List<LimitacioNormativaSerieObject> listaLNS
			,List<NormativaAprobacioObject> normativasList) throws I18NException {
		Seriedocumental toPersist = new Seriedocumental();
		
		//Primero persistimos la Serie Documental
		toPersist.setCodi(codi);
		if(tipusSerieId!=null)toPersist.setAchTipusserie(tipuSerieService.getReference(tipusSerieId));
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
		//CREATE SERIEDOCUMENTAL
		Seriedocumental res = serieService.create(toPersist);
		//Si se ha persistido correctamente procedemos a persistir las relaciones
		if(res !=null)
		{
			List<Aplicacio> appsToPersist = new ArrayList<Aplicacio>();
			for(AplicacionObject b : listaApps)
			{
				Aplicacio toList = aplicacioService.getReference(b.getId());
				appsToPersist.add(toList);
			}
			List<Seriedocumental> seriesRelaccs = new ArrayList<Seriedocumental>();
			for(SerieDocumentalObject b : relateds)
			{
				Seriedocumental toList = serieService.getReference(b.getSerieId());
				seriesRelaccs.add(toList);
			}	
			//generamos las series relacionadas
			for(Seriedocumental a : seriesRelaccs)
			{
				Serierelacionada rel = new Serierelacionada();
				rel.setAchSeriedocumental1(res);
				rel.setAchSeriedocumental2(a);
				serieRelacionadaService.create(rel);
			}
			
			List<Serieargen> seriesArgenRelaccs = new ArrayList<Serieargen>();
			for(SerieArgenObject argen : argensList)
			{
				Serieargen sargen = serieArgenService.getReference(argen.getId());
				seriesArgenRelaccs.add(sargen);
			}
			for(Serieargen argen : seriesArgenRelaccs)
			{
				Serierelacionada rel = new Serierelacionada();
				rel.setAchSeriedocumental1(res);
				rel.setAchSerieargen(argen);
				serieRelacionadaService.create(rel);
			}
			//Instanciamos las aplicaciones relacionadas
			for(Aplicacio app : appsToPersist)
			{
				AplicacioSeriePK pk = new AplicacioSeriePK();
				pk.setAplicacioId(app.getId());
				pk.setSeriedocumentalId(res.getId());
				AplicacioSerie appRel = new AplicacioSerie();
				appRel.setId(pk);
				appRel.setAchAplicacio(app);
				appRel.setAchSeriedocumental(res);
				appRel.setDescripcio("Relacion entre "+res.getNom()+ " y "+app.getNom());
				appRel.setInici(new Date());
				aplicacioSerieService.create(appRel);
			}
			
			for(LimitacioNormativaSerieObject lns : listaLNS) {
				lns.setSeriedocumental(new SerieDocumentalObject(res));
				LimitacioNormativaSerie lnsDB = lns.toDbObject();
				lnsDB.setAchNormativa(normativaService.getReference(lnsDB.getId().getNormativaId()));
				lnsDB.setAchCausalimitacio(causaLimitacioService.getReference(lnsDB.getId().getCausalimitacioId()));
				lnsDB.setAchSeriedocumental(res);
				limitacioNormativaSerieService.create(lnsDB);
			}
			
			for( NormativaAprobacioObject norm : normativasList)
			{
				NormativaSeriedocumentalPK pk = new NormativaSeriedocumentalPK();
				pk.setNormativaId(norm.getId());
				pk.setSeriedocumentalId(res.getId());
				NormativaSeriedocumental d = new NormativaSeriedocumental();
				d.setId(pk);
				d.setAchNormativa(normativaService.getReference(norm.getId()));
				d.setAchSeriedocumental(res);
				d.setInici(new Date());
				normativaSerieService.create(d);
			}
		}

		return new SerieDocumentalObject(res);
	}

	@Transactional
	public SerieDocumentalObject updateSerieDocumental(Long idSerie,String codi, String nom, String nomCas, Long catalegSeriId, Long funcioId,
			String descripcio, String descripcioCas, String resumMigracio, String dir3Promotor, String estat, Long tipusSerieId,
			String codiIecisa,List<AplicacionObject> listaApps,List<SerieDocumentalObject> relateds,List<SerieArgenObject> argenRelateds ,List<LimitacioNormativaSerieObject> listaLNS
			,List<NormativaAprobacioObject> normativasList) throws Exception {
		Seriedocumental toPersist = serieService.getReference(idSerie);
		System.out.println("Updating");
		if(toPersist == null)
			throw new Exception("Entity not found");
		
		//Primero encontramos la Serie Documental
		
		if(codi!= null)toPersist.setCodi(codi);
		if(tipusSerieId!=null)toPersist.setAchTipusserie(tipuSerieService.getReference(tipusSerieId));
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
		Seriedocumental res = this.serieService.update(toPersist);
		if(res !=null)
		{
			//CREATE SERIEDOCUMENTAL
			//Seriedocumental res = serieService.create(toPersist);
			//Si se ha persistido correctamente procedemos a persistir las relaciones
			//Encontramos las aplicaciones a borrar
			Map<String,Object> filtersApps = new HashMap<>();
			filtersApps.put("achSeriedocumental", toPersist);
			List<AplicacioSerie> appsFromSerie =  aplicacioSerieService.findFiltered(filtersApps);
			//Recupero las listas de AplicacioSerie por SerieID
			//Recorro la lista de nuevas ( listaApps)
			//Se comparan las listas, si no encuentra la AppSerie, es qeu se ha borrado y se ha de borrar
			Predicate<AplicacioSerie> findToDeletePredicate = appToDelete ->{
				List<Long> find = listaApps.stream().map(AplicacionObject::getId).collect(Collectors.toList());
				return !find.contains(appToDelete.getAchAplicacio().getId());
			};
				
			List<AplicacioSerie> toDelete = appsFromSerie.stream().filter(findToDeletePredicate).collect(Collectors.toList());										
			
			for(AplicacioSerie s : toDelete)
				aplicacioSerieService.delete(s.getId());
			for(AplicacionObject app : listaApps)
			{
				AplicacioSeriePK pk = new AplicacioSeriePK();
				pk.setAplicacioId(app.getId());
				pk.setSeriedocumentalId(toPersist.getId());
				try {
					AplicacioSerie t =aplicacioSerieService.getReference(pk) ;
					if(t== null)
						throw new Exception("t");
					System.out.println(t.toString());
		
				}catch(Exception e)
				{
					AplicacioSerie appRel = new AplicacioSerie();
					appRel.setId(pk);
					appRel.setAchAplicacio(aplicacioService.findById(app.getId()));
					appRel.setAchSeriedocumental(toPersist);
					appRel.setDescripcio("Relacion entre "+toPersist.getNom()+ " y "+app.getNom());
					appRel.setInici(new Date());
					aplicacioSerieService.create(appRel);
				}
				
			}
					
			Map<String,Object> filtersSeries = new HashMap<>();
			filtersSeries.put("achSeriedocumental1", toPersist);
			filtersSeries.put("achSerieargen", null);
			List<Serierelacionada> seriesRelacionadasFromSerie =  serieRelacionadaService.findFiltered(filtersSeries);
			//Recupero las listas de AplicacioSerie por SerieID
			//Recorro la lista de nuevas ( listaApps)
			//Se comparan las listas, si no encuentra la AppSerie, es qeu se ha borrado y se ha de borrar
			Predicate<Serierelacionada> findToDeleteSeriesPredicate = relSeriesToDelete ->{
				List<Long> find = relateds.stream().map(SerieDocumentalObject::getSerieId).collect(Collectors.toList());
				return !find.contains(relSeriesToDelete.getAchSeriedocumental2().getId());
				};
			List<Serierelacionada> seriestoDelete = seriesRelacionadasFromSerie.stream().filter(findToDeleteSeriesPredicate).collect(Collectors.toList());										
			
			for(Serierelacionada s : seriestoDelete )
				serieRelacionadaService.delete(s.getId());
			for(SerieDocumentalObject rel: relateds)
			{
				Seriedocumental serie2 = serieService.getReference(rel.getSerieId());
		
				try {
					Serierelacionada t =serieRelacionadaService.findByRel(toPersist, serie2) ;
					if(t== null)
						throw new Exception("t");
					System.out.println(t.toString());
		
				}catch(Exception e)
				{
					Serierelacionada relatedNew = new Serierelacionada();
					relatedNew.setAchSeriedocumental1(toPersist);
					relatedNew.setAchSeriedocumental2(serie2);
					serieRelacionadaService.create(relatedNew);
				}
				
			}
			
			Map<String,Object> filtersSeriesArgen = new HashMap<>();
			filtersSeriesArgen.put("achSeriedocumental1", toPersist);
			filtersSeriesArgen.put("achSeriedocumental2", null);
			List<Serierelacionada> seriesRelacionadasArgenFromSerie =  serieRelacionadaService.findFiltered(filtersSeriesArgen);
			//Recupero las listas de AplicacioSerie por SerieID
			//Recorro la lista de nuevas ( listaApps)
			//Se comparan las listas, si no encuentra la AppSerie, es qeu se ha borrado y se ha de borrar
			Predicate<Serierelacionada> findToDeleteSeriesArgenPredicate = relSeriesToDelete ->{
				List<Long> find = argenRelateds.stream().map(SerieArgenObject::getId).collect(Collectors.toList());
				return !find.contains(relSeriesToDelete.getAchSerieargen().getId());
				};
			List<Serierelacionada> seriesArgentoDelete = seriesRelacionadasArgenFromSerie .stream().filter(findToDeleteSeriesArgenPredicate).collect(Collectors.toList());										
			
			for(Serierelacionada s : seriesArgentoDelete )
				serieRelacionadaService.delete(s.getId());
			for(SerieArgenObject rel: argenRelateds)
			{
				Serieargen serieargn = serieArgenService.getReference(rel.getId());
		
				try {
					Serierelacionada t =serieRelacionadaService.findByRelArgen(toPersist, serieargn) ;
					if(t== null)
						throw new Exception("t");
					System.out.println(t.toString());
		
				}catch(Exception e)
				{
					Serierelacionada relatedNew = new Serierelacionada();
					relatedNew.setAchSeriedocumental1(toPersist);
					relatedNew.setAchSerieargen(serieargn);
					serieRelacionadaService.create(relatedNew);
				}
				
			}
			Map<String,Object> filtersNorms = new HashMap<>();
			filtersNorms.put("achSeriedocumental", toPersist);
			List<LimitacioNormativaSerie> lnsDB =  limitacioNormativaSerieService.findFiltered(filtersNorms);
			
			List<LimitacioNormativaSerie> lnsNew = new ArrayList<LimitacioNormativaSerie>();
			
			for(LimitacioNormativaSerieObject lnsOb: listaLNS)
			{
				lnsNew.add(lnsOb.toDbObject());
			}
			
			for(LimitacioNormativaSerie itemDb: lnsDB)
			{
				Iterator<LimitacioNormativaSerie> it = lnsNew.iterator();
				boolean encontrado = false;
			    while(it.hasNext() && encontrado==false) {
			    	LimitacioNormativaSerie itemNew = (LimitacioNormativaSerie)it.next();
			    	if(itemNew.getId().equals(itemDb.getId())) {
			    		encontrado=true;
			    		itemNew.setAchCausalimitacio(itemDb.getAchCausalimitacio());
			    		itemNew.setAchNormativa(itemDb.getAchNormativa());
			    		itemNew.setAchSeriedocumental(itemDb.getAchSeriedocumental());
			    		limitacioNormativaSerieService.update(itemNew);
			    		it.remove();
			    	}
			    }
			    
				if(encontrado==false) {
					limitacioNormativaSerieService.delete(itemDb.getId());;
				}
			}
			
			for(LimitacioNormativaSerie itemNew: lnsNew)
			{
				itemNew.setAchCausalimitacio(causaLimitacioService.getReference(itemNew.getId().getCausalimitacioId()));
	    		itemNew.setAchNormativa(normativaService.getReference(itemNew.getId().getNormativaId()));
	    		itemNew.setAchSeriedocumental(serieService.getReference(itemNew.getId().getSeriedocumentalId()));
	    		limitacioNormativaSerieService.create(itemNew);
			}
			/* SERIES NORMATIVAS*/
			
			Map<String,Object> filtersNormativasSeries = new HashMap<>();
			filtersSeries.put("achSeriedocumental", toPersist);
			List<NormativaSeriedocumental> normativasFromSerie =  normativaSerieService.findFiltered(filtersNormativasSeries);
			//Recupero las listas de AplicacioSerie por SerieID
			//Recorro la lista de nuevas ( listaApps)
			//Se comparan las listas, si no encuentra la AppSerie, es qeu se ha borrado y se ha de borrar
			Predicate<NormativaSeriedocumental> findToDeleteNormativasSeriePredicate = relNormativaToDelete ->{
				List<Long> find = normativasList.stream().map(NormativaAprobacioObject::getId).collect(Collectors.toList());
				return !find.contains(relNormativaToDelete.getId().getNormativaId());
				};
			List<NormativaSeriedocumental> normativasToDelete= normativasFromSerie.stream().filter(findToDeleteNormativasSeriePredicate).collect(Collectors.toList());										
			
			for(NormativaSeriedocumental s : normativasToDelete) {
				normativaSerieService.delete(s.getId());
				
			}
				
			for(NormativaAprobacioObject normativaSerie: normativasList)
			{
				NormativaSeriedocumentalPK pk = new NormativaSeriedocumentalPK();
				pk.setNormativaId(normativaSerie.getId());
				pk.setSeriedocumentalId(toPersist.getId());
				try {
					NormativaSeriedocumental ns =normativaSerieService.getReference(pk) ;
					if(ns== null)
						throw new Exception("t");
					System.out.println(ns.toString());
					
		
				}catch(Exception e)
				{
					NormativaSeriedocumental normSerie = new NormativaSeriedocumental();
					normSerie.setId(pk);
					normSerie.setAchNormativa(normativaService.findById(normativaSerie.getId()));
					normSerie.setAchSeriedocumental(toPersist);
					normSerie.setInici(new Date());
					normativaSerieService.create(normSerie);
				}
				
			}
			
		}

		return new SerieDocumentalObject(res);
	}
	
	
}

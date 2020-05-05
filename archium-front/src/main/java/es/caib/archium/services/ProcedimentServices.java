package es.caib.archium.services;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.AplicacioEJB;
import es.caib.archium.ejb.FamiliaProcedimentEJB;
import es.caib.archium.ejb.FormaIniciEJB;
import es.caib.archium.ejb.MateriaEJB;
import es.caib.archium.ejb.NivellelectronicEJB;
import es.caib.archium.ejb.NormativaEJB;
import es.caib.archium.ejb.ProcedimientoEJB;
import es.caib.archium.ejb.SerieDocumentalEJB;
import es.caib.archium.ejb.SilenciEJB;
import es.caib.archium.ejb.TipusDocumentProcedimentEJB;
import es.caib.archium.ejb.tablasMaestras.TipusdocumentalEJB;
import es.caib.archium.ejb.tablasMaestras.TipuspublicEJB;
import es.caib.archium.objects.AplicacioObject;
import es.caib.archium.objects.FamiliaprocedimentObject;
import es.caib.archium.objects.FormainiciObject;
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
import es.caib.archium.persistence.model.Aplicacio;
import es.caib.archium.persistence.model.Familiaprocediment;
import es.caib.archium.persistence.model.Formainici;
import es.caib.archium.persistence.model.LimitacioNormativaSerie;
import es.caib.archium.persistence.model.Materia;
import es.caib.archium.persistence.model.Nivellelectronic;
import es.caib.archium.persistence.model.Normativa;
import es.caib.archium.persistence.model.Procediment;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.Serierelacionada;
import es.caib.archium.persistence.model.Silenci;
import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.Tipusdocumental;
import es.caib.archium.persistence.model.Tipuspublic;

@Named("ProcedimentServices")
@ApplicationScoped
public class ProcedimentServices {
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1842783603922160066L;
	@Inject
	ProcedimientoEJB 		procedimentEJB;
	@Inject
	FamiliaProcedimentEJB 	familiaProcedimentEJB;
	@Inject
	FormaIniciEJB 			formainiciEJB;
	@Inject
	SilenciEJB 				silenciEJB;
	@Inject
	NivellelectronicEJB 	nivellelectronicEJB;
	@Inject
	SerieDocumentalEJB 		serieDocumentalEJB;
	@Inject
	AplicacioEJB 			aplicacioEJB;
	@Inject
	NormativaEJB 			normativaEJB;
	@Inject
	MateriaEJB 				materiaEJB;
	@Inject
	TipusdocumentalEJB 		tipusDocumentaEJB;
	@Inject
	TipusDocumentProcedimentEJB tipusDocumentProcedimentEJB;
	@Inject
	TipuspublicEJB 				tipusPublicEJB;
	
	@Transactional
	public List<ProcedimentObject> findAllProcedimiento() throws I18NException{	
		//System.out.println("SErvice del front find all");
		List<Procediment> res= procedimentEJB.findAll();
		List<ProcedimentObject> lista = new ArrayList<ProcedimentObject>();
		for(Procediment i : res)
		{				
			lista.add(new ProcedimentObject(i));
		}
		return lista;
	}
	
	@Transactional
	public List<FamiliaprocedimentObject> findAllFamiliaprocediment() throws I18NException{	
		
		List<FamiliaprocedimentObject> lista = new ArrayList< >();
		List<Familiaprocediment> res= familiaProcedimentEJB.findAll();
		
		for(Familiaprocediment  i : res)
		{				
			lista.add(new FamiliaprocedimentObject(i));
		}		
		return lista;
	}
	
	@Transactional
	public List<FormainiciObject> findAllFormainici() throws I18NException{	
		
		List<FormainiciObject> lista = new ArrayList< >();
		List<Formainici> res= formainiciEJB.findAll();
		
		for(Formainici  i : res)
		{				
			lista.add(new FormainiciObject(i));
		}
		
		return lista;
	}

	@Transactional
	public List<SilenciObject> findAllSilenci() throws I18NException{	
		
		List<SilenciObject> lista = new ArrayList< >();
		List<Silenci> res= silenciEJB.findAll();
		
		for(Silenci i : res)
		{				
			lista.add(new SilenciObject(i));
		}
		
		return lista;
	}
	
	@Transactional
	public List<NivellelectronicObject> findAllNivellelectronic() throws I18NException{	
		
		List<NivellelectronicObject> lista = new ArrayList< >();
		List<Nivellelectronic > res= nivellelectronicEJB.findAll();
		
		for(Nivellelectronic i : res)
		{				
			lista.add(new NivellelectronicObject(i));
		}
		
		return lista;
	}
	
	@Transactional
	public List<SerieDocumentalObject> findAllSeriedocumental() throws I18NException{	
		
		List<SerieDocumentalObject> lista = new ArrayList< >();
		List<Seriedocumental> res= serieDocumentalEJB.findAll();
		
		for(Seriedocumental  i : res)
		{				
			lista.add(new SerieDocumentalObject(i));
		}
		
		return lista;
	}
	
	@Transactional
	public List<AplicacioObject> findAllAplicacio() throws I18NException{	
		
		List<AplicacioObject> lista = new ArrayList< >();
		List<Aplicacio> res= aplicacioEJB.findAll();
		
		for(Aplicacio  i : res)
		{				
			lista.add(new AplicacioObject(i));
		}
		
		return lista;
	}
		
	@Transactional
	public List<NormativaObject> findAllNormativa() throws I18NException{	
		
		List<NormativaObject> lista = new ArrayList< >();
		List<Normativa> res= normativaEJB.findAll();
		
		for(Normativa  i : res)
		{				
			lista.add(new NormativaObject(i));
		}
		
		return lista;
	}
	
	@Transactional
	public List<MateriaObject> findAllMateria() throws I18NException{	
		
		List<MateriaObject> lista = new ArrayList< >();
		List<Materia> res= materiaEJB.findAll();
		
		for(Materia  i : res)
		{				
			lista.add(new MateriaObject(i));
		}	
		return lista;
	}
	
	@Transactional
	public List<TipuDocumentalProcedimentObject> getListaTDP(Long id) throws I18NException{
		
		List<TipuDocumentalProcedimentObject> lista 	= new ArrayList< >();
		Map<String,Object> filters = new HashMap<>();
		filters.put("achProcediment", id );

		List<TipusdocumentProcediment> res = tipusDocumentProcedimentEJB.findFiltered(filters);
		
		int i=0;
		for(TipusdocumentProcediment  tdp : res)
		{				
			TipuDocumentalProcedimentObject newTDP = new TipuDocumentalProcedimentObject(tdp);
			newTDP.setId(i);
			lista.add(newTDP);
			i++;
		}	
		return lista;
	
	}
	
	@Transactional
	public List<TipuDocumentalObject> findAllTipoDocumental() throws I18NException{	
		
		List<TipuDocumentalObject> 	lista 	= new ArrayList< >();
		List<Tipusdocumental> 			res		= tipusDocumentaEJB.findAll();
		
		for(Tipusdocumental  i : res)
		{				
			lista.add(new TipuDocumentalObject(i));
		}	
		return lista;
	}
	
	@Transactional
	public List<tipusPublicObject> findAllTipusPublic() throws I18NException{	
		
		List<tipusPublicObject> 	lista 	= new ArrayList< >();
		List<Tipuspublic> 			res		= tipusPublicEJB.findAll();
		
		for(Tipuspublic  i : res)
		{				
			lista.add(new tipusPublicObject(i));
		}	
		return lista;
	}
	
	public Familiaprocediment familia(Long id) {
		return familiaProcedimentEJB.getReference(id);
	}
	
	public Formainici forma(Long id) {		
		return formainiciEJB.getReference(id);
	}
	public Silenci silenci(Long id) {		
		return silenciEJB.getReference(id);
	}
	public Nivellelectronic nivel(Long id) {		
		return nivellelectronicEJB.getReference(id);
	}
	public Seriedocumental serie(Long id) {		
		return serieDocumentalEJB.getReference(id);
	}
	public Aplicacio aplicacion(Long id) {		
		return aplicacioEJB.getReference(id);
	}
	
	@Transactional
	public void create(ProcedimentObject i, List<MateriaObject> lista1, List<tipusPublicObject> lista2, List<NormativaObject> lista3, List<TipuDocumentalProcedimentObject> listaTDP) throws I18NException {	
		Procediment db = new Procediment();
		try 
		{
			
			db.setCodisia(i.getCodisia());
			db.setNom(i.getNom());
			db.setObjecte(i.getObjecte());
			db.setEstat(i.getEstat());
			db.setDestinataris(i.getDestinataris());
			db.setObservacions(i.getObservacions());
			db.setUri(i.getUri());

			if (i.getFamiliaprocediment()!= null) {		
				db.setAchFamiliaprocediment(familia(i.getFamiliaprocediment().getId()));
			}
			if (i.getFormainici()!= null){
				db.setAchFormainici(forma(i.getFormainici().getId()));			
			}
			if (i.getSilenci()!= null) {
				db.setAchSilenci(silenci(i.getSilenci().getId()));
			}
			if (i.getNivellelectronic()!= null) {
				db.setAchNivellelectronic(nivel(i.getNivellelectronic().getId()));
			}
			if (i.getSeriedocumental()!= null) {
				db.setAchSeriedocumental(serie(i.getSeriedocumental().getSerieId()));
			}
			if (i.getAplicacio()!= null) {
				db.setAchAplicacio(aplicacion(i.getAplicacio().getId()));
			}			
			db.setCodirolsac(i.getCodirolsac());
			db.setTermini(i.getTermine());
			db.setTermininotif(i.getTermininotif());
			db.setFiviaadministrativa(i.getFiViaAdministrativa()?new BigDecimal(1):new BigDecimal(0));
			db.setTaxa(i.getTaxa()?new BigDecimal(1):new BigDecimal(0));
			db.setDir3Resolvent(i.getDir3Resolvent());
			db.setDir3Instructor(i.getDir3Instructor());
			db.setPublicacio(i.getPublicacio());
			db.setCaducitat(i.getCaducitat());
			db.setGestor(i.getGestor());
			db.setModificacio(new Date());
			List<Materia> listb1 = new ArrayList<>();
			for (MateriaObject var : lista1) {
				Materia b = materiaEJB.getReference(var.getId());
				listb1.add(b);
			}
			db.setAchMaterias(listb1);
			List<Tipuspublic> listb2 = new ArrayList<>();
			for (tipusPublicObject var : lista2) {
				System.out.println("TYPO PUBLIC:" + var.getId());
				Tipuspublic b = tipusPublicEJB.getReference(var.getId());
				listb2.add(b);
			}
			db.setAchTipuspublics(listb2);
			List<Normativa> listb3 = new ArrayList<>();
			for (NormativaObject var : lista3) {
				Normativa b = normativaEJB.getReference(var.getId());
				listb3.add(b);
			}			
			db.setAchNormativas(listb3);
			
			Procediment res = this.procedimentEJB.create(db);			
			
			for(TipuDocumentalProcedimentObject tdp : listaTDP) {
				tdp.setProcediment(new ProcedimentObject(res));
				TipusdocumentProcediment tdpDB = tdp.toDbObject();
				tdpDB.setAchTipusdocumental(tipusDocumentaEJB.getReference(tdp.getTipusDocumental().getId()));;
				tdpDB.setAchProcediment(res);
				tipusDocumentProcedimentEJB.create(tdpDB);
				
			}
			
		}
		catch (Exception e){
			System.err.println("Error persistiendo en base dados Salvar ");
			e.printStackTrace();
		}		
	}
	
	
	// no se usa ahora pero hay que completarlo.
	@Transactional
	public void createTipusDocumental(List<TipuDocumentalProcedimentObject> tiposDocumentalSelected) throws I18NException {	
		
		for (TipuDocumentalProcedimentObject var: tiposDocumentalSelected) 
		{
			TipusdocumentProcediment db  = new TipusdocumentProcediment();
			db.setAchProcediment(this.procedimentEJB.getReference(var.getProcediment().getId()));			
			db.setAchTipusdocumental(this.tipusDocumentaEJB.getReference(var.getTipusDocumental().getId()));
			db.setObligatori(var.getObligatori()?new BigDecimal(1):new BigDecimal(0));
			db.setRecapitulatiu(var.getRecapitulatiu()?new BigDecimal(1):new BigDecimal(0));
		}
	}
	

	@Transactional
	public void update(ProcedimentObject i, List<MateriaObject> lista1, List<tipusPublicObject> lista2, List<NormativaObject> lista3, List<TipuDocumentalProcedimentObject> listaTDP) throws I18NException {
		Procediment db = new Procediment();
		try {
			db.setId(i.getId());
			db.setCodisia(i.getCodisia());
			db.setNom(i.getNom());
			db.setObjecte(i.getObjecte());
			db.setEstat(i.getEstat());
			db.setDestinataris(i.getDestinataris());
			db.setObservacions(i.getObservacions());
			db.setUri(i.getUri());
			
			if (i.getFamiliaprocediment()!= null) {		
				db.setAchFamiliaprocediment(familia(i.getFamiliaprocediment().getId()));
			}
			if (i.getFormainici()!= null){
				db.setAchFormainici(forma(i.getFormainici().getId()));			
			}
			if (i.getSilenci()!= null) {
				db.setAchSilenci(silenci(i.getSilenci().getId()));
			}
			if (i.getNivellelectronic()!= null) {
				db.setAchNivellelectronic(nivel(i.getNivellelectronic().getId()));
			}
			if (i.getSeriedocumental()!= null) {
				db.setAchSeriedocumental(serie(i.getSeriedocumental().getSerieId()));
			}
			if (i.getAplicacio()!= null) {
				db.setAchAplicacio(aplicacion(i.getAplicacio().getId()));
			}			
			db.setCodirolsac(i.getCodirolsac());
			db.setTermini(i.getTermine());
			db.setTermininotif(i.getTermininotif());
			db.setFiviaadministrativa(i.getFiViaAdministrativa()?new BigDecimal(1):new BigDecimal(0));
			db.setTaxa(i.getTaxa()?new BigDecimal(1):new BigDecimal(0));
			db.setDir3Resolvent(i.getDir3Resolvent());
			db.setDir3Instructor(i.getDir3Instructor());
			db.setPublicacio(i.getPublicacio());
			db.setCaducitat(i.getCaducitat());
			db.setGestor(i.getGestor());
			db.setModificacio(new Date());
			
			List<Materia> listb1 = new ArrayList<>();
			for (MateriaObject var : lista1) {
				Materia b = materiaEJB.getReference(var.getId());
				listb1.add(b);
			}
			db.setAchMaterias(listb1);
			List<Tipuspublic> listb2 = new ArrayList<>();
			for (tipusPublicObject var : lista2) {
				Tipuspublic b = tipusPublicEJB.getReference(var.getId());
				listb2.add(b);
			}
			db.setAchTipuspublics(listb2);
			List<Normativa> listb3 = new ArrayList<>();
			for (NormativaObject var : lista3) {
				Normativa b = normativaEJB.getReference(var.getId());
				listb3.add(b);
			}			
			db.setAchNormativas(listb3);
			
			Procediment res = this.procedimentEJB.update(db);
			
			Map<String,Object> filtersNorms = new HashMap<>();
			filtersNorms.put("achProcediment", res);
			List<TipusdocumentProcediment> lnsDB =  tipusDocumentProcedimentEJB.findFiltered(filtersNorms);
			
			List<TipusdocumentProcediment> tdpNew = new ArrayList<TipusdocumentProcediment>();
			
			for(TipuDocumentalProcedimentObject tdpOb: listaTDP)
			{
				tdpNew.add(tdpOb.toDbObject());
			}
			
			for(TipusdocumentProcediment itemDb: lnsDB)
			{
				Iterator<TipusdocumentProcediment> it = tdpNew.iterator();
				boolean encontrado = false;
			    while(it.hasNext() && encontrado==false) {
			    	TipusdocumentProcediment itemNew = (TipusdocumentProcediment)it.next();
			    	if(itemNew.getId().equals(itemDb.getId())) {
			    		encontrado=true;
			    		System.out.println("ITEMNEW=>" + itemNew);
			    		itemNew.setAchProcediment(itemDb.getAchProcediment());
			    		itemNew.setAchTipusdocumental(itemDb.getAchTipusdocumental());
			    		tipusDocumentProcedimentEJB.update(itemNew);
			    		it.remove();
			    	}
			    }
			    
				if(encontrado==false) {
					tipusDocumentProcedimentEJB.delete(itemDb.getId());;
				}
				
			}
			
			for(TipusdocumentProcediment itemNew: tdpNew)
			{
				itemNew.setAchProcediment(procedimentEJB.getReference(itemNew.getId().getProcedimentId()));
	    		itemNew.setAchTipusdocumental(tipusDocumentaEJB.getReference(itemNew.getId().getTipusdocumentalId()));
	    		tipusDocumentProcedimentEJB.create(itemNew);
			}
			
			
		} catch (Exception e) {
			e.printStackTrace();
			System.err.println("Error persistiendo en base dados Salvar");
		}
		
	}
	@Transactional
	public List<TipuDocumentalObject> listaTipo (List<TipuDocumentalProcedimentObject> lista){
		List<TipuDocumentalObject> list = new ArrayList<>();
		for (TipuDocumentalProcedimentObject var:lista ) {
			Tipusdocumental 		bd 		= this.tipusDocumentaEJB.getReference(var.getProcediment().getId());
			TipuDocumentalObject 	objeto 	= new TipuDocumentalObject(bd);
			list.add(objeto);
		}
		return list;
	}	
}

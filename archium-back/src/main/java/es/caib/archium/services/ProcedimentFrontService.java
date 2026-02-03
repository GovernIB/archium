package es.caib.archium.services;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.commons.query.OrderBy;
import es.caib.archium.commons.query.OrderType;
import es.caib.archium.ejb.objects.Dir3;
import es.caib.archium.ejb.service.AplicacioService;
import es.caib.archium.ejb.service.Dir3Service;
import es.caib.archium.ejb.service.FamiliaProcedimentService;
import es.caib.archium.ejb.service.FormaIniciService;
import es.caib.archium.ejb.service.MateriaService;
import es.caib.archium.ejb.service.NivellElectronicService;
import es.caib.archium.ejb.service.NormativaService;
import es.caib.archium.ejb.service.ProcedimientoService;
import es.caib.archium.ejb.service.SerieDocumentalService;
import es.caib.archium.ejb.service.SilenciService;
import es.caib.archium.ejb.service.TipusDocumentProcedimentService;
import es.caib.archium.ejb.service.TipusDocumentalService;
import es.caib.archium.ejb.service.TipusPublicService;
import es.caib.archium.objects.AplicacioObject;
import es.caib.archium.objects.Dir3Object;
import es.caib.archium.objects.FamiliaprocedimentObject;
import es.caib.archium.objects.FormainiciObject;
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
import es.caib.archium.persistence.model.Materia;
import es.caib.archium.persistence.model.Nivellelectronic;
import es.caib.archium.persistence.model.Normativa;
import es.caib.archium.persistence.model.Procediment;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.Silenci;
import es.caib.archium.persistence.model.TipusDocumental;
import es.caib.archium.persistence.model.TipusdocumentProcediment;
import es.caib.archium.persistence.model.Tipuspublic;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Named("ProcedimentServices")
@ApplicationScoped
public class ProcedimentFrontService {
	
	/**
	 * 
	 */
	//private static final long serialVersionUID = 1842783603922160066L;
	@Inject
	ProcedimientoService 		procedimentEJB;
	@Inject
	FamiliaProcedimentService 	familiaProcedimentEJB;
	@Inject
	FormaIniciService 			formainiciEJB;
	@Inject
	SilenciService 				silenciEJB;
	@Inject
	NivellElectronicService 	nivellelectronicEJB;
	@Inject
	SerieDocumentalService 		serieDocumentalEJB;
	@Inject
	AplicacioService 			aplicacioEJB;
	@Inject
	NormativaService 			normativaEJB;
	@Inject
	MateriaService 				materiaEJB;
	@Inject
	TipusDocumentalService 		tipusDocumentaEJB;
	@Inject
	TipusDocumentProcedimentService tipusDocumentProcedimentEJB;
	@Inject
	TipusPublicService 				tipusPublicEJB;
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
	public List<ProcedimentObject> findAllProcedimiento() throws I18NException{	
		
		try {
			List<Procediment> res= procedimentEJB.findAll();
			List<ProcedimentObject> lista = new ArrayList<ProcedimentObject>();
			for(Procediment i : res)
			{				
				lista.add(new ProcedimentObject(i));
			}
			return lista;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllProcedimiento");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllProcedimiento");
		}
	}

	@Transactional
	public ProcedimentObject findProcedimentById(Long procedimentId) throws I18NException{

		try {
			return new ProcedimentObject(procedimentEJB.findById(procedimentId));
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findProcedimentById");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findProcedimentById");
		}
	}
	
	@Transactional
	public List<FamiliaprocedimentObject> findAllFamiliaprocediment() throws I18NException{

		try {
			List<FamiliaprocedimentObject> lista = new ArrayList< >();
			List<Familiaprocediment> res= familiaProcedimentEJB.findAll();

			for(Familiaprocediment  i : res)
			{
				lista.add(new FamiliaprocedimentObject(i));
			}
			return lista;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllFamiliaprocediment");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllFamiliaprocediment");
		}
	}

	@Transactional
	public FamiliaprocedimentObject findFamiliaprocedimentById(Long familiaId) throws I18NException{

		try {
			return new FamiliaprocedimentObject(familiaProcedimentEJB.findById(familiaId));
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findFamiliaprocedimentById");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findFamiliaprocedimentById");
		}
	}
	
	@Transactional
	public List<FormainiciObject> findAllFormainici() throws I18NException{	
		
		try {
			List<FormainiciObject> lista = new ArrayList< >();
			List<Formainici> res= formainiciEJB.findAll();
			
			for(Formainici  i : res)
			{				
				lista.add(new FormainiciObject(i));
			}
			
			return lista;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllFormainici");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllFormainici");
		}
	}

	@Transactional
	public FormainiciObject findFormaIniciById(Long formaIniciId) throws I18NException {
		try {
			return new FormainiciObject(formainiciEJB.findById(formaIniciId));
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findFormaIniciById");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findFormaIniciById");
		}
	}

	@Transactional
	public List<SilenciObject> findAllSilenci() throws I18NException{	
		
		try {
			List<SilenciObject> lista = new ArrayList< >();
			List<Silenci> res= silenciEJB.findAll();
			
			for(Silenci i : res)
			{				
				lista.add(new SilenciObject(i));
			}
			
			return lista;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllSilenci");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllSilenci");
		}
	}

	@Transactional
	public SilenciObject findSilenciById(Long silenciId) throws I18NException{

		try {
			return new SilenciObject(silenciEJB.findById(silenciId));
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findSilenciById");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findSilenciById");
		}
	}
	
	@Transactional
	public List<NivellelectronicObject> findAllNivellelectronic() throws I18NException{	
		
		try {
			List<NivellelectronicObject> lista = new ArrayList< >();
			List<Nivellelectronic > res= nivellelectronicEJB.findAll();
			
			for(Nivellelectronic i : res)
			{				
				lista.add(new NivellelectronicObject(i));
			}
			
			return lista;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllNivellelectronic");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllNivellelectronic");
		}
	}

	@Transactional
	public NivellelectronicObject findNivellElectronicById(Long nivellId) throws I18NException{
		try {
			return new NivellelectronicObject(nivellelectronicEJB.findById(nivellId));
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findNivellElectronicById");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findNivellElectronicById");
		}
	}
	
	@Transactional
	public List<SerieDocumentalObject> findAllSeriedocumental() throws I18NException{	
		
		try {
			List<SerieDocumentalObject> lista = new ArrayList< >();
			List<Seriedocumental> res= serieDocumentalEJB.findAll();
			
			for(Seriedocumental  i : res)
			{				
				lista.add(new SerieDocumentalObject(i));
			}
			
			return lista;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllSeriedocumental");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllSeriedocumental");
		}
		
		
		
	}



	@Transactional
	public List<AplicacioObject> findAllAplicacio() throws I18NException{	
		
		try {
			List<AplicacioObject> lista = new ArrayList< >();
			List<Aplicacio> res= aplicacioEJB.findAll(new OrderBy("nom", OrderType.ASC));
			
			for(Aplicacio  i : res)
			{				
				lista.add(new AplicacioObject(i));
			}
			
			return lista;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllAplicacio");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllAplicacio");
		}
	}

	@Transactional
	public AplicacioObject findAplicacioById(Long aplicacioId) throws I18NException{

		AplicacioObject aplicacioObject = null;
		try {
			Aplicacio res= aplicacioEJB.findById(aplicacioId);
			aplicacioObject = new AplicacioObject(res);
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAplicacioById");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAplicacioById");
		}
		return aplicacioObject;

	}
		
	@Transactional
	public List<NormativaObject> findAllNormativa() throws I18NException{	

		try {
			List<NormativaObject> lista = new ArrayList< >();
			List<Normativa> res= normativaEJB.findAll();
			
			for(Normativa  i : res)
			{				
				lista.add(new NormativaObject(i));
			}
			
			return lista;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllNormativa");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllNormativa");
		}
		
	}
	
	@Transactional
	public List<MateriaObject> findAllMateria() throws I18NException{	
	
		try {
			List<MateriaObject> lista = new ArrayList< >();
			List<Materia> res= materiaEJB.findAll();
			
			for(Materia  i : res)
			{				
				lista.add(new MateriaObject(i));
			}	
			return lista;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllMateria");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllMateria");
		}
		
	}
	
	@Transactional
	public List<TipuDocumentalProcedimentObject> getListaTDP(Long id) throws I18NException{

		try {
			List<TipuDocumentalProcedimentObject> lista = new ArrayList< >();
			List<TipusdocumentProcediment> res = tipusDocumentProcedimentEJB.getTipusDocument(id);
			
			int i=0;
			for(TipusdocumentProcediment  tdp : res)
			{				
				TipuDocumentalProcedimentObject newTDP = new TipuDocumentalProcedimentObject(tdp);
				newTDP.setId(i);
				lista.add(newTDP);
				i++;
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

	@Transactional
	public TipuDocumentalObject findTipusDocumentalById(Long tipusDocumentalId) throws I18NException{

		try {
			return new TipuDocumentalObject(tipusDocumentaEJB.findById(tipusDocumentalId));
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findTipusDocumentalById");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findTipusDocumentalById");
		}
	}

	@Transactional
	public List<tipusPublicObject> findAllTipusPublic() throws I18NException{	
		
		try {
			List<tipusPublicObject> 	lista 	= new ArrayList< >();
			List<Tipuspublic> 			res		= tipusPublicEJB.findAll();
			
			for(Tipuspublic  i : res)
			{				
				lista.add(new tipusPublicObject(i));
			}	
			return lista;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllTipusPublic");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllTipusPublic");
		}
		
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
	public ProcedimentObject findById(Long id) throws I18NException {
		try {
			Procediment p = this.procedimentEJB.findById(id);
			
			if (p!=null) {
				return new ProcedimentObject(p);
			}
			return null;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findById");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findById");
		}
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
			// #6345 Simplificació
			/*if (i.getFamiliaprocediment()!= null) {
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
			}*/
			if (i.getSeriedocumental()!= null) {
				db.setAchSeriedocumental(serie(i.getSeriedocumental().getSerieId()));
			}

			db.setCodirolsac(i.getCodirolsac());
			/*db.setTermini(i.getTermine());
			db.setTermininotif(i.getTermininotif());
			if(i.getFiViaAdministrativa()!=null)
				db.setFiviaadministrativa(i.getFiViaAdministrativa()?new BigDecimal(1):new BigDecimal(0));
			if(i.getTaxa()!=null)
				db.setTaxa(i.getTaxa()?new BigDecimal(1):new BigDecimal(0));
			db.setDir3Resolvent(i.getDir3Resolvent());
			db.setDir3Instructor(i.getDir3Instructor());
			db.setPublicacio(i.getPublicacio());
			db.setCaducitat(i.getCaducitat());
			db.setGestor(i.getGestor());*/
			db.setModificacio(new Date());
			/*List<Materia> listb1 = new ArrayList<>();
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
			db.setAchTipuspublics(listb2);*/
			List<Normativa> listb3 = new ArrayList<>();
			for (NormativaObject var : lista3) {
				Normativa b = normativaEJB.getReference(var.getId());
				listb3.add(b);
			}			
			db.setAchNormativas(listb3);
			
			db.setAchTipusdocumentProcediments(new ArrayList<TipusdocumentProcediment>());
			for(TipuDocumentalProcedimentObject tdp : listaTDP) {
				TipusdocumentProcediment tdpDB = tdp.toDbObject();
				tdpDB.setAchTipusdocumental(tipusDocumentaEJB.getReference(tdp.getTipusDocumental().getId()));;
				db.addAchTipusdocumentProcediment(tdpDB);
			}
			Procediment res = this.procedimentEJB.create(db);			
			
			
			
		}catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "create");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "create");
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
			db.setCodirolsac(i.getCodirolsac());
			db.setTermini(i.getTermine());
			db.setTermininotif(i.getTermininotif());
			if(i.getFiViaAdministrativa()!=null)
				db.setFiviaadministrativa(i.getFiViaAdministrativa()?new BigDecimal(1):new BigDecimal(0));
			if(i.getTaxa()!=null)
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
			
			db.setAchTipusdocumentProcediments(new ArrayList<TipusdocumentProcediment>());
			for(TipuDocumentalProcedimentObject tdpOb: listaTDP)
			{
				TipusdocumentProcediment tdpDB = tdpOb.toDbObject();
				tdpDB.setAchProcediment(procedimentEJB.getReference(tdpOb.getProcediment().getId()));
				tdpDB.setAchTipusdocumental(tipusDocumentaEJB.getReference(tdpOb.getTipusDocumental().getId()));
				db.addAchTipusdocumentProcediment(tdpDB);
			}
			
			Procediment res = this.procedimentEJB.update(db);		
			
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "update");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "update");
		}
		
	}
	@Transactional
	public List<TipuDocumentalObject> listaTipo (List<TipuDocumentalProcedimentObject> lista) throws I18NException{
		
		
		try {
			List<TipuDocumentalObject> list = new ArrayList<>();
			for (TipuDocumentalProcedimentObject var:lista ) {
				TipusDocumental bd 		= this.tipusDocumentaEJB.getReference(var.getTipusDocumental().getId());
				TipuDocumentalObject 	objeto 	= new TipuDocumentalObject(bd);
				list.add(objeto);
			}
			return list;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "listaTipo");
		} catch(Exception e) {
			e.printStackTrace();
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "listaTipo");
		}
		
		
	}	
	
	@Transactional
	public void deleteProcediment(Long idProcediment) throws I18NException{
		try {
			this.procedimentEJB.delete(idProcediment);
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "deleteProcediment");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "deleteProcediment");
		}
	}
	
}

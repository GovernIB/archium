package es.caib.archium.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.DictamenService;
import es.caib.archium.ejb.service.EnsService;
import es.caib.archium.ejb.service.LopdService;
import es.caib.archium.ejb.service.NormativaService;
import es.caib.archium.ejb.service.SerieService;
import es.caib.archium.ejb.service.TipuAccesService;
import es.caib.archium.ejb.service.TipuDictamenService;
import es.caib.archium.objects.DictamenObject;
import es.caib.archium.objects.EnsObject;
import es.caib.archium.objects.LopdObject;
import es.caib.archium.objects.NormativaAprobacioObject;
import es.caib.archium.objects.SerieDocumentalObject;
import es.caib.archium.objects.TipuAccesObject;
import es.caib.archium.objects.TipuDictamenObject;
import es.caib.archium.persistence.model.*;

@Named
@ApplicationScoped
public class DictamenFrontService {
	
	@Inject
	DictamenService dictamenEJB;
	@Inject
	TipuDictamenService tipuDictamenEJB;
	@Inject
	SerieService serieEJB;
	@Inject
	TipuAccesService tipuAccesEJB;
	@Inject
	EnsService ensEJB;
	@Inject
	LopdService lopdEJB;
	@Inject
	NormativaService normativaEJB;

	
	public List<DictamenObject> getBySerie(SerieDocumentalObject serie) throws I18NException{
		
		try {
			List<DictamenObject> res = new ArrayList<DictamenObject>();
			List<Dictamen> list = dictamenEJB.getBySerieId(serie.getSerieId());
			
			for(Dictamen i : list) {
				res.add(new DictamenObject(i));
			}
			
			return res;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getBySerie");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getBySerie");
		}
		
	}
	
	public DictamenObject findById(Long id) throws I18NException{	
		
		try {
			
			Dictamen res = dictamenEJB.findById(id);
			
			if(res!=null) {
				return new DictamenObject(res);
			} else {
				return null;
			}
			
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findById");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findById");
		}
		
	}
	
	public List<DictamenObject> findAll() throws I18NException{	
		try {
			List<DictamenObject> listaDictamen = new ArrayList<DictamenObject>();
			
			List<Dictamen> res= dictamenEJB.findAll();
			
			for(Dictamen i : res)
			{				
				listaDictamen.add(new DictamenObject(i));
			}
			
			return listaDictamen;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAll");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAll");
		}
	}
	
	public List<SerieDocumentalObject> findAllDocumental() throws I18NException{	
		
		try {
			List<SerieDocumentalObject> listaSeries = new ArrayList<SerieDocumentalObject>();
			
			List<Seriedocumental> res= serieEJB.findAll();
			
			for(Seriedocumental i : res)
			{				
				listaSeries.add(new SerieDocumentalObject(i));
			}
			
			return listaSeries;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllDocumental");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllDocumental");
		}
		
		
	}
	
	public List<TipuDictamenObject> findAllTipuDictamen() throws I18NException{	
		
		try {
			List<TipuDictamenObject> listaTipuDictamen = new ArrayList<TipuDictamenObject>();
			
			List<Tipusdictamen> res= tipuDictamenEJB.findAll();
			
			for(Tipusdictamen i : res)
			{				
				listaTipuDictamen.add(new TipuDictamenObject(i));
			}
			
			return listaTipuDictamen;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllTipuDictamen");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllTipuDictamen");
		}
		
		
	}

	public List<TipuAccesObject> findAllTipuAcces() throws I18NException{	
		
		try {
			List<TipuAccesObject> listaTipuAcces = new ArrayList<TipuAccesObject>();
			
			List<Tipusacce> res= tipuAccesEJB.findAll();
			
			for(Tipusacce i : res)
			{				
				listaTipuAcces.add(new TipuAccesObject(i));
			}
			
			return listaTipuAcces;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllTipuAcces");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllTipuAcces");
		}
		
		
	}
	
	public List<EnsObject> findAllEns() throws I18NException{	
		
		try {
			List<EnsObject> listaEns = new ArrayList<EnsObject>();
			
			List<Ens> res= ensEJB.findAll();
			
			for(Ens i : res)
			{				
				listaEns.add(new EnsObject(i));
			}
			
			return listaEns;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllEns");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllEns");
		}
		
		
	}
	
	public List<LopdObject> findAllLopd() throws I18NException{	
		
		try {
			List<LopdObject> listaLopd = new ArrayList<LopdObject>();
			
			List<Lopd> res= lopdEJB.findAll();
			
			for(Lopd i : res)
			{				
				listaLopd.add(new LopdObject(i));
			}
			
			return listaLopd;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllLopd");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllLopd");
		}
		
		
	}
	
	public List<NormativaAprobacioObject> findAllNormativaAprovacio() throws I18NException{	
		
		try {
			List<NormativaAprobacioObject> listaNormativa = new ArrayList<NormativaAprobacioObject>();
			
			List<Normativa> res= normativaEJB.findAll();
			
			for(Normativa i : res)
			{				
				listaNormativa.add(new NormativaAprobacioObject(i));
			}
			
			return listaNormativa;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAllNormativaAprovacio");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAllNormativaAprovacio");
		}
		
	}
	
	@Transactional
	public void changeEstatDictamen(DictamenObject dictamen, String estat) throws I18NException {
		
		try {
			
			Dictamen dic = this.dictamenEJB.getReference(dictamen.getId());
			dic.setEstat(estat);
			this.dictamenEJB.update(dic);
			
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "changeEstatDictamen");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "changeEstatDictamen");
		}	
		
	}
	
	@Transactional
	public void changeVigent2Obsolet(Long serieId) throws I18NException {
		
		try {
			
			List<Dictamen> listDictamenes = this.serieEJB.getDictamenVigent(serieId);
			
			for(Dictamen d : listDictamenes) {
				d.setEstat("Obsolet");
				this.dictamenEJB.update(d);
			}
			
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "changeVigent2Obsolet");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "changeVigent2Obsolet");
		}		
		
	}
	
	public Boolean checkDictamenVigent(Long id, String estat, Long idSerie) throws I18NException {
		
		try {
			
			List<Dictamen> listDictamenes = this.serieEJB.getDictamenVigent(idSerie);
			
			if(listDictamenes!=null && estat.equals("Vigent")) {
				
				Iterator<Dictamen> it = listDictamenes.iterator();
				Boolean existsVigent = false;
				while(it.hasNext() && existsVigent==false) {
					Dictamen d = it.next();
					if(!(d.getId().equals(id))) {
						existsVigent = true;
					}
				}
				return existsVigent;
			} else {
				return false;
			}
			
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "checkDictamenVigent");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "checkDictamenVigent");
		}		
		
		
		
	}
	
	@Transactional
	public DictamenObject create(SerieDocumentalObject serieDocumental, TipuDictamenObject tipuDictamen, String accioDictaminada, String termini, String destinatariRestringits, Date fin, 
								TipuAccesObject tipuAcces, EnsObject ens, LopdObject lopd, String condicioReutilizacio, Boolean serieEsencial, NormativaAprobacioObject normativaAprovacio, Date aprovacio,
								String codi, String estat) throws I18NException {	
		
		try {
			DictamenObject ob = new DictamenObject();
			ob.setAccioDictaminada(accioDictaminada);
			ob.setTermini(termini);
			ob.setInici(new Date());
			ob.setDestinatarisRestrigits(destinatariRestringits);
			ob.setFi(fin);
			ob.setCondicioReutilitzacio(condicioReutilizacio);
			ob.setSerieEsencial(serieEsencial);
			ob.setAprovacio(aprovacio);		
			ob.setCodi(codi);
			ob.setEstat(estat);
			Dictamen dictamen = ob.toDbObject((tipuDictamen!=null ? this.tipuDictamenEJB.getReference(tipuDictamen.getId()) : null), 
											  (ens!=null ? this.ensEJB.getReference(ens.getId()) : null), 
											  (lopd!=null ? this.lopdEJB.getReference(lopd.getId()) : null), 
											  (normativaAprovacio!=null ? this.normativaEJB.getReference(normativaAprovacio.getId()) : null), 
											  (serieDocumental!=null ? this.serieEJB.getReference(serieDocumental.getSerieId()) : null), 
											  (tipuAcces!=null ? this.tipuAccesEJB.getReference(tipuAcces.getId()) : null));
			
			return new DictamenObject(this.dictamenEJB.create(dictamen));
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "create");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "create");
		}
		
		
	
	}
	
	
	@Transactional
	public DictamenObject update(DictamenObject objeto) throws I18NException {
		
		try {
			Dictamen dictamen = objeto.toDbObject((objeto.getTipusdictamen()!=null ? this.tipuDictamenEJB.getReference(objeto.getTipusdictamen().getId()) : null), 
					  (objeto.getEns()!=null ? this.ensEJB.getReference(objeto.getEns().getId()) : null), 
					  (objeto.getLopd()!=null ? this.lopdEJB.getReference(objeto.getLopd().getId()) : null), 
					  (objeto.getNormativaAprovacio()!=null ? this.normativaEJB.getReference(objeto.getNormativaAprovacio().getId()) : null), 
					  (objeto.getSerieDocumental()!=null ? this.serieEJB.getReference(objeto.getSerieDocumental().getSerieId()) : null), 
					  (objeto.getTipusAcces()!=null ? this.tipuAccesEJB.getReference(objeto.getTipusAcces().getId()) : null));
			return new DictamenObject(this.dictamenEJB.update(dictamen));
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "update");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "update");
		}
		
		
	}	
	
	@Transactional
	public void deleteDictamen(Long idDictamen) throws I18NException{
		try {
			this.dictamenEJB.delete(idDictamen);
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "deleteDictamen");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "deleteDictamen");
		}
	}

	
	
}

package es.caib.archium.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.DictamenEJB;
import es.caib.archium.ejb.EnsEJB;
import es.caib.archium.ejb.LopdEJB;
import es.caib.archium.ejb.NormativaEJB;
import es.caib.archium.ejb.SerieEJB;
import es.caib.archium.ejb.TipuAccesEJB;
import es.caib.archium.ejb.TipuDictamenEJB;
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
import es.caib.archium.persistence.model.Dictamen;
import es.caib.archium.persistence.model.Ens;
import es.caib.archium.persistence.model.Lopd;
import es.caib.archium.persistence.model.Normativa;
import es.caib.archium.persistence.model.Seriedocumental;
import es.caib.archium.persistence.model.Tipusacce;
import es.caib.archium.persistence.model.Tipusdictamen;

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
		List<DictamenObject> res = new ArrayList<DictamenObject>();
		List<Dictamen> list = dictamenEJB.getBySerieId(serie.getSerieId());
		
		for(Dictamen i : list) {
			res.add(new DictamenObject(i));
		}
		
		return res;
	}
	
	public List<DictamenObject> findAll() throws I18NException{	
		//System.out.println("SErvice del front find all");
		List<DictamenObject> listaDictamen = new ArrayList<DictamenObject>();
		
		List<Dictamen> res= dictamenEJB.findAll();
		
		for(Dictamen i : res)
		{				
			listaDictamen.add(new DictamenObject(i));
		}
		
		return listaDictamen;
	}
	
	public List<SerieDocumentalObject> findAllDocumental() throws I18NException{	
		
		List<SerieDocumentalObject> listaSeries = new ArrayList<SerieDocumentalObject>();
		
		List<Seriedocumental> res= serieEJB.findAll();
		
		for(Seriedocumental i : res)
		{				
			listaSeries.add(new SerieDocumentalObject(i));
		}
		
		return listaSeries;
	}
	
	public List<TipuDictamenObject> findAllTipuDictamen() throws I18NException{	
		
		List<TipuDictamenObject> listaTipuDictamen = new ArrayList<TipuDictamenObject>();
		
		List<Tipusdictamen> res= tipuDictamenEJB.findAll();
		
		for(Tipusdictamen i : res)
		{				
			listaTipuDictamen.add(new TipuDictamenObject(i));
		}
		
		return listaTipuDictamen;
	}

	public List<TipuAccesObject> findAllTipuAcces() throws I18NException{	
		
		List<TipuAccesObject> listaTipuAcces = new ArrayList<TipuAccesObject>();
		
		List<Tipusacce> res= tipuAccesEJB.findAll();
		
		for(Tipusacce i : res)
		{				
			listaTipuAcces.add(new TipuAccesObject(i));
		}
		
		return listaTipuAcces;
	}
	
	public List<EnsObject> findAllEns() throws I18NException{	
		
		List<EnsObject> listaEns = new ArrayList<EnsObject>();
		
		List<Ens> res= ensEJB.findAll();
		
		for(Ens i : res)
		{				
			listaEns.add(new EnsObject(i));
		}
		
		return listaEns;
	}
	
	public List<LopdObject> findAllLopd() throws I18NException{	
		
		List<LopdObject> listaLopd = new ArrayList<LopdObject>();
		
		List<Lopd> res= lopdEJB.findAll();
		
		for(Lopd i : res)
		{				
			listaLopd.add(new LopdObject(i));
		}
		
		return listaLopd;
	}
	
	public List<NormativaAprobacioObject> findAllNormativaAprovacio() throws I18NException{	
		
		List<NormativaAprobacioObject> listaNormativa = new ArrayList<NormativaAprobacioObject>();
		
		List<Normativa> res= normativaEJB.findAll();
		
		for(Normativa i : res)
		{				
			listaNormativa.add(new NormativaAprobacioObject(i));
		}
		
		return listaNormativa;
	}
	
	
	@Transactional
	public DictamenObject create(SerieDocumentalObject serieDocumental, TipuDictamenObject tipuDictamen, String accioDictaminada, String termini, Date inici, String destinatariRestringits, Date fin, 
								TipuAccesObject tipuAcces, EnsObject ens, LopdObject lopd, String condicioReutilizacio, Boolean serieEsencial, NormativaAprobacioObject normativaAprovacio, Date aprovacio,
								String codi, String estat) throws I18NException {	
		
		DictamenObject ob = new DictamenObject();
		ob.setAccioDictaminada(accioDictaminada);
		ob.setTermini(termini);
		ob.setInici(inici);
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
	
	}
	
	
	@Transactional
	public DictamenObject update(DictamenObject objeto) throws I18NException {
		
		Dictamen dictamen = objeto.toDbObject((objeto.getTipusdictamen()!=null ? this.tipuDictamenEJB.getReference(objeto.getTipusdictamen().getId()) : null), 
				  (objeto.getEns()!=null ? this.ensEJB.getReference(objeto.getEns().getId()) : null), 
				  (objeto.getLopd()!=null ? this.lopdEJB.getReference(objeto.getLopd().getId()) : null), 
				  (objeto.getNormativaAprovacio()!=null ? this.normativaEJB.getReference(objeto.getNormativaAprovacio().getId()) : null), 
				  (objeto.getSerieDocumental()!=null ? this.serieEJB.getReference(objeto.getSerieDocumental().getSerieId()) : null), 
				  (objeto.getTipusAcces()!=null ? this.tipuAccesEJB.getReference(objeto.getTipusAcces().getId()) : null));
		return new DictamenObject(this.dictamenEJB.update(dictamen));
	}	
	
}

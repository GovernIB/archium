package es.caib.archium.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.ButlletiEJB;
import es.caib.archium.ejb.CategoriaNtiEJB;
import es.caib.archium.ejb.CausaLimitacioEJB;
import es.caib.archium.ejb.FaseArxiuEJB;
import es.caib.archium.ejb.NormativaEJB;
import es.caib.archium.ejb.QuadreTipusDocumentalEJB;
import es.caib.archium.ejb.TipusContacteEJB;
import es.caib.archium.ejb.TipusDocumentalEJB;
import es.caib.archium.ejb.TipusNormativaEJB;
import es.caib.archium.ejb.TipusNtiEJB;
import es.caib.archium.ejb.TipusPublicEJB;
import es.caib.archium.ejb.TipusValorEJB;
import es.caib.archium.ejb.ValorSecundariEJB;
import es.caib.archium.ejb.service.AplicacioService;
import es.caib.archium.ejb.service.ButlletiService;
import es.caib.archium.ejb.service.CategoriaNtiService;
import es.caib.archium.ejb.service.CausaLimitacioService;
import es.caib.archium.ejb.service.EnsService;
import es.caib.archium.ejb.service.FamiliaProcedimentService;
import es.caib.archium.ejb.service.FaseArxiuService;
import es.caib.archium.ejb.service.FormaIniciService;
import es.caib.archium.ejb.service.LopdService;
import es.caib.archium.ejb.service.MateriaService;
import es.caib.archium.ejb.service.NivellElectronicService;
import es.caib.archium.ejb.service.NormativaService;
import es.caib.archium.ejb.service.QuadreTipusDocumentalService;
import es.caib.archium.ejb.service.SerieArgenService;
import es.caib.archium.ejb.service.SilenciService;
import es.caib.archium.ejb.service.TipuAccesService;
import es.caib.archium.ejb.service.TipuDictamenService;
import es.caib.archium.ejb.service.TipusSerieService;
import es.caib.archium.ejb.service.TipusContacteService;
import es.caib.archium.ejb.service.TipusDocumentalService;
import es.caib.archium.ejb.service.TipusNormativaService;
import es.caib.archium.ejb.service.TipusNtiService;
import es.caib.archium.ejb.service.TipusPublicService;
import es.caib.archium.ejb.service.TipusValorService;
import es.caib.archium.ejb.service.ValorSecundariService;
import es.caib.archium.ejb.AplicacioEJB;
import es.caib.archium.ejb.EnsEJB;
import es.caib.archium.ejb.FamiliaProcedimentEJB;
import es.caib.archium.ejb.FormaIniciEJB;
import es.caib.archium.ejb.LopdEJB;
import es.caib.archium.ejb.MateriaEJB;
import es.caib.archium.ejb.NivellElectronicEJB;
import es.caib.archium.ejb.SerieArgenEJB;
import es.caib.archium.ejb.SilenciEJB;
import es.caib.archium.ejb.TipuAccesEJB;
import es.caib.archium.ejb.TipuDictamenEJB;
import es.caib.archium.ejb.TipusSerieEJB;
import es.caib.archium.objects.AplicacionObject;
import es.caib.archium.objects.TauleMestreObject;
import es.caib.archium.persistence.model.Aplicacio;
import es.caib.archium.persistence.model.AplicacioSerie;
import es.caib.archium.persistence.model.Butlleti;
import es.caib.archium.persistence.model.Categorianti;
import es.caib.archium.persistence.model.Causalimitacio;
import es.caib.archium.persistence.model.Ens;
import es.caib.archium.persistence.model.Familiaprocediment;
import es.caib.archium.persistence.model.Fasearxiu;
import es.caib.archium.persistence.model.Formainici;
import es.caib.archium.persistence.model.Lopd;
import es.caib.archium.persistence.model.Materia;
import es.caib.archium.persistence.model.Nivellelectronic;
import es.caib.archium.persistence.model.Normativa;
import es.caib.archium.persistence.model.Quadretipusdocumental;
import es.caib.archium.persistence.model.Serieargen;
import es.caib.archium.persistence.model.Silenci;
import es.caib.archium.persistence.model.Tipusacce;
import es.caib.archium.persistence.model.Tipuscontacte;
import es.caib.archium.persistence.model.Tipusdictamen;
import es.caib.archium.persistence.model.Tipusdocumental;
import es.caib.archium.persistence.model.Tipusnormativa;
import es.caib.archium.persistence.model.Tipusnti;
import es.caib.archium.persistence.model.Tipuspublic;
import es.caib.archium.persistence.model.Tipusserie;
import es.caib.archium.persistence.model.Tipusvalor;
import es.caib.archium.persistence.model.Valorsecundari;


@Named("TablaMaestraServices")
@ApplicationScoped
public class TablaMaestraFrontService {
	
	@Inject
	SerieArgenService           serieArgenEJB;
	@Inject
	CategoriaNtiService         categoriantiEJB;
	@Inject
	TipusNtiService      		tipusntiEJB;
	@Inject
	QuadreTipusDocumentalService quadretipusdocumentalEJB;
	@Inject
	TipusDocumentalService      tipusdocumentalEJB;
	@Inject
	TipusSerieService          tipusSerieEJB;
	@Inject
	EnsService            		ensEJB;
	@Inject
	LopdService           		lopdEJB;
	@Inject
	AplicacioService            aplicacioEJB;
	@Inject
	TipuAccesService          tipuAccesEJB;
	@Inject
	CausaLimitacioService       causalimitacioEJB;
	@Inject
	TipusValorService           tipusvalorEJB;
	@Inject
	ValorSecundariService       valorsecundariEJB;
	@Inject
	FaseArxiuService            fasearxiuEJB;
	@Inject
	TipuDictamenService       tipuDictamenEJB;
	@Inject
	FormaIniciService           formaIniciEJB;
	@Inject
	MateriaService        		materiaEJB;
	@Inject
	SilenciService        		silenciEJB;
	@Inject
	NivellElectronicService     nivellelectronicEJB;
	@Inject
	FamiliaProcedimentService   familiaprocedimentEJB;
	@Inject
	TipusContacteService        tipuscontacteEJB;
	@Inject
	TipusPublicService         	tipuspublicEJB;
	@Inject
	NormativaService         	normativaEJB;
	@Inject
	TipusNormativaService       tipusnormativaEJB;
	@Inject
	ButlletiService         	butlletiEJB;
	
	public List<TauleMestreObject> findAll(String option) throws I18NException{		
		
		try {
			List<TauleMestreObject> lista = new ArrayList<TauleMestreObject>();
	        if ( option.equals("Serieargen")){
	        	List<Serieargen> res= serieArgenEJB.findAll();
	    		for(Serieargen i : res)
	    		{				
	    			TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	    		}
	    		return lista;    
	        }
	        if ( option.equals("Categorianti")){
	        	List<Categorianti> res= categoriantiEJB.findAll();
	            for(Categorianti i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista; 
	        }
	        if ( option.equals("Tipusnti")){
	        	List<Tipusnti> res= tipusntiEJB.findAll();
	            for(Tipusnti i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista; 
	        }
	        if ( option.equals("Quadretipusdocumental")){
	        	List<Quadretipusdocumental> res= quadretipusdocumentalEJB.findAll();
	            for(Quadretipusdocumental i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;   
	        }
	        if ( option.equals("Tipusdocumental")){
	        	List<Tipusdocumental> res= tipusdocumentalEJB.findAll();
	            for(Tipusdocumental i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Tipusserie")){
	        	List<Tipusserie> res= tipusSerieEJB.findAll();
	            for(Tipusserie i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Ens")){
	        	List<Ens> res= ensEJB.findAll();
	            for(Ens i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Lopd")){
	        	List<Lopd> res= lopdEJB.findAll();
	            for(Lopd i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Aplicacio")){
	        	List<Aplicacio> res= aplicacioEJB.findAll();
	            for(Aplicacio i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Tipusacces")){
	        	List<Tipusacce> res= tipuAccesEJB.findAll();
	            for(Tipusacce i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Causalimitacio")){
	        	List<Causalimitacio> res= causalimitacioEJB.findAll();
	            for(Causalimitacio i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Tipusvalor")){
	        	List<Tipusvalor> res= tipusvalorEJB.findAll();
	            for(Tipusvalor i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Valorsecundari")){
	        	List<Valorsecundari> res= valorsecundariEJB.findAll();
	            for(Valorsecundari i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Fasearxiu")){
	        	List<Fasearxiu> res= fasearxiuEJB.findAll();
	            for(Fasearxiu i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Tipusdictament")){
	        	List<Tipusdictamen> res= tipuDictamenEJB.findAll();
	            for(Tipusdictamen i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option .equals("Formainici")){
	        	List<Formainici> res= formaIniciEJB.findAll();
	            for(Formainici i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Materia")){
	        	List<Materia> res= materiaEJB.findAll();
	            for(Materia i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Silenci")){
	        	List<Silenci> res= silenciEJB.findAll();
	            for(Silenci i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Nivellelectronic")){
	        	List<Nivellelectronic> res= nivellelectronicEJB.findAll();
	            for(Nivellelectronic i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Familiaprocediment")){
	        	List<Familiaprocediment> res= familiaprocedimentEJB.findAll();
	            for(Familiaprocediment i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Tipuscontacte")){
	        	List<Tipuscontacte> res= tipuscontacteEJB.findAll();
	            for(Tipuscontacte i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Tipuspublic")){
	        	List<Tipuspublic> res= tipuspublicEJB.findAll();
	            for(Tipuspublic i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }  
	        if ( option.equals("Normativa")){
	        	List<Normativa> res= normativaEJB.findAll();
	            for(Normativa i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Tipusnormativa")){
	        	List<Tipusnormativa> res= tipusnormativaEJB.findAll();    
	        	for(Tipusnormativa i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
	        if ( option.equals("Butlleti")){
	        	List<Butlleti> res= butlletiEJB.findAll();
	            for(Butlleti i : res)
	            {                       
	            	TauleMestreObject aux = new TauleMestreObject();
	    			aux.setNom(i.getNom());
	    			lista.add(aux);
	            }
	            return lista;
	        }
			return lista; 	
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAll");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAll");
		}
		
			
	}
}


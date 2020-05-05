package es.caib.archium.services;

import java.util.ArrayList;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.ButlletiEJB;
import es.caib.archium.ejb.NormativaEJB;
import es.caib.archium.ejb.TipusnormativaEJB;
import es.caib.archium.ejb.tablasMaestras.AplicacioEJB;
import es.caib.archium.ejb.tablasMaestras.CategoriantiEJB;
import es.caib.archium.ejb.tablasMaestras.CausalimitacioEJB;
import es.caib.archium.ejb.tablasMaestras.EnsEJB;
import es.caib.archium.ejb.tablasMaestras.FamiliaprocedimentEJB;
import es.caib.archium.ejb.tablasMaestras.FasearxiuEJB;
import es.caib.archium.ejb.tablasMaestras.FormainiciEJB;
import es.caib.archium.ejb.tablasMaestras.LopdEJB;
import es.caib.archium.ejb.tablasMaestras.MateriaEJB;
import es.caib.archium.ejb.tablasMaestras.NivellelectronicEJB;
import es.caib.archium.ejb.tablasMaestras.QuadretipusdocumentalEJB;
import es.caib.archium.ejb.tablasMaestras.SerieargenEJB;
import es.caib.archium.ejb.tablasMaestras.SilenciEJB;
import es.caib.archium.ejb.tablasMaestras.TipusaccesEJB;
import es.caib.archium.ejb.tablasMaestras.TipuscontacteEJB;
import es.caib.archium.ejb.tablasMaestras.TipusdictamentEJB;
import es.caib.archium.ejb.tablasMaestras.TipusdocumentalEJB;
import es.caib.archium.ejb.tablasMaestras.TipusntiEJB;
import es.caib.archium.ejb.tablasMaestras.TipuspublicEJB;
import es.caib.archium.ejb.tablasMaestras.TipusserieEJB;
import es.caib.archium.ejb.tablasMaestras.TipusvalorEJB;
import es.caib.archium.ejb.tablasMaestras.ValorsecundariEJB;
import es.caib.archium.objects.TauleMestreObject;
import es.caib.archium.persistence.model.Aplicacio;
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
public class TablaMaestraServices {
	
	@Inject
	SerieargenEJB           serieargenEJB;
	@Inject
	CategoriantiEJB         categoriantiEJB;
	@Inject
	TipusntiEJB       		tipusntiEJB;
	@Inject
	QuadretipusdocumentalEJB quadretipusdocumentalEJB;
	@Inject
	TipusdocumentalEJB      tipusdocumentalEJB;
	@Inject
	TipusserieEJB           tipusserieEJB;
	@Inject
	EnsEJB            		ensEJB;
	@Inject
	LopdEJB           		lopdEJB;
	@Inject
	AplicacioEJB            aplicacioEJB;
	@Inject
	TipusaccesEJB           tipusaccesEJB;
	@Inject
	CausalimitacioEJB       causalimitacioEJB;
	@Inject
	TipusvalorEJB           tipusvalorEJB;
	@Inject
	ValorsecundariEJB       valorsecundariEJB;
	@Inject
	FasearxiuEJB            fasearxiuEJB;
	@Inject
	TipusdictamentEJB       tipusdictamentEJB;
	@Inject
	FormainiciEJB           formainiciEJB;
	@Inject
	MateriaEJB        		materiaEJB;
	@Inject
	SilenciEJB        		silenciEJB;
	@Inject
	NivellelectronicEJB     nivellelectronicEJB;
	@Inject
	FamiliaprocedimentEJB   familiaprocedimentEJB;
	@Inject
	TipuscontacteEJB        tipuscontacteEJB;
	@Inject
	TipuspublicEJB         	tipuspublicEJB;
	@Inject
	NormativaEJB         	normativaEJB;
	@Inject
	TipusnormativaEJB       tipusnormativaEJB;
	@Inject
	ButlletiEJB         	butlletiEJB;
	
	//@Inject
	//QuadreclassificacioEJB quadreclassificacioEjb;
	
	//@Inject
	//TipusserieEJB tipusserieEjb;
	
	public List<TauleMestreObject> findAll(String option) throws I18NException{		
		
		List<TauleMestreObject> lista = new ArrayList<TauleMestreObject>();
        if ( option.equals("Serieargen")){
        	List<Serieargen> res= serieargenEJB.findAll();
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
        	List<Tipusserie> res= tipusserieEJB.findAll();
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
        	List<Tipusacce> res= tipusaccesEJB.findAll();
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
        	List<Tipusdictamen> res= tipusdictamentEJB.findAll();
            for(Tipusdictamen i : res)
            {                       
            	TauleMestreObject aux = new TauleMestreObject();
    			aux.setNom(i.getNom());
    			lista.add(aux);
            }
            return lista;
        }
        if ( option .equals("Formainici")){
        	List<Formainici> res= formainiciEJB.findAll();
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
	}
}


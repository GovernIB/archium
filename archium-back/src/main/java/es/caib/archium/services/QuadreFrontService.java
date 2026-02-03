package es.caib.archium.services;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.persistence.model.Quadreclassificacio;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Named
@ApplicationScoped
public class QuadreFrontService {
	
	@Inject
	QuadreClassificacioService quadreEjb;

	// ACH-006 Que per defecte s'obri el quadre de classificació per defecte
	public Long getQuadreClassificacioPerDefecte(List<QuadreObject> quadresSeleccionats) {
		Long idQuadreDefecte = quadreEjb.getQuadreClassificacioPerDefecte();
		Long idQuadreSeleccionat = null;
		boolean trobat = false;
		if (quadresSeleccionats != null) {
			int i = 0;
			while (i < quadresSeleccionats.size() && !trobat) {
				if (idQuadreDefecte.equals(quadresSeleccionats.get(i).getId())) {
					trobat = true;
				}
				i++;
			}

			if (trobat) {
				idQuadreSeleccionat = idQuadreDefecte;
			} else {
				idQuadreSeleccionat = quadresSeleccionats.get(0).getId();
			}
		}
		return idQuadreSeleccionat;
	}
	
	public Boolean checkNameUnique(Long id, String nombre) throws I18NException {
		
		for(QuadreObject i:  this.findAll()){
			
			if (id==null) {
				if (i.getNom().equalsIgnoreCase(nombre.trim())) {
	    			return false;
	    		}    
			} else {
				if (i.getNom().equalsIgnoreCase(nombre.trim()) && !i.getId().equals(id)) {
	    			return false;
	    		}    
			}  	
			
		}
		
		return true;
		
	}
	
	public QuadreObject getQuadreById(Long id) throws I18NException{
		try {
			Quadreclassificacio res= quadreEjb.findById(id);
			
			if (res != null) {
				return new QuadreObject(res);
			}
			
			return null;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "getQuadreById");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "getQuadreById");
		}
	}

	public QuadreObject findQuadreByNom(String nomQuadre) throws I18NException {
		try {
			Map<String, Object> filtres = new HashMap<>();
			filtres.put("nom", nomQuadre);
			List<Quadreclassificacio> quadres = quadreEjb.findFiltered(filtres);

			QuadreObject quadreObject = null;
			if (quadres != null && quadres.size() > 0) {
				quadreObject = new QuadreObject(quadres.get(0));
			}
			return quadreObject;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findQuadreByNom");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findQuadreByNom");
		}

	}
	
	public List<QuadreObject> findAll() throws I18NException{
				
		try {
			List<QuadreObject> listaQuadres = new ArrayList<QuadreObject>();
			
			List<Quadreclassificacio> res= quadreEjb.findAll();
			
			for(Quadreclassificacio q : res)
			{				
				listaQuadres.add(new QuadreObject(q));
			}
			
			return listaQuadres;
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "findAll");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "findAll");
		}

		
	}

	@Transactional
    public QuadreObject save(QuadreObject object ) throws I18NException {    

		try {
			
			QuadreObject nuevo = new QuadreObject();
			nuevo.setNom(object.getNom().trim());
			nuevo.setNomCas(object.getNomCas() != null? object.getNomCas(): null);
			nuevo.setEstat(object.getEstat());
			nuevo.setInici(new Date());
			nuevo.setModificacio(new Date());
			nuevo.setVersio("1.0");
			nuevo.setFi(null);
			
			Quadreclassificacio nuevoBD = new Quadreclassificacio();
			nuevoBD.setNom(nuevo.getNom());
			nuevoBD.setNomcas(nuevo.getNomCas());
			nuevoBD.setEstat(nuevo.getEstat());
			nuevoBD.setInici(nuevo.getInici());
			nuevoBD.setModificacio(nuevo.getModificacio());
			nuevoBD.setVersio(nuevo.getVersio());
			nuevoBD.setFi(nuevo.getFi());
				
			return new QuadreObject(quadreEjb.create(nuevoBD));
		} catch(NullPointerException e) {
			throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "save");
		} catch(Exception e) {
			throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "save");
		}

 
	 }
	 
	 @Transactional
	 public QuadreObject update(QuadreObject object) throws I18NException {
	    
			try {
				Quadreclassificacio baseDato = this.quadreEjb.getReference(object.getId());
	        	baseDato.setNom(object.getNom());
	        	baseDato.setNomcas(object.getNomCas());
	        	baseDato.setEstat(object.getEstat());
	        	baseDato.setFi(object.getFi());
	        	baseDato.setVersio(object.getVersio());
	        	baseDato.setModificacio(new Date());
	        	return new QuadreObject(quadreEjb.update(baseDato));
			} catch(NullPointerException e) {
				throw new I18NException("excepcion.general.NullPointerException", this.getClass().getSimpleName(), "update");
			} catch(Exception e) {
				throw new I18NException("excepcion.general.Exception", this.getClass().getSimpleName(), "update");
			}
	    }
}

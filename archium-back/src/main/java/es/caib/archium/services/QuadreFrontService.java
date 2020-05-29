package es.caib.archium.services;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.enterprise.context.ApplicationScoped;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.inject.Inject;
import javax.inject.Named;
import javax.transaction.Transactional;

import es.caib.archium.commons.i18n.I18NException;
import es.caib.archium.ejb.QuadreClassificacioEJB;
import es.caib.archium.ejb.service.QuadreClassificacioService;
import es.caib.archium.objects.FuncioObject;
import es.caib.archium.objects.QuadreObject;
import es.caib.archium.persistence.model.Funcio;
import es.caib.archium.persistence.model.Quadreclassificacio;

@Named
@ApplicationScoped
public class QuadreFrontService {
	
	@Inject
	QuadreClassificacioService quadreEjb;
	
	public QuadreObject getQuadreById(long id) throws I18NException{
				
		Quadreclassificacio res= quadreEjb.findById(id);
		
		if (res != null) {
			return new QuadreObject(res);
		}
		
		return null;
	}
	
	public List<QuadreObject> findAll() throws I18NException{
		
		List<QuadreObject> listaQuadres = new ArrayList<QuadreObject>();
		
		List<Quadreclassificacio> res= quadreEjb.findAll();
		
		for(Quadreclassificacio q : res)
		{				
			listaQuadres.add(new QuadreObject(q));
		}
		
		return listaQuadres;
	}

	@Transactional
    public boolean save(QuadreObject object ) {    

    		QuadreObject nuevo = new QuadreObject();
			nuevo.setNom(object.getNom().trim());
			nuevo.setNomCas(object.getNomCas() != null? object.getNomCas(): null);
			nuevo.setEstat(object.getEstat());
			nuevo.setInici(new Date());
			nuevo.setModificacio(new Date());
			nuevo.setVersio("1.0");
			nuevo.setFi(null);
			//listaCuadros.add(services.);
			
			try {
				Quadreclassificacio nuevoBD = new Quadreclassificacio();
				nuevoBD.setNom(nuevo.getNom());
				nuevoBD.setNomcas(nuevo.getNomCas());
				if ((nuevoBD.getNom() == null) || (nuevoBD.getNom() == "")){
					/*FacesMessage message = new FacesMessage();
		    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		    		message.setSummary("Error Salvants Dades");
		    		message.setDetail("Error Salvants Dades");
		    		FacesContext.getCurrentInstance().addMessage(null, message);*/
					return false;
				}
				nuevoBD.setEstat(nuevo.getEstat());
				nuevoBD.setInici(nuevo.getInici());
				nuevoBD.setModificacio(nuevo.getModificacio());
				nuevoBD.setVersio(nuevo.getVersio());
				nuevoBD.setFi(nuevo.getFi());
				//listaCuadros.add(nuevo);
				quadreEjb.create(nuevoBD);	
				
				//FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dades Salvats"));
				return true;
			} catch (I18NException e) {
				// TODO Auto-generated catch block
				/*FacesMessage message = new FacesMessage();
	    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
	    		message.setSummary("Error Salvants Dades");
	    		message.setDetail("Error Salvants Dades");
	    		FacesContext.getCurrentInstance().addMessage(null, message);
				e.printStackTrace();*/
				return false;
			} catch (Exception eee) {
				/*FacesMessage message = new FacesMessage();
				message.setSeverity(FacesMessage.SEVERITY_ERROR);
				message.setSummary("Error Salvants Dades");
				message.setDetail("Error Salvants Dades");
				FacesContext.getCurrentInstance().addMessage(null, message);*/
				return false;
				//eee.printStackTrace();
			}

				//FacesContext ctxt = FacesContext.getCurrentInstance(); //get your hands on the current request context
				//ctxt.getPartialViewContext().getRenderIds().add("panel");

	   }
	 
	 @Transactional
	 public QuadreObject update(QuadreObject object) {
	    	try {
	    		//QuadreObject update =new QuadreObject();
	        	/*
	    		int pos = 0;
	        	for (QuadreObject i: lista) {
	        		if (i.equals(object)) {
	        			pos = lista.indexOf(i);
	        			update =  i;
	        		}	        		
	        	}
	        	
	        	*/
	    		Quadreclassificacio baseDato = this.quadreEjb.getReference(object.getId());
	        	baseDato.setNom(object.getNom());
	        	baseDato.setNomcas(object.getNomCas());
	        	baseDato.setEstat(object.getEstat());
	        	baseDato.setFi(object.getFi());
	        	baseDato.setVersio(object.getVersio());
	        	baseDato.setModificacio(new Date());
	        	/*FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dades Actualitzades"));*/
	        	return new QuadreObject(quadreEjb.update(baseDato));
	
		    }
	    	catch (Exception e) {
	 			// TODO Auto-generated catch block
	    		/*FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error en Actualitzad Quadre"));
	    		FacesMessage msg = new FacesMessage("Error en Actualitzad Quadre");
	 			e.printStackTrace();*/
	 			return null;
	 		}	    	
	    }
}

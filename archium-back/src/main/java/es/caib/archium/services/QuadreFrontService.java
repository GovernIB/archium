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
	
	/*private QuadreObject parseDbO2bject(Quadreclassificacio dbQuadre) {
				
		QuadreObject quadre = new QuadreObject();
		quadre.setEstat(dbQuadre.getEstat());
		quadre.setFi(dbQuadre.getFi());
		quadre.setId(dbQuadre.getId());
		quadre.setInici(dbQuadre.getInici());
		quadre.setModificacio(dbQuadre.getModificacio());
		quadre.setNom(dbQuadre.getNom());
		quadre.setNomCas(dbQuadre.getNomcas());
		quadre.setVersio(dbQuadre.getVersio());				
		
		return quadre;
	}*/
	
	@Transactional
    public boolean save(List<QuadreObject> listaCuadros, QuadreObject object ) {    
	    	boolean b = true;
	    	for(QuadreObject i:  listaCuadros)
			{
	    		if (i.getNom().equalsIgnoreCase(object.getNom().trim())) {
	    			b = false;
	    		}    		
			}
	    	if (b) {
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
					//System.out.println("nombre-"+nuevo.getNom() );
					if ((nuevoBD.getNom() == null) || (nuevoBD.getNom() == "")){
						FacesMessage message = new FacesMessage();
			    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
			    		message.setSummary("Error Salvants Dades");
			    		message.setDetail("Error Salvants Dades");
			    		FacesContext.getCurrentInstance().addMessage(null, message);
						//System.out.println("nombre nulo");
					}
					nuevoBD.setEstat(nuevo.getEstat());
					nuevoBD.setInici(nuevo.getInici());
					nuevoBD.setModificacio(nuevo.getModificacio());
					nuevoBD.setVersio(nuevo.getVersio());
					nuevoBD.setFi(nuevo.getFi());
					listaCuadros.add(nuevo);
					quadreEjb.create(nuevoBD);	
					
					FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dades Salvats"));
					return true;
				} catch (I18NException e) {
					// TODO Auto-generated catch block
					FacesMessage message = new FacesMessage();
		    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
		    		message.setSummary("Error Salvants Dades");
		    		message.setDetail("Error Salvants Dades");
		    		FacesContext.getCurrentInstance().addMessage(null, message);
					e.printStackTrace();
				} catch (Exception eee) {
					FacesMessage message = new FacesMessage();
					message.setSeverity(FacesMessage.SEVERITY_ERROR);
					message.setSummary("Error Salvants Dades");
					message.setDetail("Error Salvants Dades");
					FacesContext.getCurrentInstance().addMessage(null, message);
					//eee.printStackTrace();
				}

				FacesContext ctxt = FacesContext.getCurrentInstance(); //get your hands on the current request context
				ctxt.getPartialViewContext().getRenderIds().add("panel");
	    	}
	    	else {
	    		FacesContext.getCurrentInstance().validationFailed();
	    		FacesMessage message = new FacesMessage();
	    		message.setSeverity(FacesMessage.SEVERITY_ERROR);
	    		message.setSummary("Error Salvants Dades: Nom repetit");
	    		message.setDetail("Error Salvants Dades: Nom repetit");
	    		FacesContext.getCurrentInstance().addMessage(null, message);
	    		return false;
	    	}
			return false;
	    }
	 
	 @Transactional
	 public QuadreObject update(List<QuadreObject> lista,  QuadreObject object) {
	    	//System.out.println("update"+ object.toString());
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
	        	
	        	System.out.println("posicion "+pos);	
	        	*/
	    		Quadreclassificacio baseDato = new Quadreclassificacio();
	        	//if (update.getNom().equals(object.getNom()))
	        		baseDato.setNom(object.getNom());
	        	//if (update.getNomCast().equals(object.getNomCast()))
	        		baseDato.setNomcas(object.getNomCas());
	        	//if (update.getEstat().equals(object.getEstat()))
	        		baseDato.setEstat(object.getEstat());
	        	//if (update.getInici().equals(object.getInici()))
	        		baseDato.setInici(object.getInici());
	        	//if (update.getFi().equals(object.getFi()))
	        		baseDato.setFi(object.getFi());
	        	//if (update.getVersio().equals(object.getVersio()))
	        		baseDato.setVersio(object.getVersio());
	        	baseDato.setModificacio(new Date());
	        	baseDato.setId(object.getId());
	        	//System.out.println("A persistis los datos en la base de datos.");
	        	//System.out.println(baseDato.toString());
	        	FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Dades Actualitzades"));
	        	return new QuadreObject(quadreEjb.update(baseDato));
	
		    }
	    	catch (Exception e) {
	 			// TODO Auto-generated catch block
	    		FacesContext.getCurrentInstance().addMessage(null, new FacesMessage("Error en Actualitzad Quadre"));
	    		FacesMessage msg = new FacesMessage("Error en Actualitzad Quadre");
	 			e.printStackTrace();
	 			return null;
	 		}	    	
	    }
}

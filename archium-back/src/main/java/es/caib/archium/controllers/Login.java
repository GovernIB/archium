package es.caib.archium.controllers;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;
import javax.faces.context.ExternalContext;
import javax.faces.view.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.security.enterprise.SecurityContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import java.io.IOException;
import java.io.Serializable;

//import es.caib.archium.objects.CuadroClasificacion;
//import es.caib.archium.services.Login implements Serializable, Serializable;


@Named
@ViewScoped
public class Login implements Serializable{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -4657002560838501675L;
	private String usuario;
    private String password;
    
    @Inject 
    private ExternalContext externalContext;
    
    @PostConstruct
    public void init() {
        // inicializar valores para probar.        
    }
 
    public String logOff() {
        //HttpSession session = request.getSession();
    	HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
    	String path = "";
    	try {
			request.logout();
		} catch (ServletException e) {
			
		} finally {
			
			if(request.getServletPath().contains("error")) {
	    		path = "/views/quadre.xhtml?faces-redirect=true";
	    	} else {
	    		path = request.getServletPath() + "?faces-redirect=true";
	    	}
			
		}
    	
    	
    	
    	return path;
    	
    }
    public String getUsuario() {
		return usuario;
	}

	public void setUsuario(String usuario) {
		this.usuario = usuario;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}


}
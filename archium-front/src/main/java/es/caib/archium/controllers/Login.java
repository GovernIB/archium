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
import javax.servlet.http.HttpSession;
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
    	System.out.println("PST");
        //HttpSession session = request.getSession();
    	HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
    	try {
			request.logout();
		} catch (ServletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    	return "/views/quadre.xhtml?faces-redirect=true";
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
package es.caib.archium.ejb.service;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;
import javax.enterprise.context.RequestScoped;

import javax.inject.Named;

//import es.caib.archium.objects.CuadroClasificacion;
//import es.caib.archium.services.Login;


@Named
@RequestScoped
public class LoginService {
	
	private String usuario;
    private String password;
    
    @PostConstruct
    public void init() {
        // inicializar valores para probar.        
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


    public void abrirModal() {
        Map<String,Object> options = new HashMap<String, Object>();
        options.put("resizable", false);
        options.put("width", 1100);
        options.put("height", 600);
        options.put("contentWidth", "100%");
        options.put("contentHeight", "100%");
       // PrimeFaces.current().dialog().openDynamic("nuevaSerie", options, null);
    }
}
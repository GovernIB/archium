package es.caib.archium.communication.producer;

import es.caib.csgd.apirest.ApiCSGDServices;
import es.caib.csgd.apirest.facade.pojos.cabecera.CabeceraPeticion;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.communication.exception.CSGDException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.enterprise.inject.Produces;
import javax.faces.context.ExternalContext;
import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class CSGDClientProducer {

    protected final Logger log = LoggerFactory.getLogger(CSGDClientProducer.class);

    @Inject
    private ExternalContext externalContext;

    private static final String propertiesRuta = "csgdProperties.properties";


    @Produces
    public ApiCSGDServices csgdClientProducer() throws CSGDException {
        log.info("Se procede a cargar la informacion para la construccion de la cabecera de la peticion");
        HttpServletRequest request = (HttpServletRequest) externalContext.getRequest();
        Properties prop = new Properties();
        try {
            InputStream input = CSGDClientProducer.class.getClassLoader().getResourceAsStream(propertiesRuta);

            prop.load(input);
        } catch (FileNotFoundException e) {
            log.error("El fichero de propiedades que se intenta cargar no se encuentra en su ruta [" + propertiesRuta + "]. Error = " + e);
            throw new CSGDException(Constants.ExceptionConstants.HEADER_ERROR.getValue(), "El fichero de propiedades que se sintenta cargar no se encuentra en su ruta");
        } catch (IOException e) {
            log.error("Se ha producido un error cargando las propiedades para la creacion de la cabecera de la peticion gdib. Error = " + e);
            throw new CSGDException(Constants.ExceptionConstants.HEADER_ERROR.getValue(), "Se ha producido un error cargando las propiedades para la creacion de la cabecera de la peticion gdib");
        }

        String url = prop.getProperty("csgd.url");
        String usuario = request.getUserPrincipal().getName();
        String solicitanteNombre = request.getUserPrincipal().getName();
        //TODO: Como conseguir el documento? que poner en usuario y en solicitante?
//        String solicitanteDocumento = request.getUserPrincipal().
        String solicitanteDocumento = "123456789Z";
        String procedimiento = prop.getProperty("csgd.procedimiento.nombre");
        String organizacion = prop.getProperty("csgd.organizacion");
        String aplicacionCliente = prop.getProperty("csgd.aplicacion.cliente");
        String servicioVersion = prop.getProperty("csgd.servicio.version");
        String user = prop.getProperty("csgd.seguridad.usuario");
        String pass = prop.getProperty("csgd.seguridad.password");


//        CabeceraPeticion cabecera = UtilCabeceras.generarCabeceraMock();
        CabeceraPeticion cabecera = UtilCabeceras.generarCabecera(usuario, solicitanteNombre, solicitanteDocumento,
                procedimiento, organizacion, aplicacionCliente, servicioVersion, user, pass);

        log.info("Se ha generado la cabecera para realizar la petición");

        return new ApiCSGDServices(url, cabecera);
    }
}

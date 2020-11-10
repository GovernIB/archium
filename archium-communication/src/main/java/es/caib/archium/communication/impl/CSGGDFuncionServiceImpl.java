package es.caib.archium.communication.impl;

import es.caib.archium.apirest.ApiArchium;
import es.caib.archium.apirest.csgd.entidades.resultados.CreateFunctionResult;
import es.caib.archium.apirest.facade.pojos.Funcion;
import es.caib.archium.apirest.facade.resultados.ResultadoSimple;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.exception.ExceptionConstants;
import es.caib.archium.communication.iface.CSGDFuncionService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;

//TODO: Crear el nuevo rol
@Stateless
//@RolesAllowed(Constants.ACH_ALFRESCO)
public class CSGGDFuncionServiceImpl implements CSGDFuncionService {

    protected final Logger log = LoggerFactory.getLogger(CSGGDFuncionServiceImpl.class);

    @Inject
    private ApiArchium client;

    @Override
    public void removeFuncion(String funcionId) throws CSGDException {

        try {
            ResultadoSimple rs = client.borrarFuncion(funcionId);
            //FIXME: Puede producirse que se borre en gdib y en bbdd no, hay que controlar de manera diferente si no
            // existe en gdib para que siga el proceso como que sí se hizo
            //TODO: Constante con codigos de resultados
            if (rs.getCodigoResultado() != "200") {
                //TODO: Que hacer si no se borra
                log.error("Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado de la funcion." +
                        " Mensaje: " + rs.getMsjResultado());
                throw new CSGDException(ExceptionConstants.ERROR_RETURNED.getValue(),rs.getCodigoResultado(),rs.getMsjResultado(),"Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado de " +
                        "la funcion. Mensaje: " + rs.getMsjResultado());
            }
        } catch (IOException e) {
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            throw new CSGDException(ExceptionConstants.CLIENT_ERROR.getValue(),"Se ha producido un error desconocido en la llamada en el el cliente",e);
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de eliminación de la funcion: " + e);
            throw new CSGDException(ExceptionConstants.GENERIC_ERROR.getValue(),"Se ha producido un error no controlado en el proceso de eliminación de la funcion",e);
        }
    }

    @Override
    public String synchronizeFunction(Funcion funcion) throws CSGDException {
        try {
            CreateFunctionResult rs = client.crearFuncion(funcion);
            if (rs.getCreateFunctionResult() == null || rs.getCreateFunctionResult().getResult() == null) {
                //TODO: Error genérico
                log.error("Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion de la funcion");
                throw new CSGDException(ExceptionConstants.MALFORMED_RESULT.name(),"Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion de la funcion");
            }

            //TODO: Constante con codigos de resultados
            if (rs.getCreateFunctionResult() != null && rs.getCreateFunctionResult().getResult() != null
                    && rs.getCreateFunctionResult().getResult().getCode() != "200") {
                //TODO: Que hacer si no se crea
                log.error("Se ha devuelto un codigo [" + rs.getCreateFunctionResult().getResult().getCode() + "] en el " +
                        "proceso de sincronizacion de la funcion. Mensaje: " + rs.getCreateFunctionResult().getResult()
                        .getDescription());
                throw new CSGDException(ExceptionConstants.ERROR_RETURNED.getValue(),rs.getCreateFunctionResult().getResult().getCode(),rs.getCreateFunctionResult().getResult().getDescription(),"Se ha devuelto un codigo [" + rs.getCreateFunctionResult().getResult().getCode() + "] " +
                        "en el proceso de sincronizacion de la funcion. Mensaje: " + rs.getCreateFunctionResult().getResult());
            }


            log.debug("funcion creada correctamente. NodeId: " + rs.getCreateFunctionResult().getResParam());
            return rs.getCreateFunctionResult().getResParam();



        } catch (IOException e) {
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            throw new CSGDException(ExceptionConstants.CLIENT_ERROR.getValue(),"Se ha producido un error desconocido en la llamada en el el cliente",e);
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de sincronizacion de la funcion: " + e);
            throw new CSGDException(ExceptionConstants.GENERIC_ERROR.getValue(),"Se ha producido un error no controlado en el proceso de sincronizacion de la funcion",e);
        }
    }


    public ApiArchium getClient() {
        return client;
    }

    public void setClient(ApiArchium client) {
        this.client = client;
    }
}

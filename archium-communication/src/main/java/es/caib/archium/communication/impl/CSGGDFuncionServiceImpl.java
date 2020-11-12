package es.caib.archium.communication.impl;

import es.caib.archium.apirest.ApiArchium;
import es.caib.archium.apirest.csgd.entidades.resultados.CreateFunctionResult;
import es.caib.archium.apirest.facade.pojos.Funcion;
import es.caib.archium.apirest.facade.resultados.ResultadoSimple;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.communication.exception.CSGDException;
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
            if (!rs.getCodigoResultado().equals("COD_000")) {
                // Cuando se produce un error porque el nodo a borrar no existe devuelve el cod_021, por lo que  continuamos
                // con el proceso
                if (rs.getCodigoResultado().equals("COD_021")) {
                    log.info("Se ha recibido el código de error COD_021, el nodo no existe en Alfresco, así que " +
                            "continuamos con el proceso de borrado");
                    return;
                } else {
                    log.error("Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado de la funcion." +
                            " Mensaje: " + rs.getMsjResultado());
                    throw new CSGDException(Constants.ExceptionConstants.ERROR_RETURNED.getValue(), rs.getCodigoResultado(), rs.getMsjResultado(), "Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado de " +
                            "la funcion. Mensaje: " + rs.getMsjResultado());
                }
            }
        } catch (CSGDException e) {
            throw e;
        } catch (IOException e) {
            log.error("Se ha producido un error desconocido en el cliente al realizar la llamada al endpoint: " + e);
            throw new CSGDException(Constants.ExceptionConstants.CLIENT_ERROR.getValue(), "Se ha producido un error desconocido en el cliente al realizar la llamada al endpoint", e);
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de eliminación de la funcion: " + e);
            throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no controlado en el proceso de eliminación de la funcion", e);
        }
    }

    @Override
    public String synchronizeFunction(Funcion funcion, String parentId) throws CSGDException {
        try {
            CreateFunctionResult rs = client.crearFuncion(funcion, parentId);

            if (rs.getCreateFunctionResult() != null && rs.getCreateFunctionResult().getResult() != null
                    && rs.getCreateFunctionResult().getResult().getCode() != "COD_001") {
                log.error("Se ha devuelto un codigo [" + rs.getCreateFunctionResult().getResult().getCode() + "] en el " +
                        "proceso de sincronizacion de la funcion. Mensaje: " + rs.getCreateFunctionResult().getResult()
                        .getDescription());
                throw new CSGDException(Constants.ExceptionConstants.ERROR_RETURNED.getValue(), rs.getCreateFunctionResult().getResult().getCode(), rs.getCreateFunctionResult().getResult().getDescription(), "Se ha devuelto un codigo [" + rs.getCreateFunctionResult().getResult().getCode() + "] " +
                        "en el proceso de sincronizacion de la funcion. Mensaje: " + rs.getCreateFunctionResult().getResult());
            }

            if (rs.getCreateFunctionResult() == null || rs.getCreateFunctionResult().getResult() == null) {
                log.error("Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion de la funcion");
                throw new CSGDException(Constants.ExceptionConstants.MALFORMED_RESULT.name(), "Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion de la funcion");
            }

            log.debug("funcion creada correctamente. NodeId: " + rs.getCreateFunctionResult().getResParam());
            return rs.getCreateFunctionResult().getResParam();


        } catch (CSGDException e) {
            throw e;
        } catch (IOException e) {
            log.error("Se ha producido un error desconocido en el cliente al realizar la llamada al endpoint: " + e);
            throw new CSGDException(Constants.ExceptionConstants.CLIENT_ERROR.getValue(), "Se ha producido un error desconocido en el cliente al realizar la llamada al endpoint", e);
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de sincronizacion de la funcion: " + e);
            throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no controlado en el proceso de sincronizacion de la funcion", e);
        }
    }


    public ApiArchium getClient() {
        return client;
    }

    public void setClient(ApiArchium client) {
        this.client = client;
    }
}

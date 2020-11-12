package es.caib.archium.communication.impl;

import es.caib.archium.apirest.ApiArchium;
import es.caib.archium.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.apirest.facade.pojos.borrar.Documento;
import es.caib.archium.apirest.facade.resultados.Resultado;
import es.caib.archium.apirest.facade.resultados.ResultadoSimple;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.iface.CSGDCuadroService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;

//TODO: Crear el nuevo rol
@Stateless
//@RolesAllowed(Constants.ACH_ALFRESCO)
public class CSGGDCuadroServiceImpl implements CSGDCuadroService {

    protected final Logger log = LoggerFactory.getLogger(CSGGDCuadroServiceImpl.class);

    @Inject
    private ApiArchium client;

    @Override
    public void removeCuadro(String cuadroId) throws CSGDException {

        try {
            ResultadoSimple rs = client.borrarCuadro(cuadroId);
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
                    log.error("Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado del cuadro." +
                            " Mensaje: " + rs.getMsjResultado());
                    throw new CSGDException(Constants.ExceptionConstants.ERROR_RETURNED.getValue(), rs.getCodigoResultado(), rs.getMsjResultado(), "Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado del " +
                            "cuadro. Mensaje: " + rs.getMsjResultado());
                }
            }
        } catch (CSGDException e) {
            throw e;
        } catch (IOException e) {
            log.error("Se ha producido un error desconocido en el cliente al realizar la llamada al endpoint: " + e);
            throw new CSGDException(Constants.ExceptionConstants.CLIENT_ERROR.getValue(), "Se ha producido un error desconocido en el cliente al realizar la llamada al endpoint", e);
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de eliminación del cuadro: " + e);
            throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no controlado en el proceso de eliminación del cuadro", e);
        }
    }

    @Override
    public String synchronizeClassificationTable(CuadroClasificacion cuadroWs) throws CSGDException {
        try {
            // mock
            String nodeId = "c93b7bf8-55b4-47be-bfb1-99f6f3c6355b";

            Resultado<Documento> rs = client.obtenerDocumento(nodeId, false);


            if (rs.getCodigoResultado() != null && !rs.getCodigoResultado().equals("COD_000")) {
                log.error("Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el " +
                        "proceso de sincronizacion del cuadro de clasificacion. Mensaje: " + rs.getMsjResultado());
                throw new CSGDException(Constants.ExceptionConstants.ERROR_RETURNED.getValue(), rs.getCodigoResultado(), rs.getMsjResultado(), "Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el " +
                        "proceso de sincronizacion del cuadro de clasificacion. Mensaje: " + rs.getMsjResultado());
            }

            if (rs.getElementoDevuelto() == null) {
                log.error("Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion del cuadro de clasificacion");
                throw new CSGDException(Constants.ExceptionConstants.MALFORMED_RESULT.name(), "Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion del cuadro de clasificacion");
            }


            return rs.getElementoDevuelto().getId();

//            CreateClassificationTableResult rs = client.crearCuadro(cuadroWs);
//
//            //TODO: Constante con codigos de resultados
//            if (rs.getCreateClassificationTableResult() != null && rs.getCreateClassificationTableResult().getResult() != null
//                    && rs.getCreateClassificationTableResult().getResult().getCode() != "200") {
//                log.error("Se ha devuelto un codigo [" + rs.getCreateClassificationTableResult().getResult().getCode() + "] en el " +
//                        "proceso de sincronizacion del cuadro de clasificacion. Mensaje: " + rs.getCreateClassificationTableResult().getResult()
//                        .getDescription());
//                throw new CSGDException(Constants.ExceptionConstants.ERROR_RETURNED.getValue(),rs.getCreateClassificationTableResult().getResult().getCode(),rs.getCreateClassificationTableResult().getResult().getDescription(),"Se ha devuelto un codigo [" + rs.getCreateClassificationTableResult().getResult().getCode() + "] " +
//                        "en el proceso de sincronizacion del cuadro de clasificacion. Mensaje: " + rs.getCreateClassificationTableResult().getResult());
//            }
//
//            if (rs.getCreateClassificationTableResult() == null || rs.getCreateClassificationTableResult().getResult() == null) {
//                log.error("Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
//                        "el proceso de creacion del cuadro de clasificacion");
//                throw new CSGDException(Constants.ExceptionConstants.MALFORMED_RESULT.name(), "Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
//                        "el proceso de creacion del cuadro de clasificacion");
//            }
//
//            log.debug("Cuadro creado correctamente. NodeId: " + rs.getCreateClassificationTableResult().getResParam());
//            return rs.getCreateClassificationTableResult().getResParam();


        } catch (CSGDException e) {
            throw e;
        } catch (IOException e) {
            log.error("Se ha producido un error desconocido en el cliente al realizar la llamada al endpoint: " + e);
            throw new CSGDException(Constants.ExceptionConstants.CLIENT_ERROR.getValue(), "Se ha producido un error desconocido en el cliente al realizar la llamada al endpoint", e);
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de sincronizacion del cuadro: " + e);
            throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no controlado en el proceso de sincronizacion del cuadro", e);
        }
    }


    public ApiArchium getClient() {
        return client;
    }

    public void setClient(ApiArchium client) {
        this.client = client;
    }
}

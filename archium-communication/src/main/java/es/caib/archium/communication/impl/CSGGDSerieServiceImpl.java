package es.caib.archium.communication.impl;

import es.caib.archium.apirest.ApiArchium;
import es.caib.archium.apirest.csgd.entidades.resultados.CreateSerieResult;
import es.caib.archium.apirest.facade.pojos.Serie;
import es.caib.archium.apirest.facade.resultados.ResultadoSimple;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.iface.CSGDSerieService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;

//TODO: Crear el nuevo rol
@Stateless
//@RolesAllowed(Constants.ACH_ALFRESCO)
public class CSGGDSerieServiceImpl implements CSGDSerieService {

    protected final Logger log = LoggerFactory.getLogger(CSGGDSerieServiceImpl.class);

    @Inject
    private ApiArchium client;

    @Override
    public void removeSerie(String serieId) throws CSGDException {

        try {
            ResultadoSimple rs = client.borrarSerie(serieId);
            //TODO: Constante con codigos de resultados
            if (!rs.getCodigoResultado().equals("COD_000")) {
                // Cuando se produce un error porque el nodo a borrar no existe devuelve el cod_021, por lo que  continuamos
                // con el proceso
                if (rs.getCodigoResultado().equals("COD_021")) {
                    log.info("Se ha recibido el código de error COD_021, el nodo no existe en Alfresco, así que " +
                            "continuamos con el proceso de borrado");
                    return;
                } else {
                    log.error("Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado de la serie." +
                            " Mensaje: " + rs.getMsjResultado());
                    throw new CSGDException(Constants.ExceptionConstants.ERROR_RETURNED.getValue(), rs.getCodigoResultado(), rs.getMsjResultado(), "Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado de " +
                            "la serie. Mensaje: " + rs.getMsjResultado());
                }
            }
        } catch (CSGDException e) {
            throw e;
        } catch (IOException e) {
            log.error("Se ha producido un error desconocido en el cliente al realizar la llamada al endpoint: " + e);
            throw new CSGDException(Constants.ExceptionConstants.CLIENT_ERROR.getValue(), "Se ha producido un error desconocido en el cliente al realizar la llamada al endpoint", e);
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de eliminación de la serie: " + e);
            throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no controlado en el proceso de eliminación de la serie", e);
        }
    }

    @Override
    public String synchronizeSerie(Serie serie, String functionParent) throws CSGDException {
        try {
            CreateSerieResult rs = client.crearSerie(serie, functionParent);

            if (rs.getCreateSerieResult() != null && rs.getCreateSerieResult().getResult() != null
                    && rs.getCreateSerieResult().getResult().getCode() != "COD_001") {
                log.error("Se ha devuelto un codigo [" + rs.getCreateSerieResult().getResult().getCode() + "] en el " +
                        "proceso de sincronizacion de la serie. Mensaje: " + rs.getCreateSerieResult().getResult()
                        .getDescription());
                throw new CSGDException(Constants.ExceptionConstants.ERROR_RETURNED.getValue(), rs.getCreateSerieResult().getResult().getCode(), rs.getCreateSerieResult().getResult().getDescription(), "Se ha devuelto un codigo [" + rs.getCreateSerieResult().getResult().getCode() + "] " +
                        "en el proceso de sincronizacion de la serie. Mensaje: " + rs.getCreateSerieResult().getResult());
            }

            if (rs.getCreateSerieResult() == null || rs.getCreateSerieResult().getResult() == null) {
                log.error("Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion de la serie");
                throw new CSGDException(Constants.ExceptionConstants.MALFORMED_RESULT.name(), "Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion de la serie");
            }

            log.debug("Serie creada correctamente. NodeId: " + rs.getCreateSerieResult().getResParam());
            return rs.getCreateSerieResult().getResParam();


        } catch (CSGDException e) {
            throw e;
        } catch (IOException e) {
            log.error("Se ha producido un error desconocido en el cliente al realizar la llamada al endpoint: " + e);
            throw new CSGDException(Constants.ExceptionConstants.CLIENT_ERROR.getValue(), "Se ha producido un error desconocido en el cliente al realizar la llamada al endpoint", e);
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de sincronizacion de la serie: " + e);
            throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no controlado en el proceso de sincronizacion de la serie", e);
        }
    }


    public ApiArchium getClient() {
        return client;
    }

    public void setClient(ApiArchium client) {
        this.client = client;
    }
}

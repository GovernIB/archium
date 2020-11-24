package es.caib.archium.communication.impl;

import es.caib.archium.csgd.apirest.ApiCSGDServices;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.csgd.apirest.facade.resultados.Resultado;
import es.caib.archium.csgd.apirest.facade.resultados.ResultadoSimple;
import es.caib.archium.commons.utils.Constants;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.iface.CSGDSerieService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.io.IOException;

@Stateless
@RolesAllowed({Constants.ACH_CSGD})
public class CSGGDSerieServiceImpl implements CSGDSerieService {

    protected final Logger log = LoggerFactory.getLogger(CSGGDSerieServiceImpl.class);

    @Inject
    private ApiCSGDServices client;

    @Override
    public void removeSerie(String serieId) throws CSGDException {

        try {
            ResultadoSimple rs = client.borrarSerie(serieId);
            //TODO: Constante con codigos de resultados
            if (!"COD_000".equals(rs.getCodigoResultado())) {
                // Cuando se produce un error porque el nodo a borrar no existe devuelve el cod_021, por lo que  continuamos
                // con el proceso
                //FIXME: Puede producirse que se borre en gdib y en bbdd no, hay que controlar de manera diferente si no
                // existe en gdib para que siga el proceso como que sí se hizo. Mockeado con el COD_021
                if ("COD_021".equals(rs.getCodigoResultado())) {
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
            Resultado<String> rs = client.crearSerie(serie, functionParent);

            if (!"COD_000".equals(rs.getCodigoResultado())) {
                log.error("Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el " +
                        "proceso de sincronizacion de la serie. Mensaje: " + rs.getMsjResultado());
                throw new CSGDException(Constants.ExceptionConstants.ERROR_RETURNED.getValue(), rs.getCodigoResultado(), rs.getMsjResultado(), "Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] " +
                        "en el proceso de sincronizacion de la serie. Mensaje: " + rs.getMsjResultado());
            }

            if (StringUtils.trimToNull(rs.getElementoDevuelto())==null) {
                log.error("Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion de la serie");
                throw new CSGDException(Constants.ExceptionConstants.MALFORMED_RESULT.name(), "Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion de la serie");
            }

            log.debug("Serie creada correctamente. NodeId: " + rs.getElementoDevuelto());
            return rs.getElementoDevuelto();


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


    public ApiCSGDServices getClient() {
        return client;
    }

    public void setClient(ApiCSGDServices client) {
        this.client = client;
    }
}

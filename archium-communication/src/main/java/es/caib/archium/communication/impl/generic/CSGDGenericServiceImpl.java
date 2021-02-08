package es.caib.archium.communication.impl.generic;

import es.caib.archium.commons.utils.Constants;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.iface.generic.CSGDService;
import es.caib.archium.csgd.apirest.ApiCSGDServices;
import es.caib.archium.csgd.apirest.facade.pojos.Nodo;
import es.caib.archium.csgd.apirest.facade.pojos.eliminar.EliminarNodo;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverNodo;
import es.caib.archium.csgd.apirest.facade.resultados.Resultado;
import es.caib.archium.csgd.apirest.facade.resultados.ResultadoSimple;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;

@ApplicationScoped
@RolesAllowed({Constants.ACH_CSGD})
public abstract class CSGDGenericServiceImpl<T extends Nodo, R extends EliminarNodo, M extends MoverNodo> implements CSGDService<T, R, M> {

    protected final Logger log = LoggerFactory.getLogger(CSGDGenericServiceImpl.class);

    @Inject
    protected ApiCSGDServices client;

    @Override
    public void deleteNode(R wsId) throws CSGDException {
        log.info("Se procede a borrar el nodo");
        try {
            ResultadoSimple rs = client.borrarNodo(wsId);

            if (!Constants.CodigosRespuestaCSGD.RESPUESTA_OK.getValue().equals(rs.getCodigoResultado())) {
                if (Constants.CodigosRespuestaCSGD.RESPUESTA_EXCEPCION.getValue().equals(rs.getCodigoResultado())) {
                    // Cuando se produce un error porque el nodo a borrar no existe devuelve el cod_021, por lo que  continuamos
                    // con el proceso, si no devuelve nada es que el nodo no existe en gdib y se continua con normalidad
                    T result = getNode(wsId);
                    if(result == null) {
                        log.info("Se ha recibido el código de error COD_021, el nodo no existe en Alfresco, así que " +
                                "continuamos con el proceso de borrado");
                        return;
                    }else{
                        log.error("Se produjo un error en el proceso de eliminacion del nodo.");
                        throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se produjo un error en el proceso de eliminacion del nodo.");
                    }
                } else {
                    log.error("Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado." +
                            " Mensaje: " + rs.getMsjResultado());
                    throw new CSGDException(Constants.ExceptionConstants.ERROR_RETURNED.getValue(), rs.getCodigoResultado(), rs.getMsjResultado(), "Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado. Mensaje: " + rs.getMsjResultado());
                }
            }
        } catch (CSGDException e) {
            throw e;
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de eliminación: " + e);
            throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no controlado en el proceso de eliminación", e);
        }
        log.info("Nodo borrado correctamente");
    }

    @Override
    public void moveNode(M wsDto) throws CSGDException {
        log.info("Se procede a mover el nodo");
        try {
            ResultadoSimple rs = client.moverNodo(wsDto);

            if (!Constants.CodigosRespuestaCSGD.RESPUESTA_OK.getValue().equals(rs.getCodigoResultado())) {
                log.error("Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado." +
                        " Mensaje: " + rs.getMsjResultado());
                throw new CSGDException(Constants.ExceptionConstants.ERROR_RETURNED.getValue(), rs.getCodigoResultado(), rs.getMsjResultado(), "Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de mover. Mensaje: " + rs.getMsjResultado());
            }
        } catch (CSGDException e) {
            throw e;
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de mover: " + e);
            throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no controlado en el proceso de mover", e);
        }
        log.info("Nodo movido correctamente");
    }

    /**
     * Se llama al metodo getNode, se utilizara para comprobara si el nodo existe cuando se devuelve un error realizando
     * otra accion, ya que en el bus se pierden las excepciones
     *
     * @param wsId
     * @throws CSGDException
     */
    private T getNode(R wsId) throws CSGDException {
        log.info("Se procede a obtener el nodo");
        try {
            Resultado<T> rs = client.getNode(wsId);

            if (!Constants.CodigosRespuestaCSGD.RESPUESTA_OK.getValue().equals(rs.getCodigoResultado())) {
                log.error("Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de obtencion del nodo. " +
                        "Mensaje: " + rs.getMsjResultado());
                throw new CSGDException(Constants.ExceptionConstants.ERROR_RETURNED.getValue(), rs.getCodigoResultado(),
                        rs.getMsjResultado(), "Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el " +
                        "proceso de obtencion del nodo. Mensaje: " + rs.getMsjResultado());
            }
            log.debug("Obtencion del nodo realizada correctamente.");
            return rs.getElementoDevuelto();
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de obtencion del nodo: " + e);
            throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no controlado en el proceso de obtencion del nodo", e);
        }
    }


    @Override
    public String synchronizeNode(T wsDto, String parentId) throws CSGDException {
        log.info("Se procede a sincronizar el nodo");
        try {
            if (StringUtils.trimToNull(wsDto.getNodeId()) == null) {
                Resultado<String> rs = client.crearNodo(wsDto, parentId);
                return prepararResultado(rs);
            } else {
                ResultadoSimple rs = client.modificarNodo(wsDto);
                prepararResultado(rs);
                return wsDto.getNodeId();
            }

        } catch (CSGDException e) {
            throw e;
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de sincronizacion: " + e);
            throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no " +
                    "controlado en el proceso de sincronizacion", e);
        }
    }

    @Override
    public String synchronizeNode(T wsDto) throws CSGDException {
        return synchronizeNode(wsDto, null);
    }

    /**
     * Analiza si la respuesta fue correcta en la sincronizacion y devuelve una respuesta en caso de necesitarla
     *
     * @param rs
     * @return
     * @throws CSGDException
     */
    private String prepararResultado(ResultadoSimple rs) throws CSGDException {
        if (!Constants.CodigosRespuestaCSGD.RESPUESTA_OK.getValue().equals(rs.getCodigoResultado())) {
            log.error("Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de sincronizacion. " +
                    "Mensaje: " + rs.getMsjResultado());
            throw new CSGDException(Constants.ExceptionConstants.ERROR_RETURNED.getValue(), rs.getCodigoResultado(),
                    rs.getMsjResultado(), "Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el " +
                    "proceso de sincronizacion. Mensaje: " + rs.getMsjResultado());
        }

        if (rs instanceof Resultado) {
            Resultado<String> result = (Resultado<String>) rs;
            if (StringUtils.trimToNull(result.getElementoDevuelto()) == null) {
                log.error("Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion de gdib");
                throw new CSGDException(Constants.ExceptionConstants.MALFORMED_RESULT.name(), "Se ha producido un error " +
                        "no esperado al no recibir correctamente los datos de respuesta en el proceso de creacion de gdib");
            }

            log.debug("Sincronizacion realizada correctamente. NodeId: " + result.getElementoDevuelto());
            return result.getElementoDevuelto();
        } else {
            return null;
        }
    }

}

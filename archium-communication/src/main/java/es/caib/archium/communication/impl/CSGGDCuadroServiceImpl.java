package es.caib.archium.communication.impl;

import es.caib.archium.apirest.ApiArchium;
import es.caib.archium.apirest.csgd.entidades.resultados.CreateClassificationTableResult;
import es.caib.archium.apirest.csgd.entidades.resultados.CreateSerieResult;
import es.caib.archium.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.apirest.facade.pojos.borrar.Documento;
import es.caib.archium.apirest.facade.resultados.Resultado;
import es.caib.archium.apirest.facade.resultados.ResultadoSimple;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.exception.ExceptionConstants;
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
            if (rs.getCodigoResultado() != "200") {
                log.error("Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado del cuadro." +
                        " Mensaje: " + rs.getMsjResultado());
                throw new CSGDException(ExceptionConstants.ERROR_RETURNED.getValue(),rs.getCodigoResultado(),rs.getMsjResultado(),"Se ha devuelto un codigo [" + rs.getCodigoResultado() + "] en el proceso de borrado del " +
                        "cuadro. Mensaje: " + rs.getMsjResultado());
            }
        } catch (IOException e) {
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            throw new CSGDException(ExceptionConstants.CLIENT_ERROR.getValue(),"Se ha producido un error desconocido en la llamada en el el cliente",e);
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de eliminación del cuadro: " + e);
            throw new CSGDException(ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no controlado en el proceso de eliminación del cuadro",e);
        }
    }

    @Override
    public String synchronizeClassificationTable(CuadroClasificacion cuadroWs) throws CSGDException {
        log.debug("llega al communication");
        try {
            // mock
            String nodeId = "c93b7bf8-55b4-47be-bfb1-99f6f3c6355b";

            Resultado<Documento> rs = client.obtenerDocumento(nodeId,false);

            if(rs.getElementoDevuelto()!=null) {
                log.debug("Documento obtenido: "+rs.getElementoDevuelto().getName());
            }else{
                log.debug("Elemento no encontrado");
            }
            if(rs.getElementoDevuelto()==null){
                log.error("Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion del cuadro de clasificacion");
                throw new CSGDException(ExceptionConstants.MALFORMED_RESULT.name(), "Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
                        "el proceso de creacion del cuadro de clasificacion");
            }

            return rs.getElementoDevuelto().getId();

//            CreateClassificationTableResult rs = client.crearCuadro(cuadroWs);
//            if (rs.getCreateClassificationTableResult() == null || rs.getCreateClassificationTableResult().getResult() == null) {
//                log.error("Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
//                        "el proceso de creacion del cuadro de clasificacion");
//                throw new CSGDException(ExceptionConstants.MALFORMED_RESULT.name(), "Se ha producido un error no esperado al no recibir correctamente los datos de respuesta en " +
//                        "el proceso de creacion del cuadro de clasificacion");
//            }
//
//            //TODO: Constante con codigos de resultados
//            if (rs.getCreateClassificationTableResult() != null && rs.getCreateClassificationTableResult().getResult() != null
//                    && rs.getCreateClassificationTableResult().getResult().getCode() != "200") {
//                log.error("Se ha devuelto un codigo [" + rs.getCreateClassificationTableResult().getResult().getCode() + "] en el " +
//                        "proceso de sincronizacion del cuadro de clasificacion. Mensaje: " + rs.getCreateClassificationTableResult().getResult()
//                        .getDescription());
//                throw new CSGDException(ExceptionConstants.ERROR_RETURNED.getValue(),rs.getCreateClassificationTableResult().getResult().getCode(),rs.getCreateClassificationTableResult().getResult().getDescription(),"Se ha devuelto un codigo [" + rs.getCreateClassificationTableResult().getResult().getCode() + "] " +
//                        "en el proceso de sincronizacion del cuadro de clasificacion. Mensaje: " + rs.getCreateClassificationTableResult().getResult());
//            }
//
//
//            log.debug("Cuadro creado correctamente. NodeId: " + rs.getCreateClassificationTableResult().getResParam());
//            return rs.getCreateClassificationTableResult().getResParam();



        } catch (IOException e) {
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            throw new CSGDException(ExceptionConstants.CLIENT_ERROR.getValue(),"Se ha producido un error desconocido en la llamada en el el cliente",e);
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de sincronizacion del cuadro: " + e);
            throw new CSGDException(ExceptionConstants.GENERIC_ERROR.getValue(),"Se ha producido un error no controlado en el proceso de sincronizacion del cuadro",e);
        }
    }


    public ApiArchium getClient() {
        return client;
    }

    public void setClient(ApiArchium client) {
        this.client = client;
    }
}

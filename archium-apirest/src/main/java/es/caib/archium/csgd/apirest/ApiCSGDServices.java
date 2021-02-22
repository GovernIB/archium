package es.caib.archium.csgd.apirest;

import es.caib.archium.csgd.apirest.constantes.*;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.*;
import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.*;
import es.caib.archium.csgd.apirest.csgd.entidades.resultados.*;
import es.caib.archium.csgd.apirest.csgd.peticiones.*;
import es.caib.archium.csgd.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.csgd.apirest.facade.pojos.Funcion;
import es.caib.archium.csgd.apirest.facade.pojos.Nodo;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.csgd.apirest.facade.pojos.cabecera.CabeceraAplicacion;
import es.caib.archium.csgd.apirest.facade.pojos.cabecera.CabeceraLogin;
import es.caib.archium.csgd.apirest.facade.pojos.cabecera.CabeceraPeticion;
import es.caib.archium.csgd.apirest.facade.pojos.cabecera.CabeceraTramite;
import es.caib.archium.csgd.apirest.facade.pojos.eliminar.EliminarCuadro;
import es.caib.archium.csgd.apirest.facade.pojos.eliminar.EliminarFuncion;
import es.caib.archium.csgd.apirest.facade.pojos.eliminar.EliminarNodo;
import es.caib.archium.csgd.apirest.facade.pojos.eliminar.EliminarSerie;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverFuncion;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverNodo;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverSerie;
import es.caib.archium.csgd.apirest.facade.resultados.Resultado;
import es.caib.archium.csgd.apirest.facade.resultados.ResultadoSimple;
import es.caib.archium.csgd.apirest.jerseyclient.JerseyClientGet;
import es.caib.archium.csgd.apirest.jerseyclient.ResultadoJersey;
import es.caib.archium.csgd.apirest.utils.Constantes;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ws.rs.ProcessingException;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class ApiCSGDServices {

    protected final Logger log = LoggerFactory.getLogger(ApiCSGDServices.class);

    protected final CabeceraPeticion cabeceraPeticion;

    protected final String urlBase;

    boolean trazas = true;


    public ApiCSGDServices(String urlBase, CabeceraPeticion cabeceraPeticion) {
        this.cabeceraPeticion = cabeceraPeticion;
        this.urlBase = urlBase;
    }

    public ApiCSGDServices(String urlBase, CabeceraAplicacion cabeceraApp, CabeceraLogin cabeceraLogin,
                           CabeceraTramite cabeceraTramite) {

        this(urlBase, constructCabeceraPeticion(cabeceraApp, cabeceraLogin, cabeceraTramite));
    }

    /**
     * Llamada post generica para relizara desde todos los metodos que esperan un Resultado<String>
     *
     * @param result
     * @param servicio
     * @param peticion
     * @param entityResult
     * @param <T>
     * @return
     * @throws IOException
     */
    private <T> ResultadoJersey postCall(Resultado<String> result, String servicio, Object peticion, Class<T> entityResult) {
        try {
            log.debug("Se realiza la llamada con parametros de entrada: " + peticion.toString());
            return JerseyClientGet.post(this.urlBase, servicio, peticion, entityResult, trazas);
        } catch (ProcessingException e) {
            log.error("No es posible realizar conexion con el servicio. URL = [" + urlBase + servicio + "]: " + e);
            result.setCodigoResultado(String.valueOf(Constantes.CodigosRespuesta.ERROR_CONEXION.getValue()));
            result.setMsjResultado("No es posible realizar conexion con el servicio");
        } catch (IOException e) {
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            result.setCodigoResultado(String.valueOf(Constantes.CodigosRespuesta.ERROR_DESCONOCIDO_PETICION.getValue()));
            result.setMsjResultado("Se ha producido una excepcion en la respuesta a la peticion");
        }
        return null;
    }

    /**
     * Llamada post generica para relizara desde todos los metodos que esperan un ResultadoSimple
     *
     * @param rs
     * @param servicio
     * @param peticion
     * @param entityResult
     * @param <T>
     * @return
     */
    private <T> ResultadoJersey postCall(ResultadoSimple rs, String servicio, Object peticion, Class<T> entityResult) {
        try {
            log.debug("Se realiza la llamada con parametros de entrada: " + peticion.toString());
            return JerseyClientGet.post(this.urlBase, servicio, peticion, entityResult, trazas);
        } catch (ProcessingException e) {
            log.error("No es posible realizar conexion con el servicio. URL = [" + urlBase + servicio + "]: " + e);
            rs.setCodigoResultado(String.valueOf(Constantes.CodigosRespuesta.ERROR_CONEXION.getValue()));
            rs.setMsjResultado("No es posible realizar conexion con el servicio");
        } catch (IOException e) {
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            rs.setCodigoResultado(String.valueOf(Constantes.CodigosRespuesta.ERROR_DESCONOCIDO_PETICION.getValue()));
            rs.setMsjResultado("Se ha producido una excepcion en la respuesta a la peticion");
        }
        return null;
    }

    public ResultadoSimple modificarSerie(Serie serie){
        SetSerieResult result = null;
        ResultadoSimple resultado = new ResultadoSimple();

        SerieNode serieNode = convertirASerieNode(serie);

        Request<ParamSetSerie> request = new Request<ParamSetSerie>();
        SetSerie peticion = new SetSerie();
        ParamSetSerie param = new ParamSetSerie();
        param.setSerie(serieNode);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);
        peticion.setSetSerieRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(resultado, Servicios.SET_SERIE, peticion, SetSerieResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return resultado;
        }


        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            result = (SetSerieResult) output.getContenido();
            resultado.setCodigoResultado(result.getSetSerieResult().getResult().getCode());
            resultado.setMsjResultado(result.getSetSerieResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            resultado.setCodigoResultado(except.getException().getCode());
            resultado.setMsjResultado(except.getException().getDescription());
        }

        return resultado;
    }

    public Resultado<Serie> getSerie(String nodeId) {
        Resultado<Serie> result = new Resultado<>();

        Request<ParamNodeId> request = new Request<ParamNodeId>();
        ParamNodeId param;
        GetSerie peticion = new GetSerie();

        param = generarParametrosNodeId(nodeId);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);

        peticion.setGetSerieRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(result, Servicios.GET_SERIE, peticion, GetSerieResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return result;
        }

        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            GetSerieResult resultado = (GetSerieResult) output.getContenido();
            result.setElementoDevuelto(toSerie(resultado.getGetSerieResult().getResParam()));
            result.setCodigoResultado(resultado.getGetSerieResult().getResult().getCode());
            result.setMsjResultado(resultado.getGetSerieResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            result.setCodigoResultado(except.getException().getCode());
            result.setMsjResultado(except.getException().getDescription());
        }
        return result;
    }

    public Resultado<String> crearSerie(Serie serie, String funcionPadre) {
        log.debug("Se prepara la llamada en el cliente para crearSerie");
        Resultado<String> result = new Resultado<>();
        SerieNode serieNode = null;

        serieNode = convertirASerieNode(serie);

        Request<ParamCreateSerie> request = new Request<ParamCreateSerie>();
        CreateSerie peticion = new CreateSerie();
        ParamCreateSerie param = new ParamCreateSerie();
        param.setSerie(serieNode);
        param.setParentId(funcionPadre);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);
        peticion.setCreateSerieRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(result, Servicios.CREATE_SERIE, peticion, CreateSerieResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return result;
        }

        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            CreateSerieResult resultado = (CreateSerieResult) output.getContenido();
            result.setElementoDevuelto(resultado.getCreateSerieResult().getResParam().getId());
            result.setCodigoResultado(resultado.getCreateSerieResult().getResult().getCode());
            result.setMsjResultado(resultado.getCreateSerieResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            result.setCodigoResultado(except.getException().getCode());
            result.setMsjResultado(except.getException().getDescription());
        }
        return result;
    }

    public ResultadoSimple moverSerie(String serieId, String newParent){

        MoveSerieResult result = null;
        ResultadoSimple resultado = new ResultadoSimple();

        Request<ParamMoveNode> request = new Request<>();
        ParamMoveNode param;
        MoveSerie peticion = new MoveSerie();

        param = generarParametrosMoveNode(serieId,newParent);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);

        peticion.setMoveSerieRequest(request);


        ResultadoJersey output;
        // Realizamos la llamada
        output = postCall(resultado, Servicios.MOVE_SERIE, peticion, MoveSerieResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return resultado;
        }

        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            result = (MoveSerieResult) output.getContenido();
            resultado.setCodigoResultado(result.getMoveSerieResult().getResult().getCode());
            resultado.setMsjResultado(result.getMoveSerieResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            resultado.setCodigoResultado(except.getException().getCode());
            resultado.setMsjResultado(except.getException().getDescription());
        }

        return resultado;

    }

    public ResultadoSimple borrarSerie(String nodeId) {

        RemoveSerieResult result = null;
        ResultadoSimple resultado = new ResultadoSimple();

        Request<ParamNodeId> request = new Request<ParamNodeId>();
        ParamNodeId param;
        RemoveSerie peticion = new RemoveSerie();

        param = generarParametrosNodeId(nodeId);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);

        peticion.setRemoveSerieRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(resultado, Servicios.REMOVE_SERIE, peticion, RemoveSerieResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return resultado;
        }


        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            result = (RemoveSerieResult) output.getContenido();
            resultado.setCodigoResultado(result.getRemoveSerieResult().getResult().getCode());
            resultado.setMsjResultado(result.getRemoveSerieResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            resultado.setCodigoResultado(except.getException().getCode());
            resultado.setMsjResultado(except.getException().getDescription());
        }

        return resultado;
    }

    public ResultadoSimple modificarFuncion(Funcion funcion){
        SetFunctionResult result = null;
        ResultadoSimple resultado = new ResultadoSimple();

        FunctionNode functionNode = convertirAFunctionNode(funcion);

        Request<ParamSetFunction> request = new Request<ParamSetFunction>();
        SetFunction peticion = new SetFunction();
        ParamSetFunction param = new ParamSetFunction();
        param.setClassificationFunction(functionNode);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);
        peticion.setSetFunctionRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(resultado, Servicios.SET_FUNCTION, peticion, SetFunctionResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return resultado;
        }


        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            result = (SetFunctionResult) output.getContenido();
            resultado.setCodigoResultado(result.getSetFunctionResult().getResult().getCode());
            resultado.setMsjResultado(result.getSetFunctionResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            resultado.setCodigoResultado(except.getException().getCode());
            resultado.setMsjResultado(except.getException().getDescription());
        }

        return resultado;
    }

    public Resultado<Funcion> getFuncion(String nodeId) {
        Resultado<Funcion> result = new Resultado<>();

        Request<ParamNodeId> request = new Request<ParamNodeId>();
        ParamNodeId param;
        GetFunction peticion = new GetFunction();

        param = generarParametrosNodeId(nodeId);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);

        peticion.setGetFunctionRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(result, Servicios.GET_FUNCTION, peticion, GetFunctionResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return result;
        }

        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            GetFunctionResult resultado = (GetFunctionResult) output.getContenido();
            result.setElementoDevuelto(toFuncion(resultado.getGetFunctionResult().getResParam()));
            result.setCodigoResultado(resultado.getGetFunctionResult().getResult().getCode());
            result.setMsjResultado(resultado.getGetFunctionResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            result.setCodigoResultado(except.getException().getCode());
            result.setMsjResultado(except.getException().getDescription());
        }
        return result;
    }

    public Resultado<String> crearFuncion(Funcion funcion, String parentId) {

        Resultado<String> result = new Resultado<>();

//        CreateFunctionResult result = null;
        FunctionNode functionNode = convertirAFunctionNode(funcion);

        CreateFunction peticion = new CreateFunction();
        Request<ParamCreateFunction> request = new Request<ParamCreateFunction>();
        ParamCreateFunction param = new ParamCreateFunction();
        param.setClassificationFunction(functionNode);
        param.setParentId(parentId);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);
        peticion.setCreateFunctionRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(result, Servicios.CREATE_FUNCTION, peticion, CreateFunctionResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return result;
        }

        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            CreateFunctionResult resultado = (CreateFunctionResult) output.getContenido();
            result.setElementoDevuelto(resultado.getCreateFunctionResult().getResParam().getId());
            result.setCodigoResultado(resultado.getCreateFunctionResult().getResult().getCode());
            result.setMsjResultado(resultado.getCreateFunctionResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            result.setCodigoResultado(except.getException().getCode());
            result.setMsjResultado(except.getException().getDescription());
        }
        return result;
    }

    public ResultadoSimple moverFuncion(String functionId, String newParent){

        MoveFunctionResult result = null;
        ResultadoSimple resultado = new ResultadoSimple();

        Request<ParamMoveNode> request = new Request<>();
        ParamMoveNode param;
        MoveFunction peticion = new MoveFunction();

        param = generarParametrosMoveNode(functionId,newParent);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);

        peticion.setMoveFunctionRequest(request);


        ResultadoJersey output;
        // Realizamos la llamada
        output = postCall(resultado, Servicios.MOVE_FUNCTION, peticion, MoveFunctionResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return resultado;
        }

        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            result = (MoveFunctionResult) output.getContenido();
            resultado.setCodigoResultado(result.getMoveFunctionResult().getResult().getCode());
            resultado.setMsjResultado(result.getMoveFunctionResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            resultado.setCodigoResultado(except.getException().getCode());
            resultado.setMsjResultado(except.getException().getDescription());
        }

        return resultado;

    }


    public ResultadoSimple borrarFuncion(String nodeId) {

        RemoveFunctionResult result = null;
        ResultadoSimple resultado = new ResultadoSimple();

        Request<ParamNodeId> request = new Request<ParamNodeId>();
        ParamNodeId param;
        RemoveFunction peticion = new RemoveFunction();

        param = generarParametrosNodeId(nodeId);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);

        peticion.setRemoveFunctionRequest(request);

        ResultadoJersey output;
        // Realizamos la llamada
        output = postCall(resultado, Servicios.REMOVE_FUNCTION, peticion, RemoveFunctionResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return resultado;
        }

        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            result = (RemoveFunctionResult) output.getContenido();
            resultado.setCodigoResultado(result.getRemoveFunctionResult().getResult().getCode());
            resultado.setMsjResultado(result.getRemoveFunctionResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            resultado.setCodigoResultado(except.getException().getCode());
            resultado.setMsjResultado(except.getException().getDescription());
        }

        return resultado;
    }

    public ResultadoSimple modificarCuadro(CuadroClasificacion cuadro){
        SetClassificationRootResult result = null;
        ResultadoSimple resultado = new ResultadoSimple();

        RootNode rootNode = convertirAClassificationRootNode(cuadro);

        Request<ParamSetRoot> request = new Request<ParamSetRoot>();
        SetClassificationRoot peticion = new SetClassificationRoot();
        ParamSetRoot param = new ParamSetRoot();
        param.setClassificationRoot(rootNode);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);
        peticion.setSetClassificationRootRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(resultado, Servicios.SET_CLASSIFICATION_ROOT, peticion, SetClassificationRootResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return resultado;
        }


        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            result = (SetClassificationRootResult) output.getContenido();
            resultado.setCodigoResultado(result.getSetClassificationRootResult().getResult().getCode());
            resultado.setMsjResultado(result.getSetClassificationRootResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            resultado.setCodigoResultado(except.getException().getCode());
            resultado.setMsjResultado(except.getException().getDescription());
        }

        return resultado;
    }

    public Resultado<CuadroClasificacion> getCuadro(String nodeId) {
        Resultado<CuadroClasificacion> result = new Resultado<>();

        Request<ParamNodeId> request = new Request<ParamNodeId>();
        ParamNodeId param;
        GetClassificationRoot peticion = new GetClassificationRoot();

        param = generarParametrosNodeId(nodeId);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);

        peticion.setGetClassificationRootRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(result, Servicios.GET_CLASSIFICATION_ROOT, peticion, GetClassificationRootResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return result;
        }

        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            GetClassificationRootResult resultado = (GetClassificationRootResult) output.getContenido();
            result.setElementoDevuelto(toCuadroClasificacion(resultado.getGetClassificationRootResult().getResParam()));
            result.setCodigoResultado(resultado.getGetClassificationRootResult().getResult().getCode());
            result.setMsjResultado(resultado.getGetClassificationRootResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            result.setCodigoResultado(except.getException().getCode());
            result.setMsjResultado(except.getException().getDescription());
        }
        return result;
    }

    public Resultado<String> crearCuadro(CuadroClasificacion cuadro) {

        Resultado<String> result = new Resultado<>();
        RootNode classificationTableNode = convertirAClassificationRootNode(cuadro);

        Request<ParamCreateClassificationRoot> request = new Request<ParamCreateClassificationRoot>();
        CreateClassificationRoot peticion = new CreateClassificationRoot();
        ParamCreateClassificationRoot param = new ParamCreateClassificationRoot();
        param.setClassificationRoot(classificationTableNode);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);
        peticion.setCreateClassificationRootRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(result, Servicios.CREATE_CLASSIFICATION_ROOT, peticion, CreateClassificationRootResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return result;
        }

        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            CreateClassificationRootResult resultado = (CreateClassificationRootResult) output.getContenido();
            result.setElementoDevuelto(resultado.getCreateClassificationRootResult().getResParam().getId());
            result.setCodigoResultado(resultado.getCreateClassificationRootResult().getResult().getCode());
            result.setMsjResultado(resultado.getCreateClassificationRootResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            result.setCodigoResultado(except.getException().getCode());
            result.setMsjResultado(except.getException().getDescription());
        }
        return result;
    }

    public ResultadoSimple borrarCuadro(String nodeId) {

        RemoveClassificationRootResult result = null;
        ResultadoSimple resultado = new ResultadoSimple();

        Request<ParamNodeId> request = new Request<ParamNodeId>();
        ParamNodeId param;
        RemoveClassificationRoot peticion = new RemoveClassificationRoot();

        param = generarParametrosNodeId(nodeId);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);

        peticion.setRemoveClassificationRootRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(resultado, Servicios.REMOVE_CLASSIFICATION_ROOT, peticion, RemoveClassificationRootResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return resultado;
        }

        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            result = (RemoveClassificationRootResult) output.getContenido();
            resultado.setCodigoResultado(result.getRemoveClassificationRootResult().getResult().getCode());
            resultado.setMsjResultado(result.getRemoveClassificationRootResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            resultado.setCodigoResultado(except.getException().getCode());
            resultado.setMsjResultado(except.getException().getDescription());
        }

        return resultado;
    }


    public CabeceraPeticion getCabeceraPeticion() {
        return cabeceraPeticion;
    }

    public String getUrlBase() {
        return urlBase;
    }

    public boolean isTrazas() {
        return trazas;
    }

    public void setTrazas(boolean trazas) {
        this.trazas = trazas;
    }

    // -------------------------------------------------
    // -------------------------------------------------
    // ------------------- UTILITATS -------------------
    // -------------------------------------------------
    // -------------------------------------------------

    private ServiceHeader internalServiceHeader = null;

    public ServiceHeader getServiceHeader() {
        if (internalServiceHeader == null) {
            CabeceraPeticion cabecera = this.getCabeceraPeticion();

            PersonIdentAuditInfo ciudadano = new PersonIdentAuditInfo();
            PersonIdentAuditInfo trabajador = new PersonIdentAuditInfo();
            PublicServantAuditInfo publicServan = new PublicServantAuditInfo();

            ProceedingsAuditInfo procedimiento_administrastivo = new ProceedingsAuditInfo();
            FileAuditInfo fileCabecera = new FileAuditInfo();
            ServiceSecurityInfo securityInfo = new ServiceSecurityInfo();
            ServiceAuditInfo auditInfo = new ServiceAuditInfo();
            ServiceHeader serviceHeader = new ServiceHeader();

            ciudadano.setName(cabecera.getNombreSolicitante());
            ciudadano.setDocument(cabecera.getDocumentoSolicitante());

            trabajador.setName(cabecera.getNombreUsuario());
            trabajador.setDocument(cabecera.getDocumentoUsuario());

            publicServan.setIdentificationData(trabajador);
            publicServan.setOrganization(cabecera.getOrganizacion());

            procedimiento_administrastivo.setName(cabecera.getNombreProcedimiento());

            fileCabecera.setId(cabecera.getIdExpediente());
            fileCabecera.setProceedings(procedimiento_administrastivo);

            securityInfo.setUser(cabecera.getUsuarioSeguridad());
            securityInfo.setPassword(cabecera.getPasswordSeguridad());

            auditInfo.setApplicant(ciudadano);
            auditInfo.setPublicServant(publicServan);
            auditInfo.setFile(fileCabecera);
            auditInfo.setApplication(cabecera.getCodiAplicacion());

            serviceHeader.setAuditInfo(auditInfo);
            serviceHeader.setServiceVersion(cabecera.getServiceVersion());
            serviceHeader.setSecurityInfo(securityInfo);

            internalServiceHeader = serviceHeader;

        }
        return internalServiceHeader;
    }


    private static final String VERSION_SERVICIO = "1.0";


    /**
     * @param cabeceraApp
     * @param cabeceraLogin
     * @param cabeceraTramite
     * @return
     */
    public static CabeceraPeticion constructCabeceraPeticion(CabeceraAplicacion cabeceraApp,
                                                             CabeceraLogin cabeceraLogin, CabeceraTramite cabeceraTramite) {
        CabeceraPeticion cabeceraTmp = new CabeceraPeticion();

        // intern api
        cabeceraTmp.setServiceVersion(VERSION_SERVICIO);

        // CabeceraAplicacion
        cabeceraTmp.setCodiAplicacion(cabeceraApp.getCodiAplicacion());
        cabeceraTmp.setOrganizacion(cabeceraApp.getOrganizacion());
        cabeceraTmp.setUsuarioSeguridad(cabeceraApp.getUsuarioSeguridad());
        cabeceraTmp.setPasswordSeguridad(cabeceraApp.getPasswordSeguridad());
        // Cabecera Login
        cabeceraTmp.setDocumentoSolicitante(cabeceraLogin.getDocumentoSolicitante());
        cabeceraTmp.setDocumentoUsuario(cabeceraLogin.getDocumentoUsuario());
        cabeceraTmp.setNombreSolicitante(cabeceraLogin.getNombreSolicitante());
        cabeceraTmp.setNombreUsuario(cabeceraLogin.getNombreUsuario());
        // CabeceraTramite
        cabeceraTmp.setIdExpediente(cabeceraTramite.getIdExpediente());
        cabeceraTmp.setNombreProcedimiento(cabeceraTramite.getNombreProcedimiento());
        return cabeceraTmp;
    }

    /**
     * Llama al metodo preciso en funcion del tipo del dto
     *
     * @param wsDto
     * @param parentId
     * @param <T>
     * @return
     */
    public <T extends Nodo> Resultado<String> crearNodo(T wsDto, String parentId) {
        if (wsDto instanceof CuadroClasificacion) {
            return crearCuadro((CuadroClasificacion) wsDto);
        } else if (wsDto instanceof Funcion) {
            return crearFuncion((Funcion) wsDto, parentId);
        } else if (wsDto instanceof Serie) {
            return crearSerie((Serie) wsDto, parentId);
        }
        return null;
    }

    /**
     * Llama al metodo preciso en funcion del tipo del dto
     *
     * @param wsDto
     * @param <T>
     * @return
     */
    public <T extends Nodo> ResultadoSimple modificarNodo(T wsDto) {
        if (wsDto instanceof CuadroClasificacion) {
            return modificarCuadro((CuadroClasificacion) wsDto);
        } else if (wsDto instanceof Funcion) {
            return modificarFuncion((Funcion) wsDto);
        } else if (wsDto instanceof Serie) {
            return modificarSerie((Serie) wsDto);
        }
        return null;
    }

    /**
     * Llama al metodo preciso en funcion del tipo del dto
     *
     * @param wsId
     * @param <T>
     * @return
     */
    public <ID extends EliminarNodo> ResultadoSimple borrarNodo(ID wsId) {
        if (wsId instanceof EliminarCuadro) {
            return borrarCuadro(wsId.getNodoId());
        } else if (wsId instanceof EliminarFuncion) {
            return borrarFuncion(wsId.getNodoId());
        } else if (wsId instanceof EliminarSerie) {
            return borrarSerie(wsId.getNodoId());
        }
        return null;
    }

    /**
     * Llama al metodo preciso en fucnion del tipo del dto
     *
     * @param wsDto
     * @param <M>
     * @return
     */
    public <M extends MoverNodo> ResultadoSimple moverNodo(M wsDto) {
        if (wsDto instanceof MoverFuncion) {
            return moverFuncion(wsDto.getNodoId(),wsDto.getParentId());
        } else if (wsDto instanceof MoverSerie) {
            return moverSerie(wsDto.getNodoId(),wsDto.getParentId());
        }
        return null;
    }

    /**
     * Crea el dto para mover funcion
     *
     * @param functionId
     * @param newParent
     * @return
     */
    private ParamMoveNode generarParametrosMoveNode(String functionId, String newParent) {
        ParamMoveNode param = new ParamMoveNode();
        param.setTargetParent(newParent);
        param.setNodeId(functionId);
        return param;
    }

    /**
     * Llama al metodo preciso en funcion del tipo de dto, utiliza el dto de eliminar porque esta accion se creo para
     * complementar la logica de este otro debido a las limitacioneos del bus
     *
     * @param wsId
     * @param <T>
     * @param <ID>
     * @return
     */
    public <T extends Nodo, ID extends EliminarNodo> Resultado<T> getNode(ID wsId) {
        if (wsId instanceof EliminarCuadro) {
            return (Resultado<T>) getCuadro(wsId.getNodoId());
        } else if (wsId instanceof EliminarFuncion) {
            return (Resultado<T>) getFuncion(wsId.getNodoId());
        } else if (wsId instanceof EliminarSerie) {
            return (Resultado<T>) getSerie(wsId.getNodoId());
        }
        return null;
    }

    /**
     * Crea el dto de Node para el envio a GDIB
     *
     * @param cuadro
     * @return
     */
    private SerieNode convertirASerieNode(Serie serie) {
        SerieNode dto = new SerieNode();
        // Ponemos el codigo como nombre ya que queremos que la carpeta en alfresco se cree con el codigo como nombre
        dto.setName(serie.getCodigo());
        dto.setType(TiposObjetoSGD.SERIE_DOCUMENTAL);
        dto.setId(serie.getNodeId());
        Content content = new Content();
        content.setContent(serie.getContent());
        content.setEncoding(serie.getEncoding());
        content.setMimetype(serie.getMimeType());
        dto.setBinaryContent(content);

        setMetadataYAspectosSerie(dto, serie);

        return dto;
    }

    /**
     * Establece la lista de propiedades y aspectos del nodo serie
     *
     * @param dto
     * @param serie
     */
    private void setMetadataYAspectosSerie(SerieNode dto, Serie serie) {
        dto.setMetadataCollection(new HashSet<>());
        // Añadimos las propiedades obligatorias, ya se ha validado que estén informadas
        dto.getMetadataCollection().add(new Metadata(Constantes.CODIGO_CLASIFICACION_QNAME, serie.getCodigo()));
        dto.getMetadataCollection().add(new Metadata(Constantes.DENOMINACION_CLASE_QNAME, serie.getDenominacionClase()));
        dto.getMetadataCollection().add(new Metadata(Constantes.TIPO_CLASIFICACION_QNAME, serie.getTipoClasificacion()));
        dto.getMetadataCollection().add(new Metadata(Constantes.LOPD_QNAME, serie.getLopd().getValue()));
        dto.getMetadataCollection().add(new Metadata(Constantes.CONFIDENCIALIDAD_QNAME, serie.getConfidencialidad().getValue()));
        dto.getMetadataCollection().add(new Metadata(Constantes.TIPO_ACCESO_QNAME, serie.getTipoAcceso().getValue()));
        dto.getMetadataCollection().add(new Metadata(Constantes.VALOR_SECUNDARIO_QNAME, serie.getValorSecundario().getValue()));
        dto.getMetadataCollection().add(new Metadata(Constantes.TIPO_VALOR_QNAME, serie.getTipoValor().stream().map(x -> x.getValue()).collect(Collectors.toList())));
        dto.getMetadataCollection().add(new Metadata(Constantes.TIPO_DICTAMEN_QNAME, serie.getTipoDictamen()));
        dto.getMetadataCollection().add(new Metadata(Constantes.DOCUMENTO_ESENCIAL_QNAME, serie.getEsencial().booleanValue()));
        dto.getMetadataCollection().add(new Metadata(Constantes.PLAZO_RESELLADO_QNAME, serie.getResellado()));
        dto.getMetadataCollection().add(new Metadata(Constantes.PLAZO_UNIDAD_RESELLADO_QNAME, serie.getUnidadResellado().toString()));


        // Propiedades optativas, solo las metemos si tienen valor
        if(serie.getUsuariosAplicacion() != null && !serie.getUsuariosAplicacion().isEmpty()){
            dto.getMetadataCollection().add(new Metadata(Constantes.USUARIO_APLICACION_QNAME,serie.getUsuariosAplicacion()));
        }
        if(serie.getSeriesArgen() != null && !serie.getSeriesArgen().isEmpty()){
            dto.getMetadataCollection().add(new Metadata(Constantes.SERIE_ARGEN_QNAME,serie.getSeriesArgen()));
        }

        if (serie.getUnidadPlazoAccionDictaminada() != null) {
            dto.getMetadataCollection().add(new Metadata(Constantes.PLAZO_UNIDAD_ACCION_DICTAMINADA_QNAME, serie.getUnidadPlazoAccionDictaminada().toString()));
        }

        if (serie.getPlazoAccionDictaminada() != null) {
            dto.getMetadataCollection().add(new Metadata(Constantes.PLAZO_ACCION_DICTAMINADA_QNAME, serie.getPlazoAccionDictaminada()));
        }
        if (StringUtils.trimToNull(serie.getAccionDictaminada()) != null) {
            dto.getMetadataCollection().add(new Metadata(Constantes.ACCION_DICTAMINADA_QNAME, serie.getAccionDictaminada()));
        }
        if (serie.getCondicionReutilizacion() != null && !serie.getCondicionReutilizacion().isEmpty()) {
            dto.getMetadataCollection().add(new Metadata(Constantes.CONDICION_REUTILIZACION_QNAME, serie.getCondicionReutilizacion()));
        }
        if (serie.getCodigoLimitacion() != null && !serie.getCodigoLimitacion().isEmpty()) {
            dto.getMetadataCollection().add(new Metadata(Constantes.CODIGO_CAUSA_LIMITACION_QNAME, serie.getCodigoLimitacion()));
        }

        // De momento no hay ningun aspecto que tenga todas las propiedades como optativas, por lo tanto vamos a meter
        // siempre todos los aspectos, si en el futuro hay alguno que pueda no estar, habria que comprobar aqui si alguna
        // de sus propiedades esta informada para meterlo o no
        dto.setAspects(Aspects.getAspectosSerie());

    }

    /**
     * Crea el dto de Node para el envio a GDIB
     *
     * @param funcion
     * @return
     */
    private FunctionNode convertirAFunctionNode(Funcion funcion) {
        FunctionNode dto = new FunctionNode();
        dto.setId(funcion.getNodeId());
        dto.setName(funcion.getCodigo());
        dto.setType(TiposObjetoSGD.FUNCION);
        setMetadataYAspectosFuncion(dto, funcion);
        return dto;
    }

    /**
     * Establece la lista de propiedades y aspectos del nodo funcion
     *
     * @param dto
     * @param funcion
     */
    private void setMetadataYAspectosFuncion(FunctionNode dto, Funcion funcion) {
        dto.setMetadataCollection(new HashSet<>());
        // Añadimos las propiedades obligatorias, ya se ha validado que estén informadas
        dto.getMetadataCollection().add(new Metadata(Constantes.CODIGO_FUNCION_QNAME, funcion.getCodigo()));
        dto.getMetadataCollection().add(new Metadata(Constantes.FUNCION_PADRE_QNAME, funcion.getFuncionPadre().booleanValue()));

        // Si la funcion esta obsoleta, añadimos el aspecto
        if (Estado.OBSOLET == funcion.getEstado()) {
            // De momento funcion solo tiene un aspecto, si tuviese mas habria que discriminar en este set
            dto.setAspects(Aspects.getAspectosFuncion());
        }
    }

    /**
     * Crea el deto de serie a partir del nodo devuelto
     *
     * @param serie
     * @return
     */
    private Serie toSerie(SerieNode serie) {
        Serie dto = new Serie();
        dto.setCodigo(serie.getName());
        dto.setNodeId(serie.getId());
        // FIXME : En un principio no hace falta los demas atributos asi que no se setean, si algun dia hacen falta,
        //  se añadiran aqui
        return dto;
    }

    /**
     * Crea el dto de funcion a partir del nodo devuelto
     *
     * @param funcion
     * @return
     */
    private Funcion toFuncion(FunctionNode funcion) {
        Funcion dto = new Funcion();
        dto.setCodigo(funcion.getName());
        dto.setNodeId(funcion.getId());
        Metadata funcionPadre = getMetadata(funcion.getMetadataCollection(),Constantes.FUNCION_PADRE_QNAME);
        dto.setFuncionPadre(funcionPadre == null? null : (Boolean) funcionPadre.getValue());
        Boolean isObsolete = getEstado(funcion.getAspects());
        if(isObsolete){
            dto.setEstado(Estado.OBSOLET);
        }
        return dto;
    }


    /**
     * Crea el dto de cuadro a partir del nodo devuelto
     *
     * @param cuadro
     * @return
     */
    private CuadroClasificacion toCuadroClasificacion(RootNode cuadro) {
        CuadroClasificacion dto = new CuadroClasificacion();
        dto.setCodigo(cuadro.getName());
        dto.setNodeId(cuadro.getId());
        Boolean isObsolete = getEstado(cuadro.getAspects());
        if(isObsolete){
            dto.setEstado(Estado.OBSOLET);
        }
        return dto;
    }

    /**
     * Devuelve true si entre la lista de aspectos esta el de obsoleto
     *
     * @param aspects
     * @return
     */
    private Boolean getEstado(List<Aspects> aspects) {
        for(Aspects entry : aspects){
            if(entry.equals(Aspects.OBSOLETO.getValue())){
                return true;
            }
        }
        return false;
    }

    /**
     * En la lista de metadatos del nodo, encuentra la propiedad indicada
     *
     * @param metadataCollection
     * @param qname
     * @return
     */
    private Metadata getMetadata(Set<Metadata> metadataCollection, String qname) {
        for(Metadata entry : metadataCollection){
            if(entry.getQname().equalsIgnoreCase(qname)){
                return entry;
            }
        }
        return null;
    }

    /**
     * Crea el dto de Node para el envio a GDIB
     *
     * @param cuadro
     * @return
     */
    private RootNode convertirAClassificationRootNode(CuadroClasificacion cuadro) {
        RootNode dto = new RootNode();
        dto.setId(cuadro.getNodeId());
        dto.setName(cuadro.getCodigo());
        dto.setType(TiposObjetoSGD.CUADRO_CLASIFICACION);
        setMetadataYAspectosCuadro(dto, cuadro);
        return dto;
    }

    private void setMetadataYAspectosCuadro(RootNode dto, CuadroClasificacion cuadro) {
         dto.setMetadataCollection(new HashSet<>());
        // Añadimos las propiedades cuando haya
        dto.getMetadataCollection().add(new Metadata(Constantes.CODIGO_CUADRO_QNAME,cuadro.getCodigo()));

        // Añadimos los aspectos
        if(Estado.OBSOLET == cuadro.getEstado()) {
            // Por el momento solo hay un aspecto, si en el futuro hubiese mas habria que cambiar este set
            dto.setAspects(Aspects.getAspectosCuadro());
        }
    }

    private ParamNodeId generarParametrosNodeId(String identificador) {
        ParamNodeId param = new ParamNodeId();

        param.setId(identificador);

        return param;
    }
}

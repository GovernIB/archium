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
import java.util.*;

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
     * @param result
     * @param servicio
     * @param peticion
     * @param entityResult
     * @param <T>
     * @return
     * @throws IOException
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
        output = postCall(resultado, Servicios.MODIFY_SERIE, peticion, SetSerieResult.class);
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
        output = postCall(resultado, Servicios.MODIFY_FUNCTION, peticion, SetFunctionResult.class);
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
        SetRootResult result = null;
        ResultadoSimple resultado = new ResultadoSimple();

        RootNode rootNode = convertirAClassificationRootNode(cuadro);

        Request<ParamSetRoot> request = new Request<ParamSetRoot>();
        SetRoot peticion = new SetRoot();
        ParamSetRoot param = new ParamSetRoot();
        param.setRoot(rootNode);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);
        peticion.setSetRootRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(resultado, Servicios.SET_CLASSIFICATION_ROOT, peticion, SetFunctionResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return resultado;
        }


        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            result = (SetRootResult) output.getContenido();
            resultado.setCodigoResultado(result.getSetRootResult().getResult().getCode());
            resultado.setMsjResultado(result.getSetRootResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            resultado.setCodigoResultado(except.getException().getCode());
            resultado.setMsjResultado(except.getException().getDescription());
        }

        return resultado;
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

        RemoveCuadroResult result = null;
        ResultadoSimple resultado = new ResultadoSimple();

        Request<ParamNodeId> request = new Request<ParamNodeId>();
        ParamNodeId param;
        RemoveCuadro peticion = new RemoveCuadro();

        param = generarParametrosNodeId(nodeId);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);

        peticion.setRemoveRootRequest(request);

        ResultadoJersey output;

        // Realizamos la llamada
        output = postCall(resultado, Servicios.REMOVE_CLASSIFICATION_ROOT, peticion, RemoveCuadroResult.class);
        // Si el output es null es que se produjo una excepcion
        if (output == null) {
            return resultado;
        }

        if (output.getEstadoRespuestaHttp() == Constantes.CodigosRespuesta.HTTP_RESPUESTA_OK.getValue()) {
            result = (RemoveCuadroResult) output.getContenido();
            resultado.setCodigoResultado(result.getRemoveRootResult().getResult().getCode());
            resultado.setMsjResultado(result.getRemoveRootResult().getResult().getDescription());
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
     * @param wsDto
     * @param <T>
     * @return
     */
    public <ID extends Id> ResultadoSimple borrarNodo(ID wsId) {
        if (wsId instanceof RootId) {
            return borrarCuadro(wsId.getId());
        } else if (wsId instanceof FunctionId) {
            return borrarFuncion(wsId.getId());
        } else if (wsId instanceof SerieId) {
            return borrarSerie(wsId.getId());
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
        dto.getMetadataCollection().add(new Metadata(Constantes.TIPO_VALOR_QNAME, toStringList(serie.getTipoValor())));
        dto.getMetadataCollection().add(new Metadata(Constantes.TIPO_DICTAMEN_QNAME, serie.getTipoDictamen()));
        dto.getMetadataCollection().add(new Metadata(Constantes.DOCUMENTO_ESENCIAL_QNAME, serie.getEsencial().booleanValue()));
        dto.getMetadataCollection().add(new Metadata(Constantes.PLAZO_RESELLADO_QNAME, serie.getResellado()));
        dto.getMetadataCollection().add(new Metadata(Constantes.PLAZO_UNIDAD_RESELLADO_QNAME, serie.getUnidadResellado().toString()));


        // Propiedades optativas, solo las metemos si tienen valor
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
     * Convierte la lista de enumerados en una de String
     *
     * @param tipoValor
     * @return
     */
    private List<String> toStringList(List<TipoValor> tipoValor) {
        if (tipoValor != null && !tipoValor.isEmpty()) {
            List<String> valores = new ArrayList<>();
            for (TipoValor t : tipoValor) {
                valores.add(t.getValue());
            }
            return valores;
        } else {
            return null;
        }
    }

    /**
     * Crea el dto de Node para el envio a GDIB
     *
     * @param cuadro
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

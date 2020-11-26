package es.caib.archium.csgd.apirest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import es.caib.archium.csgd.apirest.constantes.Servicios;
import es.caib.archium.csgd.apirest.constantes.TipoValor;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.*;
import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamCreateClassificationTable;
import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamCreateFunction;
import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamCreateSerie;
import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;
import es.caib.archium.csgd.apirest.csgd.entidades.resultados.*;
import es.caib.archium.csgd.apirest.csgd.peticiones.*;
import es.caib.archium.csgd.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.csgd.apirest.facade.pojos.Funcion;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.csgd.apirest.facade.pojos.cabecera.CabeceraAplicacion;
import es.caib.archium.csgd.apirest.facade.pojos.cabecera.CabeceraLogin;
import es.caib.archium.csgd.apirest.facade.pojos.cabecera.CabeceraPeticion;
import es.caib.archium.csgd.apirest.facade.pojos.cabecera.CabeceraTramite;
import es.caib.archium.csgd.apirest.facade.resultados.Resultado;
import es.caib.archium.csgd.apirest.facade.resultados.ResultadoSimple;
import es.caib.archium.csgd.apirest.jerseyclient.JerseyClientGet;
import es.caib.archium.csgd.apirest.jerseyclient.ResultadoJersey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.TransformerException;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

@Stateless
public class ApiCSGDServices {

    protected final Logger log = LoggerFactory.getLogger(ApiCSGDServices.class);


    public static final String VERSION_SERVICIO = "1.0";

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

    public Resultado<String> crearSerie(Serie serie, String funcionPadre) throws IOException {
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
        try {
            log.debug("Se realiza la llamada con parametros de entrada: " + peticion.toString());
            output = JerseyClientGet.post(this.urlBase, Servicios.CREATE_SERIE, peticion, CreateSerieResult.class, trazas);
        } catch (UnrecognizedPropertyException e) {
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            result.setCodigoResultado("500");
            result.setMsjResultado("Se ha producido una excepcion en la respuesta a la peticion");
            return result;
        }

        if (output.getEstadoRespuestaHttp() == 200) {
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

    private SerieNode convertirASerieNode(Serie serie) {
        SerieNode dto = new SerieNode();

        dto.setAccionDictaminada(serie.getAccionDictaminada());
        dto.setCausaLimitacion(serie.getCausaLimitacion());
        dto.setCodigoClasificacion(serie.getCodigoClasificacion());
        dto.setCondicionReutilizacion(serie.getCondicionReutilizacion());
        dto.setConfidencialidad(serie.getConfidencialidad() == null ? null : serie.getConfidencialidad().getValue());
        dto.setEsencial(serie.getEsencial());
        dto.setLopd(serie.getLopd() == null ? null : serie.getLopd().getValue());
        dto.setResellado(serie.getResellado());
        dto.setTermino(serie.getTermino());
        dto.setTerminoAccionDictaminada(serie.getTerminoAccionDictaminada());
        dto.setTipoAcceso(serie.getTipoAcceso() == null ? null : serie.getTipoAcceso().getValue());
        dto.setTipoClasificacion(serie.getTipoClasificacion() == null ? null : serie.getTipoClasificacion());
        dto.setTipoDictamen(serie.getTipoDictamen() == null ? null : serie.getTipoDictamen().getValue());
        dto.setTipoValor(serie.getTipoValor() == null ? null : toStringList(serie.getTipoValor()));
        dto.setValorSecundario(serie.getValorSecundario() == null ? null : serie.getValorSecundario());
        dto.setContent(serie.getContent());
        return dto;
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


    public ResultadoSimple borrarSerie(String nodeId) throws IOException {

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
        try {
            output = JerseyClientGet.post(urlBase, Servicios.REMOVE_SERIE, peticion, RemoveSerieResult.class, trazas);
        } catch (UnrecognizedPropertyException e) {
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            ResultadoSimple rs = new ResultadoSimple();
            rs.setCodigoResultado("500");
            rs.setMsjResultado("Se ha producido una excepcion en la respuesta a la peticion");
            return rs;
        }

        if (output.getEstadoRespuestaHttp() == 200) {
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

    public Resultado<String> crearFuncion(Funcion funcion, String parentId) throws IOException {

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
        try {
            output = JerseyClientGet.post(this.urlBase, Servicios.CREATE_FUNCTION, peticion, CreateFunctionResult.class, trazas);
        } catch (UnrecognizedPropertyException e) {
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            result.setCodigoResultado("500");
            result.setMsjResultado("Se ha producido una excepcion en la respuesta a la peticion");
            return result;
        }
        if (output.getEstadoRespuestaHttp() == 200) {
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

    private FunctionNode convertirAFunctionNode(Funcion funcion) {
        FunctionNode dto = new FunctionNode();
        dto.setCode(funcion.getCodigo());
        dto.setState(funcion.getEstado() == null ? null : funcion.getEstado().toString());
        return dto;
    }

    public ResultadoSimple borrarFuncion(String nodeId) throws IOException {

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
        try {
            output = JerseyClientGet.post(urlBase, Servicios.REMOVE_FUNCTION, peticion, RemoveFunctionResult.class, trazas);
        } catch (UnrecognizedPropertyException e) {
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            ResultadoSimple rs = new ResultadoSimple();
            rs.setCodigoResultado("500");
            rs.setMsjResultado("Se ha producido una excepcion en la respuesta a la peticion");
            return rs;
        }

        if (output.getEstadoRespuestaHttp() == 200) {
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


    public Resultado<String> crearCuadro(CuadroClasificacion cuadro) throws IOException {

        Resultado<String> result = new Resultado<>();
        ClassificationTableNode classificationTableNode = convertirAClassificationTableNode(cuadro);

        Request<ParamCreateClassificationTable> request = new Request<ParamCreateClassificationTable>();
        CreateClassificationTable peticion = new CreateClassificationTable();
        ParamCreateClassificationTable param = new ParamCreateClassificationTable();
        param.setClassificationRoot(classificationTableNode);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);
        peticion.setCreateClassificationRootRequest(request);

        ResultadoJersey output;
        try {
            output = JerseyClientGet.post(this.urlBase, Servicios.CREATE_CLASSIFICATION_TABLE, peticion, CreateClassificationTableResult.class, trazas);
        } catch (UnrecognizedPropertyException e) {
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            result.setCodigoResultado("500");
            result.setMsjResultado("Se ha producido una excepcion en la respuesta a la peticion");
            return result;
        }

        if (output.getEstadoRespuestaHttp() == 200) {
            CreateClassificationTableResult resultado = (CreateClassificationTableResult) output.getContenido();
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

    private ClassificationTableNode convertirAClassificationTableNode(CuadroClasificacion cuadro) {
        ClassificationTableNode dto = new ClassificationTableNode();
        dto.setCode(cuadro.getCodigo());
        dto.setState(cuadro.getEstado() == null ? null : cuadro.getEstado().toString());
        return dto;
    }

    public ResultadoSimple borrarCuadro(String nodeId) throws IOException {

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
        try {
            output = JerseyClientGet.post(urlBase, Servicios.REMOVE_CLASSIFICATION_TABLE, peticion, RemoveCuadroResult.class, trazas);
        } catch (UnrecognizedPropertyException e) {
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            ResultadoSimple rs = new ResultadoSimple();
            rs.setCodigoResultado("500");
            rs.setMsjResultado("Se ha producido una excepcion en la respuesta a la peticion");
            return rs;
        }

        if (output.getEstadoRespuestaHttp() == 200) {
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

    // -------------------------------------------------
    // -------------------------------------------------
    // ------------------- UTILITATS -------------------
    // -------------------------------------------------
    // -------------------------------------------------

    protected ServiceHeader internalServiceHeader = null;

    protected ServiceHeader getServiceHeader() {
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

    ;

    /**
     * @param cabeceraApp
     * @param cabeceraLogin
     * @param cabeceraTramite
     * @return
     */
    protected static CabeceraPeticion constructCabeceraPeticion(CabeceraAplicacion cabeceraApp,
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

    private ParamNodeId generarParametrosNodeId(String identificador)
            throws JsonProcessingException {
        ParamNodeId param = new ParamNodeId();

        param.setId(identificador);

        return param;
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
}

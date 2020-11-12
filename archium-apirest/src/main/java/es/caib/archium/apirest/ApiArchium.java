package es.caib.archium.apirest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.exc.UnrecognizedPropertyException;
import es.caib.archium.apirest.constantes.Servicios;
import es.caib.archium.apirest.csgd.entidades.comunes.*;
import es.caib.archium.apirest.csgd.entidades.parametrosllamada.*;
import es.caib.archium.apirest.csgd.entidades.resultados.*;
import es.caib.archium.apirest.csgd.peticiones.*;
import es.caib.archium.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.apirest.facade.pojos.Funcion;
import es.caib.archium.apirest.facade.pojos.Serie;
import es.caib.archium.apirest.facade.pojos.borrar.*;
import es.caib.archium.apirest.facade.pojos.cabecera.CabeceraAplicacion;
import es.caib.archium.apirest.facade.pojos.cabecera.CabeceraLogin;
import es.caib.archium.apirest.facade.pojos.cabecera.CabeceraPeticion;
import es.caib.archium.apirest.facade.pojos.cabecera.CabeceraTramite;
import es.caib.archium.apirest.facade.resultados.Resultado;
import es.caib.archium.apirest.facade.resultados.ResultadoSimple;
import es.caib.archium.apirest.jerseyclient.JerseyClientGet;
import es.caib.archium.apirest.jerseyclient.ResultadoJersey;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.ejb.Stateless;
import java.io.IOException;
import java.util.LinkedHashMap;

@Stateless
public class ApiArchium {

    protected final Logger log = LoggerFactory.getLogger(ApiArchium.class);

    public static final String VERSION_SERVICIO = "1.0";

    protected final CabeceraPeticion cabeceraPeticion;

    protected final String urlBase;

    boolean trazas = true;


    public ApiArchium(String urlBase, CabeceraPeticion cabeceraPeticion) {
        this.cabeceraPeticion = cabeceraPeticion;
        this.urlBase = urlBase;
    }

    public ApiArchium(String urlBase, CabeceraAplicacion cabeceraApp, CabeceraLogin cabeceraLogin,
                      CabeceraTramite cabeceraTramite) {

        this(urlBase, constructCabeceraPeticion(cabeceraApp, cabeceraLogin, cabeceraTramite));
    }

    public CreateSerieResult crearSerie(Serie serie, String funcionPadre) throws IOException {
        log.debug("Se prepara la llamada en el cliente para crearSerie");

        CreateSerieResult result = null;
        SerieNode serieNode = convertirASerieNode(serie);

        Request<ParamCreateSerie> request = new Request<ParamCreateSerie>();
        CreateSerie peticion = new CreateSerie();
        ParamCreateSerie param = new ParamCreateSerie();
        param.setSerie(serieNode);
        param.setParent(funcionPadre);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);
        peticion.setCreateSerieRequest(request);

        ResultadoJersey output;
        try {
            log.debug("Se realiza la llamada con parametros de entrada: "+peticion.toString());
            output = JerseyClientGet.post(this.urlBase, Servicios.CREATE_SERIE, peticion, CreateSerieResult.class, trazas);
        }catch (UnrecognizedPropertyException e){
            log.error("Se ha producido un error desconocido en la llamada en el el cliente: " + e);
            RespuestaGenerica<String> rg = new RespuestaGenerica<>();
            ResultData rd = new ResultData();
            rd.setCode("500");
            rd.setDescription("Se ha producido una excepcion en la respuesta a la peticion");
            rg.setResult(rd);
            result.setCreateSerieResult(rg);
            return result;
        }

        log.debug("Llamada realizada con exito, codigo de la respuesta: "+output.getEstadoRespuestaHttp());
        if(output.getEstadoRespuestaHttp() == 200){
            result = (CreateSerieResult) output.getContenido();
        }else{
            ExceptionResult except = (ExceptionResult) output.getContenido();
            RespuestaGenerica<String> csr = new RespuestaGenerica<>();
            ResultData rd = new ResultData();
            rd.setCode(except.getException().getCode());
            rd.setDescription(except.getException().getDescription());
            csr.setResult(rd);
            result = new CreateSerieResult();
            result.setCreateSerieResult(csr);
        }
        return result;
    }

    private SerieNode convertirASerieNode(Serie serie) {

        //TODO: hacer converter
        return null;
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
        output = JerseyClientGet.post(urlBase, Servicios.REMOVE_SERIE, peticion,RemoveSerieResult.class, trazas);

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

    public CreateFunctionResult crearFuncion(Funcion funcion, String parentId) throws IOException {

        CreateFunctionResult result = null;
        FunctionNode functionNode = convertirAFunctionNode(funcion);

        Request<ParamCreateFunction> request = new Request<ParamCreateFunction>();
        CreateFunction peticion = new CreateFunction();
        ParamCreateFunction param = new ParamCreateFunction();
        param.setFunction(functionNode);
        param.setParent(parentId);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);
        peticion.setCreateFuncionRequest(request);

        ResultadoJersey output;
        output = JerseyClientGet.post(this.urlBase,Servicios.CREATE_FUNCTION,peticion,CreateFunctionResult.class,trazas);

        if(output.getEstadoRespuestaHttp() == 200){
            result = (CreateFunctionResult) output.getContenido();
        }else{
            ExceptionResult except = (ExceptionResult) output.getContenido();
            RespuestaGenerica<String> cfr = new RespuestaGenerica<>();
            ResultData rd = new ResultData();
            rd.setCode(except.getException().getCode());
            rd.setDescription(except.getException().getDescription());
            cfr.setResult(rd);
            result = new CreateFunctionResult();
            result.setCreateFunctionResult(cfr);
        }
        return result;
    }

    private FunctionNode convertirAFunctionNode(Funcion funcion) {
        //TODO: Hacer converter
        return null;
    }

    public ResultadoSimple borrarFuncion(String nodeId) throws IOException {

        RemoveFuncionResult result = null;
        ResultadoSimple resultado = new ResultadoSimple();

        Request<ParamNodeId> request = new Request<ParamNodeId>();
        ParamNodeId param;
        RemoveSerie peticion = new RemoveSerie();

        param = generarParametrosNodeId(nodeId);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);

        peticion.setRemoveSerieRequest(request);

        ResultadoJersey output;
        output = JerseyClientGet.post(urlBase, Servicios.REMOVE_FUNCTION, peticion,RemoveFuncionResult.class, trazas);

        if (output.getEstadoRespuestaHttp() == 200) {
            result = (RemoveFuncionResult) output.getContenido();
            resultado.setCodigoResultado(result.getRemoveFuncionResult().getResult().getCode());
            resultado.setMsjResultado(result.getRemoveFuncionResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            resultado.setCodigoResultado(except.getException().getCode());
            resultado.setMsjResultado(except.getException().getDescription());
        }

        return resultado;
    }


    public CreateClassificationTableResult crearCuadro(CuadroClasificacion cuadro) throws IOException {

        CreateClassificationTableResult result = null;
        ClassificationTableNode classificationTableNode = convertirAClassificationTableNode(cuadro);

        Request<ParamCreateClassificationTable> request = new Request<ParamCreateClassificationTable>();
        CreateClassificationTable peticion = new CreateClassificationTable();
        ParamCreateClassificationTable param = new ParamCreateClassificationTable();
        param.setClassificationTable(classificationTableNode);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);
        peticion.setCreateClassificationTableRequest(request);

        ResultadoJersey output;
        output = JerseyClientGet.post(this.urlBase,Servicios.CREATE_CLASSIFICATION_TABLE,peticion,CreateClassificationTableResult.class,trazas);

        if(output.getEstadoRespuestaHttp() == 200){
            result = (CreateClassificationTableResult) output.getContenido();
        }else{
            ExceptionResult except = (ExceptionResult) output.getContenido();
            RespuestaGenerica<String> cctr = new RespuestaGenerica<>();
            ResultData rd = new ResultData();
            rd.setCode(except.getException().getCode());
            rd.setDescription(except.getException().getDescription());
            cctr.setResult(rd);
            result = new CreateClassificationTableResult();
            result.setCreateClassificationTableResult(cctr);
        }
        return result;
    }

    private ClassificationTableNode convertirAClassificationTableNode(CuadroClasificacion cuadro) {
        //TODO: Hacer converter
        return null;
    }

    public ResultadoSimple borrarCuadro(String nodeId) throws IOException {

        RemoveCuadroResult result = null;
        ResultadoSimple resultado = new ResultadoSimple();

        Request<ParamNodeId> request = new Request<ParamNodeId>();
        ParamNodeId param;
        RemoveSerie peticion = new RemoveSerie();

        param = generarParametrosNodeId(nodeId);

        request.setServiceHeader(getServiceHeader());
        request.setParam(param);

        peticion.setRemoveSerieRequest(request);

        ResultadoJersey output;
        output = JerseyClientGet.post(urlBase, Servicios.REMOVE_CLASSIFICATION_TABLE, peticion,RemoveCuadroResult.class, trazas);

        if (output.getEstadoRespuestaHttp() == 200) {
            result = (RemoveCuadroResult) output.getContenido();
            resultado.setCodigoResultado(result.getRemoveCuadroResult().getResult().getCode());
            resultado.setMsjResultado(result.getRemoveCuadroResult().getResult().getDescription());
        } else {
            ExceptionResult except = (ExceptionResult) output.getContenido();
            resultado.setCodigoResultado(except.getException().getCode());
            resultado.setMsjResultado(except.getException().getDescription());
        }

        return resultado;
    }

    /**
     * Antic getDocument
     * @param uuid
     * @param retrieveContent
     * @return
     * @throws IOException
     */
    public Resultado<Documento> obtenerDocumento(String uuid, boolean retrieveContent) throws IOException {

        // param = generarParametrosllamadaGetDocument(uuid,content);
        ParamGetDocument param = new ParamGetDocument();
        DocumentId documentId = new DocumentId();
        documentId.setNodeId(uuid);
        param.setDocumentId(documentId);
        param.setContent(String.valueOf(retrieveContent));

        Request<ParamGetDocument> request = new Request<ParamGetDocument>();
        request.setServiceHeader(getServiceHeader());
        request.setParam(param);

        GetDocument peticion = new GetDocument();
        peticion.setGetDocumentRequest(request);

        ResultadoJersey output;
        output = JerseyClientGet.post(urlBase, Servicios.GET_DOC, peticion,GetDocumentResult.class, trazas);

    /*
    FileOutputStream fos = new FileOutputStream("rest.txt");
    fos.write(output.getContenido().getBytes());
    fos.flush();
    fos.close();
    */


        Resultado<Documento> resultado = new Resultado<Documento>();

        if (output.getEstadoRespuestaHttp() == 200) {

            GetDocumentResult result = (GetDocumentResult) output.getContenido();
            resultado.setCodigoResultado(result.getGetDocumentResult().getResult().getCode());
            resultado.setMsjResultado(result.getGetDocumentResult().getResult().getDescription());
            resultado.setElementoDevuelto(toDocument(result.getGetDocumentResult().getResParam()));
        } else {
            ExceptionResult result = (ExceptionResult) output.getContenido();
            resultado.setCodigoResultado(result.getException().getCode());
            resultado.setMsjResultado(result.getException().getDescription());
            resultado.setElementoDevuelto(null);
        }

        return resultado;
    }

    private Documento toDocument(DocumentNode resParam) {
        Documento doc = new Documento();
        doc.setName(resParam.getName());
        doc.setId(resParam.getId());
        return doc;
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

        param.setNodeId(identificador);

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

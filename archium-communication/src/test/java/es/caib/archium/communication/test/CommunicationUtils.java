package es.caib.archium.communication.test;

import es.caib.archium.commons.utils.Constants;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.iface.CSGDCuadroService;
import es.caib.archium.communication.iface.CSGDFuncionService;
import es.caib.archium.communication.iface.CSGDSerieService;
import es.caib.archium.csgd.apirest.constantes.*;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.FunctionId;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RootId;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.SerieId;
import es.caib.archium.csgd.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.csgd.apirest.facade.pojos.Funcion;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import org.junit.AfterClass;
import org.junit.Assert;
import org.junit.BeforeClass;

import javax.ejb.embeddable.EJBContainer;
import java.util.Arrays;

public class CommunicationUtils {

    protected static CSGDCuadroService cuadroService;

    protected static CSGDFuncionService funcionService;

    protected static CSGDSerieService serieService;

    private static EJBContainer ejbContainer;


    @BeforeClass
    public static void setUpClass() throws Exception {

        // Instantiate an Embeddable EJB Container
        ejbContainer = javax.ejb.embeddable.EJBContainer.createEJBContainer();
        cuadroService = (CSGDCuadroService) ejbContainer.getContext().lookup("CSGDCuadroService");
        funcionService = (CSGDFuncionService) ejbContainer.getContext().lookup("CSGDFuncionService");
        serieService = (CSGDSerieService) ejbContainer.getContext().lookup("CSGDSerieService");
    }

    @AfterClass
    public static void tearDownClass() throws Exception {
        // Close the Embeddable EJB Container, releasing all resources
        ejbContainer.close();
        // Shutdown the Derby Database server
//        derbyServer.shutdown();
    }

//    @Before
//    public void setUp() throws NamingException {
//        // Inititalize the data in the domain model
//        cuadroService = (CSGDCuadroService) ejbContainer.getContext().lookup("CSGDCuadroService");
//        funcionService = (CSGDFuncionService) ejbContainer.getContext().lookup("CSGDFuncionService");
//        serieService = (CSGDSerieService) ejbContainer.getContext().lookup("CSGDSerieService");
////        WineAppService wineAppSvcFacade =
////                (WineAppService) ejbContainer.getContext().lookup("java:global/
////                        classes/WineAppService");
//    }

    /**
     * Genera dto de un cuadro de clasificacion
     *
     * @return
     */
    protected CuadroClasificacion generarDTOCuadro() {
        CuadroClasificacion dto = new CuadroClasificacion();
        dto.setEstado(Estado.VIGENT);
        dto.setCodigo("CODROOT1");
        return dto;
    }

    /**
     * Genera el DTO de la funcion SIN FUNCION PADRE
     *
     * @return
     */
    protected Funcion generarDTOFuncion() {
        Funcion dto = new Funcion();
        dto.setFuncionPadre(false);
        dto.setEstado(Estado.VIGENT);
        dto.setCodigo("CODFUNC01");
        return dto;
    }

    /**
     * Genera el dto de serie
     *
     * @return
     */
    protected Serie generarDTOSerie() {
        Serie dto = new Serie();
        dto.setUnidadPlazoAccionDictaminada(UnidadPlazo.A);
        dto.setPlazoAccionDictaminada(4);
        dto.setAccionDictaminada("accionDictaminada");
        dto.setDenominacionClase("denominacionClase");
        dto.setUnidadResellado(UnidadPlazo.DN);
        dto.setResellado(2);
        dto.setValorSecundario(ValorSecundario.SI);
        dto.setCodigo("codigoSerie");
        dto.setEsencial(true);
        dto.setCondicionReutilizacion("condicionReutilizacion");
        dto.setTipoClasificacion(Constants.ArchiumConstants.TIPO_CLASIFICACION_VALOR_POR_DEFECTO.getValue());
        dto.setConfidencialidad(Confidencialidad.MEDIO);
        dto.setCodigoLimitacion(Arrays.asList("A", "B"));
        dto.setLopd(LOPD.BASICO);
        dto.setTipoValor(Arrays.asList(TipoValor.ADMINISTRATIVO));
        dto.setTipoDictamen(TipoDictamen.CP);
        dto.setTipoAcceso(TipoAcceso.LIBRE);
        dto.setEncoding("UTF-8");
        dto.setMimeType("application/xml");
        dto.setContent("PD94bWwgdmVyc2lvbj0iMS4wIiBlbmNvZGluZz0iVVRGLTgiPz4KPHNlcmllIGNvZGlnb19jbGFzaWZpY2FjaW9uPSJTRDA5OTk4IiBkb2N1bWVudG9fZXNlbmNpYWw9IlPDrSI+Cjwvc2VyaWU+");

        return dto;
    }

    /**
     * Llama a sincronizar cuadro de clasaificacion
     *
     * @param dto
     * @return
     */
    protected String sincronizarCuadro(CuadroClasificacion dto) {
        try {
            return cuadroService.synchronizeNode(dto);
        } catch (Exception e) {
            // Devuelve error si no se crea correctamente
            Assert.fail("Se ha producido un error sincronizando el cuadro " + e);
        }
        return null;
    }

    /**
     * Llama a sincronizar la funcion, devuelve la excepcion
     *
     * @param dto
     * @return
     */
    protected String sincronizarFuncionSinCatch(Funcion dto, String parentId) throws CSGDException {
        return funcionService.synchronizeNode(dto, parentId);
    }

    /**
     * Llama al sincronizar funcion controlando la excepcion
     *
     * @param dto
     * @return
     */
    protected String sincronizarFuncion(Funcion dto, String parentId) {
        try {
            return this.sincronizarFuncionSinCatch(dto, parentId);
        } catch (Exception e) {
            // Devuelve error si no se crea correctamente
            Assert.fail("Se ha producido un error sincronizando la funcion " + e);
        }
        return null;
    }

    /**
     * Llama a sincronizar la funcion, devuelve la excepcion
     *
     * @param dto
     * @return
     */
    protected String sincronizarSerieSinCatch(Serie dto, String parentId) throws CSGDException {
        return serieService.synchronizeNode(dto, parentId);
    }

    /**
     * Llama al sincronizar serie controlando la excepcion
     *
     * @param dto
     * @return
     */
    protected String sincronizarSerie(Serie dto, String parentId) {
        try {
            return this.sincronizarSerieSinCatch(dto, parentId);
        } catch (Exception e) {
            // Devuelve error si no se crea correctamente
            Assert.fail("Se ha producido un error sincronizando la serie " + e);
        }
        return null;
    }

    /**
     * Borra el cuadro indicado
     *
     * @param nodeId
     */
    protected void borrarCuadro(String nodeId) {
        RootId rootId = new RootId(nodeId);
        try {
            cuadroService.deleteNode(rootId);
        } catch (Exception e) {
            // Devuelve error si no se borra correctamente
            Assert.fail("Se ha producido un error borrando el cuadro " + e);
        }
    }


    /**
     * Borra la funcion y su padre
     *
     * @param cuadroId
     * @param funciones
     */
    protected void borrarFuncion(String cuadroId, String... funciones) {
        for (String funcionId : Arrays.asList(funciones)) {
            borrarFuncion(funcionId);
        }
        borrarCuadro(cuadroId);
    }

    /**
     * Borra la funcion
     *
     * @param funcionId
     */
    protected void borrarFuncion(String funcionId) {
        FunctionId nodeId = new FunctionId(funcionId);
        try {
            funcionService.deleteNode(nodeId);
        } catch (Exception e) {
            // Devuelve error si no se borra correctamente
            Assert.fail("Se ha producido un error borrando la funcion " + e);
        }
    }

    /**
     * Borra la serie y sus padres
     *
     * @param cuadroId
     * @param funciones
     */
    protected void borrarSerie(String cuadroId, String serieId, String... funciones) {
        borrarSerie(serieId);
        for (String funcionId : Arrays.asList(funciones)) {
            borrarFuncion(funcionId);
        }
        borrarCuadro(cuadroId);
    }

    /**
     * Borra la serie
     *
     * @param serieId
     */
    protected void borrarSerie(String serieId) {
        SerieId nodeId = new SerieId(serieId);
        try {
            serieService.deleteNode(nodeId);
        } catch (Exception e) {
            // Devuelve error si no se borra correctamente
            Assert.fail("Se ha producido un error borrando la serie " + e);
        }
    }


}

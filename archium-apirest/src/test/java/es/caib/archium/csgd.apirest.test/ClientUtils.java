package es.caib.archium.csgd.apirest.test;

import es.caib.archium.csgd.apirest.ApiCSGDServices;
import es.caib.archium.csgd.apirest.constantes.*;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.FunctionId;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RootId;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.SerieId;
import es.caib.archium.csgd.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.csgd.apirest.facade.pojos.Funcion;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.csgd.apirest.facade.resultados.Resultado;
import es.caib.archium.csgd.apirest.facade.resultados.ResultadoSimple;
import org.junit.Assert;

import java.util.Arrays;

public class ClientUtils {


    protected static ApiCSGDServices client;

    protected static final String URL_BASE = "http://sdesfiresb2.caib.es:8280";
    private static final String CODIGO_RESULTADO_OK = "COD_000";
    private static final int HTTP_RESPUESTA_OK = 200;

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
    protected Serie generarDTOSerie(){
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
        dto.setTipoClasificacion("Funcional");
        dto.setConfidencialidad(Confidencialidad.MEDIO);
        dto.setCodigoLimitacion(Arrays.asList("A","B"));
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
    protected String crearCuadro(CuadroClasificacion dto) {
        try {
            Resultado<String> result = client.crearCuadro(dto);
            // Comprobamos que el resultado fue ok
            Assert.assertEquals(result.getCodigoResultado(),CODIGO_RESULTADO_OK);
            return result.getElementoDevuelto();
        } catch (Exception e) {
            // Devuelve error si no se crea correctamente
            Assert.fail("Se ha producido un error sincronizando el cuadro " + e);
        }
        return null;
    }

    /**
     * Llama al sincronizar funcion controlando la excepcion
     *
     * @param dto
     * @return
     */
    protected String crearFuncion(Funcion dto, String parentId) {
        try {
            Resultado<String> result = this.client.crearFuncion(dto,parentId);
            // Comprobamos que el resultado fue ok
            Assert.assertEquals(result.getCodigoResultado(),CODIGO_RESULTADO_OK);
            return result.getElementoDevuelto();
        } catch (Exception e) {
            // Devuelve error si no se crea correctamente
            Assert.fail("Se ha producido un error creando la funcion " + e);
        }
        return null;
    }

    /**
     * Llama al sincronizar serie controlando la excepcion
     *
     * @param dto
     * @return
     */
    protected String cearSerie(Serie dto, String parentId) {
        try {
            Resultado<String> result = this.client.crearSerie(dto,parentId);
            // Comprobamos que el resultado fue ok
            Assert.assertEquals(result.getCodigoResultado(),CODIGO_RESULTADO_OK);
            return result.getElementoDevuelto();
        } catch (Exception e) {
            // Devuelve error si no se crea correctamente
            Assert.fail("Se ha producido un error creando la serie " + e);
        }
        return null;
    }

    /**
     * Borra el cuadro indicado
     *
     * @param nodeId
     */
    protected void borrarCuadro(String nodeId) {
        try {
            ResultadoSimple rs = client.borrarCuadro(nodeId);
            // Comprobamos que el reusltado fue ok
            Assert.assertEquals(rs.getCodigoResultado(),HTTP_RESPUESTA_OK);
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
        for(String funcionId : Arrays.asList(funciones)) {
            borrarFuncion(funcionId);
        }
        borrarCuadro(cuadroId);
    }

    /**
     * Borra la funcion
     *
     * @param funcionId
     */
    protected void borrarFuncion(String funcionId){
        try {
            ResultadoSimple rs = client.borrarFuncion(funcionId);
            // Comprobamos que el reusltado fue ok
            Assert.assertEquals(rs.getCodigoResultado(),HTTP_RESPUESTA_OK);
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
    protected void borrarSerie(String cuadroId,String serieId, String... funciones) {
        borrarSerie(serieId);
        for(String funcionId : Arrays.asList(funciones)) {
            borrarFuncion(funcionId);
        }
        borrarCuadro(cuadroId);
    }

    /**
     * Borra la serie
     *
     * @param serieId
     */
    protected void borrarSerie(String serieId){
        try {
            ResultadoSimple rs = client.borrarSerie(serieId);
            // Comprobamos que el reusltado fue ok
            Assert.assertEquals(rs.getCodigoResultado(),HTTP_RESPUESTA_OK);
        } catch (Exception e) {
            // Devuelve error si no se borra correctamente
            Assert.fail("Se ha producido un error borrando la serie " + e);
        }
    }



}

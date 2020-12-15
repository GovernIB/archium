package es.caib.archium.csgd.apirest.test;

import es.caib.archium.csgd.apirest.ApiCSGDServices;
import es.caib.archium.csgd.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.csgd.apirest.facade.pojos.cabecera.CabeceraPeticion;
import org.junit.Assert;
import org.junit.BeforeClass;
import org.junit.Test;

public class TestClient extends ClientUtils {

    @BeforeClass
    public static void init(){
        CabeceraPeticion cabecera = UtilCabeceras.generarCabeceraMock();

        client = new ApiCSGDServices(URL_BASE,cabecera);

    }

    @Test
    public void testCuadroDeClasificacion() {
        CuadroClasificacion dto = generarDTOCuadro();
        String nodeId = crearCuadro(dto);
        // Comprobamos que devuelve el valor para el nodeId que se le asigna
        Assert.assertNotNull(nodeId);
        borrarCuadro(nodeId);
        // No hace falta comprobar nada ya que devuelve excepcion si no se realiza correctamente
    }


    @Test
    public void testCrearFuncion(){
        String cuadroId = crearCuadro(generarDTOCuadro());
        String nodeId = crearFuncion(generarDTOFuncion(),cuadroId);
        // Comprobamos que devuelve el valor para el nodeId que se le asigna
        Assert.assertNotNull(nodeId);

        borrarFuncion(cuadroId,nodeId);
        // No hace falta comprobar nada ya que devuelve excepcion si no se realiza correctamente
    }


    @Test
    public void testCrearSerie(){
        String cuadroId = crearCuadro(generarDTOCuadro());
        String funcionPadre = crearFuncion(generarDTOFuncion(),cuadroId);

        Serie serie = generarDTOSerie();

        String nodeId = cearSerie(serie,funcionPadre);

        Assert.assertNotNull(nodeId);

        borrarSerie(cuadroId,nodeId,funcionPadre);

    }

}

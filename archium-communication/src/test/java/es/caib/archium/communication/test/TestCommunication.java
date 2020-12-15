package es.caib.archium.communication.test;

import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.csgd.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.csgd.apirest.facade.pojos.Funcion;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import org.junit.Assert;
import org.junit.Test;


public class TestCommunication extends CommunicationUtils {


    @Test
    public void testCuadroDeClasificacion() {
        CuadroClasificacion dto = generarDTOCuadro();
        String nodeId = sincronizarCuadro(dto);
        // Comprobamos que devuelve el valor para el nodeId que se le asigna
        Assert.assertNotNull(nodeId);
        borrarCuadro(nodeId);
        // No hace falta comprobar nada ya que devuelve excepcion si no se realiza correctamente
    }

    @Test
    public void testCrearFuncionSinPadre() {
        boolean excepcionProducida = false;
        Funcion dto = generarDTOFuncion();
        try {
            sincronizarFuncionSinCatch(dto,null);
        }catch (CSGDException e){
            excepcionProducida = true;
        }
        // Se espera que se produzca una excepcion si se sincroniza sin padre
        Assert.assertTrue(excepcionProducida);
    }

    @Test
    public void testCrearFuncion(){
        String cuadroId = sincronizarCuadro(generarDTOCuadro());
        String nodeId = sincronizarFuncion(generarDTOFuncion(),cuadroId);
        // Comprobamos que devuelve el valor para el nodeId que se le asigna
        Assert.assertNotNull(nodeId);

        borrarFuncion(cuadroId,nodeId);
        // No hace falta comprobar nada ya que devuelve excepcion si no se realiza correctamente
    }

    @Test
    public void testCrearFuncionPadre(){
        String cuadroId = sincronizarCuadro(generarDTOCuadro());
        String funcionPadre = sincronizarFuncion(generarDTOFuncion(),cuadroId);

        String nodeId = sincronizarFuncion(generarDTOFuncion(),funcionPadre);
        // Comprobamos que devuelve el valor para el nodeId que se le asigna
        Assert.assertNotNull(nodeId);

        borrarFuncion(cuadroId,nodeId,funcionPadre);
    }

    @Test
    public void testCrearSerieSinPadre(){
        boolean excepcionProducida = false;
        try{
            sincronizarSerieSinCatch(generarDTOSerie(),null);
        }catch (CSGDException e){
            excepcionProducida = true;
        }
        Assert.assertTrue(excepcionProducida);
    }

    @Test
    public void testCrearSerie(){
        String cuadroId = sincronizarCuadro(generarDTOCuadro());
        String funcionPadre = sincronizarFuncion(generarDTOFuncion(),cuadroId);

        Serie serie = generarDTOSerie();

        String nodeId = sincronizarSerie(serie,funcionPadre);

        Assert.assertNotNull(nodeId);

        borrarSerie(cuadroId,nodeId,funcionPadre);

    }

}

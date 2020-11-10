package es.caib.archium.communication.iface;

import es.caib.archium.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.communication.exception.CSGDException;

import javax.ejb.Local;

@Local
public interface CSGDCuadroService {

    //TODO: Crear excepciones
    void removeCuadro(String cuadroId) throws CSGDException;

    String synchronizeClassificationTable(CuadroClasificacion cuadroWs) throws CSGDException;
}

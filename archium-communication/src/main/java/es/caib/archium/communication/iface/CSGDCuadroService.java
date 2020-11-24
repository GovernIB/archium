package es.caib.archium.communication.iface;

import es.caib.archium.csgd.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.communication.exception.CSGDException;

import javax.ejb.Local;

@Local
public interface CSGDCuadroService {

    void removeCuadro(String cuadroId) throws CSGDException;

    String synchronizeClassificationTable(CuadroClasificacion cuadroWs) throws CSGDException;
}

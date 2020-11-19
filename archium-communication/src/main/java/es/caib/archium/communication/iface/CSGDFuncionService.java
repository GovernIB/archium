package es.caib.archium.communication.iface;

import es.caib.csgd.apirest.facade.pojos.Funcion;
import es.caib.archium.communication.exception.CSGDException;

import javax.ejb.Local;

@Local
public interface CSGDFuncionService {

    void removeFuncion(String funcionId) throws CSGDException;

    String synchronizeFunction(Funcion funcion, String nodeId) throws CSGDException;
}

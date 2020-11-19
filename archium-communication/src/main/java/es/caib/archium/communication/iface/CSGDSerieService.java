package es.caib.archium.communication.iface;

import es.caib.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.communication.exception.CSGDException;

import javax.ejb.Local;

@Local
public interface CSGDSerieService {

    void removeSerie(String serieId) throws CSGDException;

    String synchronizeSerie(Serie serie, String codi) throws CSGDException;
}

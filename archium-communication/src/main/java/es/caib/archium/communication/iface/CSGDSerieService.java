package es.caib.archium.communication.iface;

import es.caib.archium.communication.iface.generic.CSGDService;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.SerieId;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;

public interface CSGDSerieService extends CSGDService<Serie, SerieId> {
}

package es.caib.archium.communication.impl;

import es.caib.archium.communication.iface.CSGDSerieService;
import es.caib.archium.communication.impl.generic.CSGDGenericServiceImpl;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.SerieId;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.csgd.apirest.facade.pojos.eliminar.EliminarSerie;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverSerie;


public class CSGGDSerieServiceImpl extends CSGDGenericServiceImpl<Serie, EliminarSerie, MoverSerie>
        implements CSGDSerieService {


}

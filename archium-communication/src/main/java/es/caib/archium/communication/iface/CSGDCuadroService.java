package es.caib.archium.communication.iface;

import es.caib.archium.communication.iface.generic.CSGDService;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RootId;
import es.caib.archium.csgd.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.csgd.apirest.facade.pojos.eliminar.EliminarCuadro;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverCuadro;

public interface CSGDCuadroService extends CSGDService<CuadroClasificacion, EliminarCuadro, MoverCuadro> {
}

package es.caib.archium.communication.iface;

import es.caib.archium.communication.iface.generic.CSGDService;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.FunctionId;
import es.caib.archium.csgd.apirest.facade.pojos.Funcion;
import es.caib.archium.csgd.apirest.facade.pojos.eliminar.EliminarFuncion;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverFuncion;

public interface CSGDFuncionService extends CSGDService<Funcion, EliminarFuncion, MoverFuncion> {
}

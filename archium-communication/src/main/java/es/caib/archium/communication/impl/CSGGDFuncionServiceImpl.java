package es.caib.archium.communication.impl;

import es.caib.archium.communication.iface.CSGDFuncionService;
import es.caib.archium.communication.impl.generic.CSGDGenericServiceImpl;
import es.caib.archium.csgd.apirest.facade.pojos.Funcion;
import es.caib.archium.csgd.apirest.facade.pojos.comun.FuncionId;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverFuncion;


public class CSGGDFuncionServiceImpl extends CSGDGenericServiceImpl<Funcion, FuncionId, MoverFuncion>
        implements CSGDFuncionService {

}

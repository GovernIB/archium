package es.caib.archium.communication.impl;

import es.caib.archium.commons.utils.Constants;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.iface.CSGDCuadroService;
import es.caib.archium.communication.impl.generic.CSGDGenericServiceImpl;
import es.caib.archium.csgd.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.csgd.apirest.facade.pojos.comun.CuadroId;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverCuadro;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CSGGDCuadroServiceImpl extends CSGDGenericServiceImpl<CuadroClasificacion, CuadroId, MoverCuadro>
        implements CSGDCuadroService {

    protected final Logger LOGGER = LoggerFactory.getLogger(CSGGDCuadroServiceImpl.class);

    @Override
    public void moveNode(MoverCuadro wsDto) throws CSGDException {
        LOGGER.error("Mover nodo es una funcion no implementada para el cuadro de clasificacion");
        throw new CSGDException(Constants.ExceptionConstants.FUNCION_NO_IMPLEMENTADA.getValue(),
                "Mover nodo es una funcion no implementada para el cuadro de clasificacion");
    }
}

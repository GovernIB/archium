package es.caib.archium.communication.impl;

import es.caib.archium.commons.utils.Constants;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.iface.CSGDSerieService;
import es.caib.archium.communication.impl.generic.CSGDGenericServiceImpl;
import es.caib.archium.csgd.apirest.constantes.Permisos;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.csgd.apirest.facade.pojos.comun.SerieId;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverSerie;
import es.caib.archium.csgd.apirest.facade.resultados.ResultadoSimple;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.stream.Collectors;


public class CSGGDSerieServiceImpl extends CSGDGenericServiceImpl<Serie, SerieId, MoverSerie>
        implements CSGDSerieService {

    protected final Logger LOGGER = LoggerFactory.getLogger(CSGGDSerieServiceImpl.class);

    @Override
    public void grantPermissions(List<SerieId> nodeId, List<String> appliciationsUsers) throws CSGDException {
        log.info("Se procede a la llamada para asignar permisos al nodo");
        try {
            ResultadoSimple rs = super.client.otorgarPermisosSerie(nodeId.stream().map(x -> x.getNodoId())
                    .collect(Collectors.toList()),appliciationsUsers, Permisos.COORDINATOR);

            super.prepararRespuestaSinResultado(rs,null);
        } catch (CSGDException e) {
            throw e;
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de asignacion de permisos: " + e);
            throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no controlado en el proceso de asignacion de permisos", e);
        }
        log.info("Permisos asignados correctamente");
    }

    @Override
    public void cancelPermissions(List<SerieId> nodeId, List<String> appliciationsUsers) throws CSGDException {
        log.info("Se procede a la llamada para cancelar permisos al nodo");
        try {
            ResultadoSimple rs = super.client.cancelarPermisosSerie(nodeId.stream().map(x -> x.getNodoId())
                    .collect(Collectors.toList()),appliciationsUsers);

            super.prepararRespuestaSinResultado(rs,null);
        } catch (CSGDException e) {
            throw e;
        } catch (Exception e) {
            log.error("Se ha producido un error no controlado en el proceso de cancelacion de permisos: " + e);
            throw new CSGDException(Constants.ExceptionConstants.GENERIC_ERROR.getValue(), "Se ha producido un error no controlado en el proceso de cancelacion de permisos", e);
        }
        log.info("Permisos cancelados correctamente");
    }
}

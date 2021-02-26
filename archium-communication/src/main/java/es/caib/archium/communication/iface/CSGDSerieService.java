package es.caib.archium.communication.iface;

import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.communication.iface.generic.CSGDService;
import es.caib.archium.csgd.apirest.facade.pojos.Serie;
import es.caib.archium.csgd.apirest.facade.pojos.comun.SerieId;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverSerie;

import java.util.List;

public interface CSGDSerieService extends CSGDService<Serie, SerieId, MoverSerie> {

    /**
     * Da permisos de escritura a la lista de usuarios sobre la lista de nodos indicada
     *
     * @param nodeId
     * @param appliciationsUsers
     */
    void grantPermissions(List<SerieId> nodeId, List<String> appliciationsUsers) throws CSGDException;

    /**
     * Retira todos los permisos a la lista de usuarios sobre los nodos indicados
     *
     * @param nodeId
     * @param appliciationsUsers
     */
    void cancelPermissions(List<SerieId> nodeId, List<String> appliciationsUsers) throws CSGDException;
}

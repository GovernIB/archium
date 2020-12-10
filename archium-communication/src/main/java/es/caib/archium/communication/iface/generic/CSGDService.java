package es.caib.archium.communication.iface.generic;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.Id;
import es.caib.archium.csgd.apirest.facade.pojos.CuadroClasificacion;
import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.csgd.apirest.facade.pojos.Nodo;

import javax.ejb.Local;


@Local
public interface CSGDService<T extends Nodo, ID extends Id> {

    /**
     * Envía la instrucción a GDIB de borrar el nodeId indicado
     *
     * @param wsId
     * @throws CSGDException
     */
    void deleteNode(ID wsId) throws CSGDException;

    /**
     * Envía la instrucción a GDIB de crear el nodo indicado informando el padre
     *
     * @param dtoWs
     * @param parentId
     * @return
     * @throws CSGDException
     */
    String synchronizeNode(T dtoWs, String parentId) throws CSGDException;

    /**
     * Envía la instrucción a GDIB de crear el nodo indicado
     *
     * @param dtoWs
     * @param parentId
     * @return
     * @throws CSGDException
     */
    String synchronizeNode(T dtoWs) throws CSGDException;
}

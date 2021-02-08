package es.caib.archium.communication.iface.generic;

import es.caib.archium.communication.exception.CSGDException;
import es.caib.archium.csgd.apirest.facade.pojos.Nodo;
import es.caib.archium.csgd.apirest.facade.pojos.comun.NodoAlfresco;
import es.caib.archium.csgd.apirest.facade.pojos.eliminar.EliminarNodo;
import es.caib.archium.csgd.apirest.facade.pojos.mover.MoverNodo;

import javax.ejb.Local;


@Local
public interface CSGDService<T extends Nodo, ID extends EliminarNodo, M extends MoverNodo> {

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
     * @return
     * @throws CSGDException
     */
    String synchronizeNode(T dtoWs) throws CSGDException;


    /**
     * Envía la instrucción a GDIB de mover el nodo al padre indicado
     *
     * @param dtoId
     * @return
     * @throws CSGDException
     */
    void moveNode(M dtoId) throws CSGDException;
}

package es.caib.archium.csgd.apirest.facade.pojos.eliminar;

import es.caib.archium.csgd.apirest.facade.pojos.comun.NodoAlfresco;

public class EliminarNodo extends NodoAlfresco {

    public EliminarNodo(String nodeId) {
        super(nodeId);
    }

    @Override
    public String toString() {
        return "EliminarNodo{" +
                "nodoId='" + nodoId + '\'' +
                "} " + super.toString();
    }
}

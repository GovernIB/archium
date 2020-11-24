package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class RemoveCuadro {

    private Request<ParamNodeId> removeRootRequest;

    public Request<ParamNodeId> getRemoveRootRequest() {
        return removeRootRequest;
    }

    public void setRemoveRootRequest(Request<ParamNodeId> removeRootRequest) {
        this.removeRootRequest = removeRootRequest;
    }

    @Override
    public String toString() {
        return "RemoveCuadro{" +
                "removeRootRequest=" + removeRootRequest +
                '}';
    }
}

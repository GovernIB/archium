package es.caib.csgd.apirest.csgd.peticiones;

import es.caib.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class RemoveCuadro {

    private Request<ParamNodeId> removeRootRequest;

    public Request<ParamNodeId> getRemoveRootRequest() {
        return removeRootRequest;
    }

    public void setRemoveRootRequest(Request<ParamNodeId> removeRootRequest) {
        this.removeRootRequest = removeRootRequest;
    }
}

package es.caib.archium.apirest.csgd.peticiones;

import es.caib.archium.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class RemoveSerie {

    private Request<ParamNodeId> removeSerieRequest;

    public Request<ParamNodeId> getRemoveSerieRequest() {
        return removeSerieRequest;
    }

    public void setRemoveSerieRequest(Request<ParamNodeId> removeSerieRequest) {
        this.removeSerieRequest = removeSerieRequest;
    }
}

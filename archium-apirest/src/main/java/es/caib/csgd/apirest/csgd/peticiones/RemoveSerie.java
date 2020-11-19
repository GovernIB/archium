package es.caib.csgd.apirest.csgd.peticiones;

import es.caib.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class RemoveSerie {

    private Request<ParamNodeId> removeSerieRequest;

    public Request<ParamNodeId> getRemoveSerieRequest() {
        return removeSerieRequest;
    }

    public void setRemoveSerieRequest(Request<ParamNodeId> removeSerieRequest) {
        this.removeSerieRequest = removeSerieRequest;
    }
}

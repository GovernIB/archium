package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class RemoveSerie {

    private Request<ParamNodeId> removeSerieRequest;

    public Request<ParamNodeId> getRemoveSerieRequest() {
        return removeSerieRequest;
    }

    public void setRemoveSerieRequest(Request<ParamNodeId> removeSerieRequest) {
        this.removeSerieRequest = removeSerieRequest;
    }

    @Override
    public String toString() {
        return "RemoveSerie{" +
                "removeSerieRequest=" + removeSerieRequest +
                '}';
    }
}

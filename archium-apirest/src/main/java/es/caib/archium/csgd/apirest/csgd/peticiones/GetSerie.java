package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class GetSerie {

    private Request<ParamNodeId> getSerieRequest;

    public Request<ParamNodeId> getGetSerieRequest() {
        return getSerieRequest;
    }

    public void setGetSerieRequest(Request<ParamNodeId> getSerieRequest) {
        this.getSerieRequest = getSerieRequest;
    }

    @Override
    public String toString() {
        return "GetSerie{" +
                "getSerieRequest=" + getSerieRequest +
                '}';
    }
}

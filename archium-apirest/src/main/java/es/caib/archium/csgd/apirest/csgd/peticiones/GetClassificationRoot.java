package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class GetClassificationRoot {

    private Request<ParamNodeId> getClassificationRootRequest;

    public Request<ParamNodeId> getGetClassificationRootRequest() {
        return getClassificationRootRequest;
    }

    public void setGetClassificationRootRequest(Request<ParamNodeId> getClassificationRootRequest) {
        this.getClassificationRootRequest = getClassificationRootRequest;
    }

    @Override
    public String toString() {
        return "GetClassificationRoot{" +
                "getClassificationRootRequest=" + getClassificationRootRequest +
                '}';
    }
}

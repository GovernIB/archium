package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class RemoveClassificationRoot {

    private Request<ParamNodeId> removeClassificationRootRequest;

    public Request<ParamNodeId> getRemoveClassificationRootRequest() {
        return removeClassificationRootRequest;
    }

    public void setRemoveClassificationRootRequest(Request<ParamNodeId> removeClassificationRootRequest) {
        this.removeClassificationRootRequest = removeClassificationRootRequest;
    }

    @Override
    public String toString() {
        return "RemoveCuadro{" +
                "removeClassificationRootRequest=" + removeClassificationRootRequest +
                '}';
    }
}

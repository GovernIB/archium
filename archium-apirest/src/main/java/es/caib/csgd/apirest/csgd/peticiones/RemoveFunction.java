package es.caib.csgd.apirest.csgd.peticiones;

import es.caib.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class RemoveFunction {

    private Request<ParamNodeId> removeFunctionRequest;

    public Request<ParamNodeId> getRemoveFunctionRequest() {
        return removeFunctionRequest;
    }

    public void setRemoveFunctionRequest(Request<ParamNodeId> removeFunctionRequest) {
        this.removeFunctionRequest = removeFunctionRequest;
    }
}

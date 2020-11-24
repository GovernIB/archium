package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class RemoveFunction {

    private Request<ParamNodeId> removeFunctionRequest;

    public Request<ParamNodeId> getRemoveFunctionRequest() {
        return removeFunctionRequest;
    }

    public void setRemoveFunctionRequest(Request<ParamNodeId> removeFunctionRequest) {
        this.removeFunctionRequest = removeFunctionRequest;
    }

    @Override
    public String toString() {
        return "RemoveFunction{" +
                "removeFunctionRequest=" + removeFunctionRequest +
                '}';
    }
}

package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class GetFunction {

    private Request<ParamNodeId> getFunctionRequest;

    public Request<ParamNodeId> getGetFunctionRequest() {
        return getFunctionRequest;
    }

    public void setGetFunctionRequest(Request<ParamNodeId> getFunctionRequest) {
        this.getFunctionRequest = getFunctionRequest;
    }

    @Override
    public String toString() {
        return "GetFunction{" +
                "getFunctionRequest=" + getFunctionRequest +
                '}';
    }
}

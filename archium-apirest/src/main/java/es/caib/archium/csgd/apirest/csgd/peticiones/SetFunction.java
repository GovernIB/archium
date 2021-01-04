package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamSetFunction;

public class SetFunction {
    private Request<ParamSetFunction> setFunctionRequest;

    public Request<ParamSetFunction> getSetFunctionRequest() {
        return setFunctionRequest;
    }

    public void setSetFunctionRequest(Request<ParamSetFunction> setFunctionRequest) {
        this.setFunctionRequest = setFunctionRequest;
    }

    @Override
    public String toString() {
        return "SetFunction{" +
                "setFunctionRequest=" + setFunctionRequest +
                '}';
    }
}

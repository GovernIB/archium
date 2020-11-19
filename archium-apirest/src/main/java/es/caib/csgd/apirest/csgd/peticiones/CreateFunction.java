package es.caib.csgd.apirest.csgd.peticiones;

import es.caib.csgd.apirest.csgd.entidades.parametrosllamada.ParamCreateFunction;

public class CreateFunction {
    private Request<ParamCreateFunction> createFunctionRequest;

    public Request<ParamCreateFunction> getCreateFunctionRequest() {
        return createFunctionRequest;
    }

    public void setCreateFunctionRequest(Request<ParamCreateFunction> createFunctionRequest) {
        this.createFunctionRequest = createFunctionRequest;
    }
}

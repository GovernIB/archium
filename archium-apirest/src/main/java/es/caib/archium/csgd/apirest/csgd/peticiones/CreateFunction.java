package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamCreateFunction;

public class CreateFunction {
    private Request<ParamCreateFunction> createFunctionRequest;

    public Request<ParamCreateFunction> getCreateFunctionRequest() {
        return createFunctionRequest;
    }

    public void setCreateFunctionRequest(Request<ParamCreateFunction> createFunctionRequest) {
        this.createFunctionRequest = createFunctionRequest;
    }

    @Override
    public String toString() {
        return "CreateFunction{" +
                "createFunctionRequest=" + createFunctionRequest +
                '}';
    }
}

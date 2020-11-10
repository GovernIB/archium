package es.caib.archium.apirest.csgd.peticiones;

import es.caib.archium.apirest.csgd.entidades.parametrosllamada.ParamCreateFunction;

public class CreateFunction {
    private Request<ParamCreateFunction> createFuncionRequest;

    public Request<ParamCreateFunction> getCreateFuncionRequest() {
        return createFuncionRequest;
    }

    public void setCreateFuncionRequest(Request<ParamCreateFunction> createFuncionRequest) {
        this.createFuncionRequest = createFuncionRequest;
    }
}

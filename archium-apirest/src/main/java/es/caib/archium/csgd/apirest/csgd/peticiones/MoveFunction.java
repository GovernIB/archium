package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamMoveNode;

public class MoveFunction {

    private Request<ParamMoveNode> moveFunctionRequest;

    public Request<ParamMoveNode> getMoveFunctionRequest() {
        return moveFunctionRequest;
    }

    public void setMoveFunctionRequest(Request<ParamMoveNode> moveFunctionRequest) {
        this.moveFunctionRequest = moveFunctionRequest;
    }

    @Override
    public String toString() {
        return "MoveFunction{" +
                "moveFunctionRequest=" + moveFunctionRequest +
                '}';
    }
}

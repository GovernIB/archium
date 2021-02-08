package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.FunctionNode;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenerica;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RootNode;

public class GetFunctionResult {

    private RespuestaGenerica<FunctionNode> getFunctionResult;

    public RespuestaGenerica<FunctionNode> getGetFunctionResult() {
        return getFunctionResult;
    }

    public void setGetFunctionResult(RespuestaGenerica<FunctionNode> getFunctionResult) {
        this.getFunctionResult = getFunctionResult;
    }

    @Override
    public String toString() {
        return "GetFunctionResult{" +
                "getFunctionResult=" + getFunctionResult +
                '}';
    }
}

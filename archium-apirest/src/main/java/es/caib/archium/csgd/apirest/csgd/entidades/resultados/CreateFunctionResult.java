package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenerica;
import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class  CreateFunctionResult {
    RespuestaGenerica<ParamNodeId> createFunctionResult;

    public RespuestaGenerica<ParamNodeId> getCreateFunctionResult() {
        return createFunctionResult;
    }

    public void setCreateFunctionResult(RespuestaGenerica<ParamNodeId> createFunctionResult) {
        this.createFunctionResult = createFunctionResult;
    }

    @Override
    public String toString() {
        return "CreateFunctionResult{" +
                "createFunctionResult=" + createFunctionResult +
                '}';
    }
}

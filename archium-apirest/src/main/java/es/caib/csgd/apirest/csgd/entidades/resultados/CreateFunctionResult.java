package es.caib.csgd.apirest.csgd.entidades.resultados;

import es.caib.csgd.apirest.csgd.entidades.comunes.RespuestaGenerica;
import es.caib.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class  CreateFunctionResult {
    RespuestaGenerica<ParamNodeId> createFunctionResult;

    public RespuestaGenerica<ParamNodeId> getCreateFunctionResult() {
        return createFunctionResult;
    }

    public void setCreateFunctionResult(RespuestaGenerica<ParamNodeId> createFunctionResult) {
        this.createFunctionResult = createFunctionResult;
    }
}

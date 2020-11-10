package es.caib.archium.apirest.csgd.entidades.resultados;

import es.caib.archium.apirest.csgd.entidades.comunes.RespuestaGenerica;

public class CreateFunctionResult {
    RespuestaGenerica<String> createFunctionResult;

    public RespuestaGenerica<String> getCreateFunctionResult() {
        return createFunctionResult;
    }

    public void setCreateFunctionResult(RespuestaGenerica<String> createFunctionResult) {
        this.createFunctionResult = createFunctionResult;
    }
}

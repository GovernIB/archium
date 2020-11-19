package es.caib.csgd.apirest.csgd.entidades.resultados;

import es.caib.csgd.apirest.csgd.entidades.comunes.RespuestaGenerica;
import es.caib.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class CreateSerieResult {
    RespuestaGenerica<ParamNodeId> createSerieResult;

    public RespuestaGenerica<ParamNodeId> getCreateSerieResult() {
        return createSerieResult;
    }

    public void setCreateSerieResult(RespuestaGenerica<ParamNodeId> createSerieResult) {
        this.createSerieResult = createSerieResult;
    }
}

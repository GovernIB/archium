package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenerica;
import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class CreateSerieResult {
    RespuestaGenerica<ParamNodeId> createSerieResult;

    public RespuestaGenerica<ParamNodeId> getCreateSerieResult() {
        return createSerieResult;
    }

    public void setCreateSerieResult(RespuestaGenerica<ParamNodeId> createSerieResult) {
        this.createSerieResult = createSerieResult;
    }

    @Override
    public String toString() {
        return "CreateSerieResult{" +
                "createSerieResult=" + createSerieResult +
                '}';
    }


}

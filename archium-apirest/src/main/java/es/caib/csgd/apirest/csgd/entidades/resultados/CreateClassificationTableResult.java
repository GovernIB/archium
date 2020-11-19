package es.caib.csgd.apirest.csgd.entidades.resultados;

import es.caib.csgd.apirest.csgd.entidades.comunes.RespuestaGenerica;
import es.caib.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class CreateClassificationTableResult {
    RespuestaGenerica<ParamNodeId> createClassificationRootResult;

    public RespuestaGenerica<ParamNodeId> getCreateClassificationRootResult() {
        return createClassificationRootResult;
    }

    public void setCreateClassificationRootResult(RespuestaGenerica<ParamNodeId> createClassificationRootResult) {
        this.createClassificationRootResult = createClassificationRootResult;
    }
}

package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenerica;
import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamNodeId;

public class CreateClassificationRootResult {
    RespuestaGenerica<ParamNodeId> createClassificationRootResult;

    public RespuestaGenerica<ParamNodeId> getCreateClassificationRootResult() {
        return createClassificationRootResult;
    }

    public void setCreateClassificationRootResult(RespuestaGenerica<ParamNodeId> createClassificationRootResult) {
        this.createClassificationRootResult = createClassificationRootResult;
    }

    @Override
    public String toString() {
        return "CreateClassificationTableResult{" +
                "createClassificationRootResult=" + createClassificationRootResult +
                '}';
    }
}

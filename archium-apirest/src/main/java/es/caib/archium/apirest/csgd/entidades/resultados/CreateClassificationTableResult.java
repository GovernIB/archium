package es.caib.archium.apirest.csgd.entidades.resultados;

import es.caib.archium.apirest.csgd.entidades.comunes.RespuestaGenerica;

public class CreateClassificationTableResult {
    RespuestaGenerica<String> createClassificationTableResult;

    public RespuestaGenerica<String> getCreateClassificationTableResult() {
        return createClassificationTableResult;
    }

    public void setCreateClassificationTableResult(RespuestaGenerica<String> createClassificationTableResult) {
        this.createClassificationTableResult = createClassificationTableResult;
    }
}

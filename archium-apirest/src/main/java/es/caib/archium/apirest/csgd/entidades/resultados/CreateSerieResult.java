package es.caib.archium.apirest.csgd.entidades.resultados;

import es.caib.archium.apirest.csgd.entidades.comunes.RespuestaGenerica;

public class CreateSerieResult {
    RespuestaGenerica<String> createSerieResult;

    public RespuestaGenerica<String> getCreateSerieResult() {
        return createSerieResult;
    }

    public void setCreateSerieResult(RespuestaGenerica<String> createSerieResult) {
        this.createSerieResult = createSerieResult;
    }
}

package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenerica;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.SerieNode;

public class GetSerieResult {

    private RespuestaGenerica<SerieNode> getSerieResult;

    public RespuestaGenerica<SerieNode> getGetSerieResult() {
        return getSerieResult;
    }

    public void setGetSerieResult(RespuestaGenerica<SerieNode> getSerieResult) {
        this.getSerieResult = getSerieResult;
    }

    @Override
    public String toString() {
        return "GetSerieResult{" +
                "getSerieResult=" + getSerieResult +
                '}';
    }
}

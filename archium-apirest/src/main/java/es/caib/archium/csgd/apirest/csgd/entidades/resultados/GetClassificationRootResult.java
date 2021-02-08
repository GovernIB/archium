package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenerica;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RootNode;

public class GetClassificationRootResult {

    private RespuestaGenerica<RootNode> getClassificationRootResult;

    public RespuestaGenerica<RootNode> getGetClassificationRootResult() {
        return getClassificationRootResult;
    }

    public void setGetClassificationRootResult(RespuestaGenerica<RootNode> getClassificationRootResult) {
        this.getClassificationRootResult = getClassificationRootResult;
    }

    @Override
    public String toString() {
        return "GetClassificationRootResult{" +
                "getClassificationRootResult=" + getClassificationRootResult +
                '}';
    }
}

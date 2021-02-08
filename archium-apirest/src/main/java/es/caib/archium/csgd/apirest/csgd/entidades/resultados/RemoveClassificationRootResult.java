package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenericaUnParametro;

public class RemoveClassificationRootResult {

    private RespuestaGenericaUnParametro removeClassificationRootResult;

    public RespuestaGenericaUnParametro getRemoveClassificationRootResult() {
        return removeClassificationRootResult;
    }

    public void setRemoveClassificationRootResult(RespuestaGenericaUnParametro removeClassificationRootResult) {
        this.removeClassificationRootResult = removeClassificationRootResult;
    }

    @Override
    public String toString() {
        return "RemoveCuadroResult{" +
                "removeRootResult=" + removeClassificationRootResult +
                '}';
    }
}

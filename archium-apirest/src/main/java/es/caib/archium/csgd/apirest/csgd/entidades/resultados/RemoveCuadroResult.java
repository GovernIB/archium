package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenericaUnParametro;

public class RemoveCuadroResult {

    private RespuestaGenericaUnParametro removeRootResult;

    public RespuestaGenericaUnParametro getRemoveRootResult() {
        return removeRootResult;
    }

    public void setRemoveRootResult(RespuestaGenericaUnParametro removeRootResult) {
        this.removeRootResult = removeRootResult;
    }

    @Override
    public String toString() {
        return "RemoveCuadroResult{" +
                "removeRootResult=" + removeRootResult +
                '}';
    }
}

package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenericaUnParametro;

public class RemoveFunctionResult {

    private RespuestaGenericaUnParametro removeFunctionResult;

    public RespuestaGenericaUnParametro getRemoveFunctionResult() {
        return removeFunctionResult;
    }

    public void setRemoveFunctionResult(RespuestaGenericaUnParametro removeFunctionResult) {
        this.removeFunctionResult = removeFunctionResult;
    }

    @Override
    public String toString() {
        return "RemoveFunctionResult{" +
                "removeFunctionResult=" + removeFunctionResult +
                '}';
    }
}

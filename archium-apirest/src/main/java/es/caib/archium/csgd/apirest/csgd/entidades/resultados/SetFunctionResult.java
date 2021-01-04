package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenericaUnParametro;

public class SetFunctionResult {

    private RespuestaGenericaUnParametro setFunctionResult;

    public RespuestaGenericaUnParametro getSetFunctionResult() {
        return setFunctionResult;
    }

    public void setSetFunctionResult(RespuestaGenericaUnParametro setFunctionResult) {
        this.setFunctionResult = setFunctionResult;
    }

    @Override
    public String toString() {
        return "SetFunctionResult{" +
                "setFunctionResult=" + setFunctionResult +
                '}';
    }
}

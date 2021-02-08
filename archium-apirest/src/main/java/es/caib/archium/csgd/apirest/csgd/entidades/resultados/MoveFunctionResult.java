package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenericaUnParametro;

public class MoveFunctionResult {

    private RespuestaGenericaUnParametro moveFunctionResult;

    public RespuestaGenericaUnParametro getMoveFunctionResult() {
        return moveFunctionResult;
    }

    public void setMoveFunctionResult(RespuestaGenericaUnParametro moveFunctionResult) {
        this.moveFunctionResult = moveFunctionResult;
    }

    @Override
    public String toString() {
        return "MoveFunctionResult{" +
                "moveFunctionResult=" + moveFunctionResult +
                '}';
    }
}

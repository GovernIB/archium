package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenericaUnParametro;

public class SetRootResult {

    private RespuestaGenericaUnParametro setRootResult;

    public RespuestaGenericaUnParametro getSetRootResult() {
        return setRootResult;
    }

    public void setSetRootResult(RespuestaGenericaUnParametro setRootResult) {
        this.setRootResult = setRootResult;
    }

    @Override
    public String toString() {
        return "SetRootResult{" +
                "setRootResult=" + setRootResult +
                '}';
    }
}

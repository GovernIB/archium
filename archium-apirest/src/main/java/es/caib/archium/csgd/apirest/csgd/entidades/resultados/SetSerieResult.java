package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenericaUnParametro;

public class SetSerieResult {

    private RespuestaGenericaUnParametro setSerieResult;

    public RespuestaGenericaUnParametro getSetSerieResult() {
        return setSerieResult;
    }

    public void setSetSerieResult(RespuestaGenericaUnParametro setSerieResult) {
        this.setSerieResult = setSerieResult;
    }

    @Override
    public String toString() {
        return "SetSerieResult{" +
                "setSerieResult=" + setSerieResult +
                '}';
    }
}

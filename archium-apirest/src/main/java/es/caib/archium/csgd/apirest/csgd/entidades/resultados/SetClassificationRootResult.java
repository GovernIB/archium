package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenericaUnParametro;

public class SetClassificationRootResult {

    private RespuestaGenericaUnParametro setClassificationRootResult;

    public RespuestaGenericaUnParametro getSetClassificationRootResult() {
        return setClassificationRootResult;
    }

    public void setSetClassificationRootResult(RespuestaGenericaUnParametro setClassificationRootResult) {
        this.setClassificationRootResult = setClassificationRootResult;
    }

    @Override
    public String toString() {
        return "SetClassificationRootResult{" +
                "setClassificationRootResult=" + setClassificationRootResult +
                '}';
    }
}

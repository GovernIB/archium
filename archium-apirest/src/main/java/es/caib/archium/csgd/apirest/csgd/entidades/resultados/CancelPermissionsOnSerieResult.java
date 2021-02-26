package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenericaUnParametro;

public class CancelPermissionsOnSerieResult {

    private RespuestaGenericaUnParametro cancelPermissionsOnSeriesResult;

    public RespuestaGenericaUnParametro getCancelPermissionsOnSeriesResult() {
        return cancelPermissionsOnSeriesResult;
    }

    public void setCancelPermissionsOnSeriesResult(RespuestaGenericaUnParametro cancelPermissionsOnSeriesResult) {
        this.cancelPermissionsOnSeriesResult = cancelPermissionsOnSeriesResult;
    }

    @Override
    public String toString() {
        return "CancelPermissionsOnSeriesResult{" +
                "cancelPermissionsOnSeriesResult=" + cancelPermissionsOnSeriesResult +
                '}';
    }
}

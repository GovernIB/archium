package es.caib.archium.csgd.apirest.csgd.entidades.resultados;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RespuestaGenericaUnParametro;

public class GrantPermissionsOnSerieResult {

    private RespuestaGenericaUnParametro grantPermissionsOnSeriesResult;

    public RespuestaGenericaUnParametro getGrantPermissionsOnSeriesResult() {
        return grantPermissionsOnSeriesResult;
    }

    public void setGrantPermissionsOnSeriesResult(RespuestaGenericaUnParametro grantPermissionsOnSeriesResult) {
        this.grantPermissionsOnSeriesResult = grantPermissionsOnSeriesResult;
    }

    @Override
    public String toString() {
        return "GrantPermissionsOnSeriesResult{" +
                "grantPermissionsOnSeriesResult=" + grantPermissionsOnSeriesResult +
                '}';
    }
}

package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamCancelPermissions;
import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamGrantPermissions;

public class CancelPermissionsOnSeries {

    private Request<ParamCancelPermissions> cancelPermissionsOnSeriesRequest;

    public Request<ParamCancelPermissions> getCancelPermissionsOnSeriesRequest() {
        return cancelPermissionsOnSeriesRequest;
    }

    public void setCancelPermissionsOnSeriesRequest(Request<ParamCancelPermissions> cancelPermissionsOnSeriesRequest) {
        this.cancelPermissionsOnSeriesRequest = cancelPermissionsOnSeriesRequest;
    }

    @Override
    public String toString() {
        return "CancelPermissionsOnSeries{" +
                "cancelPermissionsOnSeriesRequest=" + cancelPermissionsOnSeriesRequest +
                '}';
    }
}

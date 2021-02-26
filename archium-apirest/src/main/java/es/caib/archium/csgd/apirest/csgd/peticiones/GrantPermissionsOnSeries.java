package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamGrantPermissions;

public class GrantPermissionsOnSeries {

    private Request<ParamGrantPermissions> grantPermissionsOnSeriesRequest;

    public Request<ParamGrantPermissions> getGrantPermissionsOnSeriesRequest() {
        return grantPermissionsOnSeriesRequest;
    }

    public void setGrantPermissionsOnSeriesRequest(Request<ParamGrantPermissions> grantPermissionsOnSeriesRequest) {
        this.grantPermissionsOnSeriesRequest = grantPermissionsOnSeriesRequest;
    }

    @Override
    public String toString() {
        return "GrantPermissionsOnSeries{" +
                "grantPermissionsOnSeriesRequest=" + grantPermissionsOnSeriesRequest +
                '}';
    }
}

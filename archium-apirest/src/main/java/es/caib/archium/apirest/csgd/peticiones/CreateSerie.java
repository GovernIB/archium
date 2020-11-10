package es.caib.archium.apirest.csgd.peticiones;

import es.caib.archium.apirest.csgd.entidades.parametrosllamada.ParamCreateSerie;

public class CreateSerie {
    private Request<ParamCreateSerie> createSerieRequest;

    public Request<ParamCreateSerie> getCreateSerieRequest() {
        return createSerieRequest;
    }

    public void setCreateSerieRequest(Request<ParamCreateSerie> createSerieRequest) {
        this.createSerieRequest = createSerieRequest;
    }
}

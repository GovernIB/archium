package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamCreateSerie;

public class CreateSerie {
    private Request<ParamCreateSerie> createSerieRequest;

    public Request<ParamCreateSerie> getCreateSerieRequest() {
        return createSerieRequest;
    }

    public void setCreateSerieRequest(Request<ParamCreateSerie> createSerieRequest) {
        this.createSerieRequest = createSerieRequest;
    }

    @Override
    public String toString() {
        return "CreateSerie{" +
                "createSerieRequest=" + createSerieRequest +
                '}';
    }
}

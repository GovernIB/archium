package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamSetSerie;

public class SetSerie {
    private Request<ParamSetSerie> setSerieRequest;

    public Request<ParamSetSerie> getSetSerieRequest() {
        return setSerieRequest;
    }

    public void setSetSerieRequest(Request<ParamSetSerie> setSerieRequest) {
        this.setSerieRequest = setSerieRequest;
    }

    @Override
    public String toString() {
        return "SetSerie{" +
                "setSerieRequest=" + setSerieRequest +
                '}';
    }
}

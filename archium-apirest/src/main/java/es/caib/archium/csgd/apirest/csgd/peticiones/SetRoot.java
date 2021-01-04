package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamSetRoot;

public class SetRoot {
    private Request<ParamSetRoot> setRootRequest;

    public Request<ParamSetRoot> getSetRootRequest() {
        return setRootRequest;
    }

    public void setSetRootRequest(Request<ParamSetRoot> setRootRequest) {
        this.setRootRequest = setRootRequest;
    }

    @Override
    public String toString() {
        return "SetRoot{" +
                "setRootRequest=" + setRootRequest +
                '}';
    }
}

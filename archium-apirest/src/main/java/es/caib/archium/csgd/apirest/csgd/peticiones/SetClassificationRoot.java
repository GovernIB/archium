package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamSetRoot;

public class SetClassificationRoot {
    private Request<ParamSetRoot> setClassificationRootRequest;

    public Request<ParamSetRoot> getSetClassificationRootRequest() {
        return setClassificationRootRequest;
    }

    public void setSetClassificationRootRequest(Request<ParamSetRoot> setClassificationRootRequest) {
        this.setClassificationRootRequest = setClassificationRootRequest;
    }

    @Override
    public String toString() {
        return "SetClassificationRoot{" +
                "setClassificationRootRequest=" + setClassificationRootRequest +
                '}';
    }
}

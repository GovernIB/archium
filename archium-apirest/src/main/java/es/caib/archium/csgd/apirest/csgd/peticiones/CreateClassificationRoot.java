package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamCreateClassificationRoot;

public class CreateClassificationRoot {
    private Request<ParamCreateClassificationRoot> createClassificationRootRequest;

    public Request<ParamCreateClassificationRoot> getCreateClassificationRootRequest() {
        return createClassificationRootRequest;
    }

    public void setCreateClassificationRootRequest(Request<ParamCreateClassificationRoot> createClassificationRootRequest) {
        this.createClassificationRootRequest = createClassificationRootRequest;
    }

    @Override
    public String toString() {
        return "CreateClassificationTable{" +
                "createClassificationRootRequest=" + createClassificationRootRequest +
                '}';
    }
}

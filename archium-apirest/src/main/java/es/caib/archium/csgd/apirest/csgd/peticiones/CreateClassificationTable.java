package es.caib.archium.csgd.apirest.csgd.peticiones;

import es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada.ParamCreateClassificationTable;

public class CreateClassificationTable {
    private Request<ParamCreateClassificationTable> createClassificationRootRequest;

    public Request<ParamCreateClassificationTable> getCreateClassificationRootRequest() {
        return createClassificationRootRequest;
    }

    public void setCreateClassificationRootRequest(Request<ParamCreateClassificationTable> createClassificationRootRequest) {
        this.createClassificationRootRequest = createClassificationRootRequest;
    }

    @Override
    public String toString() {
        return "CreateClassificationTable{" +
                "createClassificationRootRequest=" + createClassificationRootRequest +
                '}';
    }
}

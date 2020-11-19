package es.caib.csgd.apirest.csgd.peticiones;

import es.caib.csgd.apirest.csgd.entidades.parametrosllamada.ParamCreateClassificationTable;

public class CreateClassificationTable {
    private Request<ParamCreateClassificationTable> createClassificationRootRequest;

    public Request<ParamCreateClassificationTable> getCreateClassificationRootRequest() {
        return createClassificationRootRequest;
    }

    public void setCreateClassificationRootRequest(Request<ParamCreateClassificationTable> createClassificationRootRequest) {
        this.createClassificationRootRequest = createClassificationRootRequest;
    }
}

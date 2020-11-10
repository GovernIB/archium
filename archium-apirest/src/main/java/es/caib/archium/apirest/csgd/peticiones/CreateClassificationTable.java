package es.caib.archium.apirest.csgd.peticiones;

import es.caib.archium.apirest.csgd.entidades.parametrosllamada.ParamCreateClassificationTable;
import es.caib.archium.apirest.csgd.entidades.parametrosllamada.ParamCreateFunction;

public class CreateClassificationTable {
    private Request<ParamCreateClassificationTable> createClassificationTableRequest;

    public Request<ParamCreateClassificationTable> getCreateClassificationTableRequest() {
        return createClassificationTableRequest;
    }

    public void setCreateClassificationTableRequest(Request<ParamCreateClassificationTable> createClassificationTableRequest) {
        this.createClassificationTableRequest = createClassificationTableRequest;
    }
}

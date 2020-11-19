package es.caib.csgd.apirest.csgd.entidades.parametrosllamada;

import es.caib.csgd.apirest.csgd.entidades.comunes.FunctionNode;

public class ParamCreateFunction {
    private String parentId;
    private FunctionNode classificationFunction;

    public String getParentId() {
        return parentId;
    }

    public void setParentId(String parentId) {
        this.parentId = parentId;
    }

    public FunctionNode getClassificationFunction() {
        return classificationFunction;
    }

    public void setClassificationFunction(FunctionNode classificationFunction) {
        this.classificationFunction = classificationFunction;
    }
}

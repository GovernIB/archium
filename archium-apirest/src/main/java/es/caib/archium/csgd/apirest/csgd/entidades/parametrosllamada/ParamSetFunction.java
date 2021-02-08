package es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.FunctionNode;

public class ParamSetFunction {
    private FunctionNode classificationFunction;

    public FunctionNode getClassificationFunction() {
        return classificationFunction;
    }

    public void setClassificationFunction(FunctionNode classificationFunction) {
        this.classificationFunction = classificationFunction;
    }

    @Override
    public String toString() {
        return "ParamSetFunction{" +
                "classificationFunction=" + classificationFunction +
                '}';
    }
}

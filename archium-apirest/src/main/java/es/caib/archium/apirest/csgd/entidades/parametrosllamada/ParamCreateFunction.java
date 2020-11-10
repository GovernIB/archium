package es.caib.archium.apirest.csgd.entidades.parametrosllamada;

import es.caib.archium.apirest.csgd.entidades.comunes.FunctionNode;

public class ParamCreateFunction {
    private String parent;
    private FunctionNode function;

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public FunctionNode getFunction() {
        return function;
    }

    public void setFunction(FunctionNode function) {
        this.function = function;
    }
}

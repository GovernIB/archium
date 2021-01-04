package es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.FunctionNode;
import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RootNode;

public class ParamSetRoot {
    private RootNode root;

    public RootNode getRoot() {
        return root;
    }

    public void setRoot(RootNode root) {
        this.root = root;
    }

    @Override
    public String toString() {
        return "ParamSetRoot{" +
                "root=" + root +
                '}';
    }
}

package es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.RootNode;

public class ParamCreateClassificationRoot {
    private RootNode classificationRoot;

    public RootNode getClassificationRoot() {
        return classificationRoot;
    }

    public void setClassificationRoot(RootNode classificationRoot) {
        this.classificationRoot = classificationRoot;
    }

    @Override
    public String toString() {
        return "ParamCreateClassificationTable{" +
                "classificationRoot=" + classificationRoot +
                '}';
    }
}

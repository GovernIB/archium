package es.caib.archium.csgd.apirest.csgd.entidades.parametrosllamada;

import es.caib.archium.csgd.apirest.csgd.entidades.comunes.ClassificationTableNode;

public class ParamCreateClassificationTable {
    private ClassificationTableNode classificationRoot;

    public ClassificationTableNode getClassificationRoot() {
        return classificationRoot;
    }

    public void setClassificationRoot(ClassificationTableNode classificationRoot) {
        this.classificationRoot = classificationRoot;
    }

    @Override
    public String toString() {
        return "ParamCreateClassificationTable{" +
                "classificationRoot=" + classificationRoot +
                '}';
    }
}

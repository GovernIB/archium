package es.caib.csgd.apirest.csgd.entidades.parametrosllamada;

import es.caib.csgd.apirest.csgd.entidades.comunes.ClassificationTableNode;

public class ParamCreateClassificationTable {
    private ClassificationTableNode classificationRoot;

    public ClassificationTableNode getClassificationRoot() {
        return classificationRoot;
    }

    public void setClassificationRoot(ClassificationTableNode classificationRoot) {
        this.classificationRoot = classificationRoot;
    }
}

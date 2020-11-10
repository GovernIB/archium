package es.caib.archium.apirest.csgd.entidades.parametrosllamada;

import es.caib.archium.apirest.csgd.entidades.comunes.ClassificationTableNode;
import es.caib.archium.apirest.csgd.entidades.comunes.SerieNode;

public class ParamCreateClassificationTable {
    private ClassificationTableNode classificationTable;

    public ClassificationTableNode getClassificationTable() {
        return classificationTable;
    }

    public void setClassificationTable(ClassificationTableNode classificationTable) {
        this.classificationTable = classificationTable;
    }
}

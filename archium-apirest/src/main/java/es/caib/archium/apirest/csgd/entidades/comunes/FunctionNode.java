package es.caib.archium.apirest.csgd.entidades.comunes;

import es.caib.archium.apirest.constantes.Estado;

public class FunctionNode {

    private String classificationTableCode;
    private String code;
    private String parentFunction;
    private Estado state;

    public String getClassificationTableCode() {
        return classificationTableCode;
    }

    public void setClassificationTableCode(String classificationTableCode) {
        this.classificationTableCode = classificationTableCode;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getParentFunction() {
        return parentFunction;
    }

    public void setParentFunction(String parentFunction) {
        this.parentFunction = parentFunction;
    }

    public Estado getState() {
        return state;
    }

    public void setState(Estado state) {
        this.state = state;
    }
}
